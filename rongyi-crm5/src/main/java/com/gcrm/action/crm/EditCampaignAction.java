/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gcrm.action.crm;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.CampaignStatus;
import com.gcrm.domain.CampaignType;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Currency;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Target;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.mail.MailService;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Campaign
 * 
 */
public class EditCampaignAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Campaign> baseService;
    private IOptionService<CampaignType> campaignTypeService;
    private IOptionService<CampaignStatus> campaignStatusService;
    private IBaseService<Currency> currencyService;
    private IBaseService<User> userService;
    private MailService mailService;
    private IBaseService<EmailTemplate> emailTemplateService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private Campaign campaign;
    private List<CampaignType> types;
    private List<CampaignStatus> statuses;
    private List<Currency> currencies;
    private List<EmailTemplate> emailTemplates;
    private Integer statusID = null;
    private Integer typeID = null;
    private Integer currencyID = null;
    private Integer emailTemplateID = null;
    private String startDate = null;
    private String endDate = null;
    private String subject;
    boolean text_only;
    private String html_body;
    private String text_body;
    private String from;
    private String to;
    private File[] uploads;
    private String[] uploadFileNames;
    private String[] uploadContentTypes;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        Campaign originalCampaign = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalCampaign,
                campaign);
        campaign = getbaseService().makePersistent(campaign);
        this.setId(campaign.getId());
        this.setSaveFlag("true");
        if (changeLogs != null) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    batchInserChangeLogs(changeLogs);
                }
            });
        }
        return SUCCESS;
    }

    /**
     * Batch update change log
     * 
     * @param changeLogs
     *            change log collection
     */
    private void batchInserChangeLogs(Collection<ChangeLog> changeLogs) {
        this.getChangeLogService().batchUpdate(changeLogs);
    }

    /**
     * Creates change log
     * 
     * @param originalCampaign
     *            original campaign record
     * @param campaign
     *            current campaign record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Campaign originalCampaign,
            Campaign campaign) {
        Collection<ChangeLog> changeLogs = null;
        if (originalCampaign != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Campaign.class.getSimpleName();
            Integer recordID = campaign.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldName = CommonUtil.fromNullToEmpty(originalCampaign
                    .getName());
            String newName = CommonUtil.fromNullToEmpty(campaign.getName());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.name.label", oldName, newName, loginUser);

            String oldStatus = getOptionValue(originalCampaign.getStatus());
            String newStatus = getOptionValue(campaign.getStatus());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.status.label", oldStatus, newStatus, loginUser);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            String oldStartDateValue = "";
            Date oldStartDate = originalCampaign.getStart_date();
            if (oldStartDate != null) {
                oldStartDateValue = dateFormat.format(oldStartDate);
            }
            String newStartDateValue = "";
            Date newStartDate = campaign.getStart_date();
            if (newStartDate != null) {
                newStartDateValue = dateFormat.format(newStartDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.start_date.label", oldStartDateValue,
                    newStartDateValue, loginUser);

            String oldEndDateValue = "";
            Date oldEndDate = originalCampaign.getEnd_date();
            if (oldEndDate != null) {
                oldEndDateValue = dateFormat.format(oldEndDate);
            }
            String newEndDateValue = "";
            Date newEndDate = campaign.getEnd_date();
            if (newEndDate != null) {
                newEndDateValue = dateFormat.format(newEndDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.end_date.label", oldEndDateValue, newEndDateValue,
                    loginUser);

            String oldCampaignType = getOptionValue(originalCampaign.getType());
            String newCampaignType = getOptionValue(campaign.getType());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.type.label", oldCampaignType, newCampaignType,
                    loginUser);

            String oldCurrencyName = "";
            Currency oldCurrency = originalCampaign.getCurrency();
            if (oldCurrency != null) {
                oldCurrencyName = CommonUtil.fromNullToEmpty(oldCurrency
                        .getName());
            }
            String newCurrencyName = "";
            Currency newCurrency = campaign.getCurrency();
            if (newCurrency != null) {
                newCurrencyName = CommonUtil.fromNullToEmpty(newCurrency
                        .getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.currency.label", oldCurrencyName, newCurrencyName,
                    loginUser);

            String oldImpressions = String.valueOf(originalCampaign
                    .getImpressions());
            String newImpressions = String.valueOf(campaign.getImpressions());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.impressions.label", oldImpressions,
                    newImpressions, loginUser);

            String oldBudget = String.valueOf(originalCampaign.getBudget());
            String newBudget = String.valueOf(campaign.getBudget());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.budget.label", oldBudget, newBudget, loginUser);

            String oldExpectedCost = String.valueOf(originalCampaign
                    .getExpected_cost());
            String newExpectedCost = String
                    .valueOf(campaign.getExpected_cost());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.expected_cost.label", oldExpectedCost,
                    newExpectedCost, loginUser);

            String oldActualCost = String.valueOf(originalCampaign
                    .getActual_cost());
            String newActualCost = String.valueOf(campaign.getActual_cost());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.actual_cost.label", oldActualCost, newActualCost,
                    loginUser);

            String oldExpectedRevenue = String.valueOf(originalCampaign
                    .getExpected_revenue());
            String newExpectedRevenue = String.valueOf(campaign
                    .getExpected_revenue());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.expected_revenue.label", oldExpectedRevenue,
                    newExpectedRevenue, loginUser);

            String oldExpectedRespone = String.valueOf(originalCampaign
                    .getExpected_respone());
            String newExpectedRespone = String.valueOf(campaign
                    .getExpected_respone());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.expected_respone.label", oldExpectedRespone,
                    newExpectedRespone, loginUser);

            String oldObjective = CommonUtil.fromNullToEmpty(originalCampaign
                    .getObjective());
            String newObjective = CommonUtil.fromNullToEmpty(campaign
                    .getObjective());
            createChangeLog(changeLogs, entityName, recordID,
                    "campaign.objective.label", oldObjective, newObjective,
                    loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalCampaign
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(campaign.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalCampaign.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = campaign.getAssigned_to();
            if (newAssignedTo != null) {
                newAssignedToName = newAssignedTo.getName();
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.assigned_to.label",
                    CommonUtil.fromNullToEmpty(oldAssignedToName),
                    CommonUtil.fromNullToEmpty(newAssignedToName), loginUser);
        }
        return changeLogs;
    }

    /**
     * Sends invitation mail to all participants.
     * 
     * @return the SUCCESS result
     */
    public String sendInvites() throws Exception {

        UserUtil.permissionCheck("update_campaign");
        campaign = baseService.getEntityById(Campaign.class, campaign.getId());
        Date start_date = campaign.getStart_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        startDate = "";
        if (start_date != null) {
            startDate = dateFormat.format(start_date);
        }
        Date end_date = campaign.getEnd_date();
        endDate = "";
        if (end_date != null) {
            endDate = dateFormat.format(end_date);
        }
        this.setId(campaign.getId());
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session
                .get(AuthenticationSuccessListener.LOGIN_USER);
        from = loginUser.getEmail();
        if (from == null) {
            from = "";
        }
        StringBuilder targetEmails = new StringBuilder("");
        Set<TargetList> targetLists = campaign.getTargetLists();
        if (targetLists != null) {
            for (TargetList targetList : targetLists) {
                Set<Account> accounts = targetList.getAccounts();
                if (accounts != null) {
                    for (Account account : accounts) {
                        String email = account.getEmail();
                        if (CommonUtil.isNullOrEmpty(email)) {
                            continue;
                        }
                        if (targetEmails.length() > 0) {
                            targetEmails.append(",");
                        }
                        targetEmails.append(email);
                    }
                }
                Set<Lead> leads = targetList.getLeads();
                if (leads != null) {
                    for (Lead lead : leads) {
                        String email = lead.getEmail();
                        if (CommonUtil.isNullOrEmpty(email)) {
                            continue;
                        }
                        if (targetEmails.length() > 0) {
                            targetEmails.append(",");
                        }
                        targetEmails.append(email);
                    }
                }
                Set<Contact> contacts = targetList.getContacts();
                if (contacts != null) {
                    for (Contact contact : contacts) {
                        String email = contact.getOffice_email();
                        if (CommonUtil.isNullOrEmpty(email)) {
                            continue;
                        }
                        if (targetEmails.length() > 0) {
                            targetEmails.append(",");
                        }
                        targetEmails.append(email);
                    }
                }
                Set<User> users = targetList.getUsers();
                if (users != null) {
                    for (User user : users) {
                        String email = user.getEmail();
                        if (CommonUtil.isNullOrEmpty(email)
                                || (from != null && email.endsWith(from))) {
                            continue;
                        }
                        if (targetEmails.length() > 0) {
                            targetEmails.append(",");
                        }
                        targetEmails.append(email);
                    }
                }
                Set<Target> targets = targetList.getTargets();
                if (targets != null) {
                    for (Target target : targets) {
                        String email = target.getEmail();
                        if (CommonUtil.isNullOrEmpty(email)) {
                            continue;
                        }
                        if (targetEmails.length() > 0) {
                            targetEmails.append(",");
                        }
                        targetEmails.append(email);
                    }
                }
            }
        }

        if (targetEmails.length() > 0) {
            to = targetEmails.toString();
        }

        // Gets email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'campaignInvite' order by created_on";
        emailTemplates = emailTemplateService.findByHQL(hql);
        return SUCCESS;
    }

    /**
     * Selects the email template
     * 
     * @return the SUCCESS result
     */
    public String selectTemplate() throws Exception {
        UserUtil.permissionCheck("update_campaign");
        campaign = baseService.getEntityById(Campaign.class, this.getId());
        Date start_date = campaign.getStart_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        startDate = "";
        if (start_date != null) {
            startDate = dateFormat.format(start_date);
        }
        Date end_date = campaign.getEnd_date();
        endDate = "";
        if (end_date != null) {
            endDate = dateFormat.format(end_date);
        }

        EmailTemplate emailTemplte = emailTemplateService.getEntityById(
                EmailTemplate.class, emailTemplateID);
        this.setText_only(emailTemplte.isText_only());
        this.setSubject(CommonUtil.fromNullToEmpty(emailTemplte.getSubject()));
        String content = "";
        if (this.isText_only()) {
            content = emailTemplte.getText_body();
        } else {
            content = emailTemplte.getHtml_body();
        }
        // Replaces the variable in the body
        if (content != null) {
            content = content.replaceAll("\\$campaign.start_date", startDate);
            content = content.replaceAll("\\$campaign.end_date", endDate);
        }
        if (this.isText_only()) {
            this.setText_body(content);
        } else {
            this.setHtml_body(content);
        }
        // Gets email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'campaignInvite' order by created_on";
        emailTemplates = emailTemplateService.findByHQL(hql);
        return SUCCESS;
    }

    /**
     * Sends invitation mail to all participants.
     * 
     * @return the SUCCESS result
     */
    public String send() throws Exception {

        UserUtil.permissionCheck("update_campaign");
        String content = "";
        if (to != null && to.trim().length() > 0) {
            String[] tos = to.split(",");
            if (this.isText_only()) {
                content = this.getText_body();
            } else {
                content = this.getHtml_body();
            }

            // Gets attachments
            String realPath = ServletActionContext.getRequest().getSession()
                    .getServletContext().getRealPath("/upload");
            String targetDirectory = realPath;
            String[] tNames = new String[uploads.length];
            File[] tFiles = new File[uploads.length];
            for (int i = 0; i < uploads.length; i++) {
                tNames[i] = generateFileName(uploadFileNames[i]);
                File target = new File(targetDirectory, tNames[i]);
                FileUtils.copyFile(uploads[i], target);
                tFiles[i] = target;
            }

            mailService.asynSendHtmlMail(from, tos, subject, content,
                    this.getUploadFileName(), tFiles);
        }
        return SUCCESS;
    }

    /**
     * Generates file name for upload file automatically to invoid duplicate
     * file names
     * 
     * @param fileName
     *            original file names
     * @return generated file name
     */
    private String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            campaign = baseService.getEntityById(Campaign.class, this.getId());
            CampaignStatus status = campaign.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            CampaignType type = campaign.getType();
            if (type != null) {
                typeID = type.getId();
            }
            Currency currency = campaign.getCurrency();
            if (currency != null) {
                currencyID = currency.getId();
            }
            User assignedTo = campaign.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            Date start_date = campaign.getStart_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            if (start_date != null) {
                startDate = dateFormat.format(start_date);
            }
            Date end_date = campaign.getEnd_date();
            if (end_date != null) {
                endDate = dateFormat.format(end_date);
            }
            this.getBaseInfo(campaign, Campaign.class.getSimpleName(),
                    Constant.CRM_NAMESPACE);
        } else {
            this.initBaseInfo();
        }
        return SUCCESS;
    }

    /**
     * Mass update entity record information
     */
    public String massUpdate() throws Exception {
        saveEntity();
        String[] fieldNames = this.massUpdate;
        if (fieldNames != null) {
            String[] selectIDArray = this.seleteIDs.split(",");
            Collection<Campaign> campaigns = new ArrayList<Campaign>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Campaign campaignInstance = this.baseService.getEntityById(
                        Campaign.class, id);
                Campaign originalCampaign = campaignInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(campaign, fieldName);
                    BeanUtil.setFieldValue(campaignInstance, fieldName, value);
                }
                campaignInstance.setUpdated_by(user);
                campaignInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalCampaign,
                        campaignInstance);
                allChangeLogs.addAll(changeLogs);
                campaigns.add(campaignInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (campaigns.size() > 0) {
                this.baseService.batchUpdate(campaigns);
                taskExecutor.execute(new Runnable() {
                    public void run() {
                        batchInserChangeLogs(changeLogsForSave);
                    }
                });
            }
        }
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @return original campaign record
     * @throws ParseException
     */
    private Campaign saveEntity() throws Exception {
        Campaign originalCampaign = null;
        if (campaign.getId() == null) {
            UserUtil.permissionCheck("create_campaign");
        } else {
            UserUtil.permissionCheck("update_campaign");
            originalCampaign = baseService.getEntityById(Campaign.class,
                    campaign.getId());
            campaign.setTargetLists(originalCampaign.getTargetLists());
            campaign.setCreated_on(originalCampaign.getCreated_on());
            campaign.setCreated_by(originalCampaign.getCreated_by());
        }
        CampaignStatus status = null;
        if (statusID != null) {
            status = campaignStatusService.getEntityById(CampaignStatus.class,
                    statusID);
        }
        campaign.setStatus(status);
        CampaignType type = null;
        if (typeID != null) {
            type = campaignTypeService
                    .getEntityById(CampaignType.class, typeID);
        }
        campaign.setType(type);
        Currency currency = null;
        if (currencyID != null) {
            currency = currencyService
                    .getEntityById(Currency.class, currencyID);
        }
        campaign.setCurrency(currency);
        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        campaign.setAssigned_to(assignedTo);
        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        campaign.setOwner(owner);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_EDIT_FORMAT);
        Date start_date = null;
        if (!CommonUtil.isNullOrEmpty(startDate)) {
            start_date = dateFormat.parse(startDate);
        }
        campaign.setStart_date(start_date);
        Date end_date = null;
        if (!CommonUtil.isNullOrEmpty(endDate)) {
            end_date = dateFormat.parse(endDate);
        }
        campaign.setEnd_date(end_date);
        super.updateBaseInfo(campaign);
        return originalCampaign;
    }

    /**
     * Gets Campaign Relation Counts
     * 
     * @return null
     */
    public String getCampaignRelationCounts() throws Exception {
        long targetListNumber = this.baseService
                .countsByParams(
                        "select count(*) from Campaign campaign join campaign.targetLists where campaign.id = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"targetListNumber\":\"").append(targetListNumber)
                .append("\"}");
        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
        return null;
    }

    /**
     * Prepares the list
     * 
     */
    public void prepare() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        this.statuses = campaignStatusService.getOptions(
                CampaignStatus.class.getSimpleName(), local);
        this.types = campaignTypeService.getOptions(
                CampaignType.class.getSimpleName(), local);
        this.currencies = currencyService.getAllObjects(Currency.class
                .getSimpleName());
    }

    public IBaseService<Campaign> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Campaign> baseService) {
        this.baseService = baseService;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    /**
     * @return the types
     */
    public List<CampaignType> getTypes() {
        return types;
    }

    /**
     * @return the statuses
     */
    public List<CampaignStatus> getStatuses() {
        return statuses;
    }

    /**
     * @return the statusID
     */
    public Integer getStatusID() {
        return statusID;
    }

    /**
     * @param statusID
     *            the statusID to set
     */
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    /**
     * @return the typeID
     */
    public Integer getTypeID() {
        return typeID;
    }

    /**
     * @param typeID
     *            the typeID to set
     */
    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    /**
     * @return the currencyService
     */
    public IBaseService<Currency> getCurrencyService() {
        return currencyService;
    }

    /**
     * @param currencyService
     *            the currencyService to set
     */
    public void setCurrencyService(IBaseService<Currency> currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * @return the currencies
     */
    public List<Currency> getCurrencies() {
        return currencies;
    }

    /**
     * @param currencies
     *            the currencies to set
     */
    public void setCurrencies() {
        this.currencies = currencyService.getAllObjects(Currency.class
                .getSimpleName());
    }

    /**
     * @return the currencyID
     */
    public Integer getCurrencyID() {
        return currencyID;
    }

    /**
     * @param currencyID
     *            the currencyID to set
     */
    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    /**
     * @return the userService
     */
    public IBaseService<User> getUserService() {
        return userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @param types
     *            the types to set
     */
    public void setTypes(List<CampaignType> types) {
        this.types = types;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<CampaignStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * @param currencies
     *            the currencies to set
     */
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    /**
     * @return the campaignStatusService
     */
    public IOptionService<CampaignStatus> getCampaignStatusService() {
        return campaignStatusService;
    }

    /**
     * @param campaignStatusService
     *            the campaignStatusService to set
     */
    public void setCampaignStatusService(
            IOptionService<CampaignStatus> campaignStatusService) {
        this.campaignStatusService = campaignStatusService;
    }

    /**
     * @return the campaignTypeService
     */
    public IOptionService<CampaignType> getCampaignTypeService() {
        return campaignTypeService;
    }

    /**
     * @param campaignTypeService
     *            the campaignTypeService to set
     */
    public void setCampaignTypeService(
            IOptionService<CampaignType> campaignTypeService) {
        this.campaignTypeService = campaignTypeService;
    }

    /**
     * @return the mailService
     */
    public MailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the text_only
     */
    public boolean isText_only() {
        return text_only;
    }

    /**
     * @param text_only
     *            the text_only to set
     */
    public void setText_only(boolean text_only) {
        this.text_only = text_only;
    }

    /**
     * @return the html_body
     */
    public String getHtml_body() {
        return html_body;
    }

    /**
     * @param html_body
     *            the html_body to set
     */
    public void setHtml_body(String html_body) {
        this.html_body = html_body;
    }

    /**
     * @return the text_body
     */
    public String getText_body() {
        return text_body;
    }

    /**
     * @param text_body
     *            the text_body to set
     */
    public void setText_body(String text_body) {
        this.text_body = text_body;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the emailTemplateService
     */
    public IBaseService<EmailTemplate> getEmailTemplateService() {
        return emailTemplateService;
    }

    /**
     * @param emailTemplateService
     *            the emailTemplateService to set
     */
    public void setEmailTemplateService(
            IBaseService<EmailTemplate> emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    /**
     * @return the emailTemplates
     */
    public List<EmailTemplate> getEmailTemplates() {
        return emailTemplates;
    }

    /**
     * @param emailTemplates
     *            the emailTemplates to set
     */
    public void setEmailTemplates(List<EmailTemplate> emailTemplates) {
        this.emailTemplates = emailTemplates;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the emailTemplateID
     */
    public Integer getEmailTemplateID() {
        return emailTemplateID;
    }

    /**
     * @param emailTemplateID
     *            the emailTemplateID to set
     */
    public void setEmailTemplateID(Integer emailTemplateID) {
        this.emailTemplateID = emailTemplateID;
    }

    /**
     * @return the baseService
     */
    public IBaseService<Campaign> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Campaign> baseService) {
        this.baseService = baseService;
    }

    public File[] getUpload() {
        return this.uploads;
    }

    public void setUpload(File[] upload) {
        this.uploads = upload;
    }

    public String[] getUploadFileName() {
        return this.uploadFileNames;
    }

    public void setUploadFileName(String[] uploadFileName) {
        this.uploadFileNames = uploadFileName;
    }

    public String[] getUploadContentType() {
        return this.uploadContentTypes;
    }

    public void setUploadContentType(String[] uploadContentType) {
        this.uploadContentTypes = uploadContentType;
    }

    /**
     * @return the changeLogService
     */
    public IBaseService<ChangeLog> getChangeLogService() {
        return changeLogService;
    }

    /**
     * @param changeLogService
     *            the changeLogService to set
     */
    public void setChangeLogService(IBaseService<ChangeLog> changeLogService) {
        this.changeLogService = changeLogService;
    }

    /**
     * @return the taskExecutor
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    /**
     * @param taskExecutor
     *            the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

}

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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Campaign;
import com.gcrm.domain.CampaignStatus;
import com.gcrm.domain.CampaignType;
import com.gcrm.domain.Currency;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Campaign
 * 
 */
public class ListCampaignAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Campaign> baseService;
    private IBaseService<CampaignType> campaignTypeService;
    private IBaseService<CampaignStatus> campaignStatusService;
    private IBaseService<Currency> currencyService;
    private IBaseService<User> userService;
    private Campaign campaign;

    private static final String CLAZZ = Campaign.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Campaign> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Campaign> campaigns = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(campaigns, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_campaign");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("start_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("end_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_campaign(), loginUser);
        SearchResult<Campaign> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Campaign> campaigns = result.getResult().iterator();

        long totalRecords = result.getTotalRecords();
        getListJson(campaigns, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Campaign> campaigns,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String statusName = null;
        String typeName = null;
        String assignedTo = null;
        while (campaigns.hasNext()) {
            Campaign instance = campaigns.next();
            int id = instance.getId();
            String name = CommonUtil.fromNullToEmpty(instance.getName());
            CampaignStatus status = instance.getStatus();
            statusName = CommonUtil.getOptionLabel(status);
            CampaignType type = instance.getType();
            typeName = CommonUtil.getOptionLabel(type);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_FORMAT);
            String startDateString = "";
            Date startDate = instance.getStart_date();
            if (startDate != null) {
                startDateString = dateFormat.format(startDate);
            }
            String endDateString = "";
            Date endDate = instance.getEnd_date();
            if (endDate != null) {
                endDateString = dateFormat.format(endDate);
            }
            User user = instance.getAssigned_to();
            if (user != null) {
                assignedTo = CommonUtil.fromNullToEmpty(user.getName());
            } else {
                assignedTo = "";
            }
            if (isList) {
                User createdBy = instance.getCreated_by();
                String createdByName = "";
                if (createdBy != null) {
                    createdByName = CommonUtil.fromNullToEmpty(createdBy
                            .getName());
                }
                User updatedBy = instance.getUpdated_by();
                String updatedByName = "";
                if (updatedBy != null) {
                    updatedByName = CommonUtil.fromNullToEmpty(updatedBy
                            .getName());
                }
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
                        Constant.DATE_TIME_FORMAT);
                Date createdOn = instance.getCreated_on();
                String createdOnString = "";
                if (createdOn != null) {
                    createdOnString = dateTimeFormat.format(createdOn);
                }
                Date updatedOn = instance.getUpdated_on();
                String updatedOnString = "";
                if (updatedOn != null) {
                    updatedOnString = dateTimeFormat.format(updatedOn);
                }

                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
                        .append(name).append("\",\"").append(statusName)
                        .append("\",\"").append(typeName).append("\",\"")
                        .append(startDateString).append("\",\"")
                        .append(endDateString).append("\",\"")
                        .append(assignedTo).append("\",\"")
                        .append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnString).append("\",\"")
                        .append(updatedOnString).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"name\":\"").append(name)
                        .append("\",\"status.name\":\"").append(statusName)
                        .append("\",\"type.name\":\"").append(typeName)
                        .append("\",\"start_date\":\"").append(startDateString)
                        .append("\",\"end_date\":\"").append(endDateString)
                        .append("\",\"assigned_to.name\":\"")
                        .append(assignedTo).append("\"}");

            }
            if (campaigns.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }

    /**
     * Gets the related accounts.
     * 
     * @return null
     */
    public String relateCampaignTargetList() throws Exception {
        campaign = baseService.getEntityById(Campaign.class, id);
        Set<TargetList> targetLists = campaign.getTargetLists();
        Iterator<TargetList> targetListIterator = targetLists.iterator();
        long totalRecords = targetLists.size();
        ListTargetListAction.getListJson(targetListIterator, totalRecords,
                null, false);
        return null;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_campaign");
        baseService.batchDeleteEntity(Campaign.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_campaign");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Campaign oriRecord = baseService.getEntityById(Campaign.class,
                        Integer.valueOf(copyid));
                Campaign targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.getbaseService().makePersistent(targetRecord);
            }
        }
        return SUCCESS;
    }

    /**
     * Exports the entities
     * 
     * @return the exported entities inputStream
     */
    public InputStream getInputStream() throws Exception {
        return getDownloadContent(false);
    }

    /**
     * Exports the template
     * 
     * @return the exported template inputStream
     */
    public InputStream getTemplateStream() throws Exception {
        return getDownloadContent(true);
    }

    private InputStream getDownloadContent(boolean isTemplate) throws Exception {
        UserUtil.permissionCheck("view_campaign");
        String fileName = getText("entity.campaign.label") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] {
                    getText("entity.id.label"),
                    getText("entity.name.label"),
                    getText("entity.status_id.label"),
                    getText("entity.status_name.label"),
                    getText("entity.type_id.label"),
                    getText("entity.type_name.label"),
                    getText("entity.start_date.label"),
                    getText("entity.end_date.label"),
                    getText("entity.currency_id.label"),
                    getText("entity.currency_name.label"),
                    getText("campaign.impressions.label"),
                    getText("campaign.budget.label"),
                    getText("campaign.expected_cost.label"),
                    getText("campaign.actual_cost.label"),
                    getText("campaign.expected_revenue.label"),
                    getText("campaign.expected_respone.label"),
                    getText("campaign.objective.label"),
                    getText("entity.notes.label"),
                    getText("entity.assigned_to.label") + " "
                            + getText("entity.id.label"),
                    getText("entity.assigned_to.label") + " "
                            + getText("entity.name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    Campaign campaign = baseService.getEntityById(
                            Campaign.class, Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], campaign.getId());
                    data1.put(header[1],
                            CommonUtil.fromNullToEmpty(campaign.getName()));
                    CampaignStatus campaignStatus = campaign.getStatus();
                    if (campaignStatus != null) {
                        data1.put(header[2], campaignStatus.getId());
                    } else {
                        data1.put(header[2], "");
                    }
                    data1.put(header[3],
                            CommonUtil.getOptionLabel(campaignStatus));
                    CampaignType campaignType = campaign.getType();
                    if (campaignType != null) {
                        data1.put(header[4], campaignType.getId());
                    } else {
                        data1.put(header[4], "");
                    }
                    data1.put(header[5],
                            CommonUtil.getOptionLabel(campaignType));
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_EDIT_FORMAT);
                    Date startDate = campaign.getStart_date();
                    if (startDate != null) {
                        data1.put(header[6], dateFormat.format(startDate));
                    } else {
                        data1.put(header[6], "");
                    }
                    Date endDate = campaign.getEnd_date();
                    if (endDate != null) {
                        data1.put(header[7], dateFormat.format(endDate));
                    } else {
                        data1.put(header[7], "");
                    }
                    if (campaign.getCurrency() != null) {
                        data1.put(header[8], campaign.getCurrency().getId());
                        data1.put(header[9], campaign.getCurrency().getName());
                    } else {
                        data1.put(header[8], "");
                        data1.put(header[9], "");
                    }
                    data1.put(header[10], campaign.getImpressions());
                    data1.put(header[11], campaign.getBudget());
                    data1.put(header[12], campaign.getExpected_cost());
                    data1.put(header[13], campaign.getActual_cost());
                    data1.put(header[14], campaign.getExpected_revenue());
                    data1.put(header[15], campaign.getExpected_respone());
                    data1.put(header[16],
                            CommonUtil.fromNullToEmpty(campaign.getObjective()));
                    data1.put(header[17],
                            CommonUtil.fromNullToEmpty(campaign.getNotes()));
                    if (campaign.getAssigned_to() != null) {
                        data1.put(header[18], campaign.getAssigned_to().getId());
                        data1.put(header[19], campaign.getAssigned_to()
                                .getName());
                    } else {
                        data1.put(header[18], "");
                        data1.put(header[19], "");
                    }
                    writer.write(data1, header);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            writer.close();
        }

        InputStream in = new FileInputStream(file);
        this.setFileName(fileName);
        return in;
    }

    /**
     * Imports the entities
     * 
     * @return the SUCCESS result
     */
    public String importCSV() throws Exception {
        File file = this.getUpload();
        CsvListReader reader = new CsvListReader(new FileReader(file),
                CsvPreference.EXCEL_PREFERENCE);
        int failedNum = 0;
        int successfulNum = 0;
        try {
            final String[] header = reader.getCSVHeader(true);

            List<String> line = new ArrayList<String>();
            Map<String, String> failedMsg = new HashMap<String, String>();
            while ((line = reader.read()) != null) {

                Map<String, String> row = new HashMap<String, String>();
                for (int i = 0; i < line.size(); i++) {
                    row.put(header[i], line.get(i));
                }

                Campaign campaign = new Campaign();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        campaign.setId(Integer.parseInt(id));
                        UserUtil.permissionCheck("update_campaign");
                    } else {
                        UserUtil.permissionCheck("create_campaign");
                    }
                    campaign.setName(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.name.label"))));
                    String statusID = row
                            .get(getText("entity.status_id.label"));
                    if (CommonUtil.isNullOrEmpty(statusID)) {
                        campaign.setStatus(null);
                    } else {
                        CampaignStatus status = campaignStatusService
                                .getEntityById(CampaignStatus.class,
                                        Integer.parseInt(statusID));
                        campaign.setStatus(status);
                    }
                    String typeID = row.get(getText("entity.type_id.label"));
                    if (CommonUtil.isNullOrEmpty(typeID)) {
                        campaign.setType(null);
                    } else {
                        CampaignType type = campaignTypeService.getEntityById(
                                CampaignType.class, Integer.parseInt(typeID));
                        campaign.setType(type);
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_EDIT_FORMAT);
                    String startDateS = row
                            .get(getText("entity.start_date.label"));
                    if (startDateS != null) {
                        Date startDate = dateFormat.parse(startDateS);
                        campaign.setStart_date(startDate);
                    } else {
                        campaign.setStart_date(null);
                    }
                    String endDateS = row.get(getText("entity.end_date.label"));
                    if (startDateS != null) {
                        Date endDate = dateFormat.parse(endDateS);
                        campaign.setEnd_date(endDate);
                    } else {
                        campaign.setEnd_date(null);
                    }
                    String currencyID = row
                            .get(getText("entity.currency_id.label"));
                    if (CommonUtil.isNullOrEmpty(currencyID)) {
                        campaign.setCurrency(null);
                    } else {
                        Currency currency = currencyService.getEntityById(
                                Currency.class, Integer.parseInt(currencyID));
                        campaign.setCurrency(currency);
                    }
                    String impressions = row
                            .get(getText("campaign.impressions.label"));
                    if (CommonUtil.isNullOrEmpty(impressions)) {
                        campaign.setImpressions(0);
                    } else {
                        campaign.setImpressions(Double.parseDouble(impressions));
                    }
                    String budget = row.get(getText("campaign.budget.label"));
                    if (CommonUtil.isNullOrEmpty(budget)) {
                        campaign.setBudget(0);
                    } else {
                        campaign.setBudget(Double.parseDouble(budget));
                    }
                    String expectedCost = row
                            .get(getText("campaign.expected_cost.label"));
                    if (CommonUtil.isNullOrEmpty(expectedCost)) {
                        campaign.setExpected_cost(0);
                    } else {
                        campaign.setExpected_cost(Double
                                .parseDouble(expectedCost));
                    }
                    String actualCost = row
                            .get(getText("campaign.actual_cost.label "));
                    if (CommonUtil.isNullOrEmpty(actualCost)) {
                        campaign.setActual_cost(0);
                    } else {
                        campaign.setActual_cost(Double.parseDouble(actualCost));
                    }
                    String expectedRevenue = row
                            .get(getText("campaign.expected_revenue.label"));
                    if (CommonUtil.isNullOrEmpty(expectedRevenue)) {
                        campaign.setExpected_revenue(0);
                    } else {
                        campaign.setExpected_revenue(Double
                                .parseDouble(expectedRevenue));
                    }
                    String expectedRespone = row
                            .get(getText("campaign.expected_respone.label"));
                    if (CommonUtil.isNullOrEmpty(expectedRespone)) {
                        campaign.setExpected_respone(0);
                    } else {
                        campaign.setExpected_respone(Double
                                .parseDouble(expectedRespone));
                    }

                    campaign.setObjective(CommonUtil.fromNullToEmpty(row
                            .get(getText("campaign.objective.label"))));
                    campaign.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        campaign.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        campaign.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(campaign);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(campaign.getName(), e.getMessage());
                }

            }

            this.setFailedMsg(failedMsg);
            this.setFailedNum(failedNum);
            this.setSuccessfulNum(successfulNum);
            this.setTotalNum(successfulNum + failedNum);
        } finally {
            reader.close();
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return Campaign.class.getSimpleName();
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
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the campaignTypeService
     */
    public IBaseService<CampaignType> getCampaignTypeService() {
        return campaignTypeService;
    }

    /**
     * @param campaignTypeService
     *            the campaignTypeService to set
     */
    public void setCampaignTypeService(
            IBaseService<CampaignType> campaignTypeService) {
        this.campaignTypeService = campaignTypeService;
    }

    /**
     * @return the campaignStatusService
     */
    public IBaseService<CampaignStatus> getCampaignStatusService() {
        return campaignStatusService;
    }

    /**
     * @param campaignStatusService
     *            the campaignStatusService to set
     */
    public void setCampaignStatusService(
            IBaseService<CampaignStatus> campaignStatusService) {
        this.campaignStatusService = campaignStatusService;
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

}

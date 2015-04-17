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

import com.gcrm.action.system.ListUserAction;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.User;
import com.gcrm.domain.Visit;
import com.gcrm.domain.VisitStatus;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Visit
 * 
 */
public class ListVisitAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Visit> baseService;
    private IBaseService<VisitStatus> visitStatusService;
    private IBaseService<ReminderOption> reminderOptionService;
    private IBaseService<User> userService;
    private Visit visit;

    private static final String CLAZZ = Visit.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Visit> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Visit> visits = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(visits, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_visit");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("start_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("end_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_visit(), loginUser);
        SearchResult<Visit> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Visit> visits = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(visits, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Visit> visits,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String statusName = null;
        String assignedTo = null;
        while (visits.hasNext()) {
        	Visit instance = visits.next();
            int id = instance.getId();
            String subject = CommonUtil.fromNullToEmpty(instance.getSubject());
            VisitStatus status = instance.getStatus();
            statusName = CommonUtil.getOptionLabel(status);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
                    Constant.DATE_TIME_FORMAT);
            Date startDate = instance.getStart_date();
            String startDateString = "";
            if (startDate != null) {
                startDateString = dateTimeFormat.format(startDate);
            }
            Date endDate = instance.getEnd_date();
            String endDateString = "";
            if (endDate != null) {
                endDateString = dateTimeFormat.format(endDate);
            }
            String location = instance.getLocation();

            if (isList) {
                User user = instance.getAssigned_to();
                if (user != null) {
                    assignedTo = CommonUtil.fromNullToEmpty(user.getName());
                } else {
                    assignedTo = "";
                }
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
                Date createdOn = instance.getCreated_on();
                String createdOnName = "";
                if (createdOn != null) {
                    createdOnName = dateTimeFormat.format(createdOn);
                }
                Date updatedOn = instance.getUpdated_on();
                String updatedOnName = "";
                if (updatedOn != null) {
                    updatedOnName = dateTimeFormat.format(updatedOn);
                }

                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
                        .append(subject).append("\",\"").append(statusName)
                        .append("\",\"").append(startDateString)
                        .append("\",\"").append(endDateString).append("\",\"")
                        .append(location).append("\",\"").append(assignedTo)
                        .append("\",\"").append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"subject\":\"").append(subject)
                        .append("\",\"status.name\":\"").append(statusName)
                        .append("\",\"start_date\":\"").append(startDate)
                        .append("\",\"end_date\":\"").append(endDate)
                        .append("\",\"location\":\"").append(location)
                        .append("\"}");
            }
            if (visits.hasNext()) {
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
     * Gets the related leads
     * 
     * @return null
     */
    public String relateVisitLead() throws Exception {
    	visit = baseService.getEntityById(Visit.class, id);
        Set<Lead> leads = visit.getLeads();
        Iterator<Lead> leadIterator = leads.iterator();
        long totalRecords = leads.size();
        ListLeadAction.getListJson(leadIterator, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the related contacts
     * 
     * @return null
     */
    public String relateVisitContact() throws Exception {
        visit = baseService.getEntityById(Visit.class, id);
        Set<Contact> contacts = visit.getContacts();
        Iterator<Contact> contactIterator = contacts.iterator();
        long totalRecords = contacts.size();
        ListContactAction.getListJson(contactIterator, totalRecords, null,
                false);
        return null;
    }

    /**
     * Gets the related users
     * 
     * @return null
     */
    public String relateVisitUser() throws Exception {
        visit = baseService.getEntityById(Visit.class, id);
        Set<User> users = visit.getUsers();
        Iterator<User> userIterator = users.iterator();
        int totalRecords = users.size();
        ListUserAction.getListJson(userIterator, totalRecords, null, false);
        return null;
    }

    @Override
    protected String getEntityName() {
        return Visit.class.getSimpleName();
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_visit");
        baseService.batchDeleteEntity(Visit.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_visit");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Visit oriRecord = baseService.getEntityById(Visit.class,
                        Integer.valueOf(copyid));
                Visit targetRecord = oriRecord.clone();
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
        UserUtil.permissionCheck("view_visit");
        String fileName = getText("entity.visit.label") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { getText("entity.id.label"),
                    getText("entity.subject.label"),
                    getText("entity.status_id.label"),
                    getText("entity.status_name.label"),
                    getText("entity.start_date.label"),
                    getText("entity.end_date.label"),
                    getText("entity.related_object.label"),
                    getText("entity.related_record_id.label"),
                    getText("visit.location.label"),
                    getText("entity.reminder_email.label"),
                    getText("entity.reminder_option_email_id.label"),
                    getText("entity.reminder_option_email_name.label"),
                    getText("entity.notes.label"),
                    getText("entity.assigned_to_id.label"),
                    getText("entity.assigned_to_name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    Visit visit = baseService.getEntityById(Visit.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], visit.getId());
                    data1.put(header[1],
                            CommonUtil.fromNullToEmpty(visit.getSubject()));
                    VisitStatus visitStatus = visit.getStatus();
                    if (visitStatus != null) {
                        data1.put(header[2], visitStatus.getId());
                    } else {
                        data1.put(header[2], "");
                    }
                    data1.put(header[3],
                            CommonUtil.getOptionLabel(visitStatus));
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_TIME_FORMAT);
                    Date startDate = visit.getStart_date();
                    if (startDate != null) {
                        data1.put(header[4], dateFormat.format(startDate));
                    } else {
                        data1.put(header[4], "");
                    }
                    Date endDate = visit.getEnd_date();
                    if (endDate != null) {
                        data1.put(header[5], dateFormat.format(endDate));
                    } else {
                        data1.put(header[5], "");
                    }
                    data1.put(header[6], CommonUtil.fromNullToEmpty(visit
                            .getRelated_object()));
                    if (visit.getRelated_record() == null) {
                        data1.put(header[7], "");
                    } else {
                        data1.put(header[7],
                                String.valueOf(visit.getRelated_record()));
                    }
                    data1.put(header[8],
                            CommonUtil.fromNullToEmpty(visit.getLocation()));
                    data1.put(header[9], visit.isReminder_email());
                    ReminderOption reminderOptionEmail = visit
                            .getReminder_option_email();
                    if (reminderOptionEmail != null) {
                        data1.put(header[10], reminderOptionEmail.getId());
                    } else {
                        data1.put(header[10], "");
                    }
                    data1.put(header[11],
                            CommonUtil.getOptionLabel(reminderOptionEmail));
                    data1.put(header[12],
                            CommonUtil.fromNullToEmpty(visit.getNotes()));
                    if (visit.getAssigned_to() != null) {
                        data1.put(header[13], visit.getAssigned_to().getId());
                        data1.put(header[14], visit.getAssigned_to()
                                .getName());
                    } else {
                        data1.put(header[13], "");
                        data1.put(header[14], "");
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
                Visit visit = new Visit();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        visit.setId(Integer.parseInt(id));
                    }
                    visit.setSubject(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.subject.label"))));
                    String statusID = row
                            .get(getText("entity.status_id.label"));
                    if (CommonUtil.isNullOrEmpty(statusID)) {
                        visit.setStatus(null);
                    } else {
                        VisitStatus status = visitStatusService
                                .getEntityById(VisitStatus.class,
                                        Integer.parseInt(statusID));
                        visit.setStatus(status);
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_TIME_FORMAT);
                    String startDateS = row
                            .get(getText("entity.start_date.label"));
                    if (startDateS != null) {
                        Date startDate = dateFormat.parse(startDateS);
                        visit.setStart_date(startDate);
                    } else {
                        visit.setStart_date(null);
                    }
                    String endDateS = row.get(getText("entity.end_date.label"));
                    if (endDateS != null) {
                        Date endDate = dateFormat.parse(endDateS);
                        visit.setEnd_date(endDate);
                    } else {
                        visit.setEnd_date(null);
                    }
                    visit.setRelated_object(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.related_object.label"))));
                    String relatedRecord = row
                            .get(getText("entity.related_record_id.label"));
                    if (CommonUtil.isNullOrEmpty(relatedRecord)) {
                        visit.setRelated_record(0);
                    } else {
                        visit.setRelated_record(Integer
                                .parseInt(relatedRecord));
                    }
                    visit.setLocation(CommonUtil.fromNullToEmpty(row
                            .get(getText("visit.location.label"))));
                    String reminderWayEmail = row
                            .get(getText("entity.reminder_email.label"));
                    if (CommonUtil.isNullOrEmpty(reminderWayEmail)) {
                        visit.setReminder_email(false);
                    } else {
                        visit.setReminder_email(Boolean
                                .parseBoolean(reminderWayEmail));
                    }
                    String reminderOptionEmailID = row
                            .get(getText("entity.reminder_option_email_id.label"));
                    if (CommonUtil.isNullOrEmpty(reminderOptionEmailID)) {
                    	visit.setReminder_option_email(null);
                    } else {
                        ReminderOption reminderOption = reminderOptionService
                                .getEntityById(ReminderOption.class,
                                        Integer.parseInt(reminderOptionEmailID));
                        visit.setReminder_option_email(reminderOption);
                    }
                    visit.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                    	visit.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        visit.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(visit);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(visit.getSubject(), e.getMessage());
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

    public IBaseService<Visit> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Visit> baseService) {
        this.baseService = baseService;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
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
     * @return the visitStatusService
     */
    public IBaseService<VisitStatus> getVisitStatusService() {
        return visitStatusService;
    }

    /**
     * @param visitStatusService
     *            the visitStatusService to set
     */
    public void setVisitStatusService(
            IBaseService<VisitStatus> visitStatusService) {
        this.visitStatusService = visitStatusService;
    }

    /**
     * @return the reminderOptionService
     */
    public IBaseService<ReminderOption> getReminderOptionService() {
        return reminderOptionService;
    }

    /**
     * @param reminderOptionService
     *            the reminderOptionService to set
     */
    public void setReminderOptionService(
            IBaseService<ReminderOption> reminderOptionService) {
        this.reminderOptionService = reminderOptionService;
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

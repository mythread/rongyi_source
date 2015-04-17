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
import com.gcrm.domain.Meeting;
import com.gcrm.domain.MeetingStatus;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Meeting
 * 
 */
public class ListMeetingAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Meeting> baseService;
    private IBaseService<MeetingStatus> meetingStatusService;
    private IBaseService<ReminderOption> reminderOptionService;
    private IBaseService<User> userService;
    private Meeting meeting;

    private static final String CLAZZ = Meeting.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Meeting> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Meeting> meetings = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(meetings, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_meeting");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("start_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("end_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_meeting(), loginUser);
        SearchResult<Meeting> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Meeting> meetings = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(meetings, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Meeting> meetings,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String statusName = null;
        String assignedTo = null;
        while (meetings.hasNext()) {
            Meeting instance = meetings.next();
            int id = instance.getId();
            String subject = CommonUtil.fromNullToEmpty(instance.getSubject());
            MeetingStatus status = instance.getStatus();
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
            if (meetings.hasNext()) {
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
    public String relateMeetingLead() throws Exception {
        meeting = baseService.getEntityById(Meeting.class, id);
        Set<Lead> leads = meeting.getLeads();
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
    public String relateMeetingContact() throws Exception {
        meeting = baseService.getEntityById(Meeting.class, id);
        Set<Contact> contacts = meeting.getContacts();
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
    public String relateMeetingUser() throws Exception {
        meeting = baseService.getEntityById(Meeting.class, id);
        Set<User> users = meeting.getUsers();
        Iterator<User> userIterator = users.iterator();
        int totalRecords = users.size();
        ListUserAction.getListJson(userIterator, totalRecords, null, false);
        return null;
    }

    @Override
    protected String getEntityName() {
        return Meeting.class.getSimpleName();
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_meeting");
        baseService.batchDeleteEntity(Meeting.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_meeting");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Meeting oriRecord = baseService.getEntityById(Meeting.class,
                        Integer.valueOf(copyid));
                Meeting targetRecord = oriRecord.clone();
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
        UserUtil.permissionCheck("view_meeting");
        String fileName = getText("entity.meeting.label") + ".csv";
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
                    getText("meeting.location.label"),
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
                    Meeting meeting = baseService.getEntityById(Meeting.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], meeting.getId());
                    data1.put(header[1],
                            CommonUtil.fromNullToEmpty(meeting.getSubject()));
                    MeetingStatus meetingStatus = meeting.getStatus();
                    if (meetingStatus != null) {
                        data1.put(header[2], meetingStatus.getId());
                    } else {
                        data1.put(header[2], "");
                    }
                    data1.put(header[3],
                            CommonUtil.getOptionLabel(meetingStatus));
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_TIME_FORMAT);
                    Date startDate = meeting.getStart_date();
                    if (startDate != null) {
                        data1.put(header[4], dateFormat.format(startDate));
                    } else {
                        data1.put(header[4], "");
                    }
                    Date endDate = meeting.getEnd_date();
                    if (endDate != null) {
                        data1.put(header[5], dateFormat.format(endDate));
                    } else {
                        data1.put(header[5], "");
                    }
                    data1.put(header[6], CommonUtil.fromNullToEmpty(meeting
                            .getRelated_object()));
                    if (meeting.getRelated_record() == null) {
                        data1.put(header[7], "");
                    } else {
                        data1.put(header[7],
                                String.valueOf(meeting.getRelated_record()));
                    }
                    data1.put(header[8],
                            CommonUtil.fromNullToEmpty(meeting.getLocation()));
                    data1.put(header[9], meeting.isReminder_email());
                    ReminderOption reminderOptionEmail = meeting
                            .getReminder_option_email();
                    if (reminderOptionEmail != null) {
                        data1.put(header[10], reminderOptionEmail.getId());
                    } else {
                        data1.put(header[10], "");
                    }
                    data1.put(header[11],
                            CommonUtil.getOptionLabel(reminderOptionEmail));
                    data1.put(header[12],
                            CommonUtil.fromNullToEmpty(meeting.getNotes()));
                    if (meeting.getAssigned_to() != null) {
                        data1.put(header[13], meeting.getAssigned_to().getId());
                        data1.put(header[14], meeting.getAssigned_to()
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
                Meeting meeting = new Meeting();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        meeting.setId(Integer.parseInt(id));
                    }
                    meeting.setSubject(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.subject.label"))));
                    String statusID = row
                            .get(getText("entity.status_id.label"));
                    if (CommonUtil.isNullOrEmpty(statusID)) {
                        meeting.setStatus(null);
                    } else {
                        MeetingStatus status = meetingStatusService
                                .getEntityById(MeetingStatus.class,
                                        Integer.parseInt(statusID));
                        meeting.setStatus(status);
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_TIME_FORMAT);
                    String startDateS = row
                            .get(getText("entity.start_date.label"));
                    if (startDateS != null) {
                        Date startDate = dateFormat.parse(startDateS);
                        meeting.setStart_date(startDate);
                    } else {
                        meeting.setStart_date(null);
                    }
                    String endDateS = row.get(getText("entity.end_date.label"));
                    if (endDateS != null) {
                        Date endDate = dateFormat.parse(endDateS);
                        meeting.setEnd_date(endDate);
                    } else {
                        meeting.setEnd_date(null);
                    }
                    meeting.setRelated_object(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.related_object.label"))));
                    String relatedRecord = row
                            .get(getText("entity.related_record_id.label"));
                    if (CommonUtil.isNullOrEmpty(relatedRecord)) {
                        meeting.setRelated_record(0);
                    } else {
                        meeting.setRelated_record(Integer
                                .parseInt(relatedRecord));
                    }
                    meeting.setLocation(CommonUtil.fromNullToEmpty(row
                            .get(getText("meeting.location.label"))));
                    String reminderWayEmail = row
                            .get(getText("entity.reminder_email.label"));
                    if (CommonUtil.isNullOrEmpty(reminderWayEmail)) {
                        meeting.setReminder_email(false);
                    } else {
                        meeting.setReminder_email(Boolean
                                .parseBoolean(reminderWayEmail));
                    }
                    String reminderOptionEmailID = row
                            .get(getText("entity.reminder_option_email_id.label"));
                    if (CommonUtil.isNullOrEmpty(reminderOptionEmailID)) {
                        meeting.setReminder_option_email(null);
                    } else {
                        ReminderOption reminderOption = reminderOptionService
                                .getEntityById(ReminderOption.class,
                                        Integer.parseInt(reminderOptionEmailID));
                        meeting.setReminder_option_email(reminderOption);
                    }
                    meeting.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        meeting.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        meeting.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(meeting);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(meeting.getSubject(), e.getMessage());
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

    public IBaseService<Meeting> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Meeting> baseService) {
        this.baseService = baseService;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
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
     * @return the meetingStatusService
     */
    public IBaseService<MeetingStatus> getMeetingStatusService() {
        return meetingStatusService;
    }

    /**
     * @param meetingStatusService
     *            the meetingStatusService to set
     */
    public void setMeetingStatusService(
            IBaseService<MeetingStatus> meetingStatusService) {
        this.meetingStatusService = meetingStatusService;
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

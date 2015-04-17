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

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Contact;
import com.gcrm.domain.Task;
import com.gcrm.domain.TaskPriority;
import com.gcrm.domain.TaskStatus;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Task
 * 
 */
public class ListTaskAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Task> baseService;
    private IBaseService<TaskStatus> taskStatusService;
    private IBaseService<Contact> contactService;
    private IBaseService<TaskPriority> taskPriorityService;
    private IBaseService<User> userService;
    private Task task;
    private String moreFilterKey;
    private String moreFilterValue;

    private static final String CLAZZ = Task.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Task> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Task> tasks = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(tasks, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_task");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("due_date", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_task(), loginUser);
        if (!CommonUtil.isNullOrEmpty(moreFilterKey)) {
            String condition = searchCondition.getCondition();
            condition += " and " + moreFilterKey + "=" + moreFilterValue;
            searchCondition.setCondition(condition);
        }

        SearchResult<Task> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Task> tasks = result.getResult().iterator();

        long totalRecords = result.getTotalRecords();
        getListJson(tasks, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Task> tasks, long totalRecords,
            SearchCondition searchCondition, boolean isList) throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String contactName = null;
        String assignedTo = null;
        while (tasks.hasNext()) {
            Task instance = tasks.next();
            int id = instance.getId();
            TaskStatus statu=instance.getStatus();
            String statuName = CommonUtil.getOptionLabel(statu);
            String subject = instance.getSubject();
            Contact contact = instance.getContact();
            if (contact != null) {
                contactName = contact.getLast_name() + " "
                        + contact.getLast_name();
            } else {
                contactName = "";
            }
            String relatedObject = CommonUtil.fromNullToEmpty(instance
                    .getRelated_object());
            if(relatedObject.equals("Account")){
            	relatedObject="商场";
            }else if(relatedObject.equals("Case")){
            	relatedObject="售后服务";
            }else if(relatedObject.equals("Contact")){
            	relatedObject="联系人";
            }else if(relatedObject.equals("Opportunity")){
            	relatedObject="商业机会";
            }else if(relatedObject.equals("Target")){
            	relatedObject="目标";
            }else if(relatedObject.equals("Task")){
            	relatedObject="任务";
            }
            Date dueDate = instance.getDue_date();
            String dueDateS = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_TIME_FORMAT);
            if (dueDate != null) {
                dueDateS = dateFormat.format(dueDate);
            }

            User user = instance.getAssigned_to();
            if (user != null) {
                assignedTo = user.getName();
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
                Date createdOn = instance.getCreated_on();
                String createdOnName = "";
                if (createdOn != null) {
                    createdOnName = dateFormat.format(createdOn);
                }
                Date updatedOn = instance.getUpdated_on();
                String updatedOnName = "";
                if (updatedOn != null) {
                    updatedOnName = dateFormat.format(updatedOn);
                }

                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
                        .append(subject).append("\",\"").append(contactName)
                        .append("\",\"").append(relatedObject).append("\",\"")
                        .append(statuName).append("\",\"")
                        .append(dueDateS).append("\",\"").append(assignedTo)
                        .append("\",\"").append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"subject\":\"").append(subject)
                        .append("\",\"contact.name\":\"").append(contactName)
                        .append("\",\"related_object\":\"").append(relatedObject)
                        .append("\",\"statuName\":\"").append(statuName)
                        .append("\",\"due_date\":\"")
                        .append(dueDateS).append("\",\"assigned_to.name\":\"")
                        .append(assignedTo).append("\"}");
            }
            if (tasks.hasNext()) {
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
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_task");
        baseService.batchDeleteEntity(Task.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Removes the related entities
     * 
     * @return the SUCCESS result
     */
    public String remove() throws ServiceException {
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String removeId = ids[i];
                task = baseService.getEntityById(Task.class,
                        Integer.valueOf(removeId));
                if ("Contact".endsWith(super.getRemoveKey())) {
                    task.setContact(null);
                } else {
                    task.setRelated_object(null);
                    task.setRelated_record(null);
                }
                this.baseService.makePersistent(task);
            }
        }
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_task");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Task oriRecord = baseService.getEntityById(Task.class,
                        Integer.valueOf(copyid));
                Task targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.getBaseService().makePersistent(targetRecord);
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
        UserUtil.permissionCheck("view_task");
        String fileName = getText("entity.task.label") + ".csv";
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
                    getText("task.due_date.label"),
                    getText("entity.related_object.label"),
                    getText("entity.related_record_id.label"),
                    getText("entity.contact_id.label"),
                    getText("entity.contact_name.label"),
                    getText("entity.priority_id.label"),
                    getText("entity.priority_name.label"),
                    getText("entity.description.label"),
                    getText("entity.notes.label"),
                    getText("entity.assigned_to_id.label"),
                    getText("entity.assigned_to_name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    Task task = baseService.getEntityById(Task.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], task.getId());
                    data1.put(header[1],
                            CommonUtil.fromNullToEmpty(task.getSubject()));
                    TaskStatus taskStatus = task.getStatus();
                    if (taskStatus != null) {
                        data1.put(header[2], taskStatus.getId());
                    } else {
                        data1.put(header[2], "");
                    }
                    data1.put(header[3], CommonUtil.getOptionLabel(taskStatus));
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_TIME_FORMAT);
                    Date startDate = task.getStart_date();
                    if (startDate != null) {
                        data1.put(header[4], dateFormat.format(startDate));
                    } else {
                        data1.put(header[4], "");
                    }
                    Date due_date = task.getDue_date();
                    if (due_date != null) {
                        data1.put(header[5], dateFormat.format(due_date));
                    } else {
                        data1.put(header[5], "");
                    }
                    data1.put(header[6], CommonUtil.fromNullToEmpty(task
                            .getRelated_object()));
                    if (task.getRelated_record() != null) {
                        data1.put(header[7], task.getRelated_record());
                    } else {
                        data1.put(header[7], "");
                    }
                    if (task.getContact() != null) {
                        data1.put(header[8], task.getContact().getId());
                        data1.put(header[9], task.getContact().getName());
                    } else {
                        data1.put(header[8], "");
                        data1.put(header[9], "");
                    }
                    TaskPriority taskPriority = task.getPriority();
                    if (taskPriority != null) {
                        data1.put(header[10], taskPriority.getId());
                    } else {
                        data1.put(header[10], "");
                    }
                    data1.put(header[11],
                            CommonUtil.getOptionLabel(taskPriority));
                    data1.put(header[12],
                            CommonUtil.fromNullToEmpty(task.getDescription()));
                    data1.put(header[13],
                            CommonUtil.fromNullToEmpty(task.getNotes()));
                    if (task.getAssigned_to() != null) {
                        data1.put(header[14], task.getAssigned_to().getId());
                        data1.put(header[15], task.getAssigned_to().getName());
                    } else {
                        data1.put(header[14], "");
                        data1.put(header[15], "");
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

                Task task = new Task();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        task.setId(Integer.parseInt(id));
                    }
                    task.setSubject(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.subject.label"))));
                    String statusID = row
                            .get(getText("entity.status_id.label"));
                    if (CommonUtil.isNullOrEmpty(statusID)) {
                        task.setStatus(null);
                    } else {
                        TaskStatus status = taskStatusService.getEntityById(
                                TaskStatus.class, Integer.parseInt(statusID));
                        task.setStatus(status);
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            Constant.DATE_TIME_FORMAT);
                    String startDateS = row
                            .get(getText("entity.start_date.label"));
                    if (startDateS != null) {
                        Date startDate = dateFormat.parse(startDateS);
                        task.setStart_date(startDate);
                    } else {
                        task.setStart_date(null);
                    }
                    String dueDateS = row.get(getText("task.due_date.label"));
                    if (dueDateS != null) {
                        Date dueDate = dateFormat.parse(dueDateS);
                        task.setDue_date(dueDate);
                    } else {
                        task.setDue_date(null);
                    }
                    task.setRelated_object(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.related_object.label"))));
                    String relatedRecord = row
                            .get(getText("entity.related_record_id.label"));
                    if (CommonUtil.isNullOrEmpty(relatedRecord)) {
                        task.setRelated_record(0);
                    } else {
                        task.setRelated_record(Integer.parseInt(relatedRecord));
                    }
                    String contactID = row
                            .get(getText("entity.contact_id.label"));
                    if (CommonUtil.isNullOrEmpty(contactID)) {
                        task.setContact(null);
                    } else {
                        Contact contact = contactService.getEntityById(
                                Contact.class, Integer.parseInt(contactID));
                        task.setContact(contact);
                    }
                    String priorityID = row
                            .get(getText("entity.priority_id.label"));
                    if (CommonUtil.isNullOrEmpty(priorityID)) {
                        task.setPriority(null);
                    } else {
                        TaskPriority priority = taskPriorityService
                                .getEntityById(TaskPriority.class,
                                        Integer.parseInt(priorityID));
                        task.setPriority(priority);
                    }
                    task.setDescription(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.description.label"))));
                    task.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        task.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        task.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(task);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(task.getSubject(), e.getMessage());
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
        return Task.class.getSimpleName();
    }

    /**
     * @return the baseService
     */
    public IBaseService<Task> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Task> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the taskStatusService
     */
    public IBaseService<TaskStatus> getTaskStatusService() {
        return taskStatusService;
    }

    /**
     * @param taskStatusService
     *            the taskStatusService to set
     */
    public void setTaskStatusService(IBaseService<TaskStatus> taskStatusService) {
        this.taskStatusService = taskStatusService;
    }

    /**
     * @return the contactService
     */
    public IBaseService<Contact> getContactService() {
        return contactService;
    }

    /**
     * @param contactService
     *            the contactService to set
     */
    public void setContactService(IBaseService<Contact> contactService) {
        this.contactService = contactService;
    }

    /**
     * @return the taskPriorityService
     */
    public IBaseService<TaskPriority> getTaskPriorityService() {
        return taskPriorityService;
    }

    /**
     * @param taskPriorityService
     *            the taskPriorityService to set
     */
    public void setTaskPriorityService(
            IBaseService<TaskPriority> taskPriorityService) {
        this.taskPriorityService = taskPriorityService;
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
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    /**
     * @param task
     *            the task to set
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * @return the moreFilterKey
     */
    public String getMoreFilterKey() {
        return moreFilterKey;
    }

    /**
     * @param moreFilterKey
     *            the moreFilterKey to set
     */
    public void setMoreFilterKey(String moreFilterKey) {
        this.moreFilterKey = moreFilterKey;
    }

    /**
     * @return the moreFilterValue
     */
    public String getMoreFilterValue() {
        return moreFilterValue;
    }

    /**
     * @param moreFilterValue
     *            the moreFilterValue to set
     */
    public void setMoreFilterValue(String moreFilterValue) {
        this.moreFilterValue = moreFilterValue;
    }

}

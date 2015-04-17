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
package com.gcrm.action.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

import com.gcrm.action.crm.BaseListAction;
import com.gcrm.domain.Call;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.Role;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.domain.UserStatus;
import com.gcrm.domain.Visit;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists User
 * 
 */
public class ListUserAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<User> baseService;
    private IBaseService<UserStatus> userStatusService;
    private IBaseService<TargetList> targetListService;
    private IBaseService<Call> callService;
    private IBaseService<Meeting> meetingService;
    private IBaseService<Visit> visitService;
    private User user;

    private static final String CLAZZ = User.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<User> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
    	Iterator<User> users = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(users, totalRecords, null, false);
        return null;
    	
    }
    /**
     * 获取过滤后的用户.
     * 
     * @return null
     */
    public String listvalidator() throws Exception {
    	User uu=getLoginUser();
    	String hql = "from User where report_to ="+uu.getId()+" OR id="+uu.getId()+"";
        List<User> result = baseService.findByHQL(hql);
        Iterator<User> users = result.iterator();
        getListJson(users, result.size(), null, false);
        return null;
    }
    
    //登录记录
    public String listUserLog() throws Exception {
    	UserUtil.permissionCheck("view_system");

    	String hql = "from User";
        List<User> result = baseService.findByHQL(hql);
        getListLogJson(result, result.size(), null);
        return null;
    }
    
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_system");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_system(), loginUser);
        SearchResult<User> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        
        Iterator<User> users = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(users, totalRecords, searchCondition, true);
        return null;
    }
    
    //登录记录
    public static void getListLogJson(List<User> users, long totalRecords,SearchCondition searchCondition) throws Exception {
    	
        
    	if(users==null || users.size()==0){
    		//TODO
    	} else {
    		StringBuilder jsonBuilder = new StringBuilder("");
    		int totle=1;
    		if(users.size()/15>1){
    			totle=users.size()/15;
    		}
    		jsonBuilder.append("{\"total\":").append("\""+totle+"\",");
    		jsonBuilder.append("\"page\":").append("\""+1+"\",");
    		jsonBuilder.append("\"records\":").append("\""+users.size()+"\",");
    		jsonBuilder.append("\"rows\":").append("[");
    		
    		for(int i=users.size()-1;i>=0;i--){
    			
    			jsonBuilder.append("{\"cell\":[\"").append(users.get(i).getId()).append("\",\"")
    			.append(users.get(i).getLast_name()).append("\",\"")
    			.append(users.get(i).getLogin_on()).append("\",\"")
    			.append(users.get(i).getCount()).append("\",\"")
    			.append(users.get(i).getIp()).append("\",\"")
    			.append("\"]}");
    			if (i>0) {
        			jsonBuilder.append(",");
        		}
    		}
    	    jsonBuilder.append("]}");
    	    HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(jsonBuilder.toString());
    	}
    	 
    }
    
    public static void getListJson(Iterator<User> users, long totalRecords,
    		SearchCondition searchCondition, boolean isList) throws Exception {
    	
    	StringBuilder jsonBuilder = new StringBuilder("");
    	jsonBuilder
    	.append(getJsonHeader(totalRecords, searchCondition, isList));
    	
    	while (users.hasNext()) {
    		User instance = users.next();
    		int id = instance.getId();
    		String name = CommonUtil.fromNullToEmpty(instance.getLast_name());
    		String title = CommonUtil.fromNullToEmpty(instance.getTitle());
    		String department = CommonUtil.fromNullToEmpty(instance
    				.getDepartment());
    		UserStatus status = instance.getStatus();
    		String statusName = CommonUtil.getOptionLabel(status);
    		
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
    			SimpleDateFormat dateFormat = new SimpleDateFormat(
    					Constant.DATE_TIME_FORMAT);
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
    			.append(name).append("\",\"").append(title)
    			.append("\",\"").append(department).append("\",\"")
    			.append(statusName).append("\",\"")
    			.append(createdByName).append("\",\"")
    			.append(updatedByName).append("\",\"")
    			.append(createdOnName).append("\",\"")
    			.append(updatedOnName).append("\"]}");
    		} else {
    			jsonBuilder.append("{\"id\":\"").append(id)
    			.append("\",\"name\":\"").append(name)
    			.append("\",\"title\":\"").append(title)
    			.append("\",\"department\":\"").append(department)
    			.append("\",\"status\":\"").append(statusName)
    			.append("\"}");
    		}
    		if (users.hasNext()) {
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
     * Selects the entities
     * 
     * @return the SUCCESS result
     */
    public String select() throws ServiceException {
        TargetList targetList = null;
        Call call = null;
        Meeting meeting = null;
        Visit visit=null;
        Set<User> users = null;

        if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class,
                    Integer.valueOf(this.getRelationValue()));
            users = targetList.getUsers();
        } else if ("Call".equals(this.getRelationKey())) {
            call = callService.getEntityById(Call.class,
                    Integer.valueOf(this.getRelationValue()));
            users = call.getUsers();
        } else if ("Meeting".equals(this.getRelationKey())) {
            meeting = meetingService.getEntityById(Meeting.class,
                    Integer.valueOf(this.getRelationValue()));
            users = meeting.getUsers();
        }else if ("Visit".equals(this.getRelationKey())) {
            visit = visitService.getEntityById(Visit.class,
                    Integer.valueOf(this.getRelationValue()));
            users = visit.getUsers();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String selectId = ids[i];
                user = baseService.getEntityById(User.class,
                        Integer.valueOf(selectId));
                users.add(user);
            }
        }

        if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        } else if ("Call".equals(this.getRelationKey())) {
            callService.makePersistent(call);
        } else if ("Meeting".equals(this.getRelationKey())) {
            meetingService.makePersistent(meeting);
        }else if ("Visit".equals(this.getRelationKey())) {
            visitService.makePersistent(visit);
        }
        return SUCCESS;
    }

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */
    public String unselect() throws ServiceException {
        TargetList targetList = null;
        Call call = null;
        Meeting meeting = null;
        Set<User> users = null;
        Visit visit=null;

        if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class,
                    Integer.valueOf(this.getRelationValue()));
            users = targetList.getUsers();
        } else if ("Call".equals(this.getRelationKey())) {
            call = callService.getEntityById(Call.class,
                    Integer.valueOf(this.getRelationValue()));
            users = call.getUsers();
        } else if ("Meeting".equals(this.getRelationKey())) {
            meeting = meetingService.getEntityById(Meeting.class,
                    Integer.valueOf(this.getRelationValue()));
            users = meeting.getUsers();
        }else if ("Visit".equals(this.getRelationKey())) {
            visit = visitService.getEntityById(Visit.class,
                    Integer.valueOf(this.getRelationValue()));
            users = visit.getUsers();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            Collection<User> selectedUsers = new ArrayList<User>();
            for (int i = 0; i < ids.length; i++) {
                Integer selectId = Integer.valueOf(ids[i]);
                A: for (User user : users) {
                    if (user.getId().intValue() == selectId.intValue()) {
                        selectedUsers.add(user);
                        break A;
                    }
                }
            }
            users.removeAll(selectedUsers);
        }

        if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        } else if ("Call".equals(this.getRelationKey())) {
            callService.makePersistent(call);
        } else if ("Meeting".equals(this.getRelationKey())) {
            meetingService.makePersistent(meeting);
        }else if ("Visit".equals(this.getRelationKey())) {
            meetingService.makePersistent(meeting);
        }
        return SUCCESS;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        baseService.batchDeleteEntity(User.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_system");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                User oriRecord = baseService.getEntityById(User.class,
                        Integer.valueOf(copyid));
                User targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.baseService.makePersistent(targetRecord);
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
        UserUtil.permissionCheck("view_system");
        String fileName = getText("entity.user.label") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { getText("entity.id.label"),
                    getText("user.name.label"),
                    getText("entity.first_name.label"),
                    getText("entity.last_name.label"),
                    getText("entity.status_id.label"),
                    getText("entity.status_name.label"),
                    getText("entity.title.label"),
                    getText("entity.email.label"),
                    getText("entity.mobile.label"),
                    getText("user.phone.label"), getText("entity.fax.label"),
                    getText("entity.department.label"),
                    getText("user.report_to_id.label"),
                    getText("user.report_to_name.label"),
                    getText("entity.mailing_street.label"),
                    getText("entity.mailing_city.label"),
                    getText("entity.mailing_state.label"),
                    getText("entity.mailing_postal_code.label"),
                    getText("entity.mailing_country.label"),
                    getText("entity.other_street.label"),
                    getText("entity.other_city.label"),
                    getText("entity.other_state.label"),
                    getText("entity.other_postal_code.label"),
                    getText("entity.other_country.label"),
                    getText("user.age.label"),
                    getText("user.smtp_username.label"),
                    getText("user.smtp_password.label"),
                    getText("entity.description.label"),
                    getText("entity.notes.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    User user = baseService.getEntityById(User.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], user.getId());
                    data1.put(header[1],
                            CommonUtil.fromNullToEmpty(user.getName()));
                    data1.put(header[2],
                            CommonUtil.fromNullToEmpty(user.getFirst_name()));
                    data1.put(header[3],
                            CommonUtil.fromNullToEmpty(user.getLast_name()));
                    UserStatus userStatus = user.getStatus();
                    if (userStatus != null) {
                        data1.put(header[4], userStatus.getId());
                    } else {
                        data1.put(header[4], "");
                    }
                    data1.put(header[5], CommonUtil.getOptionLabel(userStatus));
                    data1.put(header[6],
                            CommonUtil.fromNullToEmpty(user.getTitle()));
                    data1.put(header[7],
                            CommonUtil.fromNullToEmpty(user.getEmail()));
                    data1.put(header[8],
                            CommonUtil.fromNullToEmpty(user.getMobile()));
                    data1.put(header[9],
                            CommonUtil.fromNullToEmpty(user.getPhone()));
                    data1.put(header[10],
                            CommonUtil.fromNullToEmpty(user.getFax()));
                    data1.put(header[11],
                            CommonUtil.fromNullToEmpty(user.getDepartment()));
                    if (user.getReport_to() != null) {
                        data1.put(header[12], user.getReport_to().getId());
                        data1.put(header[13], user.getReport_to().getName());
                    } else {
                        data1.put(header[12], "");
                        data1.put(header[13], "");
                    }
                    data1.put(header[14],
                            CommonUtil.fromNullToEmpty(user.getMail_street()));
                    data1.put(header[15],
                            CommonUtil.fromNullToEmpty(user.getMail_city()));
                    data1.put(header[16],
                            CommonUtil.fromNullToEmpty(user.getMail_state()));
                    data1.put(header[17], CommonUtil.fromNullToEmpty(user
                            .getMail_postal_code()));
                    data1.put(header[18],
                            CommonUtil.fromNullToEmpty(user.getMail_country()));
                    data1.put(header[19],
                            CommonUtil.fromNullToEmpty(user.getOther_street()));
                    data1.put(header[20],
                            CommonUtil.fromNullToEmpty(user.getOther_city()));
                    data1.put(header[21],
                            CommonUtil.fromNullToEmpty(user.getOther_state()));
                    data1.put(header[22], CommonUtil.fromNullToEmpty(user
                            .getOther_postal_code()));
                    data1.put(header[23],
                            CommonUtil.fromNullToEmpty(user.getOther_country()));
                    int age = 0;
                    if (user.getAge() != null) {
                        age = user.getAge();
                    }
                    data1.put(header[24], age);
                    data1.put(header[25],
                            CommonUtil.fromNullToEmpty(user.getSmtp_username()));
                    data1.put(header[26],
                            CommonUtil.fromNullToEmpty(user.getSmtp_password()));
                    data1.put(header[27],
                            CommonUtil.fromNullToEmpty(user.getDescription()));
                    data1.put(header[28],
                            CommonUtil.fromNullToEmpty(user.getNotes()));
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

                User user = new User();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        user.setId(Integer.parseInt(id));
                    }
                    user.setName(CommonUtil.fromNullToEmpty(row
                            .get(getText("user.name.label"))));
                    user.setFirst_name(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.first_name.label"))));
                    user.setLast_name(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.last_name.label"))));
                    String statusID = row
                            .get(getText("entity.status_id.label"));
                    if (CommonUtil.isNullOrEmpty(statusID)) {
                        user.setStatus(null);
                    } else {
                        UserStatus userStatus = userStatusService
                                .getEntityById(UserStatus.class,
                                        Integer.parseInt(statusID));
                        user.setStatus(userStatus);
                    }
                    user.setTitle(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.title.label"))));
                    user.setEmail(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.email.label"))));
                    user.setMobile(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mobile.label"))));
                    user.setPhone(CommonUtil.fromNullToEmpty(row
                            .get(getText("user.phone.label"))));
                    user.setFax(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.fax.label"))));
                    user.setDepartment(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.department.label"))));
                    String reportToID = row
                            .get(getText("user.report_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(reportToID)) {
                        user.setReport_to(null);
                    } else {
                        User reportTo = baseService.getEntityById(User.class,
                                Integer.parseInt(reportToID));
                        user.setReport_to(reportTo);
                    }
                    user.setMail_street(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mailing_street.label"))));
                    user.setMail_city(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mailing_city.label"))));
                    user.setMail_state(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mailing_state.label"))));
                    user.setMail_postal_code(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mailing_postal_code.label"))));
                    user.setMail_country(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mailing_country.label"))));
                    user.setOther_street(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_street.label"))));
                    user.setOther_city(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_city.label"))));
                    user.setOther_state(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_state.label"))));
                    user.setOther_postal_code(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_postal_code.label"))));
                    user.setOther_country(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_country.label"))));
                    String age = row.get(getText("user.age.label"));
                    if (CommonUtil.isNullOrEmpty(age)) {
                        user.setAge(0);
                    } else {
                        user.setAge(Integer.parseInt(age));
                    }
                    user.setSmtp_username(CommonUtil.fromNullToEmpty(row
                            .get(getText("user.smtp_username.label"))));
                    user.setSmtp_password(CommonUtil.fromNullToEmpty(row
                            .get(getText("user.smtp_password.label"))));
                    user.setDescription(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.description.label"))));
                    user.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    baseService.makePersistent(user);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(user.getName(), e.getMessage());
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

    /**
     * Gets the related roles.
     * 
     * @return null
     */
    public String filterUserRole() throws Exception {
        user = baseService.getEntityById(User.class, id);
        Set<Role> roles = user.getRoles();
        Iterator<Role> roleIterator = roles.iterator();
        long totalRecords = roles.size();
        ListRoleAction.getListJson(roleIterator, totalRecords, null, false);
        return null;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return User.class.getSimpleName();
    }

    /**
     * @return the baseService
     */
    public IBaseService<User> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<User> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the userStatusService
     */
    public IBaseService<UserStatus> getUserStatusService() {
        return userStatusService;
    }

    /**
     * @param userStatusService
     *            the userStatusService to set
     */
    public void setUserStatusService(IBaseService<UserStatus> userStatusService) {
        this.userStatusService = userStatusService;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
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
     * @return the targetListService
     */
    public IBaseService<TargetList> getTargetListService() {
        return targetListService;
    }

    /**
     * @param targetListService
     *            the targetListService to set
     */
    public void setTargetListService(IBaseService<TargetList> targetListService) {
        this.targetListService = targetListService;
    }

    /**
     * @return the callService
     */
    public IBaseService<Call> getCallService() {
        return callService;
    }

    /**
     * @param callService
     *            the callService to set
     */
    public void setCallService(IBaseService<Call> callService) {
        this.callService = callService;
    }

    /**
     * @return the meetingService
     */
    public IBaseService<Meeting> getMeetingService() {
        return meetingService;
    }

    /**
     * @param meetingService
     *            the meetingService to set
     */
    public void setMeetingService(IBaseService<Meeting> meetingService) {
        this.meetingService = meetingService;
    }

	public IBaseService<Visit> getVisitService() {
		return visitService;
	}

	public void setVisitService(IBaseService<Visit> visitService) {
		this.visitService = visitService;
	}

}

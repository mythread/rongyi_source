/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.action.crm;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.gcrm.domain.BaseEntity;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.OptionBase;
import com.gcrm.domain.Record;
import com.gcrm.domain.Record.RecordTypeEnum;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action for entity edit.
 */
public class BaseEditAction extends ActionSupport implements ServletResponseAware {

    private static final long   serialVersionUID = -2404576552417042445L;

    private String              createdBy        = "";
    private String              createdOn        = "";
    private String              updatedBy        = "";
    private String              updatedOn        = "";
    private Integer             ownerID          = null;
    private String              ownerText        = null;
    private Integer             assignedToID     = null;
    private String              assignedToText   = null;
    private Integer             id;
    private String              relationKey;
    private String              relationValue;
    protected String            seleteIDs        = null;
    protected String[]          massUpdate       = null;
    private String              saveFlag         = null;
    private HttpServletResponse response;

    /**
     * Updates the base information for entity.
     * 
     * @param entity instance
     */
    protected void updateBaseInfo(BaseEntity entity) {
        User loginUser = getLoginUser();
        if (entity.getId() == null) {
            entity.setCreated_by(loginUser);
            entity.setCreated_on(new Date());
            entity.setUpdated_on(new Date());
        } else {
            entity.setUpdated_by(loginUser);
            entity.setUpdated_on(new Date());
        }
    }

    /**
     * Gets login user.
     * 
     * @return login user
     */
    protected User getLoginUser() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session.get(AuthenticationSuccessListener.LOGIN_USER);
        return loginUser;
    }

    /**
     * 重定向
     */
    protected void redirectUrl(String url) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.sendRedirect(url);
    }

    /**
     * Gets the base information for entity.
     * 
     * @param entity instance
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void getBaseInfo(BaseEntity entity, String entityName, String namespace) {
        User createdUser = entity.getCreated_by();
        if (createdUser != null) {
            this.setCreatedBy(createdUser.getName());
        }
        User updatedUser = entity.getUpdated_by();
        if (updatedUser != null) {
            this.setUpdatedBy(updatedUser.getName());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
        Date createdOn = entity.getCreated_on();
        if (createdOn != null) {
            this.setCreatedOn(dateFormat.format(createdOn));
        }
        Date updatedOn = entity.getUpdated_on();
        if (updatedOn != null) {
            this.setUpdatedOn(dateFormat.format(updatedOn));
        }
        User owner = entity.getOwner();
        if (owner != null) {
            ownerID = owner.getId();
            ownerText = owner.getName();
        }

        // Sets navigation history
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ArrayList navigationList = (ArrayList) session.getAttribute(Constant.NAVIGATION_HISTORY);
        if (navigationList == null) {
            navigationList = new ArrayList();
        }
        String entityLabel = getText("entity." + CommonUtil.lowerCaseString(entityName) + ".label");
        if (!CommonUtil.isNullOrEmpty(entity.getName())) {
            entityLabel += " - " + entity.getName();
        }
        String navigatoin = "<a href='" + namespace + "edit" + entityName + ".action?id=" + entity.getId() + "'>"
                            + entityLabel + "</a>";
        if (navigationList.contains(navigatoin)) {
            navigationList.remove(navigatoin);
        }
        navigationList.add(navigatoin);
        if (navigationList.size() > Constant.NAVIGATION_HISTORY_COUNT) {
            navigationList.remove(0);
        }
        session.setAttribute(Constant.NAVIGATION_HISTORY, navigationList);
    }

    /**
     * Initiates the base information for entity.
     */
    protected void initBaseInfo() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session.get(AuthenticationSuccessListener.LOGIN_USER);
        assignedToID = loginUser.getId();
        assignedToText = loginUser.getLast_name();
        ownerID = loginUser.getId();
        ownerText = loginUser.getLast_name();
    }

    /**
     * Creates the change log.
     * 
     * @param changeLogs change log collection
     * @param entityName entity name
     * @param recordID record ID
     * @param columnName column name
     * @param oldValue old value
     * @param newValue new value
     * @param loginUser login user
     */
    protected void createChangeLog(Collection<ChangeLog> changeLogs, String entityName, Integer recordID,
                                   String columnName, String oldValue, String newValue, User loginUser) {
        if (!oldValue.equals(newValue)) {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setEntityName(entityName);
            changeLog.setRecordID(recordID);
            changeLog.setColumnName(columnName);
            changeLog.setOldValue(oldValue);
            changeLog.setNewValue(newValue);
            changeLog.setUpdated_by(loginUser);
            changeLog.setUpdated_on(new Date());
            changeLogs.add(changeLog);
        }
    }

    /**
     * Gets option value.
     * 
     * @param option option
     * @return option value
     */
    protected String getOptionValue(OptionBase option) {
        if (option == null) {
            return "";
        } else {
            return option.getValue();
        }

    }

    /**
     * 返回json数据
     */
    protected void response2Json(Object obj, boolean success, String msg) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            map.put("data", "");
        } else {
            map.put("data", obj);
        }
        map.put("msg", msg);
        if (success) {
            map.put("result", "0");
        } else {
            map.put("result", "1");
        }
        JSONObject json = JSONObject.fromObject(map);
        // HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json.toString());
    }

    /**
     * response返回二进制流文件
     * 
     * @param bytes
     * @param fileName
     * @throws Exception
     */
    protected void response2download(byte[] bytes, String fileName) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }

    /**
     * 获得record Object
     * 
     * @throws ParseException
     */
    public Record getRecordOjb(RecordTypeEnum type, Integer outerId, String visitType, String visitNote,
                               Integer accountIntent, Integer accountVisit, Integer isRemind, Object assignedDate,
                               String tableName, String memo) throws ParseException {
        Record record = new Record();
        record.setAccountIntent(accountIntent);
        record.setAccountVisit(accountVisit);
        User loginUser = getLoginUser();
        record.setCreated_by(loginUser);
        record.setCreated_on(new Date());
        record.setIsRemind(isRemind);
        record.setTableName(tableName);
        record.setMemo(memo);
        record.setOuterId(outerId);
        record.setType(type.name());
        record.setVisitNote(visitNote);
        record.setVisitType(visitType);
        if (assignedDate instanceof Date) {
            record.setAssigned_date((Date) assignedDate);
        } else if (assignedDate instanceof String) {
            String str = (String) assignedDate;
            if (StringUtils.isNotEmpty(str)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                record.setAssigned_date(dateFormat.parse(str));
            }
        }
        return record;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedOn
     */
    public String getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @param updatedOn the updatedOn to set
     */
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the relationKey
     */
    public String getRelationKey() {
        return relationKey;
    }

    /**
     * @param relationKey the relationKey to set
     */
    public void setRelationKey(String relationKey) {
        this.relationKey = relationKey;
    }

    /**
     * @return the relationValue
     */
    public String getRelationValue() {
        return relationValue;
    }

    /**
     * @param relationValue the relationValue to set
     */
    public void setRelationValue(String relationValue) {
        this.relationValue = relationValue;
    }

    /**
     * @return the seleteIDs
     */
    public String getSeleteIDs() {
        return seleteIDs;
    }

    /**
     * @param seleteIDs the seleteIDs to set
     */
    public void setSeleteIDs(String seleteIDs) {
        this.seleteIDs = seleteIDs;
    }

    /**
     * @return the massUpdate
     */
    public String[] getMassUpdate() {
        return massUpdate;
    }

    /**
     * @param massUpdate the massUpdate to set
     */
    public void setMassUpdate(String[] massUpdate) {
        this.massUpdate = massUpdate;
    }

    /**
     * @return the ownerID
     */
    public Integer getOwnerID() {
        return ownerID;
    }

    /**
     * @param ownerID the ownerID to set
     */
    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * @return the ownerText
     */
    public String getOwnerText() {
        return ownerText;
    }

    /**
     * @param ownerText the ownerText to set
     */
    public void setOwnerText(String ownerText) {
        this.ownerText = ownerText;
    }

    /**
     * @return the assignedToID
     */
    public Integer getAssignedToID() {
        return assignedToID;
    }

    /**
     * @param assignedToID the assignedToID to set
     */
    public void setAssignedToID(Integer assignedToID) {
        this.assignedToID = assignedToID;
    }

    /**
     * @return the assignedToText
     */
    public String getAssignedToText() {
        return assignedToText;
    }

    /**
     * @param assignedToText the assignedToText to set
     */
    public void setAssignedToText(String assignedToText) {
        this.assignedToText = assignedToText;
    }

    /**
     * @return the saveFlag
     */
    public String getSaveFlag() {
        return saveFlag;
    }

    /**
     * @param saveFlag the saveFlag to set
     */
    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        // TODO Auto-generated method stub
        this.response = response;

    }

}

/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.action.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gcrm.action.crm.BaseEditAction;
import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.domain.UserStatus;
import com.gcrm.security.AuthenticationFilter;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits User
 */
public class EditUserAction extends BaseEditAction implements Preparable {

    private static final long          serialVersionUID = -2404576552417042445L;

    private IBaseService<User>         baseService;
    private IOptionService<UserStatus> userStatusService;
    private IBaseService<Role>         roleService;
    private User                       user;
    private List<UserStatus>           statuses;
    private Integer                    statusID         = null;
    private String                     reportToName     = "";
    private Integer                    reportToID       = null;
    private Integer                    roleID           = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        if (user.getId() == null) {
            String existhql = "from User where name = '" + user.getName() + "'";
            List<User> userList1 = baseService.findByHQL(existhql);
            if (!userList1.isEmpty()) {
                addActionMessage(String.format("存在联系人【%s】的 账号与您现在添加的账号相同，请验证后再添加", userList1.get(0).getLast_name()));
                return ERROR;
            }
        }
        saveEntity();
        user = getBaseService().makePersistent(user);
        this.setId(user.getId());
        this.setSaveFlag("true");
        return SUCCESS;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            user = baseService.getEntityById(User.class, this.getId());
            UserStatus status = user.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            User reportTo = user.getReport_to();
            if (reportTo != null) {
                reportToName = reportTo.getLast_name();
            }
            Set<Role> roles = user.getRoles();
            if (roles.size() != 0) {
                Iterator<Role> iterator = roles.iterator();
                roleID = iterator.next().getId();
            }
            ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            for (Role role : roles) {
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                authorities.add(grantedAuthority);
                try {
                    UserUtil.setAccessValue(role, user);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to set the Access value");
                }
            }

            ResourceBundle rb = CommonUtil.getResourceBundle();
            Map<Integer, String> scopeMap = new HashMap<Integer, String>();
            scopeMap.put(0, rb.getString("access.notSet.value"));
            scopeMap.put(1, rb.getString("access.all.value"));
            scopeMap.put(2, rb.getString("access.owner.value"));
            user.setScopeMap(scopeMap);
            Map<Integer, String> accessMap = new HashMap<Integer, String>();
            accessMap.put(0, rb.getString("access.notSet.value"));
            accessMap.put(1, rb.getString("access.enabled.value"));
            accessMap.put(2, rb.getString("access.disabled.value"));
            user.setAccessMap(accessMap);
            this.getBaseInfo(user, User.class.getSimpleName(), Constant.SYSTEM_NAMESPACE);
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
            Collection<User> users = new ArrayList<User>();
            User loginUser = this.getLoginUser();
            User user = baseService.getEntityById(User.class, loginUser.getId());
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                User userInstance = this.baseService.getEntityById(User.class, id);
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(user, fieldName);
                    BeanUtil.setFieldValue(userInstance, fieldName, value);
                }
                userInstance.setUpdated_by(user);
                userInstance.setUpdated_on(new Date());
                users.add(userInstance);
            }
            if (users.size() > 0) {
                this.baseService.batchUpdate(users);
            }
        }
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @throws Exception
     */
    private void saveEntity() throws Exception {
        UserStatus status = null;
        if (statusID != null) {
            status = userStatusService.getEntityById(UserStatus.class, statusID);
        }
        user.setStatus(status);

        User reportTo = null;
        if (reportToID != null) {
            reportTo = baseService.getEntityById(User.class, reportToID);
        }
        user.setReport_to(reportTo);

        Role role = null;
        Set<Role> roles = user.getRoles();
        if (roleID != null) {
            role = roleService.getEntityById(Role.class, roleID);
            roles.clear();
            roles.add(role);
        }
        user.setRoles(roles);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        if (user.getId() != null) {
            UserUtil.permissionCheck("update_system");
            User originalUser = baseService.getEntityById(User.class, user.getId());
            String oldPassword = originalUser.getPassword();
            if (!oldPassword.equalsIgnoreCase(user.getPassword())) {
                user.setPassword(encoder.encodePassword(user.getPassword(), AuthenticationFilter.SALT));
            }
            user.setRoles(roles);
            user.setTargetLists(originalUser.getTargetLists());
            user.setCalls(originalUser.getCalls());
            user.setMeetings(originalUser.getMeetings());
            user.setCreated_on(originalUser.getCreated_on());
            user.setCreated_by(originalUser.getCreated_by());
        } else {
            UserUtil.permissionCheck("create_system");
            user.setPassword(encoder.encodePassword(user.getPassword(), AuthenticationFilter.SALT));
        }
        super.updateBaseInfo(user);
    }

    /**
     * Prepares the list
     */
    public void prepare() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        this.statuses = userStatusService.getOptions(UserStatus.class.getSimpleName(), local);
    }

    /**
     * @return the baseService
     */
    public IBaseService<User> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService the baseService to set
     */
    public void setBaseService(IBaseService<User> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the statuses
     */
    public List<UserStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses the statuses to set
     */
    public void setStatuss(List<UserStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * @return the statusID
     */
    public Integer getStatusID() {
        return statusID;
    }

    /**
     * @param statusID the statusID to set
     */
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    /**
     * @return the roleService
     */
    public IBaseService<Role> getRoleService() {
        return roleService;
    }

    /**
     * @param roleService the roleService to set
     */
    public void setRoleService(IBaseService<Role> roleService) {
        this.roleService = roleService;
    }

    /**
     * @return the userStatusService
     */
    public IOptionService<UserStatus> getUserStatusService() {
        return userStatusService;
    }

    /**
     * @param userStatusService the userStatusService to set
     */
    public void setUserStatusService(IOptionService<UserStatus> userStatusService) {
        this.userStatusService = userStatusService;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getReportToName() {
        return reportToName;
    }

    public void setReportToName(String reportToName) {
        this.reportToName = reportToName;
    }

    public Integer getReportToID() {
        return reportToID;
    }

    public void setReportToID(Integer reportToID) {
        this.reportToID = reportToID;
    }

}

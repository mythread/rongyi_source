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

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.gcrm.action.crm.BaseEditAction;
import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Role
 * 
 */
public class EditRoleAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Role> baseService;
    private IBaseService<User> userService;
    private Role role;
    private Map<Integer, String> scopeMap;
    private Map<Integer, String> accessMap;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        saveEntity();
        role = getBaseService().makePersistent(role);
        this.setId(role.getId());
        this.setSaveFlag("true");
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @throws Exception
     */
    private void saveEntity() throws Exception {
        if (role.getId() == null) {
            UserUtil.permissionCheck("create_system");
        } else {
            UserUtil.permissionCheck("update_system");
        }

        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        role.setOwner(owner);
        super.updateBaseInfo(role);
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            role = baseService.getEntityById(Role.class, this.getId());
            this.getBaseInfo(role, Role.class.getSimpleName(),
                    Constant.SYSTEM_NAMESPACE);
        } else {
            this.initBaseInfo();
        }
        return SUCCESS;
    }

    /**
     * Prepares the list
     * 
     */
    public void prepare() throws Exception {
        ResourceBundle rb = CommonUtil.getResourceBundle();
        scopeMap = new HashMap<Integer, String>();
        scopeMap.put(0, rb.getString("access.notSet.value"));
        scopeMap.put(1, rb.getString("access.all.value"));
        scopeMap.put(2, rb.getString("access.owner.value"));
        accessMap = new HashMap<Integer, String>();
        accessMap.put(0, rb.getString("access.notSet.value"));
        accessMap.put(1, rb.getString("access.enabled.value"));
        accessMap.put(2, rb.getString("access.disabled.value"));
    }

    /**
     * @return the baseService
     */
    public IBaseService<Role> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Role> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the scopeMap
     */
    public Map<Integer, String> getScopeMap() {
        return scopeMap;
    }

    /**
     * @param scopeMap
     *            the scopeMap to set
     */
    public void setScopeMap(Map<Integer, String> scopeMap) {
        this.scopeMap = scopeMap;
    }

    /**
     * @return the accessMap
     */
    public Map<Integer, String> getAccessMap() {
        return accessMap;
    }

    /**
     * @param accessMap
     *            the accessMap to set
     */
    public void setAccessMap(Map<Integer, String> accessMap) {
        this.accessMap = accessMap;
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

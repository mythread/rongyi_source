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
package com.gcrm.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.gcrm.domain.Role;
import com.gcrm.service.IBaseService;

/**
 * Security metadata source
 */
public class SecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    private IBaseService<Role> roleService;
    public static Map<String, Collection<ConfigAttribute>> permissionMap = null;

    public SecurityMetadataSource(IBaseService<Role> roleService) {
        this.roleService = roleService;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        return null;
    }

    /**
     * @return the roleService
     */
    public IBaseService<Role> getRoleService() {
        return roleService;
    }

    /**
     * @param roleService
     *            the roleService to set
     */
    public void setRoleService(IBaseService<Role> roleService) {
        this.roleService = roleService;
    }

}

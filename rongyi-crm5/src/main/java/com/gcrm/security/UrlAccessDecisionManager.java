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

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * Url access decision manager
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

    public void decide(Authentication authentication, Object securityObject,
            Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        try {
            if (authentication.getPrincipal() instanceof String
                    && "anonymousUser".equals(authentication.getPrincipal())) {
                throw new AccessDeniedException(" no permission access！");
            }
            return;
        } catch (Exception e) {
            throw new AccessDeniedException("Role check fails.");
        }
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

}

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

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

/**
 * Authentication success listener
 */
public class AuthenticationSuccessListener implements
        HttpSessionAttributeListener {
    public final static String LOGIN_USER = "loginUser";

    public void attributeAdded(HttpSessionBindingEvent se) {
        String sessionName = se.getName();
        if (sessionName.equals("SPRING_SECURITY_CONTEXT")) {
            SecurityContext securityContext = (SecurityContext) se.getSession()
                    .getAttribute("SPRING_SECURITY_CONTEXT");
            Authentication authentication = securityContext.getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication
                    .getPrincipal();
            se.getSession().setAttribute(LOGIN_USER, userDetails.getUser());
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {

    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
        String sessionName = se.getName();
        if (sessionName.equals("SPRING_SECURITY_CONTEXT")) {
            SecurityContext securityContext = (SecurityContext) se.getSession()
                    .getAttribute("SPRING_SECURITY_CONTEXT");
            Authentication authentication = securityContext.getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication
                    .getPrincipal();
            se.getSession().setAttribute(LOGIN_USER, userDetails.getUser());
        }
    }

}

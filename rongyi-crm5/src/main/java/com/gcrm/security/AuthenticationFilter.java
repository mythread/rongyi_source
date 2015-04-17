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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gcrm.domain.User;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.security.UserUtil;

/**
 * Authentication filter
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String USERNAME = "j_username";
    public static final String PASSWORD = "j_password";
    public static final String LANGUAGE = "j_language";
    public static final String SALT = "Grass";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: "
                            + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // Validates username and password
        username = username.trim();

        String localValue = obtainLanguage(request);
        localValue = "zh_CN";
        String[] locals = localValue.split("_");
        Locale locale = new Locale(locals[0], locals[1]);
        request.getSession().setAttribute("WW_TRANS_I18N_LOCALE", locale);
        request.getSession().setAttribute("locale", localValue);
        Locale.setDefault(locale);
        User user = UserUtil.getUser(username);
        if(user.getCount()==null){
        	user.setCount(0);
        }
        user.setCount(user.getCount()+1);
        try {
			InetAddress localhost   =   InetAddress.getLocalHost();
			user.setIp(localhost.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
        
        user.setLogin_on(new Date(System.currentTimeMillis()));
        UserUtil.updateUser(user);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        password = encoder.encodePassword(password, AuthenticationFilter.SALT);
        if (user == null || !user.getPassword().equals(password)) {
            ResourceBundle rb = CommonUtil.getResourceBundle();
            String errorMessage = rb.getString("error.login.denied");
            throw new AuthenticationServiceException(errorMessage);
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        setDetails(request, authRequest);

       //return authRequest;
       return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        Object obj = request.getParameter(USERNAME);
        return null == obj ? "" : obj.toString();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        Object obj = request.getParameter(PASSWORD);
        return null == obj ? "" : obj.toString();
    }

    protected String obtainLanguage(HttpServletRequest request) {
        Object obj = request.getParameter(LANGUAGE);
        return null == obj ? "" : obj.toString();
    }

}

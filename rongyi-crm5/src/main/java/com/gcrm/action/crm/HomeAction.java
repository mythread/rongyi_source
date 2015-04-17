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

import java.util.Map;

import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.security.AuthenticationSuccessListener;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Home Action
 * 
 */
public class HomeAction extends ActionSupport {

	private static final long serialVersionUID = -2404576552417042445L;

	private Integer userID = null;
	
	/**
	 * Selects the entities
	 * 
	 * @return the SUCCESS result
	 */
	public String load() throws ServiceException {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		this.userID = loginUser.getId();
		return SUCCESS;
	}

	/**
	 * @return the userID
	 */
	public Integer getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Integer userID) {
		this.userID = userID;
	}

}

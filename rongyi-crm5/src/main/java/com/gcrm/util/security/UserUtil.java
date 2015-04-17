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
package com.gcrm.util.security;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.gcrm.dao.impl.BaseDao;
import com.gcrm.domain.BaseEntity;
import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IUserService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.spring.SpringContextUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * User util
 */
public class UserUtil {
    /**
     * Gets current login user name
     * 
     * @return curretn login user name
     */
	private static BaseDao<User>baseDao;
    public static String getUserName() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            UserDetails userDetails = (UserDetails) authentication
                    .getPrincipal();
            return userDetails.getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets user by user name
     * 
     * @param userName
     *            user name
     * @return user instance
     */
    public static User getUser(String userName) {
        IUserService userService = (IUserService) SpringContextUtil
                .getBean("userService");
        String hql="from User where name='"+userName+"'";
        
        /*StringBuilder hqlBuilder = new StringBuilder(
                "select new User(name,password) from User");
        hqlBuilder.append(" where name = ?");*/
        try {
            List<User> result = userService.findByHQL(hql);
            if (result == null) {
                return null;
            } else {
                return result.get(0);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void setAccessValue(Role role, User user) throws Exception {

        Field[] fields = role.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.startsWith("scope_") || fieldName.startsWith("view_")
                    || fieldName.startsWith("create_")
                    || fieldName.startsWith("update_")
                    || fieldName.startsWith("delete_")) {
                Integer valueOfRole = (Integer) BeanUtil.getFieldValue(role,
                        fieldName);
                if (valueOfRole == null) {
                    continue;
                }
                Integer valueOfUser = (Integer) BeanUtil.getFieldValue(user,
                        fieldName);
                switch (valueOfRole) {
                case Role.NOT_SET:
                    if (valueOfUser == null) {
                        BeanUtil.setFieldValue(user, fieldName, Role.NOT_SET);
                    }
                    break;
                case Role.ALL_OR_ENABLED:
                    if (valueOfUser == null
                            || valueOfUser != Role.OWNER_OR_DISABLED) {
                        BeanUtil.setFieldValue(user, fieldName,
                                Role.ALL_OR_ENABLED);
                    }
                    break;
                case Role.OWNER_OR_DISABLED:
                    BeanUtil.setFieldValue(user, fieldName,
                            Role.OWNER_OR_DISABLED);
                    break;
                }
            }
        }

    }

    public static void permissionCheck(String fieldName) throws Exception {
        User loginUser = UserUtil.getLoginUser();
        Integer value = (Integer) BeanUtil.getFieldValue(loginUser, fieldName);
        if (value != Role.ALL_OR_ENABLED) {
            ResourceBundle rb = CommonUtil.getResourceBundle();
            String errorMessage = rb.getString("access.nopermission");
            throw new AccessDeniedException(errorMessage);
        }
    }

    public static void scopeCheck(BaseEntity entity, String fieldName)
            throws Exception {
        User loginUser = UserUtil.getLoginUser();
        Integer value = (Integer) BeanUtil.getFieldValue(loginUser, fieldName);
        if (value == Role.OWNER_OR_DISABLED) {
            if (loginUser.getId().intValue() != entity.getOwner().getId()
                    .intValue()) {
                ResourceBundle rb = CommonUtil.getResourceBundle();
                String errorMessage = rb
                        .getString("access.nopermission.record");
                throw new AccessDeniedException(errorMessage);
            }
        }
    }

    public static User getLoginUser() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session
                .get(AuthenticationSuccessListener.LOGIN_USER);
        return loginUser;
    }
    
    public static void updateUser(User user){
    	baseDao.batchUpdate(user);
    }
	public static BaseDao<User> getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao<User> baseDao) {
		UserUtil.baseDao = baseDao;
	}

}

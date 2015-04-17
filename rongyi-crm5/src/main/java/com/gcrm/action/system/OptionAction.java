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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.gcrm.action.crm.BaseListAction;
import com.gcrm.domain.OptionBase;
import com.gcrm.service.IBaseService;
import com.gcrm.service.impl.OptionService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Manages the dropdown list
 * 
 */
public abstract class OptionAction<T extends OptionBase> extends BaseListAction
        implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;
    private OptionBase entity;
    private IBaseService<T> baseService;

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    @Override
    public String list() throws Exception {
        UserUtil.permissionCheck("view_system");
        SearchCondition searchCondition = getSearchCondition();
        SearchResult<T> result = getBaseService().getPaginationObjects(
                getEntityClass().getSimpleName(), searchCondition);
        List<T> optons = result.getResult();

        long totalRecords = result.getTotalRecords();

        // Constructs the JSON data
        String json = "{\"total\": " + totalRecords + ",\"rows\": [";
        int size = optons.size();
        for (int i = 0; i < size; i++) {
            OptionBase instance = optons.get(i);
            Integer id = instance.getId();
            String value = instance.getValue();
            String label_en_US = instance.getLabel_en_US();
            String label_zh_CN = instance.getLabel_zh_CN();
            int sequence = instance.getSequence();

            json += "{\"id\":\"" + id + "\",\"entity.id\":\"" + id
                    + "\",\"entity.value\":\"" + value
                    + "\",\"entity.label_en_US\":\""
                    + CommonUtil.fromNullToEmpty(label_en_US)
                    + "\",\"entity.label_zh_CN\":\""
                    + CommonUtil.fromNullToEmpty(label_zh_CN)
                    + "\",\"entity.sequence\":\"" + sequence + "\"}";
            if (i < size - 1) {
                json += ",";
            }
        }
        json += "]}";

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
        return null;
    }

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        OptionBase optionBase = this.getEntity();
        if (optionBase.getId() == null) {
            UserUtil.permissionCheck("create_system");
        } else {
            UserUtil.permissionCheck("update_system");
        }
        T instance = this.getEntityClass().newInstance();
        instance.setId(optionBase.getId());
        instance.setValue(optionBase.getValue());
        instance.setLabel_en_US(optionBase.getLabel_en_US());
        instance.setLabel_zh_CN(optionBase.getLabel_zh_CN());
        instance.setSequence(optionBase.getSequence());
        getBaseService().makePersistent(instance);
        OptionService.optionMap.remove(this.getEntityClass().getSimpleName());
        return SUCCESS;
    }

    /**
     * Deletes the selected entity.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        getBaseService().batchDeleteEntity(getEntityClass(),
                this.getSeleteIDs());
        OptionService.optionMap.remove(this.getEntityClass().getSimpleName());
        return SUCCESS;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String init() throws Exception {
        // Sets navigation history
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ArrayList navigationList = (ArrayList) session
                .getAttribute(Constant.NAVIGATION_HISTORY);
        if (navigationList == null) {
            navigationList = new ArrayList();
        }
        String entityLabel = getText("menu."
                + CommonUtil.lowerCaseString(this.getEntityName()) + ".title");
        String navigatoin = "<a href='" + Constant.APP_PATH
                + Constant.SYSTEM_NAMESPACE + "list" + this.getEntityName()
                + "Page.action'>" + entityLabel + "</a>";
        if (navigationList.contains(navigatoin)) {
            navigationList.remove(navigatoin);
        }
        navigationList.add(navigatoin);
        if (navigationList.size() > Constant.NAVIGATION_HISTORY_COUNT) {
            navigationList.remove(0);
        }
        session.setAttribute(Constant.NAVIGATION_HISTORY, navigationList);

        return SUCCESS;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void prepare() throws Exception {
        Map request = (Map) ActionContext.getContext().get("request");
        String entityName = getEntityName();
        request.put("entityName", entityName);
        String title = getText("title.grid."
                + CommonUtil.lowerCaseString(entityName));
        request.put("title", title);

    }

    @Override
    protected String getEntityName() {
        return getEntityClass().getSimpleName();
    }

    protected abstract Class<T> getEntityClass();

    // protected abstract IBaseService<T> getBaseService();

    /**
     * @return the entity
     */
    public OptionBase getEntity() {
        return entity;
    }

    /**
     * @param entity
     *            the entity to set
     */
    public void setEntity(OptionBase entity) {
        this.entity = entity;
    }

    /**
     * @return the baseService
     */
    public IBaseService<T> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<T> baseService) {
        this.baseService = baseService;
    }

}
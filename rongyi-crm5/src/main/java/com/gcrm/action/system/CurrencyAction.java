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

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.action.crm.BaseListAction;
import com.gcrm.domain.Currency;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Manages the Currency dropdown list
 * 
 */
public class CurrencyAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Currency> baseService;
    private Currency currency;

    private static final String CLAZZ = Currency.class.getSimpleName();

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    @Override
    public String list() throws Exception {
        UserUtil.permissionCheck("view_system");
        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Currency> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        List<Currency> currencys = result.getResult();

        long totalRecords = result.getTotalRecords();

        // Constructs the JSON data
        String json = "{\"total\": " + totalRecords + ",\"rows\": [";
        int size = currencys.size();
        for (int i = 0; i < size; i++) {
            Currency instance = currencys.get(i);
            Integer id = instance.getId();
            String name = CommonUtil.fromNullToEmpty(instance.getName());
            String code = CommonUtil.fromNullToEmpty(instance.getCode());
            Double rate = instance.getRate();
            String rateS = "";
            if (rate != null) {
                rateS = String.valueOf(rate);
            }
            String symbol = CommonUtil.fromNullToEmpty(instance.getSymbol());
            String status = CommonUtil.fromNullToEmpty(instance.getStatus());

            json += "{\"id\":\"" + id + "\",\"currency.id\":\"" + id
                    + "\",\"currency.name\":\"" + name
                    + "\",\"currency.code\":\"" + code
                    + "\",\"currency.rate\":\"" + rateS
                    + "\",\"currency.symbol\":\"" + symbol
                    + "\",\"currency.status\":\"" + status + "\"}";
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
        if (currency.getId() == null) {
            UserUtil.permissionCheck("create_system");
        } else {
            UserUtil.permissionCheck("update_system");
        }
        getbaseService().makePersistent(currency);
        return SUCCESS;
    }

    /**
     * Deletes the selected entity.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        baseService.batchDeleteEntity(Currency.class, this.getSeleteIDs());
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return Currency.class.getSimpleName();
    }

    public IBaseService<Currency> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Currency> baseService) {
        this.baseService = baseService;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}

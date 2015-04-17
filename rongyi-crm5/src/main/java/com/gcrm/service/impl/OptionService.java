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
package com.gcrm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gcrm.domain.OptionBase;
import com.gcrm.service.IOptionService;

/**
 * Lead service
 */
public class OptionService<T extends OptionBase> extends BaseService<T>
        implements IOptionService<T> {
    @SuppressWarnings("rawtypes")
    public static Map<String, List> optionMap = new HashMap<String, List>();
    @SuppressWarnings("rawtypes")
    public static Map<String, Map> itemsMap = new HashMap<String, Map>();

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IOptionService#getOptions(java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> getOptions(String clazz, String local) throws Exception {
        List<T> options = null;
        if (optionMap.containsKey(clazz)) {
            options = optionMap.get(clazz);
        } else {
            options = this.getBaseDao().getAllSortedObjects(clazz, "sequence",
                    "asc");
            optionMap.put(clazz, options);
            Map<Integer, T> itemMap = new HashMap<Integer, T>();
            for (T option : options) {
                itemMap.put(option.getId(), option);
            }
            itemsMap.put(clazz, itemMap);
        }

        for (T option : options) {
            if ("zh_CN".equals(local)) {
                option.setLabel(option.getLabel_zh_CN());
            } else {
                option.setLabel(option.getLabel_en_US());
            }
        }
        return options;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IOptionService#getOptionById(java.lang.Class,
     * java.lang.Integer, java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public T getOptionById(Class<T> entityClass, Integer id, String local) {
        T option = this.getBaseDao().getEntityById(entityClass, id);
        if ("zh_CN".equals(local)) {
            option.setLabel(option.getLabel_zh_CN());
        } else {
            option.setLabel(option.getLabel_en_US());
        }
        return option;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IOptionService#getOptionById(java.lang.Class,
     * java.lang.Integer)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public T getOptionById(Class<T> entityClass, Integer id) {
        T option = null;
        String clazz = entityClass.getSimpleName();
        if (itemsMap.containsKey(clazz)) {
            Map itemMap = itemsMap.get(clazz);
            option = (T) itemMap.get(id);
        } else {
            option = this.getBaseDao().getEntityById(entityClass, id);
        }

        return option;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IOptionService#findByValue(java.lang.String,
     * java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public T findByValue(String clazz, String value) {
        String hql = "from " + clazz + " where value = ?";
        T object = null;
        List<T> result = null;

        result = findByParam(hql, value);
        if (result != null && result.size() > 0) {
            object = result.get(0);
        }
        return object;
    }
}

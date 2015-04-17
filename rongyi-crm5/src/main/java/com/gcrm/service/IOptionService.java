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
package com.gcrm.service;

import java.util.List;

import com.gcrm.domain.OptionBase;

/**
 * Option service Interface
 */
public interface IOptionService<T extends OptionBase> extends IBaseService<T> {

    /**
     * Gets option list
     * 
     * @param clazz
     * @param local
     * @return option list
     * @throws Exception
     */
    public List<T> getOptions(String clazz, String local) throws Exception;

    /**
     * Gets option by ID and set label according local
     * 
     * @param entityClass
     * @param id
     * @param local
     * @return option
     */
    public T getOptionById(Class<T> entityClass, Integer id, String local);

    /**
     * Gets option by ID
     * 
     * @param entityClass
     * @param id
     * @return option
     */
    public T getOptionById(Class<T> entityClass, Integer id);

    /**
     * Finds option by value
     * 
     * @param clazz
     * @param value
     * @return option
     */
    public T findByValue(String clazz, String value);

}

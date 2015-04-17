/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.gcrm.exception.ServiceException;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Base service Interface
 */
public interface IBaseService<T extends Serializable> {

    /**
     * Gets all entity instances
     * 
     * @param clazz class name
     * @return result list
     */
    public List<T> getAllObjects(String clazz);

    /**
     * Gets all entity instances with select columns
     * 
     * @param clazz class name
     * @param columns column name list
     * @return result list
     */
    public List<T> getAllObjects(String clazz, String columns);

    /**
     * Persists entity
     * 
     * @param entity entity instance
     */
    public T makePersistent(T entity);

    /**
     * Batch updates entities
     * 
     * @param entities entity instance collection
     */
    public void batchUpdate(Collection<T> entities);

    /**
     * Deletes entity by id
     * 
     * @param entityClass entity class
     * @param id entity id
     */
    public void deleteEntity(Class<T> entityClass, Integer id);

    /**
     * Batch deletes entities
     * 
     * @param entityClass entity class
     * @param seleteIDs selected entities' id
     * @throws ServiceException
     */
    public void batchDeleteEntity(Class<T> entityClass, String seleteIDs) throws ServiceException;

    /**
     * Gets entity by id
     * 
     * @param entityClass entity class
     * @param id entity instance id
     * @return entity instance
     */
    public T getEntityById(Class<T> entityClass, Integer id);

    /**
     * Finds records according to name
     * 
     * @param clazz class name
     * @param name entity name
     * @return entity instance
     * @throws Exception
     */
    public T findByName(String clazz, String name);

    /**
     * Finds records by hql with one parameter
     * 
     * @param hql hql with parameters
     * @param paramValue parameter value
     * @return result list
     * @throws Exception
     */
    public List<T> findByParam(String hql, Object paramValue);

    /**
     * Gets record counts by hql with parameters
     * 
     * @param hql hql with parameters
     * @param paramValues parameter value array
     * @return result count number
     * @throws Exception
     */
    public long countsByParams(String hql, Object[] paramValues);

    /**
     * Finds records by hql
     * 
     * @param hql hql with parameters
     * @return result list
     * @throws Exception
     */
    public List<T> findByHQL(String hql);

    /**
     * Finds record detail by hql
     * 
     * @param hql hql with parameters
     * @return result list
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public List findVOByHQL(String hql);

    /**
     * Finds record detail by hql with params
     * 
     * @param hql hql with parameters
     * @param paramValues parameter value array
     * @return result list
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public List findVOByParams(String hql, Object[] paramValues);

    /**
     * Gets object count
     * 
     * @param clazz entity class name
     * @return object count
     */
    public long getObjectsCount(String clazz);

    /**
     * Gets pagination objects
     * 
     * @param clazz entity class name
     * @param searchCondition search condition
     * @return search result
     */
    public SearchResult<T> getPaginationObjects(final String clazz, final SearchCondition searchCondition);

    /**
     * Gets pagination objects with selected columns
     * 
     * @param clazz entity class name
     * @param columns column name list
     * @param searchCondition search condition
     * @return search result with selected columns
     */
    public SearchResult<T> getPaginationObjects(String clazz, String columns, SearchCondition searchCondition);

    /**
     * Gets all entity instances that meet condition
     * 
     * @param clazz class name
     * @param condition search condition
     * @return result list that meet condition
     */
    public List<T> getObjects(final String clazz, final String condition);

    /**
     * Gets all entity instances that meet condition with selected columns
     * 
     * @param clazz class name
     * @param columns column name list
     * @param condition search condition
     * @return result list that meet condition with selected columns
     */
    public List<T> getObjects(String clazz, String columns, String condition);

    public void updateByHql(String hql);
}

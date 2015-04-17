/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gcrm.dao.IBaseDao;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Base service
 */
@Transactional
public class BaseService<T extends Serializable> implements IBaseService<T> {

    private IBaseDao<T> baseDao;

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#getAllObjects(java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> getAllObjects(String clazz) {
        return baseDao.getAllObjects(clazz);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#makePersistent(java.io.Serializable)
     */
    public T makePersistent(T entity) {
        return baseDao.makePersistent(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#deleteEntity(java.lang.Class, java.lang.Integer)
     */
    public void deleteEntity(Class<T> entityClass, Integer id) {
        baseDao.deleteEntity(entityClass, id);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#batchDeleteEntity(java.lang.Class, java.lang.String)
     */
    public void batchDeleteEntity(Class<T> entityClass, String seleteIDs) throws ServiceException {
        if (seleteIDs != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String deleteid = ids[i];
                baseDao.deleteEntity(entityClass, Integer.valueOf(deleteid));
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#getEntityById(java.lang.Class, java.lang.Integer)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public T getEntityById(Class<T> entityClass, Integer id) {
        return baseDao.getEntityById(entityClass, id);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findByName(java.lang.String, java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public T findByName(String clazz, String name) {
        return baseDao.findByName(clazz, name);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#findByParam(java.lang.String, java.lang.Object)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> findByParam(String hql, Object paramValue) {
        return baseDao.findByParam(hql, paramValue);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#countsByParams(java.lang.String, java.lang.Object[])
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public long countsByParams(String hql, Object[] paramValues) {
        return baseDao.countsByParams(hql, paramValues);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#findByHQL(java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> findByHQL(String hql) {
        return baseDao.findByHQL(hql);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#findVOByHQL(java.lang.String)
     */
    @SuppressWarnings("rawtypes")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List findVOByHQL(String hql) {
        return baseDao.findVOByHQL(hql);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#findVOByParams(java.lang.String, java.lang.Object[])
     */
    @SuppressWarnings({ "rawtypes" })
    public List findVOByParams(String hql, Object[] paramValues) {

        return baseDao.findVOByParams(hql, paramValues);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#getObjectsCount(java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public long getObjectsCount(String clazz) {
        return baseDao.getObjectsCount(clazz);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#getPaginationObjects(java.lang.String, com.gcrm.vo.SearchCondition)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public SearchResult<T> getPaginationObjects(String clazz, final SearchCondition searchCondition) {

        return baseDao.getPaginationObjects(clazz, searchCondition);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#getObjects(java.lang.String, java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> getObjects(final String clazz, final String condition) {
        return baseDao.getObjects(clazz, condition);
    }

    public IBaseDao<T> getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(IBaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.service.IBaseService#batchUpdate(java.util.Collection)
     */
    @Override
    public void batchUpdate(Collection<T> entities) {
        baseDao.batchUpdate(entities);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> getAllObjects(String clazz, String columns) {
        return baseDao.getAllObjects(clazz, columns);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public SearchResult<T> getPaginationObjects(String clazz, String columns, SearchCondition searchCondition) {
        return baseDao.getPaginationObjects(clazz, columns, searchCondition);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> getObjects(String clazz, String columns, String condition) {
        return baseDao.getObjects(clazz, columns, condition);
    }

    public void updateByHql(String hql) {
        baseDao.upateByHql(hql);
    }

}

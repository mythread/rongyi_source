/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gcrm.dao.IBaseDao;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Base Dao
 */
public class BaseDao<T extends Serializable> extends HibernateDaoSupport implements IBaseDao<T> {

    private static String SELECT_HQL = "select ";
    private static String FROM_HQL   = "from ";

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getAllObjects(java.lang.String)
     */
    public List<T> getAllObjects(String clazz) {
        return getAllObjects(clazz, null);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getAllObjects(java.lang.String,java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllObjects(String clazz, String columns) {
        StringBuilder hqlBuilder = new StringBuilder("");
        if (columns != null) {
            hqlBuilder.append(SELECT_HQL).append(columns).append(" ");
        }

        hqlBuilder.append(FROM_HQL).append(clazz);
        List<T> objects = null;

        objects = getHibernateTemplate().find(hqlBuilder.toString());

        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getAllSortedObjects(java.lang.String, java.lang.String, java.lang.String)
     */
    public List<T> getAllSortedObjects(String clazz, String sortColumn, String order) {
        return getAllSortedObjects(clazz, null, sortColumn, order);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getAllSortedObjects(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllSortedObjects(String clazz, String columns, String sortColumn, String order) {
        StringBuilder hqlBuilder = new StringBuilder("");
        if (columns != null) {
            hqlBuilder.append(SELECT_HQL).append(columns).append(" ");
        }

        hqlBuilder.append(FROM_HQL).append(clazz).append(" order by ").append(sortColumn).append(" ").append(order);
        List<T> objects = null;

        objects = getHibernateTemplate().find(hqlBuilder.toString());

        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findByParam(java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public List<T> findByParam(String hql, Object paramValue) {

        List<T> objects = null;

        objects = getHibernateTemplate().find(hql, paramValue);
        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findByParams(java.lang.String, java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    public List<T> findByParams(String hql, Object[] paramValues) {

        List<T> objects = null;

        objects = getHibernateTemplate().find(hql, paramValues);
        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#countsByParams(java.lang.String, java.lang.Object[])
     */
    public long countsByParams(String hql, Object[] paramValues) {
        long count = 0;
        count = (Long) getHibernateTemplate().find(hql, paramValues).get(0);
        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findByHQL(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<T> findByHQL(String hql) {

        List<T> objects = null;

        objects = getHibernateTemplate().find(hql);
        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findVOByHQL(java.lang.String)
     */
    @SuppressWarnings({ "rawtypes" })
    public List findVOByHQL(String hql) {

        List objects = null;

        objects = getHibernateTemplate().find(hql);
        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findVOByParams(java.lang.String, java.lang.Object[])
     */
    @SuppressWarnings({ "rawtypes" })
    public List findVOByParams(String hql, Object[] paramValues) {

        List objects = null;

        objects = getHibernateTemplate().find(hql, paramValues);
        return objects;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#findByName(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public T findByName(String clazz, String name) {
        String hql = FROM_HQL + clazz + " where name = ?";
        T object = null;
        List result = null;

        result = findByParam(hql, name);
        if (result != null && result.size() > 0) {
            object = (T) result.get(0);
        }
        return object;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#makePersistent(java.io.Serializable)
     */
    public T makePersistent(T entity) {
        T result = (T) getHibernateTemplate().merge(entity);
        getHibernateTemplate().flush();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#batchUpdate(java.util.Collection)
     */
    public void batchUpdate(Collection<T> entities) {
        getHibernateTemplate().saveOrUpdateAll(entities);
        getHibernateTemplate().flush();
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#deleteEntity(java.lang.Class, java.lang.Integer)
     */
    public void deleteEntity(Class<T> entityClass, Integer id) {
        T entity = (T) getHibernateTemplate().get(entityClass, id);
        getHibernateTemplate().delete(entity);
        getHibernateTemplate().flush();
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getObjectsCount(java.lang.String)
     */
    public long getObjectsCount(String clazz) {
        String hql = "select count(*) from " + clazz;

        long count = 0;

        count = (Long) getHibernateTemplate().find(hql).get(0);

        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getEntityById(java.lang.Class, java.lang.Integer)
     */
    public T getEntityById(Class<T> entityClass, Integer id) {
        T entity = null;
        entity = (T) getHibernateTemplate().load(entityClass, id);
        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getPaginationObjects(java.lang.String, com.gcrm.vo.SearchCondition)
     */
    public SearchResult<T> getPaginationObjects(String clazz, SearchCondition searchCondition) {
        return getPaginationObjects(clazz, null, searchCondition);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getPaginationObjects(java.lang.String, java.lang.String, com.gcrm.vo.SearchCondition)
     */
    @SuppressWarnings("unchecked")
    public SearchResult<T> getPaginationObjects(final String clazz, final String columns,
                                                final SearchCondition searchCondition) {

        List<T> objects = null;

        final String condition = searchCondition.getCondition();

        objects = getHibernateTemplate().executeFind(new HibernateCallback() {

            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuilder hqlBuilder = new StringBuilder("");
                if (columns != null) {
                    hqlBuilder.append(SELECT_HQL).append(columns).append(" ");
                }
                hqlBuilder.append(FROM_HQL).append(clazz);
                if (condition != null && condition.length() > 0) {
                    hqlBuilder.append(" where ");
                    hqlBuilder.append(condition);
                }
                hqlBuilder.append(" order by ").append(searchCondition.getSidx()).append(" ").append(searchCondition.getSord());
                int pageSize = searchCondition.getPageSize();
                int pageNo = searchCondition.getPageNo();

                Query query = session.createQuery(hqlBuilder.toString());

                if (pageNo != 0 && pageSize != 0) {
                    int rowNumber = (pageNo - 1) * pageSize;
                    query.setFirstResult(rowNumber);
                    query.setMaxResults(pageSize);
                }
                List<T> list = query.list();

                return list;
            }
        });

        long count = 0;
        String countHql = "select count(*) from " + clazz;
        if (condition != null && condition.length() > 0) {
            countHql += " where ";
            countHql += condition;
        }

        count = (Long) getHibernateTemplate().find(countHql).get(0);
        SearchResult<T> result = new SearchResult<T>(count, objects);

        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getObjects(java.lang.String, java.lang.String)
     */
    public List<T> getObjects(String clazz, String condition) {
        return getObjects(null, clazz, condition);
    }

    /*
     * (non-Javadoc)
     * @see com.gcrm.dao.IBaseDao#getObjects(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<T> getObjects(final String clazz, final String columns, final String condition) {

        List<T> objects = null;

        objects = getHibernateTemplate().executeFind(new HibernateCallback() {

            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuilder hqlBuilder = new StringBuilder("");
                if (columns != null) {
                    hqlBuilder.append(SELECT_HQL).append(columns).append(" ");
                }
                hqlBuilder.append(FROM_HQL).append(clazz);
                if (condition != null && condition.length() > 0) {
                    hqlBuilder.append(" where ");
                    hqlBuilder.append(condition);
                }
                Query query = session.createQuery(hqlBuilder.toString());

                List<T> list = query.list();

                return list;
            }
        });

        return objects;
    }

    @Override
    public void batchUpdate(T entity) {
        getHibernateTemplate().update(entity);
        getHibernateTemplate().flush();
    }

    /**
     * 更新操作
     */
    @SuppressWarnings("unchecked")
    public void upateByHql(String hql) {
        final String str = hql;

        getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(str);
                query.executeUpdate();
                return null;
            }
        });
    }
}

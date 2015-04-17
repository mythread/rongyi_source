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
package com.gcrm.util;

import java.lang.reflect.Method;

/**
 * Bean util
 */
public class BeanUtil {

    /**
     * Gets specified field value of bean
     * 
     * @param bean
     *            java bean
     * @param fieldName
     *            field name
     * @return field value
     */
    public static Object getFieldValue(Object bean, String fieldName)
            throws Exception {
        Class<?> cls = bean.getClass();

        Method[] methods = cls.getDeclaredMethods();
        String fieldGetName = getGetFieldName(fieldName);
        if (!checkGetMethod(methods, fieldGetName)) {
            return null;
        }
        Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
        Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
        return fieldVal;
    }

    /**
     * Sets field value
     * 
     * @param bean
     * @param valMap
     * @throws Exception
     */
    public static void setFieldValue(Object bean, String fieldName, Object value)
            throws Exception {
        Class<?> cls = bean.getClass();
        Method[] methods = cls.getDeclaredMethods();
        String fieldSetName = getSetFieldName(fieldName);
        Method fieldSetMet = getSetMet(methods, fieldSetName);
        if (fieldSetMet == null) {
            throw new Exception("No fields are found");
        }

        if (null != value) {
            fieldSetMet.invoke(bean, value);
        }
    }

    /**
     * Gets matched method
     * 
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    public static Method getSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return met;
            }
        }
        return null;
    }

    /**
     * Checks if the method exists or not
     * 
     * @param methods
     *            methods array
     * @param fieldMethod
     *            field method
     * @return the flag to indicates if the method exists or not
     */
    public static boolean checkGetMethod(Method[] methods, String fieldMethod) {
        for (Method met : methods) {
            if (fieldMethod.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets get field method name
     * 
     * 
     * @param fieldName
     *            field name
     * @return get field method name
     */
    public static String getGetFieldName(String fieldName) {
        if (CommonUtil.isNullOrEmpty(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

    /**
     * Gets set field method name
     * 
     * @param fieldName
     * @return set field method name
     */
    public static String getSetFieldName(String fieldName) {
        if (CommonUtil.isNullOrEmpty(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }
}
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Date Time util
 */
public class DateTimeUtil {
    public static final int DATE_NONE = 0;
    public static final int DATE_TODAY = 1;
    public static final int DATE_YESTERDAY = 2;
    public static final int DATE_TOMORROW = 3;
    public static final int DATE_THIS_WEEK = 4;
    public static final int DATE_LAST_WEEK = 5;
    public static final int DATE_NEXT_WEEK = 6;
    public static final int DATE_THIS_MONTH = 7;
    public static final int DATE_LAST_MONTH = 8;
    public static final int DATE_NEXT_MONTH = 9;
    public static final int DATE_THIS_YEAR = 10;
    public static final int DATE_LAST_YEAR = 11;
    public static final int DATE_NEXT_YEAR = 12;
    private static String options = null;

    public static String getSelectOptions() {
        if (options == null) {
            StringBuilder optionBuider = new StringBuilder();
            ResourceBundle rb = CommonUtil.getResourceBundle();
            optionBuider.append(DateTimeUtil.DATE_NONE).append(":;")
                    .append(DateTimeUtil.DATE_TODAY).append(":")
                    .append(rb.getString("date.today")).append(";")
                    .append(DateTimeUtil.DATE_YESTERDAY).append(":")
                    .append(rb.getString("date.yesterday")).append(";")
                    .append(DateTimeUtil.DATE_TOMORROW).append(":")
                    .append(rb.getString("date.tomorrow")).append(";")
                    .append(DateTimeUtil.DATE_THIS_WEEK).append(":")
                    .append(rb.getString("date.thisWeek")).append(";")
                    .append(DateTimeUtil.DATE_LAST_WEEK).append(":")
                    .append(rb.getString("date.lastWeek")).append(";")
                    .append(DateTimeUtil.DATE_NEXT_WEEK).append(":")
                    .append(rb.getString("date.nextWeek")).append(";")
                    .append(DateTimeUtil.DATE_THIS_MONTH).append(":")
                    .append(rb.getString("date.thisMonth")).append(";")
                    .append(DateTimeUtil.DATE_LAST_MONTH).append(":")
                    .append(rb.getString("date.lastMonth")).append(";")
                    .append(DateTimeUtil.DATE_NEXT_MONTH).append(":")
                    .append(rb.getString("date.nextMonth")).append(";")
                    .append(DateTimeUtil.DATE_THIS_YEAR).append(":")
                    .append(rb.getString("date.thisYear")).append(";")
                    .append(DateTimeUtil.DATE_LAST_YEAR).append(":")
                    .append(rb.getString("date.lastYear")).append(";")
                    .append(DateTimeUtil.DATE_NEXT_YEAR).append(":")
                    .append(rb.getString("date.nextYear"));
            options = optionBuider.toString();
        }
        return options;
    }

    /**
     * Gets HQL for date scope search
     * 
     * @param key
     *            field name
     * @value value search value
     * @return the HQL for data scope search
     */
    public static String getDateScope(String key, int value) {
        if (DateTimeUtil.DATE_NONE == value) {
            return "";
        }
        StringBuilder condition = new StringBuilder("");
        Date startDate = null;
        Date endDate = null;
        String start = null;
        String end = null;
        Calendar cl = Calendar.getInstance();
        switch (value) {
        case DateTimeUtil.DATE_TODAY:
            startDate = cl.getTime();
            cl.add(Calendar.DAY_OF_MONTH, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_YESTERDAY:
            endDate = cl.getTime();
            cl.add(Calendar.DAY_OF_MONTH, -1);
            startDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_TOMORROW:
            cl.add(Calendar.DAY_OF_MONTH, 1);
            startDate = cl.getTime();
            cl.add(Calendar.DAY_OF_MONTH, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_THIS_WEEK:
            cl.set(Calendar.DAY_OF_WEEK, 1);
            cl.add(Calendar.DAY_OF_MONTH, 1);
            startDate = cl.getTime();
            cl.set(Calendar.DAY_OF_WEEK,
                    cl.getActualMaximum(Calendar.DAY_OF_WEEK));
            cl.add(Calendar.DAY_OF_MONTH, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_LAST_WEEK:
            cl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            endDate = cl.getTime();
            cl.add(Calendar.WEEK_OF_YEAR, -1);
            startDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_NEXT_WEEK:
            cl.add(Calendar.WEEK_OF_YEAR, 1);
            cl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            startDate = cl.getTime();
            cl.add(Calendar.DAY_OF_YEAR, 7);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_THIS_MONTH:
            cl.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cl.getTime();
            cl.set(Calendar.DAY_OF_MONTH,
                    cl.getActualMaximum(Calendar.DAY_OF_MONTH));
            cl.add(Calendar.DAY_OF_MONTH, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_LAST_MONTH:
            cl.add(Calendar.MONTH, -1);
            cl.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cl.getTime();
            cl = Calendar.getInstance();
            cl.set(Calendar.DAY_OF_MONTH, 0);
            cl.add(Calendar.DAY_OF_MONTH, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_NEXT_MONTH:
            cl.add(Calendar.MONTH, 1);
            cl.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cl.getTime();
            cl = Calendar.getInstance();
            cl.add(Calendar.MONTH, 2);
            cl.set(Calendar.DAY_OF_MONTH, 0);
            cl.add(Calendar.DAY_OF_MONTH, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_THIS_YEAR:
            cl.set(Calendar.DAY_OF_YEAR, 1);
            startDate = cl.getTime();
            cl = Calendar.getInstance();
            cl.add(Calendar.YEAR, 1);
            cl.set(Calendar.DAY_OF_YEAR, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_LAST_YEAR:
            cl.add(Calendar.YEAR, -1);
            cl.set(Calendar.DAY_OF_YEAR, 1);
            startDate = cl.getTime();
            cl = Calendar.getInstance();
            cl.set(Calendar.DAY_OF_YEAR, 1);
            endDate = cl.getTime();
            break;
        case DateTimeUtil.DATE_NEXT_YEAR:
            cl.add(Calendar.YEAR, 1);
            cl.set(Calendar.DAY_OF_YEAR, 1);
            startDate = cl.getTime();
            cl = Calendar.getInstance();
            cl.add(Calendar.YEAR, 2);
            cl.set(Calendar.DAY_OF_YEAR, 1);
            endDate = cl.getTime();
            break;
        }

        SimpleDateFormat dd = new SimpleDateFormat(Constant.DATE_FORMAT);
        start = dd.format(startDate);
        end = dd.format(endDate);
        condition.append(key).append(">= '").append(start).append("' AND ")
                .append(key).append("< '").append(end).append("'");
        return condition.toString();
    }
}

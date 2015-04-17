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
package com.gcrm.action.crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Call;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.Role;
import com.gcrm.domain.Task;
import com.gcrm.domain.User;
import com.gcrm.domain.Visit;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.CalendarVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Manages the Calendar
 * 
 */
public class CalendarAction extends ActionSupport {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Call> callService;
    private IBaseService<Meeting> meetingService;
    private IBaseService<Visit> visitService;
    private IBaseService<Task> taskService;

    private String dateSelect;

    /**
     * Gets Calendar Contents
     * 
     * @return null
     */
    @SuppressWarnings("rawtypes")
    public String getCalendarInfo() throws Exception {
        SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleFormate.parse(dateSelect));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int mondayPlus = 0;
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }
        calendar.add(GregorianCalendar.DATE, mondayPlus);
        Date firstDay = calendar.getTime();
        calendar.add(Calendar.DATE, 35);
        Date lastDay = calendar.getTime();

        User loginUser = UserUtil.getLoginUser();
        List<CalendarVO> calendarVOs = new ArrayList<CalendarVO>();
        CalendarVO calendarVO = null;
        String subject = null;
        Date startDate = null;
        Date endDate = null;

        // Gets call records
        int callScope = loginUser.getScope_call();
        StringBuilder hqlBuilder = new StringBuilder(
                "select subject,start_date from Call call where start_date>=? and start_date<?");
        if (callScope == Role.OWNER_OR_DISABLED) {
            hqlBuilder.append(" and owner = ").append(loginUser.getId());
        }
        List calls = callService.findVOByParams(hqlBuilder.toString(),
                new Date[] { firstDay, lastDay });
        Iterator calllIter = calls.iterator();
        while (calllIter.hasNext()) {
            Object[] row = (Object[]) calllIter.next();
            calendarVO = new CalendarVO();
            subject = (String) row[0];
            startDate = (Date) row[1];
            calendarVO.setSubject(subject);
            calendarVO.setStartDate(startDate.getTime());
            calendarVO.setEndDate(startDate.getTime());
            calendarVO.setType(getText("entity.call.label"));
            calendarVO.setDescription("");
            calendarVOs.add(calendarVO);
        }

        // Gets meeting records
        int meetingScope = loginUser.getScope_meeting();
        hqlBuilder = new StringBuilder(
                "select subject,start_date,end_date,location from Meeting meeting where start_date>=? and start_date<?");
        if (meetingScope == Role.OWNER_OR_DISABLED) {
            hqlBuilder.append(" and owner = ").append(loginUser.getId());
        }
        List meetings = meetingService.findVOByParams(hqlBuilder.toString(),
                new Date[] { firstDay, lastDay });
        Iterator meetingIter = meetings.iterator();
        String location = null;
        StringBuilder descriptionBuilder = null;
        while (meetingIter.hasNext()) {
            Object[] row = (Object[]) meetingIter.next();
            calendarVO = new CalendarVO();
            subject = (String) row[0];
            startDate = (Date) row[1];
            endDate = (Date) row[2];
            location = (String) row[3];
            descriptionBuilder = new StringBuilder("");
            descriptionBuilder.append(CommonUtil.fromNullToEmpty(location));
            calendarVO.setSubject(subject);
            calendarVO.setStartDate(startDate.getTime());
            calendarVO.setEndDate(endDate.getTime());
            calendarVO.setType(getText("entity.meeting.label"));
            calendarVO.setDescription(descriptionBuilder.toString());
            calendarVOs.add(calendarVO);
        }
        // Gets visit records
        int visitScope = loginUser.getScope_visit();
        hqlBuilder = new StringBuilder(
        		"select subject,start_date,end_date,location from Visit visit where start_date>=? and start_date<?");
        if (visitScope == Role.OWNER_OR_DISABLED) {
        	hqlBuilder.append(" and owner = ").append(loginUser.getId());
        }
        List visits = visitService.findVOByParams(hqlBuilder.toString(),
        		new Date[] { firstDay, lastDay });
        Iterator visitIter = visits.iterator();
        String location1 = null;
        StringBuilder descriptionBuilder1 = null;
        while (visitIter.hasNext()) {
        	Object[] row = (Object[]) visitIter.next();
        	calendarVO = new CalendarVO();
        	subject = (String) row[0];
        	startDate = (Date) row[1];
        	endDate = (Date) row[2];
        	location1 = (String) row[3];
        	descriptionBuilder1 = new StringBuilder("");
        	descriptionBuilder1.append(CommonUtil.fromNullToEmpty(location1));
        	calendarVO.setSubject(subject);
        	calendarVO.setStartDate(startDate.getTime());
        	calendarVO.setEndDate(endDate.getTime());
        	calendarVO.setType(getText("entity.visit.label"));
        	calendarVO.setDescription(descriptionBuilder1.toString());
        	calendarVOs.add(calendarVO);
        }

        // Gets task records
        int taskScope = loginUser.getScope_task();
        hqlBuilder = new StringBuilder(
                "select subject,start_date,due_date from Task task where start_date>=? and start_date<?");
        if (taskScope == Role.OWNER_OR_DISABLED) {
            hqlBuilder.append(" and owner = ").append(loginUser.getId());
        }
        List tasks = taskService.findVOByParams(hqlBuilder.toString(),
                new Date[] { firstDay, lastDay });
        Iterator taskIter = tasks.iterator();
        while (taskIter.hasNext()) {
            Object[] row = (Object[]) taskIter.next();
            calendarVO = new CalendarVO();
            subject = (String) row[0];
            startDate = (Date) row[1];
            endDate = (Date) row[2];
            calendarVO.setSubject(subject);
            calendarVO.setStartDate(startDate.getTime());
            calendarVO.setEndDate(endDate.getTime());
            calendarVO.setType(getText("entity.task.label"));
            calendarVO.setDescription("");
            calendarVOs.add(calendarVO);
        }

        JSONArray result = new JSONArray();
        if (calendarVOs.size() > 0) {
            for (CalendarVO t : calendarVOs) {
                result.add(t);
            }
        }

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(result.toString());
        return null;
    }

    /**
     * @return the dateSelect
     */
    public String getDateSelect() {
        return dateSelect;
    }

    /**
     * @param dateSelect
     *            the dateSelect to set
     */
    public void setDateSelect(String dateSelect) {
        this.dateSelect = dateSelect;
    }

    /**
     * @return the callService
     */
    public IBaseService<Call> getCallService() {
        return callService;
    }

    /**
     * @param callService
     *            the callService to set
     */
    public void setCallService(IBaseService<Call> callService) {
        this.callService = callService;
    }

    /**
     * @return the meetingService
     */
    public IBaseService<Meeting> getMeetingService() {
        return meetingService;
    }

    /**
     * @param meetingService
     *            the meetingService to set
     */
    public void setMeetingService(IBaseService<Meeting> meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * @return the taskService
     */
    public IBaseService<Task> getTaskService() {
        return taskService;
    }

    /**
     * @param taskService
     *            the taskService to set
     */
    public void setTaskService(IBaseService<Task> taskService) {
        this.taskService = taskService;
    }

	public IBaseService<Visit> getVisitService() {
		return visitService;
	}

	public void setVisitService(IBaseService<Visit> visitService) {
		this.visitService = visitService;
	}

//    /**
//     * @return the callService
//     */
//    public IBaseService<Call> getCallService() {
//        return callService;
//    }
//
//    /**
//     * @param callService the callService to set
//     */
//    public void setCallService(IBaseService<Call> callService) {
//        this.callService = callService;
//    }
}

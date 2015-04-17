package com.gcrm.util.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import com.gcrm.domain.Call;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.Visit;
import com.gcrm.service.IBaseService;
import com.gcrm.service.ICallService;
import com.gcrm.service.IMeetingService;
import com.gcrm.service.IVisitService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.mail.MailService;

public class MailRemind {

    private ICallService callService;
    private IMeetingService meetingService;
    private IVisitService visitService;
    private MailService mailService;
    private IBaseService<EmailTemplate> emailTemplateService;

    /**
     * Gets the scheduled instances for example calls or meetings, then schedule
     * them.
     * 
     */
    public void getRemindInstances() throws Exception {
        Calendar calendarEnd = Calendar.getInstance();
        Date now = calendarEnd.getTime();
        calendarEnd.add(Calendar.HOUR_OF_DAY, 1);
        Date dateEnd = calendarEnd.getTime();

        List<Call> calls = callService.findScheduleCalls(new Date());
        for (Call call : calls) {
            Date startDate = call.getStart_date();
            ReminderOption reminderOption = call.getReminder_option_email();
            String value = reminderOption.getValue();
            if (!CommonUtil.isNullOrEmpty(value)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MINUTE, -Integer.parseInt(value));
                Date dueDateTime = calendar.getTime();
                if (dueDateTime.after(now) && dueDateTime.before(dateEnd)) {
                    Timer timer = new Timer();
                    MailTimerTask task = new MailTimerTask();
                    task.setMailService(mailService);
                    task.setEmailTemplateService(emailTemplateService);
                    task.setCall(call);
                    timer.schedule(task, dueDateTime);

                }
            }
        }

        List<Meeting> meetings = meetingService
                .findScheduleMeetings(new Date());
        for (Meeting meeting : meetings) {
            Date startDate = meeting.getStart_date();
            ReminderOption reminderOption = meeting.getReminder_option_email();
            String value = reminderOption.getValue();
            if (!CommonUtil.isNullOrEmpty(value)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MINUTE, -Integer.parseInt(value));
                Date dueDateTime = calendar.getTime();
                if (dueDateTime.after(now) && dueDateTime.before(dateEnd)) {
                    Timer timer = new Timer();
                    MailTimerTask task = new MailTimerTask();
                    task.setMailService(mailService);
                    task.setMeeting(meeting);
                    timer.schedule(task, dueDateTime);

                }
            }
        }
        List<Visit> visits = visitService
        		.findScheduleVisits(new Date());
        for (Visit visit : visits) {
        	Date startDate = visit.getStart_date();
        	ReminderOption reminderOption = visit.getReminder_option_email();
        	String value = reminderOption.getValue();
        	if (!CommonUtil.isNullOrEmpty(value)) {
        		Calendar calendar = Calendar.getInstance();
        		calendar.setTime(startDate);
        		calendar.add(Calendar.MINUTE, -Integer.parseInt(value));
        		Date dueDateTime = calendar.getTime();
        		if (dueDateTime.after(now) && dueDateTime.before(dateEnd)) {
        			Timer timer = new Timer();
        			MailTimerTask task = new MailTimerTask();
        			task.setMailService(mailService);
        			task.setVisit(visit);
        			timer.schedule(task, dueDateTime);
        			
        		}
        	}
        }

    }

    /**
     * @return the mailService
     */
    public MailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * @return the callService
     */
    public ICallService getCallService() {
        return callService;
    }

    /**
     * @param callService
     *            the callService to set
     */
    public void setCallService(ICallService callService) {
        this.callService = callService;
    }

    /**
     * @return the meetingService
     */
    public IMeetingService getMeetingService() {
        return meetingService;
    }

    /**
     * @param meetingService
     *            the meetingService to set
     */
    public void setMeetingService(IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * @return the emailTemplateService
     */
    public IBaseService<EmailTemplate> getEmailTemplateService() {
        return emailTemplateService;
    }

    /**
     * @param emailTemplateService
     *            the emailTemplateService to set
     */
    public void setEmailTemplateService(
            IBaseService<EmailTemplate> emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

	public IVisitService getVisitService() {
		return visitService;
	}

	public void setVisitService(IVisitService visitService) {
		this.visitService = visitService;
	}

}

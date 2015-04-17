package com.gcrm.util.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimerTask;

import com.gcrm.domain.Call;
import com.gcrm.domain.Contact;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.User;
import com.gcrm.domain.Visit;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.mail.MailService;

public class MailTimerTask extends TimerTask {
    private Call call = null;
    private Meeting meeting = null;
    private Visit visit = null;
    private MailService mailService;
    private IBaseService<EmailTemplate> emailTemplateService;

    @Override
    public void run() {
        ResourceBundle rb = CommonUtil.getResourceBundle();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);

        if (call != null) {
            sendCallMail(call, rb, dateFormat);
        } else if (meeting != null) {
            sendMeetingMail(meeting, rb, dateFormat);
        }else{
        	sendVisitMail(visit, rb, dateFormat);
        }

    }

    private void sendCallMail(Call call, ResourceBundle rb,
            SimpleDateFormat dateFormat) {
        Date start_date = call.getStart_date();
        String startDateS = "";
        if (start_date != null) {
            startDateS = dateFormat.format(start_date);
        }

        StringBuilder targetEmails = new StringBuilder("");
        Set<Contact> contacts = call.getContacts();
        if (contacts != null) {
            for (Contact contact : contacts) {
                String email = contact.getOffice_email();
                if (CommonUtil.isNullOrEmpty(email)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        Set<Lead> leads = call.getLeads();
        if (leads != null) {
            for (Lead lead : leads) {
                String email = lead.getEmail();
                if (CommonUtil.isNullOrEmpty(email)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        User owner = call.getOwner();
        String from = null;
        if (owner != null) {
            from = owner.getEmail();
        }
        Set<User> users = call.getUsers();
        if (users != null) {
            for (User user : users) {
                String email = user.getEmail();
                if (CommonUtil.isNullOrEmpty(email) || email.endsWith(from)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        if (targetEmails.length() > 0) {
            EmailTemplate remindEmailTemplte = call.getReminder_template();
            String targetEmail = targetEmails.toString();
            String[] to = targetEmail.split(",");
            String mailSubject = remindEmailTemplte.getSubject();
            String content = "";
            if (remindEmailTemplte.isText_only()) {
                content = remindEmailTemplte.getText_body();
            } else {
                content = remindEmailTemplte.getHtml_body();
            }
            // Replaces the variable in the body
            if (content != null) {
                content = content.replaceAll("\\$call.subject",
                        CommonUtil.fromNullToEmpty(call.getSubject()));
                content = content.replaceAll("\\$call.start_date", startDateS);
            }
            if (CommonUtil.isNullOrEmpty(from)) {
                from = null;
            }
            mailService.asynSendHtmlMail(from, to, mailSubject, content, null,
                    null);
        }
    }

    private void sendMeetingMail(Meeting meeting, ResourceBundle rb,
            SimpleDateFormat dateFormat) {
        Date start_date = meeting.getStart_date();
        String startDateS = "";
        if (start_date != null) {
            startDateS = dateFormat.format(start_date);
        }
        Date end_date = meeting.getEnd_date();
        String endDateS = "";
        if (end_date != null) {
            endDateS = dateFormat.format(end_date);
        }

        StringBuilder targetEmails = new StringBuilder("");
        Set<Contact> contacts = meeting.getContacts();
        if (contacts != null) {
            for (Contact contact : contacts) {
                String email = contact.getOffice_email();
                if (CommonUtil.isNullOrEmpty(email)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        Set<Lead> leads = meeting.getLeads();
        if (leads != null) {
            for (Lead lead : leads) {
                String email = lead.getEmail();
                if (CommonUtil.isNullOrEmpty(email)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        User owner = meeting.getOwner();
        String from = null;
        if (owner != null) {
            from = owner.getEmail();
        }
        Set<User> users = meeting.getUsers();
        if (users != null) {
            for (User user : users) {
                String email = user.getEmail();
                if (CommonUtil.isNullOrEmpty(email) || email.endsWith(from)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }

        if (targetEmails.length() > 0) {
            EmailTemplate remindEmailTemplte = meeting.getReminder_template();
            String targetEmail = targetEmails.toString();
            String[] to = targetEmail.split(",");
            String mailSubject = remindEmailTemplte.getSubject();

            String content = "";
            if (remindEmailTemplte.isText_only()) {
                content = remindEmailTemplte.getText_body();
            } else {
                content = remindEmailTemplte.getHtml_body();
            }
            // Replaces the variable in the body
            if (content != null) {
                content = content.replaceAll("\\$meeting.subject",
                        CommonUtil.fromNullToEmpty(meeting.getSubject()));
                content = content.replaceAll("\\$meeting.start_date",
                        startDateS);
                content = content.replaceAll("\\$meeting.end_date", endDateS);
                content = content.replaceAll("\\$meeting.location",
                        CommonUtil.fromNullToEmpty(meeting.getLocation()));
            }

            if (CommonUtil.isNullOrEmpty(from)) {
                from = null;
            }
            mailService.asynSendHtmlMail(from, to, mailSubject, content, null,
                    null);
        }
    }
    private void sendVisitMail(Visit visit, ResourceBundle rb,
    		SimpleDateFormat dateFormat) {
    	Date start_date = visit.getStart_date();
    	String startDateS = "";
    	if (start_date != null) {
    		startDateS = dateFormat.format(start_date);
    	}
    	Date end_date = visit.getEnd_date();
    	String endDateS = "";
    	if (end_date != null) {
    		endDateS = dateFormat.format(end_date);
    	}
    	
    	StringBuilder targetEmails = new StringBuilder("");
    	Set<Contact> contacts = visit.getContacts();
    	if (contacts != null) {
    		for (Contact contact : contacts) {
    			String email = contact.getOffice_email();
    			if (CommonUtil.isNullOrEmpty(email)) {
    				continue;
    			}
    			if (targetEmails.length() > 0) {
    				targetEmails.append(",");
    			}
    			targetEmails.append(email);
    		}
    	}
    	Set<Lead> leads = visit.getLeads();
    	if (leads != null) {
    		for (Lead lead : leads) {
    			String email = lead.getEmail();
    			if (CommonUtil.isNullOrEmpty(email)) {
    				continue;
    			}
    			if (targetEmails.length() > 0) {
    				targetEmails.append(",");
    			}
    			targetEmails.append(email);
    		}
    	}
    	User owner = visit.getOwner();
    	String from = null;
    	if (owner != null) {
    		from = owner.getEmail();
    	}
    	Set<User> users = visit.getUsers();
    	if (users != null) {
    		for (User user : users) {
    			String email = user.getEmail();
    			if (CommonUtil.isNullOrEmpty(email) || email.endsWith(from)) {
    				continue;
    			}
    			if (targetEmails.length() > 0) {
    				targetEmails.append(",");
    			}
    			targetEmails.append(email);
    		}
    	}
    	
    	if (targetEmails.length() > 0) {
    		EmailTemplate remindEmailTemplte = visit.getReminder_template();
    		String targetEmail = targetEmails.toString();
    		String[] to = targetEmail.split(",");
    		String mailSubject = remindEmailTemplte.getSubject();
    		
    		String content = "";
    		if (remindEmailTemplte.isText_only()) {
    			content = remindEmailTemplte.getText_body();
    		} else {
    			content = remindEmailTemplte.getHtml_body();
    		}
    		// Replaces the variable in the body
    		if (content != null) {
    			content = content.replaceAll("\\$visit.subject",
    					CommonUtil.fromNullToEmpty(visit.getSubject()));
    			content = content.replaceAll("\\$visit.start_date",
    					startDateS);
    			content = content.replaceAll("\\$visit.end_date", endDateS);
    			content = content.replaceAll("\\$visit.location",
    					CommonUtil.fromNullToEmpty(visit.getLocation()));
    		}
    		
    		if (CommonUtil.isNullOrEmpty(from)) {
    			from = null;
    		}
    		mailService.asynSendHtmlMail(from, to, mailSubject, content, null,
    				null);
    	}
    }

    /**
     * @return the call
     */
    public Call getCall() {
        return call;
    }

    /**
     * @param call
     *            the call to set
     */
    public void setCall(Call call) {
        this.call = call;
    }

    /**
     * @return the meeting
     */
    public Meeting getMeeting() {
        return meeting;
    }

    /**
     * @param meeting
     *            the meeting to set
     */
    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
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

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}
}

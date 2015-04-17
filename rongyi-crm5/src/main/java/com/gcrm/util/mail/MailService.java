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
package com.gcrm.util.mail;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.gcrm.domain.EmailSetting;
import com.gcrm.service.IBaseService;

/**
 * Mail service
 */
public class MailService {
    private TaskExecutor taskExecutor;
    private IBaseService<EmailSetting> baseService;

    public void sendSimpleMail(String toAddress) throws Exception {
        List<EmailSetting> emailSettings = baseService
                .getAllObjects(EmailSetting.class.getSimpleName());
        EmailSetting emailSetting = null;
        if (emailSettings != null && emailSettings.size() > 0) {
            emailSetting = emailSettings.get(0);
        } else {
            return;
        }
        Session mailSession = this.createSmtpSession(emailSetting);
        if (mailSession != null) {
            Transport transport = mailSession.getTransport();
            MimeMessage msg = new MimeMessage(mailSession);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
            helper.setFrom(emailSetting.getFrom_address());
            helper.setTo(toAddress);
            helper.setSubject("Test Mail From " + emailSetting.getFrom_name());
            helper.setText(
                    "This is test mail from " + emailSetting.getFrom_name(),
                    true);
            transport.connect(emailSetting.getSmtp_username(), emailSetting.getSmtp_password());
            transport.sendMessage(msg,
                    msg.getRecipients(Message.RecipientType.TO));
        }
    }

    public void sendSystemSimpleMail(String toAddress, String subject,
            String text) throws Exception {
        List<EmailSetting> emailSettings = baseService
                .getAllObjects(EmailSetting.class.getSimpleName());
        EmailSetting emailSetting = null;
        if (emailSettings != null && emailSettings.size() > 0) {
            emailSetting = emailSettings.get(0);
        } else {
            return;
        }
        Session mailSession = this.createSmtpSession(emailSetting);
        if (mailSession != null) {
            Transport transport = mailSession.getTransport();
            MimeMessage msg = new MimeMessage(mailSession);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
            helper.setFrom(emailSetting.getFrom_address());
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(text, true);
            transport.connect();
            transport.sendMessage(msg,
                    msg.getRecipients(Message.RecipientType.TO));
        }
    }

    public void asynSendHtmlMail(final String from, final String[] to,
            final String subject, final String text, final String[] fileNames,
            final File[] files) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    sendHtmlMail(from, to, subject, text, fileNames, files);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void sendHtmlMail(String from, String[] to, String subject,
            String text, String[] fileNames, File[] files) throws Exception {
        List<EmailSetting> emailSettings = baseService
                .getAllObjects(EmailSetting.class.getSimpleName());
        EmailSetting emailSetting = null;
        if (emailSettings != null && emailSettings.size() > 0) {
            emailSetting = emailSettings.get(0);
        } else {
            return;
        }
        if (from == null) {
            from = emailSetting.getFrom_address();
        }
        Session mailSession = createSmtpSession(emailSetting);

        if (mailSession != null) {
            Transport transport = mailSession.getTransport();
            MimeMessage msg = new MimeMessage(mailSession);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            if (fileNames != null && files != null) {
                String fileName = null;
                File file = null;
                for (int i = 0; i < fileNames.length; i++) {
                    fileName = fileNames[i];
                    file = files[i];
                    if (fileName != null && file != null) {
                        helper.addAttachment(fileName, file);
                    }
                }
            }
            transport.connect();
            transport.sendMessage(msg,
                    msg.getRecipients(Message.RecipientType.TO));
        }

    }

    private Session createSmtpSession(EmailSetting emailSetting) {
        final Properties props = new Properties();

        int emailProvider = emailSetting.getEmail_provider();
        String smtpUsername = null;
        String smtpPassword = null;
        switch (emailProvider) {
        case EmailSetting.PROVIDER_GMAIL:
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.port", "" + 587);
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.starttls.enable", "true");
            smtpUsername = emailSetting.getGmail_address();
            smtpPassword = emailSetting.getGmail_password();
            break;
        case EmailSetting.PROVIDER_YAHOO:
            props.setProperty("mail.host", "smtp.mail.yahoo.com");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.port", "" + 587);
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.starttls.enable", "true");
            smtpUsername = emailSetting.getYahoo_mail_ID();
            smtpPassword = emailSetting.getYahoo_mail_password();
            break;
        case EmailSetting.PROVIDER_OTHER:
            props.setProperty("mail.host", emailSetting.getSmtp_server());
            props.setProperty("mail.smtp.auth",
                    String.valueOf(emailSetting.isSmtp_authentication()));
            props.setProperty("mail.smtp.port",
                    "" + emailSetting.getSmtp_port());
            smtpUsername=emailSetting.getSmtp_username();
            smtpPassword=emailSetting.getSmtp_password();
            switch (emailSetting.getSmtp_protocol()) {
            case EmailSetting.PROTOCOL_SSL:
                props.setProperty("mail.transport.protocol", "smtps");
                break;
            case EmailSetting.PROTOCOL_TLS:
                props.setProperty("mail.transport.protocol", "smtp");
                props.setProperty("mail.smtp.starttls.enable", "true");
                break;
            default:
                props.setProperty("mail.transport.protocol", "smtp");
                break;
            }
            break;
        }

        final String userName = smtpUsername;
        final String password = smtpPassword;
        return Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });
    }

    /**
     * @param taskExecutor
     *            the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @return the baseService
     */
    public IBaseService<EmailSetting> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<EmailSetting> baseService) {
        this.baseService = baseService;
    }

}

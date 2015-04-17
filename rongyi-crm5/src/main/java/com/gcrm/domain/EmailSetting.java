package com.gcrm.domain;

import java.io.Serializable;

public class EmailSetting extends BaseEntity implements Serializable {
    public static final int PROVIDER_GMAIL = 1;
    public static final int PROVIDER_YAHOO = 2;
    public static final int PROVIDER_OTHER = 3;
    public static final int PROTOCOL_NONE = 1;
    public static final int PROTOCOL_SSL = 2;
    public static final int PROTOCOL_TLS = 3;
    public final static String STATUS_SENT = "sent";

    private static final long serialVersionUID = 8250950813769457555L;

    private String from_name;
    private String from_address;
    private Integer email_provider;
    private String gmail_address;
    private String gmail_password;
    private String yahoo_mail_ID;
    private String yahoo_mail_password;
    private String smtp_server;
    private Integer smtp_port;
    private boolean smtp_authentication;
    private Integer smtp_protocol;
    private String smtp_username;
    private String smtp_password;

    /**
     * @return the from_name
     */
    public String getFrom_name() {
        return from_name;
    }

    /**
     * @param from_name
     *            the from_name to set
     */
    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    /**
     * @return the from_address
     */
    public String getFrom_address() {
        return from_address;
    }

    /**
     * @param from_address
     *            the from_address to set
     */
    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    /**
     * @return the gmail_address
     */
    public String getGmail_address() {
        return gmail_address;
    }

    /**
     * @param gmail_address
     *            the gmail_address to set
     */
    public void setGmail_address(String gmail_address) {
        this.gmail_address = gmail_address;
    }

    /**
     * @return the gmail_password
     */
    public String getGmail_password() {
        return gmail_password;
    }

    /**
     * @param gmail_password
     *            the gmail_password to set
     */
    public void setGmail_password(String gmail_password) {
        this.gmail_password = gmail_password;
    }

    /**
     * @return the yahoo_mail_ID
     */
    public String getYahoo_mail_ID() {
        return yahoo_mail_ID;
    }

    /**
     * @param yahoo_mail_ID
     *            the yahoo_mail_ID to set
     */
    public void setYahoo_mail_ID(String yahoo_mail_ID) {
        this.yahoo_mail_ID = yahoo_mail_ID;
    }

    /**
     * @return the yahoo_mail_password
     */
    public String getYahoo_mail_password() {
        return yahoo_mail_password;
    }

    /**
     * @param yahoo_mail_password
     *            the yahoo_mail_password to set
     */
    public void setYahoo_mail_password(String yahoo_mail_password) {
        this.yahoo_mail_password = yahoo_mail_password;
    }

    /**
     * @return the smtp_server
     */
    public String getSmtp_server() {
        return smtp_server;
    }

    /**
     * @param smtp_server
     *            the smtp_server to set
     */
    public void setSmtp_server(String smtp_server) {
        this.smtp_server = smtp_server;
    }

    /**
     * @return the smtp_port
     */
    public Integer getSmtp_port() {
        return smtp_port;
    }

    /**
     * @param smtp_port
     *            the smtp_port to set
     */
    public void setSmtp_port(Integer smtp_port) {
        this.smtp_port = smtp_port;
    }

    /**
     * @return the smtp_authentication
     */
    public boolean isSmtp_authentication() {
        return smtp_authentication;
    }

    /**
     * @param smtp_authentication
     *            the smtp_authentication to set
     */
    public void setSmtp_authentication(boolean smtp_authentication) {
        this.smtp_authentication = smtp_authentication;
    }

    /**
     * @return the smtp_protocol
     */
    public Integer getSmtp_protocol() {
        return smtp_protocol;
    }

    /**
     * @param smtp_protocol
     *            the smtp_protocol to set
     */
    public void setSmtp_protocol(Integer smtp_protocol) {
        this.smtp_protocol = smtp_protocol;
    }

    /**
     * @return the smtp_username
     */
    public String getSmtp_username() {
        return smtp_username;
    }

    /**
     * @param smtp_username
     *            the smtp_username to set
     */
    public void setSmtp_username(String smtp_username) {
        this.smtp_username = smtp_username;
    }

    /**
     * @return the smtp_password
     */
    public String getSmtp_password() {
        return smtp_password;
    }

    /**
     * @param smtp_password
     *            the smtp_password to set
     */
    public void setSmtp_password(String smtp_password) {
        this.smtp_password = smtp_password;
    }

    /**
     * @return the email_provider
     */
    public Integer getEmail_provider() {
        return email_provider;
    }

    /**
     * @param email_provider
     *            the email_provider to set
     */
    public void setEmail_provider(Integer email_provider) {
        this.email_provider = email_provider;
    }

    @Override
    public String getName() {
        return "";
    }

}

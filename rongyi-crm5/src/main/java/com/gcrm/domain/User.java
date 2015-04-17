package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private String name;
    private String first_name;
    private String last_name;
    private UserStatus status;
    private String password;
    private String title;
    private String email;
    private String mobile;
    private String phone;
    private String fax;
    private String department;
    private User report_to;//上级
    private String mail_street;
    private String mail_city;
    private String mail_state;
    private String mail_postal_code;
    private String mail_country;
    private String other_street;
    private String other_city;
    private String other_state;
    private String other_postal_code;
    private String other_country;
    private Integer age;
    private String smtp_username;
    private String smtp_password;
    private String description;
    private String notes;
    private Integer count;
    private Date login_on;
    private String ip;
    private Set<Role> roles = new HashSet<Role>(0);
    private Set<TargetList> targetLists = new HashSet<TargetList>(0);
    private Set<Call> calls = new HashSet<Call>(0);
    private Set<Meeting> meetings = new HashSet<Meeting>(0);
    private Set<Visit> visits = new HashSet<Visit>(0);
    private Set<Contract> contracts = new HashSet<Contract>(0);
    private Integer scope_account;
    private Integer view_account;
    private Integer create_account;
    private Integer update_account;
    private Integer delete_account;
    private Integer scope_call;
    private Integer view_call;
    private Integer create_call;
    private Integer update_call;
    private Integer delete_call;
    private Integer scope_campaign;
    private Integer view_campaign;
    private Integer create_campaign;
    private Integer update_campaign;
    private Integer delete_campaign;
    private Integer scope_case;
    private Integer view_case;
    private Integer create_case;
    private Integer update_case;
    private Integer delete_case;
    private Integer scope_contact;
    private Integer view_contact;
    private Integer create_contact;
    private Integer update_contact;
    private Integer delete_contact;
    private Integer scope_document;
    private Integer view_document;
    private Integer create_document;
    private Integer update_document;
    private Integer delete_document;
    private Integer scope_lead;
    private Integer view_lead;
    private Integer create_lead;
    private Integer update_lead;
    private Integer delete_lead;
    private Integer scope_meeting;
    private Integer view_meeting;
    private Integer create_meeting;
    private Integer update_meeting;
    private Integer delete_meeting;
    private Integer scope_visit;
    private Integer view_visit;
    private Integer create_visit;
    private Integer update_visit;
    private Integer delete_visit;
    private Integer scope_contract;
    private Integer view_contract;
    private Integer create_contract;
    private Integer update_contract;
    private Integer delete_contract;
    private Integer scope_check;
    private Integer create_check;
    private Integer update_check;
    private Integer delete_check;
    private Integer view_check;
    public Integer getScope_contract() {
		return scope_contract;
	}

	public void setScope_contract(Integer scope_contract) {
		this.scope_contract = scope_contract;
	}

	public Integer getView_contract() {
		return view_contract;
	}

	public void setView_contract(Integer view_contract) {
		this.view_contract = view_contract;
	}

	public Integer getCreate_contract() {
		return create_contract;
	}

	public void setCreate_contract(Integer create_contract) {
		this.create_contract = create_contract;
	}

	public Integer getUpdate_contract() {
		return update_contract;
	}

	public void setUpdate_contract(Integer update_contract) {
		this.update_contract = update_contract;
	}

	public Integer getDelete_contract() {
		return delete_contract;
	}

	public void setDelete_contract(Integer delete_contract) {
		this.delete_contract = delete_contract;
	}

	private Integer scope_opportunity;
    private Integer view_opportunity;
    private Integer create_opportunity;
    private Integer update_opportunity;
    private Integer delete_opportunity;
    private Integer scope_target;
    private Integer view_target;
    private Integer create_target;
    private Integer update_target;
    private Integer delete_target;
    private Integer scope_targetList;
    private Integer view_targetList;
    private Integer create_targetList;
    private Integer update_targetList;
    private Integer delete_targetList;
    private Integer scope_task;
    private Integer view_task;
    private Integer create_task;
    private Integer update_task;
    private Integer delete_task;
    private Integer scope_system;
    private Integer view_system;
    private Integer create_system;
    private Integer update_system;
    private Integer delete_system;

    private Map<Integer, String> scopeMap;
    private Map<Integer, String> accessMap;

    public User() {
        super();
    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    @Override
    public User clone() {
        User o = null;
        try {
            o = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name
     *            the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name
     *            the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     *            the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the report_to
     */
    public User getReport_to() {
        return report_to;
    }

    /**
     * @param report_to
     *            the report_to to set
     */
    public void setReport_to(User report_to) {
        this.report_to = report_to;
    }

    /**
     * @return the mail_street
     */
    public String getMail_street() {
        return mail_street;
    }

    /**
     * @param mail_street
     *            the mail_street to set
     */
    public void setMail_street(String mail_street) {
        this.mail_street = mail_street;
    }

    /**
     * @return the mail_city
     */
    public String getMail_city() {
        return mail_city;
    }

    /**
     * @param mail_city
     *            the mail_city to set
     */
    public void setMail_city(String mail_city) {
        this.mail_city = mail_city;
    }

    /**
     * @return the mail_state
     */
    public String getMail_state() {
        return mail_state;
    }

    /**
     * @param mail_state
     *            the mail_state to set
     */
    public void setMail_state(String mail_state) {
        this.mail_state = mail_state;
    }

    /**
     * @return the mail_postal_code
     */
    public String getMail_postal_code() {
        return mail_postal_code;
    }

    /**
     * @param mail_postal_code
     *            the mail_postal_code to set
     */
    public void setMail_postal_code(String mail_postal_code) {
        this.mail_postal_code = mail_postal_code;
    }

    /**
     * @return the mail_country
     */
    public String getMail_country() {
        return mail_country;
    }

    /**
     * @param mail_country
     *            the mail_country to set
     */
    public void setMail_country(String mail_country) {
        this.mail_country = mail_country;
    }

    /**
     * @return the other_street
     */
    public String getOther_street() {
        return other_street;
    }

    /**
     * @param other_street
     *            the other_street to set
     */
    public void setOther_street(String other_street) {
        this.other_street = other_street;
    }

    /**
     * @return the other_city
     */
    public String getOther_city() {
        return other_city;
    }

    /**
     * @param other_city
     *            the other_city to set
     */
    public void setOther_city(String other_city) {
        this.other_city = other_city;
    }

    /**
     * @return the other_state
     */
    public String getOther_state() {
        return other_state;
    }

    /**
     * @param other_state
     *            the other_state to set
     */
    public void setOther_state(String other_state) {
        this.other_state = other_state;
    }

    /**
     * @return the other_postal_code
     */
    public String getOther_postal_code() {
        return other_postal_code;
    }

    /**
     * @param other_postal_code
     *            the other_postal_code to set
     */
    public void setOther_postal_code(String other_postal_code) {
        this.other_postal_code = other_postal_code;
    }

    /**
     * @return the other_country
     */
    public String getOther_country() {
        return other_country;
    }

    /**
     * @param other_country
     *            the other_country to set
     */
    public void setOther_country(String other_country) {
        this.other_country = other_country;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return the targetLists
     */
    public Set<TargetList> getTargetLists() {
        return targetLists;
    }

    /**
     * @param targetLists
     *            the targetLists to set
     */
    public void setTargetLists(Set<TargetList> targetLists) {
        this.targetLists = targetLists;
    }

    /**
     * @return the calls
     */
    public Set<Call> getCalls() {
        return calls;
    }

    /**
     * @param calls
     *            the calls to set
     */
    public void setCalls(Set<Call> calls) {
        this.calls = calls;
    }

    /**
     * @return the meetings
     */
    public Set<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * @param meetings
     *            the meetings to set
     */
    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    /**
     * @return the scope_account
     */
    public Integer getScope_account() {
        return scope_account;
    }

    /**
     * @return the scope_account text
     */
    public String getScope_account_text() {
        return this.scopeMap.get(scope_account);
    }

    /**
     * @param scope_account
     *            the scope_account to set
     */
    public void setScope_account(Integer scope_account) {
        this.scope_account = scope_account;
    }

    /**
     * @return the view_account
     */
    public Integer getView_account() {
        return view_account;
    }

    /**
     * @return the view_account text
     */
    public String getView_account_text() {
        return this.accessMap.get(view_account);
    }

    /**
     * @param view_account
     *            the view_account to set
     */
    public void setView_account(Integer view_account) {
        this.view_account = view_account;
    }

    /**
     * @return the create_account
     */
    public Integer getCreate_account() {
        return create_account;
    }

    /**
     * @return the create_account text
     */
    public String getCreate_account_text() {
        return this.accessMap.get(create_account);
    }

    /**
     * @param create_account
     *            the create_account to set
     */
    public void setCreate_account(Integer create_account) {
        this.create_account = create_account;
    }

    /**
     * @return the update_account
     */
    public Integer getUpdate_account() {
        return update_account;
    }

    /**
     * @return the update_account text
     */
    public String getUpdate_account_text() {
        return this.accessMap.get(update_account);
    }

    /**
     * @param update_account
     *            the update_account to set
     */
    public void setUpdate_account(Integer update_account) {
        this.update_account = update_account;
    }

    /**
     * @return the delete_account
     */
    public Integer getDelete_account() {
        return delete_account;
    }

    /**
     * @return the delete_account text
     */
    public String getDelete_account_text() {
        return this.accessMap.get(delete_account);
    }

    /**
     * @param delete_account
     *            the delete_account to set
     */
    public void setDelete_account(Integer delete_account) {
        this.delete_account = delete_account;
    }

    /**
     * @return the scope_call
     */
    public Integer getScope_call() {
        return scope_call;
    }

    /**
     * @param scope_call
     *            the scope_call to set
     */
    public void setScope_call(Integer scope_call) {
        this.scope_call = scope_call;
    }

    /**
     * @return the view_call
     */
    public Integer getView_call() {
        return view_call;
    }

    /**
     * @param view_call
     *            the view_call to set
     */
    public void setView_call(Integer view_call) {
        this.view_call = view_call;
    }

    /**
     * @return the create_call
     */
    public Integer getCreate_call() {
        return create_call;
    }

    /**
     * @param create_call
     *            the create_call to set
     */
    public void setCreate_call(Integer create_call) {
        this.create_call = create_call;
    }

    /**
     * @return the update_call
     */
    public Integer getUpdate_call() {
        return update_call;
    }

    /**
     * @param update_call
     *            the update_call to set
     */
    public void setUpdate_call(Integer update_call) {
        this.update_call = update_call;
    }

    /**
     * @return the delete_call
     */
    public Integer getDelete_call() {
        return delete_call;
    }

    /**
     * @param delete_call
     *            the delete_call to set
     */
    public void setDelete_call(Integer delete_call) {
        this.delete_call = delete_call;
    }

    /**
     * @return the scope_campaign
     */
    public Integer getScope_campaign() {
        return scope_campaign;
    }

    /**
     * @param scope_campaign
     *            the scope_campaign to set
     */
    public void setScope_campaign(Integer scope_campaign) {
        this.scope_campaign = scope_campaign;
    }

    /**
     * @return the view_campaign
     */
    public Integer getView_campaign() {
        return view_campaign;
    }

    /**
     * @param view_campaign
     *            the view_campaign to set
     */
    public void setView_campaign(Integer view_campaign) {
        this.view_campaign = view_campaign;
    }

    /**
     * @return the create_campaign
     */
    public Integer getCreate_campaign() {
        return create_campaign;
    }

    /**
     * @param create_campaign
     *            the create_campaign to set
     */
    public void setCreate_campaign(Integer create_campaign) {
        this.create_campaign = create_campaign;
    }

    /**
     * @return the update_campaign
     */
    public Integer getUpdate_campaign() {
        return update_campaign;
    }

    /**
     * @param update_campaign
     *            the update_campaign to set
     */
    public void setUpdate_campaign(Integer update_campaign) {
        this.update_campaign = update_campaign;
    }

    /**
     * @return the delete_campaign
     */
    public Integer getDelete_campaign() {
        return delete_campaign;
    }

    /**
     * @param delete_campaign
     *            the delete_campaign to set
     */
    public void setDelete_campaign(Integer delete_campaign) {
        this.delete_campaign = delete_campaign;
    }

    /**
     * @return the scope_case
     */
    public Integer getScope_case() {
        return scope_case;
    }

    /**
     * @param scope_case
     *            the scope_case to set
     */
    public void setScope_case(Integer scope_case) {
        this.scope_case = scope_case;
    }

    /**
     * @return the view_case
     */
    public Integer getView_case() {
        return view_case;
    }

    /**
     * @param view_case
     *            the view_case to set
     */
    public void setView_case(Integer view_case) {
        this.view_case = view_case;
    }

    /**
     * @return the create_case
     */
    public Integer getCreate_case() {
        return create_case;
    }

    /**
     * @param create_case
     *            the create_case to set
     */
    public void setCreate_case(Integer create_case) {
        this.create_case = create_case;
    }

    /**
     * @return the update_case
     */
    public Integer getUpdate_case() {
        return update_case;
    }

    /**
     * @param update_case
     *            the update_case to set
     */
    public void setUpdate_case(Integer update_case) {
        this.update_case = update_case;
    }

    /**
     * @return the delete_case
     */
    public Integer getDelete_case() {
        return delete_case;
    }

    /**
     * @param delete_case
     *            the delete_case to set
     */
    public void setDelete_case(Integer delete_case) {
        this.delete_case = delete_case;
    }

    /**
     * @return the scope_contact
     */
    public Integer getScope_contact() {
        return scope_contact;
    }

    /**
     * @param scope_contact
     *            the scope_contact to set
     */
    public void setScope_contact(Integer scope_contact) {
        this.scope_contact = scope_contact;
    }

    /**
     * @return the view_contact
     */
    public Integer getView_contact() {
        return view_contact;
    }

    /**
     * @param view_contact
     *            the view_contact to set
     */
    public void setView_contact(Integer view_contact) {
        this.view_contact = view_contact;
    }

    /**
     * @return the create_contact
     */
    public Integer getCreate_contact() {
        return create_contact;
    }

    /**
     * @param create_contact
     *            the create_contact to set
     */
    public void setCreate_contact(Integer create_contact) {
        this.create_contact = create_contact;
    }

    /**
     * @return the update_contact
     */
    public Integer getUpdate_contact() {
        return update_contact;
    }

    /**
     * @param update_contact
     *            the update_contact to set
     */
    public void setUpdate_contact(Integer update_contact) {
        this.update_contact = update_contact;
    }

    /**
     * @return the delete_contact
     */
    public Integer getDelete_contact() {
        return delete_contact;
    }

    /**
     * @param delete_contact
     *            the delete_contact to set
     */
    public void setDelete_contact(Integer delete_contact) {
        this.delete_contact = delete_contact;
    }

    /**
     * @return the scope_document
     */
    public Integer getScope_document() {
        return scope_document;
    }

    /**
     * @param scope_document
     *            the scope_document to set
     */
    public void setScope_document(Integer scope_document) {
        this.scope_document = scope_document;
    }

    /**
     * @return the view_document
     */
    public Integer getView_document() {
        return view_document;
    }

    /**
     * @param view_document
     *            the view_document to set
     */
    public void setView_document(Integer view_document) {
        this.view_document = view_document;
    }

    /**
     * @return the create_document
     */
    public Integer getCreate_document() {
        return create_document;
    }

    /**
     * @param create_document
     *            the create_document to set
     */
    public void setCreate_document(Integer create_document) {
        this.create_document = create_document;
    }

    /**
     * @return the update_document
     */
    public Integer getUpdate_document() {
        return update_document;
    }

    /**
     * @param update_document
     *            the update_document to set
     */
    public void setUpdate_document(Integer update_document) {
        this.update_document = update_document;
    }

    /**
     * @return the delete_document
     */
    public Integer getDelete_document() {
        return delete_document;
    }

    /**
     * @param delete_document
     *            the delete_document to set
     */
    public void setDelete_document(Integer delete_document) {
        this.delete_document = delete_document;
    }

    /**
     * @return the scope_lead
     */
    public Integer getScope_lead() {
        return scope_lead;
    }

    /**
     * @param scope_lead
     *            the scope_lead to set
     */
    public void setScope_lead(Integer scope_lead) {
        this.scope_lead = scope_lead;
    }

    /**
     * @return the view_lead
     */
    public Integer getView_lead() {
        return view_lead;
    }

    /**
     * @param view_lead
     *            the view_lead to set
     */
    public void setView_lead(Integer view_lead) {
        this.view_lead = view_lead;
    }

    /**
     * @return the create_lead
     */
    public Integer getCreate_lead() {
        return create_lead;
    }

    /**
     * @param create_lead
     *            the create_lead to set
     */
    public void setCreate_lead(Integer create_lead) {
        this.create_lead = create_lead;
    }

    /**
     * @return the update_lead
     */
    public Integer getUpdate_lead() {
        return update_lead;
    }

    /**
     * @param update_lead
     *            the update_lead to set
     */
    public void setUpdate_lead(Integer update_lead) {
        this.update_lead = update_lead;
    }

    /**
     * @return the delete_lead
     */
    public Integer getDelete_lead() {
        return delete_lead;
    }

    /**
     * @param delete_lead
     *            the delete_lead to set
     */
    public void setDelete_lead(Integer delete_lead) {
        this.delete_lead = delete_lead;
    }

    /**
     * @return the scope_meeting
     */
    public Integer getScope_meeting() {
        return scope_meeting;
    }

    /**
     * @param scope_meeting
     *            the scope_meeting to set
     */
    public void setScope_meeting(Integer scope_meeting) {
        this.scope_meeting = scope_meeting;
    }

    /**
     * @return the view_meeting
     */
    public Integer getView_meeting() {
        return view_meeting;
    }

    /**
     * @param view_meeting
     *            the view_meeting to set
     */
    public void setView_meeting(Integer view_meeting) {
        this.view_meeting = view_meeting;
    }

    /**
     * @return the create_meeting
     */
    public Integer getCreate_meeting() {
        return create_meeting;
    }

    /**
     * @param create_meeting
     *            the create_meeting to set
     */
    public void setCreate_meeting(Integer create_meeting) {
        this.create_meeting = create_meeting;
    }

    /**
     * @return the update_meeting
     */
    public Integer getUpdate_meeting() {
        return update_meeting;
    }

    /**
     * @param update_meeting
     *            the update_meeting to set
     */
    public void setUpdate_meeting(Integer update_meeting) {
        this.update_meeting = update_meeting;
    }

    /**
     * @return the delete_meeting
     */
    public Integer getDelete_meeting() {
        return delete_meeting;
    }

    /**
     * @param delete_meeting
     *            the delete_meeting to set
     */
    public void setDelete_meeting(Integer delete_meeting) {
        this.delete_meeting = delete_meeting;
    }

    /**
     * @return the scope_opportunity
     */
    public Integer getScope_opportunity() {
        return scope_opportunity;
    }

    /**
     * @param scope_opportunity
     *            the scope_opportunity to set
     */
    public void setScope_opportunity(Integer scope_opportunity) {
        this.scope_opportunity = scope_opportunity;
    }

    /**
     * @return the view_opportunity
     */
    public Integer getView_opportunity() {
        return view_opportunity;
    }

    /**
     * @param view_opportunity
     *            the view_opportunity to set
     */
    public void setView_opportunity(Integer view_opportunity) {
        this.view_opportunity = view_opportunity;
    }

    /**
     * @return the create_opportunity
     */
    public Integer getCreate_opportunity() {
        return create_opportunity;
    }

    /**
     * @param create_opportunity
     *            the create_opportunity to set
     */
    public void setCreate_opportunity(Integer create_opportunity) {
        this.create_opportunity = create_opportunity;
    }

    /**
     * @return the update_opportunity
     */
    public Integer getUpdate_opportunity() {
        return update_opportunity;
    }

    /**
     * @param update_opportunity
     *            the update_opportunity to set
     */
    public void setUpdate_opportunity(Integer update_opportunity) {
        this.update_opportunity = update_opportunity;
    }

    /**
     * @return the delete_opportunity
     */
    public Integer getDelete_opportunity() {
        return delete_opportunity;
    }

    /**
     * @param delete_opportunity
     *            the delete_opportunity to set
     */
    public void setDelete_opportunity(Integer delete_opportunity) {
        this.delete_opportunity = delete_opportunity;
    }

    /**
     * @return the scope_target
     */
    public Integer getScope_target() {
        return scope_target;
    }

    /**
     * @param scope_target
     *            the scope_target to set
     */
    public void setScope_target(Integer scope_target) {
        this.scope_target = scope_target;
    }

    /**
     * @return the view_target
     */
    public Integer getView_target() {
        return view_target;
    }

    /**
     * @param view_target
     *            the view_target to set
     */
    public void setView_target(Integer view_target) {
        this.view_target = view_target;
    }

    /**
     * @return the create_target
     */
    public Integer getCreate_target() {
        return create_target;
    }

    /**
     * @param create_target
     *            the create_target to set
     */
    public void setCreate_target(Integer create_target) {
        this.create_target = create_target;
    }

    /**
     * @return the update_target
     */
    public Integer getUpdate_target() {
        return update_target;
    }

    /**
     * @param update_target
     *            the update_target to set
     */
    public void setUpdate_target(Integer update_target) {
        this.update_target = update_target;
    }

    /**
     * @return the delete_target
     */
    public Integer getDelete_target() {
        return delete_target;
    }

    /**
     * @param delete_target
     *            the delete_target to set
     */
    public void setDelete_target(Integer delete_target) {
        this.delete_target = delete_target;
    }

    /**
     * @return the scope_targetList
     */
    public Integer getScope_targetList() {
        return scope_targetList;
    }

    /**
     * @param scope_targetList
     *            the scope_targetList to set
     */
    public void setScope_targetList(Integer scope_targetList) {
        this.scope_targetList = scope_targetList;
    }

    /**
     * @return the view_targetList
     */
    public Integer getView_targetList() {
        return view_targetList;
    }

    /**
     * @param view_targetList
     *            the view_targetList to set
     */
    public void setView_targetList(Integer view_targetList) {
        this.view_targetList = view_targetList;
    }

    /**
     * @return the create_targetList
     */
    public Integer getCreate_targetList() {
        return create_targetList;
    }

    /**
     * @param create_targetList
     *            the create_targetList to set
     */
    public void setCreate_targetList(Integer create_targetList) {
        this.create_targetList = create_targetList;
    }

    /**
     * @return the update_targetList
     */
    public Integer getUpdate_targetList() {
        return update_targetList;
    }

    /**
     * @param update_targetList
     *            the update_targetList to set
     */
    public void setUpdate_targetList(Integer update_targetList) {
        this.update_targetList = update_targetList;
    }

    /**
     * @return the delete_targetList
     */
    public Integer getDelete_targetList() {
        return delete_targetList;
    }

    /**
     * @param delete_targetList
     *            the delete_targetList to set
     */
    public void setDelete_targetList(Integer delete_targetList) {
        this.delete_targetList = delete_targetList;
    }

    /**
     * @return the scope_task
     */
    public Integer getScope_task() {
        return scope_task;
    }

    /**
     * @param scope_task
     *            the scope_task to set
     */
    public void setScope_task(Integer scope_task) {
        this.scope_task = scope_task;
    }

    /**
     * @return the view_task
     */
    public Integer getView_task() {
        return view_task;
    }

    /**
     * @param view_task
     *            the view_task to set
     */
    public void setView_task(Integer view_task) {
        this.view_task = view_task;
    }

    /**
     * @return the create_task
     */
    public Integer getCreate_task() {
        return create_task;
    }

    /**
     * @param create_task
     *            the create_task to set
     */
    public void setCreate_task(Integer create_task) {
        this.create_task = create_task;
    }

    /**
     * @return the update_task
     */
    public Integer getUpdate_task() {
        return update_task;
    }

    /**
     * @param update_task
     *            the update_task to set
     */
    public void setUpdate_task(Integer update_task) {
        this.update_task = update_task;
    }

    /**
     * @return the delete_task
     */
    public Integer getDelete_task() {
        return delete_task;
    }

    /**
     * @param delete_task
     *            the delete_task to set
     */
    public void setDelete_task(Integer delete_task) {
        this.delete_task = delete_task;
    }

    /**
     * @return the scope_system
     */
    public Integer getScope_system() {
        return scope_system;
    }

    /**
     * @param scope_system
     *            the scope_system to set
     */
    public void setScope_system(Integer scope_system) {
        this.scope_system = scope_system;
    }

    /**
     * @return the view_system
     */
    public Integer getView_system() {
        return view_system;
    }

    /**
     * @param view_system
     *            the view_system to set
     */
    public void setView_system(Integer view_system) {
        this.view_system = view_system;
    }

    /**
     * @return the create_system
     */
    public Integer getCreate_system() {
        return create_system;
    }

    /**
     * @param create_system
     *            the create_system to set
     */
    public void setCreate_system(Integer create_system) {
        this.create_system = create_system;
    }

    /**
     * @return the update_system
     */
    public Integer getUpdate_system() {
        return update_system;
    }

    /**
     * @param update_system
     *            the update_system to set
     */
    public void setUpdate_system(Integer update_system) {
        this.update_system = update_system;
    }

    /**
     * @return the delete_system
     */
    public Integer getDelete_system() {
        return delete_system;
    }

    /**
     * @param delete_system
     *            the delete_system to set
     */
    public void setDelete_system(Integer delete_system) {
        this.delete_system = delete_system;
    }

    /**
     * @return the scopeMap
     */
    public Map<Integer, String> getScopeMap() {
        return scopeMap;
    }

    /**
     * @param scopeMap
     *            the scopeMap to set
     */
    public void setScopeMap(Map<Integer, String> scopeMap) {
        this.scopeMap = scopeMap;
    }

    /**
     * @return the accessMap
     */
    public Map<Integer, String> getAccessMap() {
        return accessMap;
    }

    /**
     * @param accessMap
     *            the accessMap to set
     */
    public void setAccessMap(Map<Integer, String> accessMap) {
        this.accessMap = accessMap;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the scope_call_text
     */
    public String getScope_call_text() {
        return this.scopeMap.get(scope_call);
    }

    /**
     * @return the view_call_text
     */
    public String getView_call_text() {
        return this.accessMap.get(view_call);
    }

    /**
     * @return the create_call_text
     */
    public String getCreate_call_text() {
        return this.accessMap.get(create_call);
    }

    /**
     * @return the update_call_text
     */
    public String getUpdate_call_text() {
        return this.accessMap.get(update_call);
    }

    /**
     * @return the delete_call_text
     */
    public String getDelete_call_text() {
        return this.accessMap.get(delete_call);
    }

    /**
     * @return the scope_campaign_text
     */
    public String getScope_campaign_text() {
        return this.scopeMap.get(scope_campaign);
    }

    /**
     * @return the view_campaign_text
     */
    public String getView_campaign_text() {
        return this.accessMap.get(view_campaign);
    }

    /**
     * @return the create_campaign_text
     */
    public String getCreate_campaign_text() {
        return this.accessMap.get(create_campaign);
    }

    /**
     * @return the update_campaign_text
     */
    public String getUpdate_campaign_text() {
        return this.accessMap.get(update_campaign);
    }

    /**
     * @return the delete_campaign_text
     */
    public String getDelete_campaign_text() {
        return this.accessMap.get(delete_campaign);
    }

    /**
     * @return the scope_case_text
     */
    public String getScope_case_text() {
        return this.scopeMap.get(scope_case);
    }

    /**
     * @return the view_case_text
     */
    public String getView_case_text() {
        return this.accessMap.get(view_case);
    }

    /**
     * @return the create_case_text
     */
    public String getCreate_case_text() {
        return this.accessMap.get(create_case);
    }

    /**
     * @return the update_case_text
     */
    public String getUpdate_case_text() {
        return this.accessMap.get(update_case);
    }

    /**
     * @return the delete_case_text
     */
    public String getDelete_case_text() {
        return this.accessMap.get(delete_case);
    }

    /**
     * @return the scope_contact_text
     */
    public String getScope_contact_text() {
        return this.scopeMap.get(scope_contact);
    }

    /**
     * @return the view_contact_text
     */
    public String getView_contact_text() {
        return this.accessMap.get(view_contact);
    }

    /**
     * @return the create_contact_text
     */
    public String getCreate_contact_text() {
        return this.accessMap.get(create_contact);
    }

    /**
     * @return the update_contact_text
     */
    public String getUpdate_contact_text() {
        return this.accessMap.get(update_contact);
    }

    /**
     * @return the delete_contact_text
     */
    public String getDelete_contact_text() {
        return this.accessMap.get(delete_contact);
    }

    /**
     * @return the scope_document_text
     */
    public String getScope_document_text() {
        return this.scopeMap.get(scope_document);
    }

    /**
     * @return the view_document_text
     */
    public String getView_document_text() {
        return this.accessMap.get(view_document);
    }

    /**
     * @return the create_document_text
     */
    public String getCreate_document_text() {
        return this.accessMap.get(create_document);
    }

    /**
     * @return the update_document_text
     */
    public String getUpdate_document_text() {
        return this.accessMap.get(update_document);
    }

    /**
     * @return the delete_document_text
     */
    public String getDelete_document_text() {
        return this.accessMap.get(delete_document);
    }

    /**
     * @return the scope_lead_text
     */
    public String getScope_lead_text() {
        return this.scopeMap.get(scope_lead);
    }

    /**
     * @return this.accessMap.get( the view_lead_text
     */
    public String getView_lead_text() {
        return this.accessMap.get(view_lead);
    }

    /**
     * @return the create_lead_text
     */
    public String getCreate_lead_text() {
        return this.accessMap.get(create_lead);
    }

    /**
     * @return the update_lead_text
     */
    public String getUpdate_lead_text() {
        return this.accessMap.get(update_lead);
    }

    /**
     * @return the delete_lead_text
     */
    public String getDelete_lead_text() {
        return this.accessMap.get(delete_lead);
    }

    /**
     * @return the scope_meeting_text
     */
    public String getScope_meeting_text() {
        return this.scopeMap.get(scope_meeting);
    }

    /**
     * @return the view_meeting_text
     */
    public String getView_meeting_text() {
        return this.accessMap.get(view_meeting);
    }

    /**
     * @return the create_meeting_text
     */
    public String getCreate_meeting_text() {
        return this.accessMap.get(create_meeting);
    }

    /**
     * @return the update_meeting_text
     */
    public String getUpdate_meeting_text() {
        return this.accessMap.get(update_meeting);
    }

    /**
     * @return the delete_meeting_text
     */
    public String getDelete_meeting_text() {
        return this.accessMap.get(delete_meeting);
    }

    /**
     * @return the scope_opportunity_text
     */
    public String getScope_opportunity_text() {
        return this.scopeMap.get(scope_opportunity);
    }

    /**
     * @return this.accessMap.get(the view_opportunity_text
     */
    public String getView_opportunity_text() {
        return this.accessMap.get(view_opportunity);
    }

    /**
     * @return the create_opportunity_text
     */
    public String getCreate_opportunity_text() {
        return this.accessMap.get(create_opportunity);
    }

    /**
     * @return the update_opportunity_text
     */
    public String getUpdate_opportunity_text() {
        return this.accessMap.get(update_opportunity);
    }

    /**
     * @return the delete_opportunity_text
     */
    public String getDelete_opportunity_text() {
        return this.accessMap.get(delete_opportunity);
    }

    /**
     * @return the scope_target_text
     */
    public String getScope_target_text() {
        return this.scopeMap.get(scope_target);
    }

    /**
     * @return the view_target_text
     */
    public String getView_target_text() {
        return this.accessMap.get(view_target);
    }

    /**
     * @return the create_target_text
     */
    public String getCreate_target_text() {
        return this.accessMap.get(create_target);
    }

    /**
     * @return the update_target_text
     */
    public String getUpdate_target_text() {
        return this.accessMap.get(update_target);
    }

    /**
     * @return the delete_target_text
     */
    public String getDelete_target_text() {
        return this.accessMap.get(delete_target);
    }

    /**
     * @return the scope_targetList_text
     */
    public String getScope_targetList_text() {
        return this.scopeMap.get(scope_targetList);
    }

    /**
     * @return the view_targetList_text
     */
    public String getView_targetList_text() {
        return this.accessMap.get(view_targetList);
    }

    /**
     * @return the create_targetList_text
     */
    public String getCreate_targetList_text() {
        return this.accessMap.get(create_targetList);
    }

    /**
     * @return the update_targetList_text
     */
    public String getUpdate_targetList_text() {
        return this.accessMap.get(update_targetList);
    }

    /**
     * @return the delete_targetList_text
     */
    public String getDelete_targetList_text() {
        return this.accessMap.get(delete_targetList);
    }

    /**
     * @return the scope_task_text
     */
    public String getScope_task_text() {
        return this.scopeMap.get(scope_task);
    }

    /**
     * @return the view_task_text
     */
    public String getView_task_text() {
        return this.accessMap.get(view_task);
    }

    /**
     * @return the create_task_text
     */
    public String getCreate_task_text() {
        return this.accessMap.get(create_task);
    }

    /**
     * @return the update_task_text
     */
    public String getUpdate_task_text() {
        return this.accessMap.get(update_task);
    }

    /**
     * @return the delete_task_text
     */
    public String getDelete_task_text() {
        return this.accessMap.get(delete_task);
    }

    /**
     * @return the scope_system_text
     */
    public String getScope_system_text() {
        return this.scopeMap.get(scope_system);
    }

    /**
     * @return the view_system_text
     */
    public String getView_system_text() {
        return this.accessMap.get(view_system);
    }

    /**
     * @return the create_system_text
     */
    public String getCreate_system_text() {
        return this.accessMap.get(create_system);
    }

    /**
     * @return the update_system_text
     */
    public String getUpdate_system_text() {
        return this.accessMap.get(update_system);
    }

    /**
     * @return the delete_system_text
     */
    public String getDelete_system_text() {
        return this.accessMap.get(delete_system);
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

	public Integer getScope_visit() {
		return scope_visit;
	}

	public void setScope_visit(Integer scope_visit) {
		this.scope_visit = scope_visit;
	}

	public Integer getView_visit() {
		return view_visit;
	}

	public void setView_visit(Integer view_visit) {
		this.view_visit = view_visit;
	}

	public Integer getCreate_visit() {
		return create_visit;
	}

	public void setCreate_visit(Integer create_visit) {
		this.create_visit = create_visit;
	}

	public Integer getUpdate_visit() {
		return update_visit;
	}

	public void setUpdate_visit(Integer update_visit) {
		this.update_visit = update_visit;
	}

	public Integer getDelete_visit() {
		return delete_visit;
	}

	public void setDelete_visit(Integer delete_visit) {
		this.delete_visit = delete_visit;
	}

	public Set<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getLogin_on() {
		return login_on;
	}

	public void setLogin_on(Date login_on) {
		this.login_on = login_on;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getView_check() {
		return view_check;
	}

	public void setView_check(Integer view_check) {
		this.view_check = view_check;
	}

	public Integer getScope_check() {
		return scope_check;
	}

	public void setScope_check(Integer scope_check) {
		this.scope_check = scope_check;
	}

	public Integer getCreate_check() {
		return create_check;
	}

	public void setCreate_check(Integer create_check) {
		this.create_check = create_check;
	}

	public Integer getUpdate_check() {
		return update_check;
	}

	public void setUpdate_check(Integer update_check) {
		this.update_check = update_check;
	}

	public Integer getDelete_check() {
		return delete_check;
	}

	public void setDelete_check(Integer delete_check) {
		this.delete_check = delete_check;
	}

}

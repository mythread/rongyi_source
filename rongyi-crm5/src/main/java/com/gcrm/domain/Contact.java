package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.gcrm.util.CommonUtil;

public class Contact extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private Salutation salutation;
    private Contact_default contact_default;
    private String last_name;
    private String nick_name;
    private String office_phone;
    private String person_phone;
    private String title;
    private String mobile;
    private String department;
    private String fax;
    private String skype_id;
    private Account account;
    private String website;
    private String qq;
    private String primary_street;
    private String primary_city;
    private String primary_state;
    private String primary_postal_code;
    private String primary_country;
    private String other_street;
    private String other_city;
    private String other_state;
    private String other_postal_code;
    private String other_country;
    private String office_email;
    private String person_email;
    private Attachment attachment;
    private String fileName;
    private String origo;
    private double height;
    private double weight;
    private String relationship;
    private String character;
    private String interest;
    private String taboo;
    private Religious religious;
    private String habit;
    private String diet;
    private String notes;
    private Contact report_to;
    private boolean not_call;
    private LeadSource leadSource;
    private Campaign campaign;
    private User assigned_to;
    private Set<Opportunity> opportunities = new HashSet<Opportunity>(0);
    private Set<Lead> leads = new HashSet<Lead>(0);
    private Set<TargetList> targetLists = new HashSet<TargetList>(0);
    private Set<CaseInstance> cases = new HashSet<CaseInstance>(0);
    private Set<Call> calls = new HashSet<Call>(0);
    private Set<Meeting> meetings = new HashSet<Meeting>(0);
    private Set<Visit> visits = new HashSet<Visit>(0);
    private Set<Document> documents = new HashSet<Document>(0);

    @Override
    public Contact clone() {
        Contact o = null;
        try {
            o = (Contact) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public String getName() {
        StringBuilder nameBuider = new StringBuilder("");
        if (!CommonUtil.isNullOrEmpty(this.last_name)) {
            nameBuider.append(this.last_name);
        }
        return nameBuider.toString();
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
     * @return the office_phone
     */
    public String getOffice_phone() {
        return office_phone;
    }

    /**
     * @param office_phone
     *            the office_phone to set
     */
    public void setOffice_phone(String office_phone) {
        this.office_phone = office_phone;
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
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
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
     * @return the report_to
     */
    public Contact getReport_to() {
        return report_to;
    }

    /**
     * @param report_to
     *            the report_to to set
     */
    public void setReport_to(Contact report_to) {
        this.report_to = report_to;
    }

    /**
     * @return the not_call
     */
    public boolean isNot_call() {
        return not_call;
    }

    /**
     * @param not_call
     *            the not_call to set
     */
    public void setNot_call(boolean not_call) {
        this.not_call = not_call;
    }

    /**
     * @return the leadSource
     */
    public LeadSource getLeadSource() {
        return leadSource;
    }

    /**
     * @param leadSource
     *            the leadSource to set
     */
    public void setLeadSource(LeadSource leadSource) {
        this.leadSource = leadSource;
    }

    /**
     * @return the campaign
     */
    public Campaign getCampaign() {
        return campaign;
    }

    /**
     * @param campaign
     *            the campaign to set
     */
    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    /**
     * @return the assigned_to
     */
    public User getAssigned_to() {
        return assigned_to;
    }

    /**
     * @param assigned_to
     *            the assigned_to to set
     */
    public void setAssigned_to(User assigned_to) {
        this.assigned_to = assigned_to;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     *            the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the opportunities
     */
    public Set<Opportunity> getOpportunities() {
        return opportunities;
    }

    /**
     * @param opportunities
     *            the opportunities to set
     */
    public void setOpportunities(Set<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }

    /**
     * @return the leads
     */
    public Set<Lead> getLeads() {
        return leads;
    }

    /**
     * @param leads
     *            the leads to set
     */
    public void setLeads(Set<Lead> leads) {
        this.leads = leads;
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
     * @return the cases
     */
    public Set<CaseInstance> getCases() {
        return cases;
    }

    /**
     * @param cases
     *            the cases to set
     */
    public void setCases(Set<CaseInstance> cases) {
        this.cases = cases;
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
     * @return the documents
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * @param documents
     *            the documents to set
     */
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    /**
     * @return the primary_street
     */
    public String getPrimary_street() {
        return primary_street;
    }

    /**
     * @param primary_street
     *            the primary_street to set
     */
    public void setPrimary_street(String primary_street) {
        this.primary_street = primary_street;
    }

    /**
     * @return the primary_city
     */
    public String getPrimary_city() {
        return primary_city;
    }

    /**
     * @param primary_city
     *            the primary_city to set
     */
    public void setPrimary_city(String primary_city) {
        this.primary_city = primary_city;
    }

    /**
     * @return the primary_state
     */
    public String getPrimary_state() {
        return primary_state;
    }

    /**
     * @param primary_state
     *            the primary_state to set
     */
    public void setPrimary_state(String primary_state) {
        this.primary_state = primary_state;
    }

    /**
     * @return the primary_postal_code
     */
    public String getPrimary_postal_code() {
        return primary_postal_code;
    }

    /**
     * @param primary_postal_code
     *            the primary_postal_code to set
     */
    public void setPrimary_postal_code(String primary_postal_code) {
        this.primary_postal_code = primary_postal_code;
    }

    /**
     * @return the primary_country
     */
    public String getPrimary_country() {
        return primary_country;
    }

    /**
     * @param primary_country
     *            the primary_country to set
     */
    public void setPrimary_country(String primary_country) {
        this.primary_country = primary_country;
    }

    /**
     * @return the salutation
     */
    public Salutation getSalutation() {
        return salutation;
    }

    /**
     * @param salutation
     *            the salutation to set
     */
    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
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

    /**
     * @return the skype_id
     */
    public String getSkype_id() {
        return skype_id;
    }

    /**
     * @param skype_id
     *            the skype_id to set
     */
    public void setSkype_id(String skype_id) {
        this.skype_id = skype_id;
    }

    /**
     * @return the origo
     */
    public String getOrigo() {
        return origo;
    }

    /**
     * @param origo
     *            the origo to set
     */
    public void setOrigo(String origo) {
        this.origo = origo;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * @param relationship
     *            the relationship to set
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * @return the character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * @param character
     *            the character to set
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * @return the interest
     */
    public String getInterest() {
        return interest;
    }

    /**
     * @param interest
     *            the interest to set
     */
    public void setInterest(String interest) {
        this.interest = interest;
    }


    /**
     * @return the religious
     */
    public Religious getReligious() {
        return religious;
    }

    /**
     * @param religious
     *            the religious to set
     */
    public void setReligious(Religious religious) {
        this.religious = religious;
    }

    /**
     * @return the habit
     */
    public String getHabit() {
        return habit;
    }

    /**
     * @param habit
     *            the habit to set
     */
    public void setHabit(String habit) {
        this.habit = habit;
    }

    /**
     * @return the diet
     */
    public String getDiet() {
        return diet;
    }

    /**
     * @param diet
     *            the diet to set
     */
    public void setDiet(String diet) {
        this.diet = diet;
    }

    /**
     * @return the attachment
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public Set<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public Contact_default getContact_default() {
		return contact_default;
	}

	public void setContact_default(Contact_default contact_default) {
		this.contact_default = contact_default;
	}

	public String getPerson_phone() {
		return person_phone;
	}

	public void setPerson_phone(String person_phone) {
		this.person_phone = person_phone;
	}

	public String getOffice_email() {
		return office_email;
	}

	public void setOffice_email(String office_email) {
		this.office_email = office_email;
	}

	public String getPerson_email() {
		return person_email;
	}

	public void setPerson_email(String person_email) {
		this.person_email = person_email;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

}

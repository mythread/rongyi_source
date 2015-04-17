package com.gcrm.domain;

import java.io.Serializable;

public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457556L;

    public static final int NOT_SET = 0;
    public static final int ALL_OR_ENABLED = 1;
    public static final int OWNER_OR_DISABLED = 2;
    private String name;
    private String description;
    private String notes;
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
    private Integer view_check;
    private Integer create_check;
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

	public Integer getScope_check() {
		return scope_check;
	}

	public void setScope_check(Integer scope_check) {
		this.scope_check = scope_check;
	}

	private Integer update_check;
    private Integer delete_check;
    private Integer scope_check;
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

	private Integer view_contract;
    private Integer create_contract;
    private Integer update_contract;
    private Integer delete_contract;
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

    @Override
    public Role clone() {
        Role o = null;
        try {
            o = (Role) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Role other = (Role) obj;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!this.getId().equals(other.getId())) {
            return false;
        }
        return true;
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
     * @return the view_account
     */
    public Integer getView_account() {
        return view_account;
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
     * @param delete_account
     *            the delete_account to set
     */
    public void setDelete_account(Integer delete_account) {
        this.delete_account = delete_account;
    }

    /**
     * @return the scope_account
     */
    public Integer getScope_account() {
        return scope_account;
    }

    /**
     * @param scope_account
     *            the scope_account to set
     */
    public void setScope_account(Integer scope_account) {
        this.scope_account = scope_account;
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

	public Integer getView_visit() {
		return view_visit;
	}

	public void setView_visit(Integer view_visit) {
		this.view_visit = view_visit;
	}

	public Integer getScope_visit() {
		return scope_visit;
	}

	public void setScope_visit(Integer scope_visit) {
		this.scope_visit = scope_visit;
	}

	public Integer getView_check() {
		return view_check;
	}

	public void setView_check(Integer view_check) {
		this.view_check = view_check;
	}
}

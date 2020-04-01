package com.rocket.crm.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import com.rocket.crm.audit.DateAudit;

@Entity(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role extends DateAudit implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;
	public static final String NAME_FIELD = "name";
	public static final String DISPLAY_NAME = "display_Name";
	public static final String PRIVILEGES_FIELD = "privileges";
	public static final String COMPANY_ID = "company_id";

	@Id
	@Column(name =NAME_FIELD)
	private String name;

	@Column(name =DISPLAY_NAME)
	private String displayName;

	@Column(name =PRIVILEGES_FIELD)
	private Set<String> privileges;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Set<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<String> privileges) {
		this.privileges = privileges;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name =COMPANY_ID)
	private String companyId;

	@Override
	public String getAuthority() {
		return this.name;
	}
}

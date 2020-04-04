package com.rocket.crm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.rocket.crm.audit.DateAudit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role extends DateAudit implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private static final String NAME_FIELD = "name";
	private static final String DISPLAY_NAME_FIELD = "display_name";

	public Role(String name) {
		super();
		this.name = name;
	}

	@Id
	@Column(name = NAME_FIELD)
	private String name;

	@Column(name = DISPLAY_NAME_FIELD)
	private String displayName;

	@Override
	public String getAuthority() {
		return this.name;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<User> users;

}

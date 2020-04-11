package com.rocket.crm.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rocket.crm.audit.DateAudit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends DateAudit implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6654208740321206499L;

	private static final String USERNAME_FIELD = "username";
	private static final String NAME_FIELD = "name";
	private static final String PHONE_FIELD = "phone";
	private static final String EMAIL_FIELD = "email";
	private static final String CITY_FIELD = "city";
	private static final String LOCATION_FIELD = "location";
	private static final String PASSWORD_FIELD = "password";
	private static final String ACCOUNT_NON_EXPIRED = "account_non_expired";
	private static final String ACCOUNT_NON_LOCKED = "account_non_locked";
	private static final String CREDENTIALS_NON_EXPIRED = "credentials_non_expired";
	private static final String ENABLED_FIELD = "enabled";

	public User(String username) {
		super();
		this.username = username;
	}

	@Id
	@Column(name = USERNAME_FIELD, unique = true, nullable = false)
	private String username;

	@Column(name = PHONE_FIELD, nullable = false)
	private String phone;

	@Column(name = NAME_FIELD, nullable = false)
	private String name;

	@Column(name = LOCATION_FIELD)
	private String location;

	@Column(name = CITY_FIELD)
	private String city;

	@Column(name = EMAIL_FIELD)
	private String email;

	@Column(name = PASSWORD_FIELD)
	private String password;

	@Column(name = ENABLED_FIELD)
	private boolean enabled = true;

	@Column(name = ACCOUNT_NON_EXPIRED)
	private boolean accountNonExpired = false;

	@Column(name = ACCOUNT_NON_LOCKED)
	private boolean accountNonLocked = false;

	@Column(name = CREDENTIALS_NON_EXPIRED)
	private boolean credentialsNonExpired = false;

	@ManyToMany(targetEntity = Role.class, cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false) })
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

}

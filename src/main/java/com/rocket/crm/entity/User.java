package com.rocket.crm.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rocket.crm.audit.DateAudit;
import com.rocket.crm.config.CustomAuthorityDeserializer;

@Entity(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User extends DateAudit implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6654208740321206499L;

	public static final String USERNAME_FIELD = "username";
	public static final String EMAIL_FIELD = "email";
	public static final String CITY_FIELD = "city";
	public static final String LOCATION_FIELD = "location";
	public static final String PASSWORD_FIELD = "password";
	public static final String ACCOUNT_NON_EXPIRED = "account_non_expired";
	public static final String ACCOUNT_NON_LOCKED = "account_non_locked";
	public static final String CREDENTIALS_NON_EXPIRED = "credentials_non_expired";
	public static final String ENABLED_FIELD = "enabled";
	public static final String ROLES_FIELD = "roles";
	public static final String NAME_FIELD = "name";
	public static final String PHONE_FIELD = "phone";
	public static final String OTP_DATE = "otp_date";

	@Id
	private String id;

	@Column(name = USERNAME_FIELD)
	private String username;

	@Column(name = LOCATION_FIELD)
	private String location;

	@Column(name = CITY_FIELD)
	private String city;

	@Column(name = EMAIL_FIELD)
	private String email;

	@Column(name = PASSWORD_FIELD)
	private String password;

	@Column(name = OTP_DATE)
	private Long OtpDate;

	@Column(name = PHONE_FIELD)
	private String phone;

	@Column(name = NAME_FIELD)
	private String name;

	@Column(name = ENABLED_FIELD)
	private boolean enabled = true;

	@Column(name = ACCOUNT_NON_EXPIRED)
	private boolean accountNonExpired = false;

	@Column(name = ACCOUNT_NON_LOCKED)
	private boolean accountNonLocked = false;

	@Column(name = CREDENTIALS_NON_EXPIRED)
	private boolean credentialsNonExpired = false;

	@Column(name = ROLES_FIELD)
	private Set<String> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getOtpDate() {
		return OtpDate;
	}

	public void setOtpDate(Long otpDate) {
		OtpDate = otpDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> rolesList = new HashSet<>();
		if (roles != null) {
			for (String role : roles) {
				Role r = new Role();
				r.setName(role);
				rolesList.add(r);
			}
		}
		return rolesList;
	}
}

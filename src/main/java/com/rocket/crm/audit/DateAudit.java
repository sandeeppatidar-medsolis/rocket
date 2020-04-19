package com.rocket.crm.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.rocket.crm.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit implements Serializable {

	private static final long serialVersionUID = 4301349316355155626L;
	public static final String CREATED_BY = "created_by";
	public static final String CREATED_DATE = "created_date";
	public static final String LAST_MODIFIED_BY = "last_modified_by";
	public static final String LAST_MODIFIED_DATE = "last_modified_date";

	@OneToOne(fetch = FetchType.LAZY)
	@CreatedBy
	@JoinColumn(name = CREATED_BY, nullable = false, updatable = false)
	private User createdBy;

	@CreatedDate
	@Column(name = CREATED_DATE, nullable = false, updatable = false)
	private Date createdDate;

	@OneToOne(fetch = FetchType.LAZY)
	@LastModifiedBy
	@JoinColumn(name = LAST_MODIFIED_BY)
	private User updatedBy;

	@LastModifiedDate
	@Column(name = LAST_MODIFIED_DATE)
	private Date lastModifiedDate;

}

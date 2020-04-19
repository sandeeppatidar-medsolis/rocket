package com.rocket.crm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rocket.crm.audit.DateAudit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "state")
@NoArgsConstructor
public class StateMaster extends DateAudit implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String COUNTRY_ID = "country_id";

	public StateMaster(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = ID, unique = true, nullable = false)
	private Long id;

	@Column(name = NAME, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = COUNTRY_ID)
	private CountryMaster country;

}

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
@Table(name = "branch")
@NoArgsConstructor
public class Branch extends DateAudit implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private static final String ID_FIELD = "id";
	private static final String NAME_FIELD = "name";
	private static final String ADDRESS_FIELD = "address";
	private static final String COUNTRY_ID = "country_id";
	private static final String STATE_ID = "state_id";
	private static final String CITY_ID = "city_id";

	public Branch(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = ID_FIELD, unique = true, nullable = false)
	private Long id;

	@Column(name = NAME_FIELD, nullable = false)
	private String name;

	@Column(name = ADDRESS_FIELD)
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = COUNTRY_ID)
	private CountryMaster country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = STATE_ID)
	private StateMaster state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = CITY_ID)
	private CityMaster city;

}

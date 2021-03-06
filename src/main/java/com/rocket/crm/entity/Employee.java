package com.rocket.crm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.rocket.crm.audit.DateAudit;
import com.rocket.crm.enums.EmployeeType;
import com.rocket.crm.enums.Gender;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employee")
@NoArgsConstructor
public class Employee extends DateAudit implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private static final String ID_FIELD = "id";
	private static final String FIRSTNAME_FIELD = "firstname";
	private static final String LASTNAME_FIELD = "lastname";
	private static final String GENDER_FIELD = "gender";
	private static final String AVATAR_FIELD = "avatar";
	private static final String DOB_FIELD = "dob";
	private static final String DOJ_FIELD = "doj";
	private static final String MOBILE_FIELD = "mobile";
	private static final String EMAIL_FIELD = "email";
	private static final String ADDRESS_FIELD = "address";
	private static final String CITY_ID = "city_id";
	private static final String STATE_ID = "state_id";
	private static final String COUNTRY_ID = "country_id";
	private static final String ACCOUNT_NUMBER = "account_number";
	private static final String IFSC_CODE = "ifsc_code";
	private static final String SALARY_FIELD = "salary";
	private static final String BRANCH_ID = "branch_id";
	private static final String USER_ID = "user_id";
	private static final String EMPLOYEE_TYPE = "employee_type";

	public Employee(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = ID_FIELD, unique = true, nullable = false)
	private Long id;

	@Column(name = FIRSTNAME_FIELD, nullable = false)
	private String firstname;

	@Column(name = LASTNAME_FIELD, nullable = false)
	private String lastname;

	@Enumerated(EnumType.STRING)
	@Type(type = "com.rocket.crm.entity.EnumTypePostgreSql")
	@Column(name = GENDER_FIELD, nullable = false)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	@Type(type = "com.rocket.crm.entity.EnumTypePostgreSql")
	@Column(name = EMPLOYEE_TYPE, nullable = false)
	private EmployeeType employeeType;

	@Column(name = AVATAR_FIELD)
	private String avatar;

	@Column(name = DOB_FIELD, nullable = false)
	private Date dob;

	@Column(name = DOJ_FIELD, nullable = false)
	private Date doj;

	@Column(name = MOBILE_FIELD, nullable = false)
	private String mobile;

	@Column(name = EMAIL_FIELD)
	private String email;

	@Column(name = ADDRESS_FIELD)
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = CITY_ID)
	private CityMaster city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = STATE_ID)
	private StateMaster state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = COUNTRY_ID)
	private CountryMaster country;

	@Column(name = ACCOUNT_NUMBER)
	private String accountNumber;

	@Column(name = IFSC_CODE)
	private String ifscCode;

	@Column(name = SALARY_FIELD)
	private Double salary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = BRANCH_ID, nullable = false)
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = USER_ID, nullable = false)
	private User user;
}

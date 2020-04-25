package com.rocket.crm.constants;

public class UriConstants {

	UriConstants() {

	}

	/*
	 * API END POINT
	 */
	public static final String USER_API = "/api/v1/user";
	public static final String BRANCH_API = "/api/v1/branch";
	public static final String ROLE_API = "/api/v1/role";
	public static final String MASTER_API = "/api/v1/master";
	public static final String EMPLOYEE_API = "/api/v1/employee";

	/*
	 * COMMON END POINT
	 */
	public static final String CREATE = "/create";
	public static final String UPDATE = "/update";
	public static final String GET_ALL = "/get_all";
	public static final String ALL = "/all";

	/*
	 * USER END POINT
	 */
	public static final String GET_BY_USERNAME = "/{username}";

	/*
	 * ROLE END POINT
	 */
	public static final String GET_BY_NAME = "/{name}";

	/*
	 * MASTER END POINT
	 */
	public static final String COUNTRY = "/country";
	public static final String STATE = "/state";
	public static final String CITY = "/city";

	/*
	 * Branch END POINT
	 */
	public static final String BRANCH = "/branch";

}

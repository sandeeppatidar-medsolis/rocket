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

	/*
	 * COMMON END POINT
	 */
	public static final String CREATE = "/create";
	public static final String UPDATE = "/update";

	/*
	 * USER END POINT
	 */
	public static final String GET_BY_USERNAME = "/{username}";

	/*
	 * ROLE END POINT
	 */
	public static final String GET_BY_NAME = "/{name}";

}

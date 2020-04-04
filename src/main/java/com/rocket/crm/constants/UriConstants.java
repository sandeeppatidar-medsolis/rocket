package com.rocket.crm.constants;

public class UriConstants {

	UriConstants() {

	}

	/*
	 * API END POINT
	 */
	public static final String USER_API = "/api/v1/user";
	public static final String BRANCH_API = "/api/v1/branch";

	
	
	/*
	 * USER END POINT
	 */
	public static final String CREATE = "/create";
	public static final String UPDATE = "/update";
	public static final String GET_BY_USERNAME = "/{username}";

	/*
	 * ROLE END POINT
	 */
	public static final String ROLE_CREATE = "/role/create";
	public static final String GET_ROLE_BY_NAME = "role/{name}";
	
	

}

package com.nitin.java.referring.domain.tracker.controller.requestMappings;

public interface RequestMappings {
	public static String TRACK_DOMAIN = "/trackDomain/{domainName}/**";
	
	public static String REGISTER_DOMAIN = "/registerDomain/{domainName}";
	
	public static String RANK_DOMAIN = "/rankDomain";
	public static String RANK_DOMAIN_BY_SIZE = "/rankDomain/{size}";
	
}

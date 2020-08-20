package com.nitin.java.referring.domain.tracker.constant;



public interface Constants {

	public static final String TRACK_DOMAIN_STRING = "trackDomain";
	public static final String DOMAIN_REGEX = TRACK_DOMAIN_STRING + "\\/(https?\\:\\/\\/)?([\\w\\.]*)";
	public static final String RANK_SIZE = "size";
	public static final String DOMAIN_NAME = "domainName";
	
	public static final String DOMAIN_DOES_NOT_EXIST = "Domain name '%s' does not exist. please create an entry using /registerDomain/{domainName} endpoint";
	public static final String DOMAIN_IS_INACTIVE = "Domain name '%s' is inactive. make domain active.";
	
	public static final String DOMAIN_WRONG_FORMAT = "Check domain name '%s' ";
	
}

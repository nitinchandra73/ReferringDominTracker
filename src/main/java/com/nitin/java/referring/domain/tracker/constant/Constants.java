package com.nitin.java.referring.domain.tracker.constant;



public interface Constants {

	public static final String TRACK_DOMAIN_STRING = "trackDomain";
	public static final String DOMAIN_REGEX = TRACK_DOMAIN_STRING + "\\/(https?\\:\\/\\/)?([\\w\\.]*)";
	public static final String RANK_SIZE = "size";
	
	public static final String DOMAIN_DOES_NOT_EXIST = "Domain name '%s' does not exist. please create an entry into db";
	
}

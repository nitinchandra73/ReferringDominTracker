package com.nitin.java.referring.domain.tracker;

import com.nitin.java.referring.domain.tracker.controller.entity.Domain;

public class TestData {

	protected Domain irsDomain;
	protected Domain googleDomain;
	protected Domain robinhoodDomain;
	protected Domain proseriesDomain;
	protected Domain appleDomain;

	public TestData() {
		super();
	}

	protected void buildAllDomains() {
		 irsDomain = Domain.builder().domainName("www.irs.com").hitCount(342).id(9).isActive(true).build();
		 googleDomain = Domain.builder().domainName("www.google.com").hitCount(223).id(10).isActive(true).build();
		 robinhoodDomain = Domain.builder().domainName("www.robinhood.com").hitCount(48).id(15).isActive(true).build();
		 proseriesDomain = Domain.builder().domainName("www.proseries.com").hitCount(32).id(8).isActive(true).build();
		 appleDomain = Domain.builder().domainName("www.apple.com").hitCount(933).id(12).isActive(false).build();
	}

}
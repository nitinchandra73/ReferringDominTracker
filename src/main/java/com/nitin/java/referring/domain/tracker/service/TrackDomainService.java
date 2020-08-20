package com.nitin.java.referring.domain.tracker.service;

import java.util.List;
import java.util.Optional;

import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.exception.UserException;

public interface TrackDomainService {
	
	public void trackDomain(String uri) throws UserException;

	public List<Domain> rankDomain(Optional<Integer> size);

	public void addDomain(String domainName);

}

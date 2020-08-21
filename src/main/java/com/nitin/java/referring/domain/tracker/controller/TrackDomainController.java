package com.nitin.java.referring.domain.tracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.nitin.java.referring.domain.tracker.controller.entity.Domain;

public interface TrackDomainController {

	


	public ResponseEntity<List<Domain>> rankReferringDomain(Optional<Integer> size);

	public ResponseEntity<Void> registerDomain(String domainName);

	public ResponseEntity<Void> trackDomain(String domainName);
	
}

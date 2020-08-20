package com.nitin.java.referring.domain.tracker.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.exception.UserException;

public interface TrackDomainController {

	

	public ResponseEntity<?> trackDomain(HttpServletRequest request) throws UserException;

	public ResponseEntity<List<Domain>> rankReferringDomain(Optional<Integer> size);

	public ResponseEntity<Void> registerDomain(String domainName);
	
}

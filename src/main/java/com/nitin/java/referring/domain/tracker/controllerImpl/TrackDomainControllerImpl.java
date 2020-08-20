package com.nitin.java.referring.domain.tracker.controllerImpl;

import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.RANK_DOMAIN;
import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.RANK_DOMAIN_BY_SIZE;
import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.REGISTER_DOMAIN;
import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.TRACK_DOMAIN;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitin.java.referring.domain.tracker.constant.Constants;
import com.nitin.java.referring.domain.tracker.controller.TrackDomainController;
import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.service.TrackDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TrackDomainControllerImpl implements TrackDomainController {

	@Autowired
	TrackDomainService service;
	
	
	@GetMapping(value = TRACK_DOMAIN)
	@Override
	public ResponseEntity<Void> trackDomain(HttpServletRequest request)  {
		
		String uri = request.getRequestURI().toString();
		log.debug("Handling track domain request for uri: "+uri);
		service.trackDomain(uri);
		log.info("Served track domain request for uri: "+uri);
		return ResponseEntity.ok().build(); 
	}
	
	
	@GetMapping(value = {RANK_DOMAIN, RANK_DOMAIN_BY_SIZE})
	@Override
	public ResponseEntity<List<Domain>> rankReferringDomain(@PathVariable(Constants.RANK_SIZE) Optional<Integer> size)  {
		
		log.debug("Serving rank referring domain");
		List<Domain> rankDomain = service.rankDomain(size);
		log.info("Served rank referring domain, Values: "+ rankDomain);
		return ResponseEntity.ok().body(rankDomain); 
	}
	
	@PostMapping(value = REGISTER_DOMAIN)
	@Override
	public ResponseEntity<Void> registerDomain(@PathVariable(Constants.DOMAIN_NAME) String domainName)  {
		
		log.debug("Serving regiseter new domain: "+domainName);
		service.addDomain(domainName);
		log.info("Served regiseter new domain: "+domainName);
		return ResponseEntity.ok().build(); 
	}
	

}

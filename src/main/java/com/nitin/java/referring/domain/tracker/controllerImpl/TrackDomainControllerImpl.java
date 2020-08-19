package com.nitin.java.referring.domain.tracker.controllerImpl;

import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.RANK_DOMAIN;
import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.RANK_DOMAIN_BY_SIZE;
import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.TRACK_DOMAIN;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nitin.java.referring.domain.tracker.constant.Constants;
import com.nitin.java.referring.domain.tracker.controller.TrackDomainController;
import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.service.TrackDomainService;

@RestController
public class TrackDomainControllerImpl implements TrackDomainController {

	@Autowired
	TrackDomainService service;
	
	
	@GetMapping(value = TRACK_DOMAIN)
	@Override
	public ResponseEntity<?> getTrackDomain(HttpServletRequest request)  {
		
		String uri = request.getRequestURI().toString();
		service.trackDomain(uri);
		return ResponseEntity.ok().build(); 
	}
	
	
	@GetMapping(value = {RANK_DOMAIN, RANK_DOMAIN_BY_SIZE})
	@Override
	public ResponseEntity<List<Domain>> rankReferringDomain(@PathVariable(Constants.RANK_SIZE) Optional<Integer> size)  {
		
		
		List<Domain> rankDomain = service.rankDomain(size);
		return ResponseEntity.ok().body(rankDomain); 
	}
	
	
//	JSONObject response = new JSONObject();
	
//		final String regex = "trackdomain\\/(https?\\:\\/\\/)?([\\w\\.]*)";
//		final String string = "trackdomain/http://www.regex101.com/https://personal101.com/cddcd/cdcd.ccc.cd/cdcd\n\n\n"
//				 + "capture my capture again";
//		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//		final Matcher matcher = pattern.matcher(uri);
//		
//		matcher.find();
//		matcher.group(2);
//		String[] domain = uri.split("trackdomain\\/(https?\\:\\/\\/)?([\\w\\.]*)");
		
		// TODO Auto-generated method stub
		
	
	
	
//	void test(){
//		final String regex = "trackdomain\\/(https?\\:\\/\\/)?([\\w\\.]*)";
//		final String string = "trackdomain/http://www.regex101.com/https://personal101.com/cddcd/cdcd.ccc.cd/cdcd\n\n\n"
//			 + "capture my capture again";
//
//		final Pattern pattern = Pattern.compile(regex);
//		final Matcher matcher = pattern.matcher(string);
//
//		if (matcher.find()) {
//		    System.out.println("Full match: " + matcher.group(0));
//		    for (int i = 1; i <= matcher.groupCount(); i++) {
//		        System.out.println("Group " + i + ": " + matcher.group(i));
//		    }
//		}
//	}

}

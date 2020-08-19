package com.nitin.java.referring.domain.tracker.serviceImpl;

import static com.nitin.java.referring.domain.tracker.constant.Constants.DOMAIN_REGEX;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nitin.java.referring.domain.tracker.constant.Constants;
import com.nitin.java.referring.domain.tracker.constant.ErrorCodes;
import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.exception.UserException;
import com.nitin.java.referring.domain.tracker.repository.TrackDomainRepository;
import com.nitin.java.referring.domain.tracker.repository.entity.DomainTrackTable;
import com.nitin.java.referring.domain.tracker.service.TrackDomainService;

@Service
@Transactional
public class TrackDomainServiceImpl implements TrackDomainService {

	@Autowired
	private TrackDomainRepository repository;
	
	@Override
	public void trackDomain(String uri)  {
		

		final Pattern pattern = Pattern.compile(DOMAIN_REGEX);
		final Matcher matcher = pattern.matcher(uri);
		

		if (matcher.find()) {
			DomainTrackTable domainEntry = repository.findByDomainName(matcher.group(2));
			if(domainEntry != null){
				domainEntry.hitCount++;
				}
			else {
				throw new UserException(String.format(Constants.DOMAIN_DOES_NOT_EXIST,matcher.group(2)), ErrorCodes.DOMAIN_DOES_NOT_EXIST);
			}
			
		}else {
			
		}
		
		
	}
	
	@Override
	public List<Domain> rankDomain(Optional<Integer> inputSize)  {
		Integer size = (inputSize.isPresent()) ? inputSize.get() : 10;
		
		List<DomainTrackTable> findTop3OrderByHitCountDesc = repository.findTop3ByOrderByHitCountDesc();
		
		return findTop3OrderByHitCountDesc.stream()
		.map(this::domainMapper)
		.filter(Objects::nonNull)
		.collect(Collectors.toList());
		
		
		
		
	}
	
	private Domain domainMapper(DomainTrackTable trackTable) {
		return Domain.builder()
				.id(trackTable.getId())
				.domainName(trackTable.getDomainName())
				.hitCount(trackTable.getHitCount())
				.build();
	}
	
	

}

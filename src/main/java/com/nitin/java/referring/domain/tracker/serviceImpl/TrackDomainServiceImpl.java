package com.nitin.java.referring.domain.tracker.serviceImpl;

import static com.nitin.java.referring.domain.tracker.constant.Constants.DOMAIN_REGEX;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nitin.java.referring.domain.tracker.constant.Constants;
import com.nitin.java.referring.domain.tracker.constant.ErrorCodes;
import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.exception.UserException;
import com.nitin.java.referring.domain.tracker.repository.TrackDomainRepository;
import com.nitin.java.referring.domain.tracker.repository.entity.DomainTrackTable;
import com.nitin.java.referring.domain.tracker.service.TrackDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TrackDomainServiceImpl implements TrackDomainService {

	@Autowired
	private TrackDomainRepository repository;

	@Override
	public void trackDomain(String domainName) {

//		final String domainName = matchPatern(uri);

		DomainTrackTable domainEntry = repository.findByDomainName(domainName);
		if (domainEntry != null) {
			if (domainEntry.isActive) {
				domainEntry.hitCount++;
			} else {
				log.error(String.format(Constants.DOMAIN_IS_INACTIVE, domainName),ErrorCodes.DOMAIN_IS_INACTIVE);
				throw new UserException(String.format(Constants.DOMAIN_IS_INACTIVE, domainName),
						ErrorCodes.DOMAIN_IS_INACTIVE);
			}

		} else {
			log.error(String.format(Constants.DOMAIN_DOES_NOT_EXIST, domainName),
					ErrorCodes.DOMAIN_DOES_NOT_EXIST);
			throw new UserException(String.format(Constants.DOMAIN_DOES_NOT_EXIST, domainName),
					ErrorCodes.DOMAIN_DOES_NOT_EXIST);
		}

	}

	@Override
	public List<Domain> rankDomain(Optional<Integer> inputSize) {
		Integer size = (inputSize.isPresent()) ? inputSize.get() : 3;
		log.info("Serving rank referring domain for top: " + size);
//		List<DomainTrackTable> findTop3OrderByHitCountDesc = repository.findTop3ByOrderByHitCountDesc();

		List<DomainTrackTable> findTopNrderByHitCountDesc = repository.findAllByIsActiveOrderByHitCountDescIdAsc(true,
				PageRequest.of(0, size));
		return findTopNrderByHitCountDesc.stream()
				.map(this::domainMapper)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

	}

	@Override
	public void addDomain(String domainName) {

		if (!domainName.isEmpty()) {
			
			DomainTrackTable tableEntry = repository.findByDomainName(domainName);
			if(tableEntry != null) {
				tableEntry.setActive(true);
			}
			else {
				tableEntry = DomainTrackTable.builder()
						.domainName(domainName)
						.hitCount(0)
						.isActive(true)
						.build();
			}
			repository.save(tableEntry);
		} else {
			log.error(String.format(Constants.DOMAIN_WRONG_FORMAT, domainName),
					ErrorCodes.DOMAIN_WRONG_FORMAT);
			throw new UserException(String.format(Constants.DOMAIN_WRONG_FORMAT, domainName),
					ErrorCodes.DOMAIN_WRONG_FORMAT);
		}

	}

	private String matchPatern(String uri) {
		final Pattern pattern = Pattern.compile(DOMAIN_REGEX);
		final Matcher matcher = pattern.matcher(uri);

		if (matcher.find()) {
			return matcher.group(2);
		}

		return StringUtils.EMPTY;
	}

	private Domain domainMapper(DomainTrackTable trackTable) {
		return Domain.builder()
				.id(trackTable.getId())
				.domainName(trackTable.getDomainName())
				.hitCount(trackTable.getHitCount())
				.isActive(trackTable.isActive)
				.build();
	}

}

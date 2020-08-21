package com.nitin.java.referring.domain.tracker.UnitTest;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.exception.UserException;
import com.nitin.java.referring.domain.tracker.repository.TrackDomainRepository;
import com.nitin.java.referring.domain.tracker.repository.entity.DomainTrackTable;
import com.nitin.java.referring.domain.tracker.serviceImpl.TrackDomainServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TrackDomainServiceTest {
	
	@Mock
	private TrackDomainRepository repository;
	@Mock
	private DomainTrackTable domainEntry1;
	@Mock
	private DomainTrackTable domainEntry2;
	@Mock
	private DomainTrackTable domainEntry3;
	
	@InjectMocks
	TrackDomainServiceImpl service;
	
	@Test
	public void trackDomainValidName() {
		when(repository.findByDomainNameIgnoreCase("test")).thenReturn(domainEntry1);
		when(domainEntry1.isActive()).thenReturn(true);
		service.trackDomain("test");
		verify(domainEntry1, times(1)).getHitCount();
		verify(domainEntry1, times(1)).setHitCount(anyInt());
	}
	
	@Test
	public void trackDomainNullName() {
		when(repository.findByDomainNameIgnoreCase(null)).thenReturn(null);
		
		UserException thrown = Assertions.assertThrows(
		           UserException.class,
		           () -> service.trackDomain(null),
		           "Expected trackDomain(null) to throw, but it didn't"
		    );

		Assert.assertEquals( "Domain name 'null' does not exist. please create an entry using /registerDomain/{domainName} endpoint", thrown.getMessage());
	}
	
	@Test
	public void trackDomainNameInactive() {
		when(repository.findByDomainNameIgnoreCase("test")).thenReturn(domainEntry1);
		when(domainEntry1.isActive()).thenReturn(false);
		UserException thrown = Assertions.assertThrows(
		           UserException.class,
		           () -> service.trackDomain("test"),
		           "Expected trackDomain(test) to throw, but it didn't"
		    );

		Assert.assertEquals( "Domain name 'test' is inactive. make domain active.", thrown.getMessage());
	}
	
	//***********************************************//
	
	@Test
	public void RankDomainCustomSize() {
		List<DomainTrackTable> domainEntries = new ArrayList<>();
		domainEntries.add(domainEntry1);
		domainEntries.add(domainEntry2);
		setWhenForDomainEntries(1,domainEntry1,  12, true);
		setWhenForDomainEntries(2,domainEntry2,  5, true);

		when(repository.findAllByIsActiveOrderByHitCountDescIdAsc(true,PageRequest.of(0, 2))).thenReturn(domainEntries);
		
		
		
		List<Domain> extectedDomains = new ArrayList<>();
		extectedDomains.add(new Domain(1, "domainName1", 12, true));
		extectedDomains.add(new Domain(2, "domainName2", 5, true));
		
		Assert.assertEquals(extectedDomains, service.rankDomain(Optional.of(2)));
	}
	
	@Test
	public void RankDomainDefaultSize() {
		List<DomainTrackTable> domainEntries = new ArrayList<>();
		domainEntries.add(domainEntry1);
		domainEntries.add(domainEntry2);
		domainEntries.add(domainEntry3);
		setWhenForDomainEntries(1,domainEntry1,  12, true);
		setWhenForDomainEntries(2,domainEntry2,  5, true);
		setWhenForDomainEntries(3,domainEntry3,  2, true);

		when(repository.findAllByIsActiveOrderByHitCountDescIdAsc(true,PageRequest.of(0, 3))).thenReturn(domainEntries);
		
		
		
		List<Domain> extectedDomains = new ArrayList<>();
		extectedDomains.add(new Domain(1, "domainName1", 12, true));
		extectedDomains.add(new Domain(2, "domainName2", 5, true));
		extectedDomains.add(new Domain(3, "domainName3", 2, true));
		
		Assert.assertEquals(extectedDomains, service.rankDomain(Optional.empty()));
	}
	
	private void setWhenForDomainEntries( int id, DomainTrackTable domainEntry, int hitCount, boolean active) {
		when(domainEntry.getId()).thenReturn(id);
		when(domainEntry.getDomainName()).thenReturn("domainName"+id);
		when(domainEntry.getHitCount()).thenReturn(hitCount);
		when(domainEntry.isActive()).thenReturn(active);
		
	}
}

package com.nitin.java.referring.domain.tracker.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nitin.java.referring.domain.tracker.repository.entity.DomainTrackTable;

public interface TrackDomainRepository extends JpaRepository<DomainTrackTable, Integer> {
	
	public DomainTrackTable findByDomainNameIgnoreCase(String domainName);
	
//	public List<DomainTrackTable> findTop3ByOrderByHitCountDesc();
	
	public List<DomainTrackTable> findAllByIsActiveOrderByHitCountDescIdAsc(boolean isActive, Pageable pageable);
	

}

package com.nitin.java.referring.domain.tracker.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DomainTrackTable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;
	
	@Column(nullable = false)
	public String domainName;
	
	public int hitCount;

	
	
	public DomainTrackTable(Integer id, String domainName, int hitCount) {
		super();
		this.id = id;
		this.domainName = domainName;
		this.hitCount = hitCount;
	}
	
	
}

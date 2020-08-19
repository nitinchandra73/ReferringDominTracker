package com.nitin.java.referring.domain.tracker.controller.entity;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter 
@Getter
@NoArgsConstructor
@ToString
@Builder
public class Domain {

	public Integer id;
	@NotBlank(message = "Domain name may not be blank")
	public String domainName;
	public Integer hitCount;
	
	public Domain(Integer id, String domainName, Integer hitCount) {
		super();
		this.id = id;
		
		this.domainName = domainName;
		this.hitCount = hitCount;
	}
	
	
	
	
}

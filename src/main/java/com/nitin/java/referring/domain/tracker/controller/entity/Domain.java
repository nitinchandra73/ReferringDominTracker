package com.nitin.java.referring.domain.tracker.controller.entity;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter 
@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Domain {

	public Integer id;
	@NotBlank(message = "Domain name may not be blank")
	public String domainName;
	public Integer hitCount;
	public Boolean isActive;
	
	
	
	
	
	
}

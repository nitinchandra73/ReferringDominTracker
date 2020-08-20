package com.nitin.java.referring.domain.tracker.controller.entity;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Domain bean")
public class Domain {

	public Integer id;
	@NotBlank(message = "Domain name cannot be blank")
	@ApiModelProperty(notes = "Domain name cannot be blank")
	public String domainName;
	public Integer hitCount;
	public Boolean isActive;
	
	
	
	
	
	
}

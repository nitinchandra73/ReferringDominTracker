package com.nitin.java.referring.domain.tracker.UnitTest;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nitin.java.referring.domain.tracker.TestData;
import com.nitin.java.referring.domain.tracker.constant.Constants;
import com.nitin.java.referring.domain.tracker.constant.ErrorCodes;
import com.nitin.java.referring.domain.tracker.controller.entity.Domain;
import com.nitin.java.referring.domain.tracker.controllerImpl.TrackDomainControllerImpl;
import com.nitin.java.referring.domain.tracker.exception.UserException;
import com.nitin.java.referring.domain.tracker.service.TrackDomainService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TrackDomainControllerImpl.class)
public class TrackDomainControllerTest extends TestData{

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	TrackDomainService service;
	
	@Test
	public void trackDomainSuccess() throws Exception {
		Mockito.doNothing().when(service).trackDomain(Mockito.anyString());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/trackDomain/www.intuit.com/cdd.cd/cdc/cc").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void trackDomainNotPresent() throws Exception {
		Mockito.doNothing().when(service).trackDomain(Mockito.anyString());
		
		Mockito.doThrow(new UserException(String.format(Constants.DOMAIN_IS_INACTIVE, "www.intuit.com"),
				ErrorCodes.DOMAIN_IS_INACTIVE)).when(service).trackDomain("www.intuit.com");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/trackDomain/www.intuit.com/cdd.cd/cdc/cc").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void trackDomainNull() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/trackDomain").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	
	// ********************************************//
	
	@Test
	public void rankDomainDefaultSizeSuccess() throws Exception {
		//Mockito.doNothing().when(service).trackDomain(Mockito.anyString());
		String expectedResponse = "[{\"id\":9,\"domainName\":\"www.irs.com\",\"hitCount\":342,\"isActive\":true},{\"id\":10,\"domainName\":\"www.google.com\",\"hitCount\":223,\"isActive\":true},{\"id\":15,\"domainName\":\"www.robinhood.com\",\"hitCount\":48,\"isActive\":true}]" ;
		
		ArrayList<Domain> arrayList = new ArrayList<Domain>() ;
		buildAllDomains();
		arrayList.add(irsDomain);
		arrayList.add(googleDomain);
		arrayList.add(robinhoodDomain);
		
		Mockito.when(service.rankDomain(Optional.ofNullable(null))).thenReturn(arrayList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/rankDomain").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(expectedResponse, result.getResponse().getContentAsString());
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void rankDomainCustomSizeSuccess() throws Exception {
		//Mockito.doNothing().when(service).trackDomain(Mockito.anyString());
		String expectedResponse = "[{\"id\":9,\"domainName\":\"www.irs.com\",\"hitCount\":342,\"isActive\":true},{\"id\":10,\"domainName\":\"www.google.com\",\"hitCount\":223,\"isActive\":true}]" ;
		
		ArrayList<Domain> arrayList = new ArrayList<Domain>() ;
		buildAllDomains();
		arrayList.add(irsDomain);
		arrayList.add(googleDomain);
		
		
		Mockito.when(service.rankDomain(Optional.ofNullable(2))).thenReturn(arrayList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/rankDomain/2").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(expectedResponse, result.getResponse().getContentAsString());
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void rankDomainCustomSizeStringFail() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/rankDomain/bvb").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	// ********************************************//
	
	@Test
	public void registerDomainSuccess() throws Exception {
		
		Mockito.doNothing().when(service).addDomain(Mockito.anyString());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/registerDomain/www.irs.com").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void registerDomainNameBlank() throws Exception {
		
		Mockito.doThrow(new UserException(String.format(Constants.DOMAIN_WRONG_FORMAT, StringUtils.EMPTY),
				ErrorCodes.DOMAIN_WRONG_FORMAT))
		.when(service).addDomain(" ");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/registerDomain/ ").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void registerDomainNull() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/registerDomain/").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}

}

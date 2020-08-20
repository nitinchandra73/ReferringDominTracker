package com.nitin.java.referring.domain.tracker.integrationTest;

import static com.nitin.java.referring.domain.tracker.controller.requestMappings.RequestMappings.RANK_DOMAIN;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.nitin.java.referring.domain.tracker.Application;
import com.nitin.java.referring.domain.tracker.controller.entity.Domain;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class) 
public class TrackDomainControllerIT {

	@LocalServerPort
    private int port;
	
	private TestRestTemplate template = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	private Domain irsDomain,googleDomain,robinhoodDomain,proseriesDomain,appleDomain;

	
	
    @Before
    public void setupJSONAcceptType() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }
    
    
    @Test
    public void trackDomainSuccessTest() throws Exception {

       // String expected = "{id:Question1,description:Largest Country in the World,correctAnswer:Russia,options:[India,Russia,United States,China]}";

        ResponseEntity<Void> response = template.exchange(
                createUrl("/trackDomain/www.mint.com/www.minct.com/cdcdcd"),
                HttpMethod.GET, new HttpEntity<String>("DUMMY_DOESNT_MATTER",
                        headers), Void.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void trackDomainFailTest() throws Exception {


        ResponseEntity<Void> response = template.exchange(
                createUrl("/trackDomain"),
                HttpMethod.GET, new HttpEntity<String>("DUMMY_DOESNT_MATTER",
                        headers), Void.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }
    
    @Test
    public void trackNullDomainFail() throws Exception {


        ResponseEntity<Void> response = template.exchange(
                createUrl("/trackDomain"),
                HttpMethod.GET, null, Void.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }
    
    @Test
    public void trackNotPresentDomain() throws Exception {


        ResponseEntity<Void> response = template.exchange(
                createUrl("/trackDomain/www.abc123.com/cd/cc"),
                HttpMethod.GET, null, Void.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }
    
    @Test
    public void trackDeletedDomain() throws Exception {
    	
        ResponseEntity<Void> response = template.exchange(
                createUrl("/trackDomain/www.apple.com/cd/cc"),
                HttpMethod.GET, null, Void.class);
      
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       
        
    }
    
 //*******************************************//
    
    @Test
    public void rankDomainDefaultSizeSuccess() throws Exception {

    	buildAllDomains();
    	Domain[] expectedResponse = new Domain[] {irsDomain,googleDomain,robinhoodDomain};
    	Domain[] actualResponsebody = getRankByWhenActive(null);
    	Assert.assertArrayEquals(expectedResponse, actualResponsebody);
        
    }
    
    @Test
    public void rankDomainCustomSizeSuccess() throws Exception {
    	
    	buildAllDomains();
    	Domain[] expectedResponse = new Domain[] {irsDomain,googleDomain,robinhoodDomain,proseriesDomain};
    	Domain[] actualResponsebody = getRankByWhenActive(4);
    	Assert.assertArrayEquals(expectedResponse, actualResponsebody);
    }
    
    @Test
    public void rankDomainContainOnlyActiveDomain() throws Exception {
    	
    	Domain[] actualResponsebody = getRankByWhenActive(20);
    	Assert.assertFalse(Arrays.asList(actualResponsebody).contains(appleDomain));
    }
    
    //*******************************************//
    
    @Test
    @DirtiesContext
    public void registerDomainSuccess() throws Exception {
    	
        ResponseEntity<Void> postResponse = template.exchange(
                createUrl("/registerDomain/www.testRegisterDomain.com"),
                HttpMethod.POST, null, Void.class);
      
        Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
       
        Domain[] getResponse =  getRankByWhenActive(20);
        Domain  testRegisterDomain = Domain.builder().domainName("www.testRegisterDomain.com").hitCount(0).id(16).isActive(true).build();
        Assert.assertTrue(Arrays.asList(getResponse).contains(testRegisterDomain));
        
    }
    
    @Test
    @DirtiesContext
    public void registerInActiveDomainSuccess() throws Exception {
    	buildAllDomains();
    	Domain[] getResponseBeforePost =  getRankByWhenActive(20);
    	Assert.assertFalse(Arrays.asList(getResponseBeforePost).contains(appleDomain));
       
    	ResponseEntity<Void> postResponse = template.exchange(
                createUrl("/registerDomain/www.apple.com"),
                HttpMethod.POST, null, Void.class);
        Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
       
        Domain[] getResponse =  getRankByWhenActive(20);
        appleDomain.setIsActive(true);
        Assert.assertTrue(Arrays.asList(getResponse).contains(appleDomain));
        
    }
    
    
    @Test
    @DirtiesContext
    public void registerNullDomain() throws Exception {
    	
       
    	ResponseEntity<Void> postResponse = template.exchange(
                createUrl("/registerDomain/"),
                HttpMethod.POST, null, Void.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, postResponse.getStatusCode());
       
        
        
    }
    
    //*******************************************//
    
    private Domain[] getRankByWhenActive(Integer size) {
    	
    	String url = (size!=null)? createUrl(RANK_DOMAIN+size):createUrl(RANK_DOMAIN);
    	ResponseEntity<Domain[]> response = template.exchange(
    			url, HttpMethod.GET, new HttpEntity<String>("DUMMY_DOESNT_MATTER",
                        headers), Domain[].class );
    	Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    	return  response.getBody();
    }
    
    private void buildAllDomains() {
    	 irsDomain = Domain.builder().domainName("www.irs.com").hitCount(342).id(9).isActive(true).build();
    	 googleDomain = Domain.builder().domainName("www.google.com").hitCount(223).id(10).isActive(true).build();
    	 robinhoodDomain = Domain.builder().domainName("www.robinhood.com").hitCount(48).id(15).isActive(true).build();
    	 proseriesDomain = Domain.builder().domainName("www.proseries.com").hitCount(32).id(8).isActive(true).build();
    	 appleDomain = Domain.builder().domainName("www.apple.com").hitCount(933).id(12).isActive(false).build();
    }
    

    private String createUrl(String uri) {
        return "http://localhost:" + port + uri;
    }
}

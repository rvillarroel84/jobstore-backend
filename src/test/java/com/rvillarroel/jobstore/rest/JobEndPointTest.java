package com.rvillarroel.jobstore.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rvillarroel.jobstore.model.Job;
import com.rvillarroel.jobstore.repository.JobRepository;
import com.rvillarroel.jobstore.util.TextUtil;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;


@RunWith(Arquillian.class)
@RunAsClient
public class JobEndPointTest {
	
	private static String jobId;
	
	private Response response;
	
	/*@Test
	@InSequence(1)
	public void shouldGetNoJobs(@ArquillianResteasyResource("api/jobs") WebTarget webTarget) {
		
		// Test counting Jobs
	    response = webTarget.path("count").request().get();		
		assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
		
		// Test find all
		
		response = webTarget.request(APPLICATION_JSON).get();
		assertEquals(NO_CONTENT, response.getStatus());			
		
	}*/
	
	 @Test
	 @InSequence(2)
	 public void shouldCreateAJob(@ArquillianResteasyResource("api/jobs") WebTarget webTarget) {
	        // Creates a book
		    Job job = new Job("Necesito      Electricista", "Trabajo temporal por Jornada", new Date(), "http://google.com.bo");
	        response = webTarget.request(APPLICATION_JSON).post(Entity.entity(job, APPLICATION_JSON));
	        assertEquals(CREATED.getStatusCode(), response.getStatus());
	        // Checks the created book
	        String location = response.getHeaderString("location");
	        assertNotNull(location);
	        jobId = location.substring(location.lastIndexOf("/") + 1);
	 }
	 
	 @Test
	    @InSequence(3)
	    public void shouldFindTheCreatedBook(@ArquillianResteasyResource("api/jobs") WebTarget webTarget) {
	        // Finds the book
	        response = webTarget.path(jobId).request(APPLICATION_JSON).get();
	        assertEquals(OK.getStatusCode(), response.getStatus());
	        // Checks the found book
	        Job jobFound = response.readEntity(Job.class);
	        assertNotNull(jobFound.getId());
	        //assertTrue(bookFound.getIsbn().startsWith("13-84356-"));
	        assertEquals("Necesito Electricista", jobFound.getName());
	    }

	    @Test
	    @InSequence(4)
	    public void shouldGetOneBook(@ArquillianResteasyResource("api/jobs") WebTarget webTarget) {
	        // Count all
	        response = webTarget.path("count").request().get();
	        assertEquals(OK.getStatusCode(), response.getStatus());
	        assertEquals(Long.valueOf(1), response.readEntity(Long.class));
	        // Find all
	        response = webTarget.request(APPLICATION_JSON).get();
	        assertEquals(OK.getStatusCode(), response.getStatus());
	        assertEquals(1, response.readEntity(List.class).size());
	    }

	    @Test
	    @InSequence(5)
	    public void shouldDeleteTheCreatedBook(@ArquillianResteasyResource("api/jobs") WebTarget webTarget) {
	        // Deletes the book
	        response = webTarget.path(jobId).request(APPLICATION_JSON).delete();
	        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
	        // Checks the deleted book
	        Response checkResponse = webTarget.path(jobId).request(APPLICATION_JSON).get();
	        assertEquals(NOT_FOUND.getStatusCode(), checkResponse.getStatus());
	    }
	
	@Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
            .addClass(Job.class)            
            .addClass(JobRepository.class)
            //.addClass(NumberGenerator.class)
            //.addClass(IsbnGenerator.class)
            .addClass(TextUtil.class)
            .addClass(JobEndPoint.class)
            .addClass(JAXRSConfiguration.class)
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

}

package com.rvillarroel.jobstore.repository;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rvillarroel.jobstore.model.Job;

import javax.inject.Inject;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class JobRepositoryTest {
	
	// ======================================
    // =             Attributes             =
    // ======================================

    private static Long jobId;
  
	@Inject
    private JobRepository jobRepository;
   
   
    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    @InSequence(1)
    public void shouldBeDeployed() {
        assertNotNull(jobRepository);
    }
    
    @Test
    @InSequence(2)
    public void ShouldGetNoJob() {
    	
    	assertEquals(Long.valueOf(0), jobRepository.countAll());
    	assertEquals(0, jobRepository.findAll().size());
    	
    }
    
    @Test
    @InSequence(3)
    public void ShouldCreateAJob(){
    	
    	Job job = new Job("Necesito Electricista", "Trabajo temporal por Jornada", new Date(), "http://google.com.bo");
    	job = jobRepository.create(job);
    	assertNotNull(job);
    	assertNotNull(job.getId());
    	jobId = job.getId();
    	
    }
    
    @Test
    @InSequence(4)
    public void shouldFindTheCreatedJob() {
    	
    	Job foundJob = jobRepository.find(jobId);
    	assertNotNull(foundJob.getId());
    	assertEquals("Necesito Electricista", foundJob.getName());
    	
    }
    
    @Test
    @InSequence(5)
    public void shouldGetOneBook() {
        // Count all
        assertEquals(Long.valueOf(1), jobRepository.countAll());
        // Find all
        assertEquals(1, jobRepository.findAll().size());
    }

    @Test
    @InSequence(6)
    public void shouldDeleteTheCreatedBook() {
        // Deletes the book
    	jobRepository.delete(jobId);
        // Checks the deleted book
        Job jobDeleted = jobRepository.find(jobId);
        assertNull(jobDeleted);
    }

    @Test
    @InSequence(7)
    public void shouldGetNoMoreBook() {
        // Count all
        assertEquals(Long.valueOf(0), jobRepository.countAll());
        // Find all
        assertEquals(0, jobRepository.findAll().size());
    }
    
	@Deployment
    public static Archive<?> createDeploymentPackage() {

       return ShrinkWrap.create(JavaArchive.class)
           .addClass(Job.class)
           //.addClass(Language.class)
           .addClass(JobRepository.class)
           .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
           .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }
   
   
   
   
}

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
import com.rvillarroel.jobstore.util.TextUtil;

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
	
    @Inject
    private TextUtil textUtil;
   
   
    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    @InSequence(1)
    public void shouldBeDeployed() {
        assertNotNull(jobRepository);
        assertNotNull(textUtil);
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
    	
    	Job job = new Job("Necesito      Electricista", "Trabajo temporal por Jornada", new Date(), "http://google.com.bo");
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
    public void shouldGetOneJob() {
        // Count all
        assertEquals(Long.valueOf(1), jobRepository.countAll());
        // Find all
        assertEquals(1, jobRepository.findAll().size());
    }

    @Test
    @InSequence(6)
    public void shouldDeleteTheCreatedJob() {
        // Deletes the book
    	jobRepository.delete(jobId);
        // Checks the deleted book
        Job jobDeleted = jobRepository.find(jobId);
        assertNull(jobDeleted);
    }

    @Test
    @InSequence(7)
    public void shouldGetNoMoreJob() {
        // Count all
        assertEquals(Long.valueOf(0), jobRepository.countAll());
        // Find all
        assertEquals(0, jobRepository.findAll().size());
    }
    
    /***
     * 
     * Bean Validation
     * @return
     */
    @Test(expected = Exception.class)
    @InSequence(8)
    public void createInvalidJob() {
    	Job job = new Job(null, "Trabajo temporal por Jornada", new Date(), "http://google.com.bo");
    	job = jobRepository.create(job);
    }
    
    @Test(expected = Exception.class)
    @InSequence(9)
    public void findWithInvalidId() {
    	jobRepository.find(null);
    }
    
	@Deployment
    public static Archive<?> createDeploymentPackage() {

       return ShrinkWrap.create(JavaArchive.class)
           .addClass(Job.class)          
           .addClass(JobRepository.class)
           .addClass(TextUtil.class)
           .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
           .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }
   
   
   
   
}

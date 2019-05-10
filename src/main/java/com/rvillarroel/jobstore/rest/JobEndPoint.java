package com.rvillarroel.jobstore.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rvillarroel.jobstore.model.Job;
import com.rvillarroel.jobstore.repository.JobRepository;

@Path("/jobs")
public class JobEndPoint {

	@Inject 
	private JobRepository jobRepository;

	public Job getJob(Long id) {
		return jobRepository.find(id);
	}

	public Job createJob(Job job) {
		return jobRepository.create(job);
	}

	public void deleteJob(Long id) {
		jobRepository.delete(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobs() {
		List<Job> jobs = jobRepository.findAll();
		
		if(jobs.size() == 0)
			return Response.noContent().build();
		
		return Response.ok(jobs).build();
	}

	public Long countJobs() {
		return jobRepository.countAll();
	}
	
	
	
}

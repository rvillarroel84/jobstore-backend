package com.rvillarroel.jobstore.rest;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.rvillarroel.jobstore.model.Job;
import com.rvillarroel.jobstore.repository.JobRepository;

@Path("/jobs")
public class JobEndPoint {

	
	@Inject 
	private JobRepository jobRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Job job, @Context UriInfo uriInfo) {
    	job = jobRepository.create(job);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(job.getId().toString()).build();
        return Response.created(createdURI).build();
    }

    @GET
    @Path("/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Job job = jobRepository.find(id);

        if (job == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(job).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        jobRepository.delete(id);
        return Response.noContent().build();
    }
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobs() {
		List<Job> jobs = jobRepository.findAll();
		
		if(jobs.size() == 0)
			return Response.noContent().build();
		
		return Response.ok(jobs).build();
	}
	
	@GET
	@Path("/count")
	public Response countJobs() {
				
		Long nbOfJobs = jobRepository.countAll();
		if (nbOfJobs == 0)
			return Response.noContent().build();
		
		return Response.ok(nbOfJobs).build();
	}
	
	
	
}

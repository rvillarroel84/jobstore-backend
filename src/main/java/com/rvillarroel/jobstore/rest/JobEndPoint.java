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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/jobs")
@Api("Job")
public class JobEndPoint {

	
	@Inject 
	private JobRepository jobRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses({
        @ApiResponse(code = 201, message = "The job is created"),
        @ApiResponse(code = 415, message = "Format is not JSon")
    })
    public Response createJob(@ApiParam(value = "Job to be created", required = true)Job job, @Context UriInfo uriInfo) {
    	job = jobRepository.create(job);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(job.getId().toString()).build();
        return Response.created(createdURI).build();
    }

    @GET
    @Path("/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retorna un Job dado un identificador", response = Job.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Job found"),
        @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
        @ApiResponse(code = 404, message = "Job not found")
    })
    public Response getJob(@PathParam("id") @Min(1) Long id) {
        Job job = jobRepository.find(id);

        if (job == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(job).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Deletes a job given an id")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Job has been deleted"),
        @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
        @ApiResponse(code = 500, message = "Job not found")
    })
    public Response deleteJob(@PathParam("id") @Min(1) Long id) {
        jobRepository.delete(id);
        return Response.noContent().build();
    }
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all the books", response = Job.class, responseContainer = "List")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Jobs found"),
        @ApiResponse(code = 204, message = "No jobs found"),
    })
	public Response getJobs() {
		List<Job> jobs = jobRepository.findAll();
		
		if(jobs.size() == 0)
			return Response.noContent().build();
		
		return Response.ok(jobs).build();
	}
	
	@GET
	@Path("/count")
	@ApiOperation(value = "Returns the number of jobs", response = Long.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Number of jobs found"),
        @ApiResponse(code = 204, message = "No jobs found"),
    })
	public Response countJobs() {
				
		Long nbOfJobs = jobRepository.countAll();
		if (nbOfJobs == 0)
			return Response.noContent().build();
		
		return Response.ok(nbOfJobs).build();
	}
	
	
	
}

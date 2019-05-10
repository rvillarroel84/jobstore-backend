package com.rvillarroel.jobstore.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.rvillarroel.jobstore.model.Job;
import static javax.transaction.Transactional.TxType.SUPPORTS;
import static javax.transaction.Transactional.TxType.REQUIRED;;


@Transactional(SUPPORTS)
public class JobRepository {
	
	@PersistenceContext(unitName = "jobStorePU")
	private EntityManager em;
	
	public Job find(@NotNull Long id) {
		return em.find(Job.class, id);
	}
	
	@Transactional(REQUIRED)
	public Job create(@NotNull Job job) {
		em.persist(job);
		return job;
	}
	
	@Transactional(REQUIRED)
	public void delete(@NotNull Long id) {
		em.remove(em.getReference(Job.class, id));			
	}
	
	
	public List<Job> findAll(){
		
		TypedQuery<Job> query = em.createQuery("SELECT b from Job b order by name", Job.class);
		return query.getResultList();
	}
	
	public Long countAll() {
		TypedQuery<Long> query = em.createQuery("select count(b) from Job b", Long.class);
		return query.getSingleResult();
	}
}

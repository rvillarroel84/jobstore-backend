package com.rvillarroel.jobstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Job {
	

	@Id @GeneratedValue
	private Long id;
	
	@Column(length = 200)
	private String name;
	
	@Column(length = 250)
	private String description;
	
	@Column(name = "publication_date")
	@Temporal(TemporalType.DATE)
	private Date publicationDate;
	
	@Column(name = "image_url")
    private String imageURL;
	
	
	/*
	 * CONSTRUCTORES
	 * 
	 * 
	 */
	
	public Job() {
		
	}
	
		
	public Job(String name, String description, Date publicationDate, String imageURL) {
		super();
		//this.id = id;
		this.name = name;
		this.description = description;
		this.publicationDate = publicationDate;
		this.imageURL = imageURL;
	}
	
	
	/*
	 * 
	 * METODOS
	 * 
	 */

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + ", descripcion=" + description + "]";
	}

	
	

}

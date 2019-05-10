package com.rvillarroel.jobstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Job resource Representation")
public class Job {
	

	@Id 
	@GeneratedValue
	@ApiModelProperty("Identificador")
	private Long id;
	
	
	@Column(length = 200)
	@NotNull
	@Size(min = 1, max = 200)
	@ApiModelProperty("Name of the Job")
	private String name;
	
	
	@Column(length = 250)
	@Size(min = 1, max = 250)
	@ApiModelProperty("Descripcion el Trabajo")
	private String description;
	
	@Column(name = "publication_date")
	@Temporal(TemporalType.DATE)
	@Past
	@ApiModelProperty("Fecha de Publicacion")
	private Date publicationDate;
	
	@Column(name = "image_url")
	@ApiModelProperty("Url de la Imagen")
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

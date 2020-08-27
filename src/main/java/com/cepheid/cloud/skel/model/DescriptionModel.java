package com.cepheid.cloud.skel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Description Request/Response Model, An Item can have multiple descriptions")
public class DescriptionModel {
	
	private String description;
	private Long id;

	@ApiModelProperty(example = "Description of the item")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ApiModelProperty(example = "1")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}

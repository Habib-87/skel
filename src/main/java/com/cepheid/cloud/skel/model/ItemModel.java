package com.cepheid.cloud.skel.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Item Request/Response Model")
public class ItemModel {
	
	private Long id;
	private String name;
	private String state;
	private List<DescriptionModel> descriptions;
	
	@ApiModelProperty(example = "1")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ApiModelProperty(example = "Phone")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ApiModelProperty(example = "Valid")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@ApiModelProperty(example = "Descriptions of the Item")
	public List<DescriptionModel> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<DescriptionModel> descriptions) {
		this.descriptions = descriptions;
	}
	
	
	

}

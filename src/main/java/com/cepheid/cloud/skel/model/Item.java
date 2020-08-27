package com.cepheid.cloud.skel.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Item")
public class Item extends AbstractEntity {
		
	public Item() {
		
	}	
	private String name;	
	@Enumerated(EnumType.STRING)
    private State type;
	@Transient
	private String state;
	@OneToMany(targetEntity = Description.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "item_mid")
	private List<Description> descriptions;
	
	public List<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public State getType() {
		return type;
	}
	public void setType(State type) {
		this.type = type;
	}
	
	
	

}

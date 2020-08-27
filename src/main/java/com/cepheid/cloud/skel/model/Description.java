package com.cepheid.cloud.skel.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Description")
public class Description extends AbstractEntity {
		
	private String description;

	/*
	 * @JsonIgnore
	 * 
	 * @ManyToOne private Item item;
	 */
	public String getDescription() {
		return description;
	}

	public Description() {
	}

	/*
	 * public Item getItem() { return item; }
	 * 
	 * public void setItem(Item item) { this.item = item; }
	 */

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	

}

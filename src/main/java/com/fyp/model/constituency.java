package com.fyp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="constituency")
public class constituency {
	
	public constituency(){}
	public constituency(String cid, String region) {
		super();
		this.cid = cid;
		this.region = region;
	}
	@Id
	@Column
	private String cid;
	@Column
	private String region;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

}

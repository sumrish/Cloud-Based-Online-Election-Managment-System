package com.fyp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="admin")
public class admin {
	
	public admin(){}
	
	public admin(String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}
	@Id
	@Column
	private String username;
	@Column
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

	
	
	

}

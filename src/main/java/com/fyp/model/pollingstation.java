package com.fyp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pollingstation")
public class pollingstation {
	
	
	
	public pollingstation(int pid, String cid, String pollingaddress) {
		super();
		this.pid = pid;
		this.cid = cid;
		this.pollingaddress = pollingaddress;
	}
	public pollingstation(){}
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO) //for autonumber
	private int pid;
	@Column
	private String cid;
	@Column
	private String pollingaddress;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPollingaddress() {
		return pollingaddress;
	}
	public void setPollingaddress(String pollingaddress) {
		this.pollingaddress = pollingaddress;
	}
	

	
	
}

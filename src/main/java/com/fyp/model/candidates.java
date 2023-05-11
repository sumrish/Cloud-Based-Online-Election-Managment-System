package com.fyp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="candidates")
public class candidates {
	
	public candidates(){}
	
	public candidates(long ccnic, String candidatename, String partyname, byte[] symbol, String cid,byte[] pic) {
		super();
		this.ccnic = ccnic;
		this.candidatename = candidatename;
		this.partyname = partyname;
		this.symbol = symbol;
		this.cid = cid;
		this.pic =pic;
	}
	@Id
	@Column
	private long ccnic;
	@Column
	private String candidatename;
	@Column
	private String partyname;
	@Column
	private byte[] symbol;
	@Column
	private String cid;
	@Column 
	private byte[] pic;
	

	public long getCcnic() {
		return ccnic;
	}
	public void setCcnic(long ccnic) {
		this.ccnic = ccnic;
	}
	public String getCandidatename() {
		return candidatename;
	}
	public void setCandidatename(String candidatename) {
		this.candidatename = candidatename;
	}
	public String getPartyname() {
		return partyname;
	}
	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public byte[] getSymbol() {
		return symbol;
	}

	public void setSymbol(byte[] symbol) {
		this.symbol = symbol;
	}

}

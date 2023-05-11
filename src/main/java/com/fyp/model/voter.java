package com.fyp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="voter")
public class voter {
	
	public voter(long cnic, String firstname, String lastname, String gender, String city, String address, int voteno,
			String cid, int pid, byte[] flag, byte[] pic, byte[] fg1, byte[] fg2,String polling) {
		super();
		this.cnic = cnic;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.city = city;
		this.address = address;
		this.voteno = voteno;
		this.cid = cid;
		this.pid = pid;
		this.flag = flag;
		this.pic = pic;
		this.fg1 = fg1;
		this.fg2 = fg2;
		this.polling = polling;
	}
	public voter(){}
	public voter(voter vt)
	{
		this.cnic = vt.cnic;
		this.firstname = vt.firstname;
		this.lastname = vt.lastname;
		this.gender = vt.gender;
		this.city = vt.city;
		this.address =vt.address;
		this.voteno = vt.voteno;
		this.cid = vt.cid;
		this.pid = vt.pid;
		this.flag = vt.flag;
		this.pic=vt.pic;
		this.fg1=vt.fg1;
		this.fg2=vt.fg2;
		this.polling=vt.polling;
	}
	
	@Id
	@Column
	private long cnic;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Column
	private String gender;
	@Column
	private String city;
	@Column
	private String address;
	@Column
	private int voteno;
	@Column
	private String cid;
	@Column
	private int pid;
	@Column
	private byte[] flag;
	@Column
	private byte[] pic;
	@Column
	private byte[] fg1;
	@Column
	private byte[] fg2;
	
	@Column
	private String polling;


	public long getCnic() {
		return cnic;
	}
	public void setCnic(long cnic) {
		this.cnic = cnic;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getVoteno() {
		return voteno;
	}
	public void setVoteno(int voteno) {
		this.voteno = voteno;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public byte[] getFlag() {
		return flag;
	}
	public void setFlag(byte[] flag) {
		this.flag = flag;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	public byte[] getFg1() {
		return fg1;
	}
	public void setFg1(byte[] fg1) {
		this.fg1 = fg1;
	}
	public byte[] getFg2() {
		return fg2;
	}
	public void setFg2(byte[] fg2) {
		this.fg2 = fg2;
	}
	public String getPolling() {
		return polling;
	}
	public void setPolling(String polling) {
		this.polling = polling;
	}
	
	

}

package com.fyp.model;

public class Candi {

	public Candi(){}
	public Candi(long[] ccnic) {
		super();
		this.ccnic = ccnic;
	}

	private long[] ccnic;
	public long[] getCcnic() {
	      return ccnic;
	  }

	  public void setCcnic(long[] ccnic) {
	      this.ccnic = ccnic;
	  }
	
}

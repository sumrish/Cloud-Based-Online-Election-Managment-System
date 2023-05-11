package com.fyp.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.fyp.model.Votes;
import com.fyp.model.admin;
import com.fyp.model.candidates;
import com.fyp.model.pollingstation;
import com.fyp.model.provisionalcandidates;
import com.fyp.model.provisionalvotes;
import com.fyp.model.voter;

public interface voterDao {
	
	public voter getVoter(long cnic);
	public List getCandidates(String cid);
	public Votes getCand(long cnic);
	public provisionalvotes getProCand(long cnic);
	public void edit(Votes votes);
	public void editPro(provisionalvotes votes);
	public List getProCandidates(String cid);
	public void updateFlag(voter vote);
	
	
	public admin check(String username, String password);
	//public admin checkOne(String username);
	
	
	public void AddNationalCand(candidates cand);
	public void AddNationalCandToVotes(Votes votes);
	public List getConstituencies();
	public void AddProvisionalCand(provisionalcandidates cand);
	public void AddProvisionalCandToVotes(provisionalvotes votes);
	
	public List getAllNatCandidates();
	public List getAllProCandidates();
	
	public void updateNatCand(candidates cand);
	public void updateProCand(provisionalcandidates cand);
	
	public List getNatCandName(String candidateName);
	public List getNatParty(String partyname);
	public List getNatConstituency(String cid);
	
	public List getProCandName(String candidateName);
	public List getProParty(String partyname);
	public List getProConstituency(String cid);
	
	public void deleteNatCand(long ccnic);
	public void deleteProCand(long ccnic);
	
	public candidates getNatSingleCandidateFromCnic(long ccnic);
	public provisionalcandidates getProSingleCandidateFromCnic(long ccnic);
	
	public List getPollingStations();
	
	public pollingstation getPidFromPollingname(String polling);
	
	public void addVoter(voter vt);
	
	public voter getVoterFg1(long vtcnic);
	public voter getVoterFg2(long vtcnic);
}

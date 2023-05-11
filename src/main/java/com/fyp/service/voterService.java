package com.fyp.service;

import java.security.Key;
import java.security.KeyStore;
import java.util.List;

import javax.crypto.SecretKey;

import com.fyp.model.Votes;
import com.fyp.model.admin;
import com.fyp.model.adminReq;
import com.fyp.model.candidates;
import com.fyp.model.pollingstation;
import com.fyp.model.provisionalcandidates;
import com.fyp.model.provisionalvotes;
import com.fyp.model.voter;

public interface voterService {
	public String getVoter(long cnic);
	public voter getSingleVoter(long cnic);
	
	public List getCandidates();
	public void updateVotes(long ccnic);
	public List getProCandidates();
	public void updateProVotes(long ccnic);
	public void updateFlag();
	public void updateFlag(voter vot);
	
	public String check(admin admin1);
	//public String checkOne(adminReq admin1);
	//public byte[] encryptPassword(String password);
	//public String decryptPassword(byte[] text);
	
	public List getConstituencies();
	
	public void AddNationalCand(candidates cand);
	public void AddNationalCandToVotes(Votes votes);
	public void AddNatToVotes(long ccnic);
	
	
	
	public byte[] EncryptionAES(String text,long ccnic);
	public String DecrptionAES(byte[] cipher,long ccnic);
	public KeyStore createKeyStore(String fileName, String pw) throws Exception;
	public String base64String(SecretKey secretKey);
	public SecretKey generateSecretKey();
	public void storeSecretKey(SecretKey secretKey, long ccnic) throws Exception;
	public SecretKey retrieveSecretKey(long ccnic) throws Exception;
	
	
	public void AddProvisionalCand(provisionalcandidates cand);
	public void AddProvisionalCandToVotes(provisionalvotes votes);
	public void AddProToVotes(long ccnic);
	
	public List getAllNatCandidates();
	public List getAllProCandidates();
	
	public void updateNatCand(candidates cand);
	public void updateProCand(provisionalcandidates cand);
	
	public List getNatCandName(candidates candidate);
	public List getNatParty(candidates candidate);
	public List getNatConstituency(candidates candidate);
	
	public List getProCandName(provisionalcandidates candidate);
	public List getProParty(provisionalcandidates candidate);
	public List getProConstituency(provisionalcandidates candidate);
	
	public void deleteNatCand(long ccnic);
	
	public void deleteProCand(long ccnic);
	

	public candidates getNatSingleCandidateFromCnic(long ccnic);
	public provisionalcandidates getProSingleCandidateFromCnic(long ccnic);
	
	public List getPollingStations();
	
	public byte[] addFlag(long cnic);
	
	public int getPidFromPollingname(String polling);
	
	public void addVoter(voter vt);
	
	public byte[] getVoterFg1(long vtcnic);
	public byte[] getVoterFg2(long vtcnic);
}

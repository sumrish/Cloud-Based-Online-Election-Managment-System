package com.fyp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;



import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fyp.dao.voterDao;
import com.fyp.model.Votes;
import com.fyp.model.admin;
import com.fyp.model.adminReq;
import com.fyp.model.candidates;
import com.fyp.model.pollingstation;
import com.fyp.model.provisionalcandidates;
import com.fyp.model.provisionalvotes;
import com.fyp.model.voter;
import com.fyp.service.voterService;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class voterServiceImpl implements voterService {
	
	@Autowired
	private voterDao voterdao;
	private voter vt;
//	String key = "Bar12345Bar12345"; // 128 bit key
	


	@Transactional
	public String getVoter(long cnic) {
		vt= voterdao.getVoter(cnic);
		byte[] flagencypted=vt.getFlag();
		String decrypt=VoterDecrptionAES(flagencypted,cnic);
		int flag = Integer.parseInt(decrypt);
		
		
		if(vt==null)
		{
			return "index?cnic=error";
		}
		else if(vt!=null && flag==1)
		{
			return "index?flag=error";
		}
		else if(vt!=null && flag!=1)
		{
			return "verifyFinger";
		}
		else 
			return "index";
	}

	@Transactional
	public voter getSingleVoter(long cnic) {
		vt= voterdao.getVoter(cnic);
		return vt;
		
	}

	
	
	@Transactional
	public List getCandidates()
	{
		String cid=vt.getCid();
		System.out.println(cid);
		return voterdao.getCandidates(cid);
	}
	
	@Transactional
	public List getProCandidates()
	{
		String cid=vt.getCid();
		System.out.println(cid);
		return voterdao.getProCandidates(cid);
	}
	
	@Transactional
	public void updateVotes(long ccnic)
	{
		Votes vot=voterdao.getCand(ccnic);
		byte[] vote_no=vot.getVote_no();
		String decrypt=DecrptionAES(vote_no,ccnic);
		int foo = Integer.parseInt(decrypt);
		foo=foo+1;
		System.out.println("Votes:"+foo);
		String text=Integer.toString(foo);
		byte[] encrypt=EncryptionAES(text,ccnic);
		vot.setVote_no(encrypt);
		voterdao.edit(vot);
	}
	
	@Transactional
	public void updateProVotes(long ccnic)
	{
		provisionalvotes vot=voterdao.getProCand(ccnic);
		byte[] vote_no=vot.getVote_no();
		String decrypt=DecrptionAES(vote_no,ccnic);
		int foo = Integer.parseInt(decrypt);
		foo=foo+1;
		System.out.println("Pro Votes:"+foo);
		String text=Integer.toString(foo);
		byte[] encrypt=EncryptionAES(text,ccnic);
		vot.setVote_no(encrypt);
		voterdao.editPro(vot);
	}
	
	@Transactional
	public void updateFlag()
	{
		byte[] encrypt=VoterEncryptionAES("1",vt.getCnic());
		vt.setFlag(encrypt);
		voterdao.updateFlag(vt);
	}
	
	@Transactional
	public void updateFlag(voter vot)
	{
		byte[] encrypt=VoterEncryptionAES("1",vot.getCnic());
		vot.setFlag(encrypt);
		voterdao.updateFlag(vot);
	}
	
	@Transactional
	public byte[] addFlag(long cnic)
	{
		byte[] encrypt=VoterEncryptionAES("0",cnic);
		return encrypt;
	}
	
	private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    
	public String Encrypt(String value) throws Exception
	    {
	        Key key = generateKey();
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
	        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
	        System.out.println("Encrypt");
	        //System.out.println( encryptedValue64);
	        //decrypt(encryptedValue64);  
	        
	       return encryptedValue64;
	        
	       
	    }
	    
	    public void decrypt(String value) throws Exception
	    {
	        Key key = generateKey();
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
	        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
	        String decryptedValue = new String(decryptedByteValue,"utf-8");
	        System.out.println("Decrypt");
	        System.out.println( decryptedValue);
	                
	    }
	    
	    private static Key generateKey() throws Exception 
	    {
	        Key key = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
	        return key;
	    }
	
	@Transactional
	public String check(admin admin1)
	{
		admin admin2=new admin();
		String Password=admin1.getPassword();
		String Username= admin1.getUsername();
		
		
			try {
				//Encrypt(Password);
				Password= Encrypt(Password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		admin2=voterdao.check(Username,Password);
		
		
		if(admin2!=null)
		{
			
			String user =admin1.getUsername();
			String one=user.split("\\.")[1];
			System.out.println(one);
			if(one.equals("returning"))
			
				return "returningHome";
			
			else if(one.equals("electcommission"))
				return "electioncommissionHome";
			else
				return "presidingHome";
		}
		else
		return "login";
	}
	
	
	/*@Transactional
	public String checkOne(adminReq admin1)
	{
		String ret="";
		admin admin2=new admin();
		
		/*try {
			byte[] password=encryptPassword("saba@123");
			System.out.println("password: "+password);
			admin2.setPassword(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//admin2.setUsername(admin1.getUsername());
		//admin2.setPassword(password);
		/*System.out.println("username: "+admin2.getUsername());
		
		System.out.println("admin2.password: "+admin2.getPassword());
		System.out.println("admin1.password: "+admin1.getPassword());
		System.out.println("[B@282ba1e.getBytes() :"+new String(admin2.getPassword()));
		System.out.println("decrypted: "+decryptPassword(admin2.getPassword()));
		String a=new String(admin2.getPassword());
		byte[] out = new byte[a.getBytes().length+1];
		System.out.println("decrypted: "+decryptPassword(out));*/
		
		
	
		/*admin2=voterdao.checkOne(admin1.getUsername());
		if(admin2!=null)
		{
			byte[] pass=admin2.getPassword();
			System.out.println("username " +admin2.getUsername());
			System.out.println("byte retrieve: " +pass);
			//String s=new String(pass);
			String de=decryptPassword(pass);
			if(de.equals(admin1.getPassword()))
			{
				String user =admin1.getUsername();
				String one=user.split("\\.")[1];
				System.out.println(one);
				if(one.equals("returning"))
				
					//return "returningHome";
					ret= "returningHome";
				
				else if(one.equals("electcommission"))
					//return "electioncommissionHome";
					ret= "electioncommissionHome";
				else
					//return "presidingHome";
					ret= "presidingHome";
			}
			
		}
		else
		
		ret= "login";
		
		return ret;
	}*/
	
	/*@Transactional
	public byte[] encryptPassword(String text)
	{
		 byte[] encrypted = null;
		try {
			  
	         String key = "app12345app54321"; // 128 bit key

	         // Create key and cipher
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
			 
			 System.out.println("key: "+aesKey);

	         // encrypt the text
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         encrypted = cipher.doFinal(text.getBytes());
	         System.out.println(encrypted);

	 

	   

	      }catch(Exception e) {

	         e.printStackTrace();
	      }

        
        return encrypted;
	}*/
	
	/*@Transactional
	public String decryptPassword(byte[] text)
	{
		String decrypted = "";
		try {
			  
	         String key = "app12345app54321"; // 128 bit key

	         // Create key and cipher
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
			 
			 System.out.println("key: "+aesKey);

	         

	         // decrypt the text

	         cipher.init(Cipher.DECRYPT_MODE, aesKey);
	         decrypted = new String(cipher.doFinal(text));
			 
			
	         System.err.println(decrypted);

	      }catch(Exception e) {

	         e.printStackTrace();
	      }

        
        return decrypted;
	}*/
	
	@Transactional
	public void AddNationalCand(candidates cand)
	{
		voterdao.AddNationalCand(cand);
	}
	
	@Transactional
	public void AddProvisionalCand(provisionalcandidates cand)
	{
		voterdao.AddProvisionalCand(cand);
	}
	
	@Transactional
	public List getConstituencies()
	{
		return voterdao.getConstituencies();
	}
	
	@Transactional
	public List getPollingStations()
	{
		return voterdao.getPollingStations();
	}
	
	@Transactional
	public void AddNatToVotes(long ccnic)
	{
		Votes vote=new Votes();
		vote.setCcnic(ccnic);
		String text=Integer.toString(0);
		byte[] encrypt=EncryptionAES(text,ccnic);
		vote.setVote_no(encrypt);
		System.out.println("Text: "+text);
		System.out.println("encrypt: "+encrypt);
		AddNationalCandToVotes(vote);
	}
	
	@Transactional
	public void AddProToVotes(long ccnic)
	{
		provisionalvotes vote=new provisionalvotes();
		vote.setCcnic(ccnic);
		String text=Integer.toString(0);
		byte[] encrypt=EncryptionAES(text,ccnic);
		vote.setVote_no(encrypt);
		AddProvisionalCandToVotes(vote);
	}
	
	@Transactional
	public void AddNationalCandToVotes(Votes votes)
	{
		voterdao.AddNationalCandToVotes(votes);
	}
	
	@Transactional
	public void AddProvisionalCandToVotes(provisionalvotes votes)
	{
		voterdao.AddProvisionalCandToVotes(votes);
	}
	
	@Transactional
	public byte[] EncryptionAES(String text,long ccnic)
	{
		byte[] encrypted = null;
		 try {

			 SecretKey secretKey=generateSecretKey();
			 storeSecretKey(secretKey,ccnic);
			 String sKey=base64String(secretKey);
			 //Key aesKey = new SecretKeySpec(sKey.getBytes(), "AES");

			 Cipher cipher = Cipher.getInstance("AES");
			 	
			// encrypt the text
			// cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			 cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			 
			 encrypted = cipher.doFinal(text.getBytes());

		 }catch(Exception e) {
			 e.printStackTrace();
		 }

		return encrypted;
	}
	
	@Transactional
	public byte[] VoterEncryptionAES(String text,long ccnic)
	{
		byte[] encrypted = null;
		 try {

			 SecretKey secretKey=generateSecretKey();
			 VoterstoreSecretKey(secretKey,ccnic);
			 String sKey=base64String(secretKey);
			 //Key aesKey = new SecretKeySpec(sKey.getBytes(), "AES");

			 Cipher cipher = Cipher.getInstance("AES");
			 	
			// encrypt the text
			// cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			 cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			 
			 encrypted = cipher.doFinal(text.getBytes());

		 }catch(Exception e) {
			 e.printStackTrace();
		 }

		return encrypted;
	}
	
	
	
	
	@Transactional
	public String DecrptionAES(byte[] ciphertext,long ccnic)
	{
		String decrypted="";
		 try {
			 SecretKey secretKey=retrieveSecretKey(ccnic);
			 String sKey=base64String(secretKey);
			// Key aesKey = new SecretKeySpec(sKey.getBytes(), "AES");
			 Cipher cipher = Cipher.getInstance("AES");
			 
			// decrypt the text
			// cipher.init(Cipher.DECRYPT_MODE, aesKey);
			 cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decrypted = new String(cipher.doFinal(ciphertext));
		 }catch(Exception e) {
			 e.printStackTrace();
		 }

		 return decrypted;
	}
	

	@Transactional
	public String VoterDecrptionAES(byte[] ciphertext,long ccnic)
	{
		String decrypted="";
		 try {
			 SecretKey secretKey=VoterretrieveSecretKey(ccnic);
			 String sKey=base64String(secretKey);
			// Key aesKey = new SecretKeySpec(sKey.getBytes(), "AES");
			 Cipher cipher = Cipher.getInstance("AES");
			 
			// decrypt the text
			// cipher.init(Cipher.DECRYPT_MODE, aesKey);
			 cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decrypted = new String(cipher.doFinal(ciphertext));
		 }catch(Exception e) {
			 e.printStackTrace();
		 }

		 return decrypted;
	}
	
	@Transactional
	public KeyStore createKeyStore(String fileName, String pw) throws Exception  {
		 File file = new File(fileName);

	        final KeyStore keyStore = KeyStore.getInstance("JCEKS");
	        if (file.exists()) {
	            // .keystore file already exists => load it
	            keyStore.load(new FileInputStream(file), pw.toCharArray());
	            System.out.println("Existing .keystore file loaded!");
	        } else {
	            // .keystore file not created yet => create it
	            keyStore.load(null, null);
	            keyStore.store(new FileOutputStream(fileName), pw.toCharArray());
	            System.out.println("New .keystore file created!");
	        }

	        return keyStore;
	}
	
	@Transactional
	public KeyStore VotercreateKeyStore(String fileName, String pw) throws Exception  {
		 File file = new File(fileName);

	        final KeyStore keyStore = KeyStore.getInstance("JCEKS");
	        if (file.exists()) {
	            // .keystore file already exists => load it
	            keyStore.load(new FileInputStream(file), pw.toCharArray());
	            System.out.println("Existing .keystore file loaded!");
	        } else {
	            // .keystore file not created yet => create it
	            keyStore.load(null, null);
	            keyStore.store(new FileOutputStream(fileName), pw.toCharArray());
	            System.out.println("New .keystore file created!");
	        }

	        return keyStore;
	}
	
	@Transactional
	public String base64String(SecretKey secretKey) {
        return new String(Base64.getEncoder().encode(secretKey.getEncoded()));
    }
	
	@Transactional
	public SecretKey generateSecretKey()
	{
		SecretKey secretKey = null;
		
		 // generate a secret key for AES encryption
			 try {
				 KeyGenerator keyGen = KeyGenerator.getInstance("AES");

			        keyGen.init(128);
				//secretKey = KeyGenerator.getInstance("AES").generateKey();
			        secretKey= keyGen.generateKey();
				System.out.println("KEY:  "+base64String(secretKey));
				System.out.println("KEY1:  "+secretKey);
				System.out.println("KEY2:  "+secretKey.toString());
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return secretKey;
	}
	
	
	@Transactional
	public void storeSecretKey(SecretKey secretKey, long ccnic) throws Exception 
	{
		 final String keyStoreFile = "C:/Users/Saba Saeed/workspace/FinalFYP/javacirecep.keystore";
	     KeyStore keyStore = createKeyStore(keyStoreFile, "javaci123");
		 // store the secret key
	     String stringCcnic= Long.toString(ccnic);
        KeyStore.SecretKeyEntry keyStoreEntry = new KeyStore.SecretKeyEntry(secretKey);
        PasswordProtection keyPassword = new PasswordProtection(stringCcnic.toCharArray());
        keyStore.setEntry(stringCcnic, keyStoreEntry, keyPassword);
        keyStore.store(new FileOutputStream(keyStoreFile), "javaci123".toCharArray());

	}
	
	@Transactional
	public void VoterstoreSecretKey(SecretKey secretKey, long ccnic) throws Exception 
	{
		 final String keyStoreFile = "C:/Users/Saba Saeed/workspace/FinalFYP/voter.keystore";
	     KeyStore keyStore = createKeyStore(keyStoreFile, "javaci123");
		 // store the secret key
	     String stringCcnic= Long.toString(ccnic);
        KeyStore.SecretKeyEntry keyStoreEntry = new KeyStore.SecretKeyEntry(secretKey);
        PasswordProtection keyPassword = new PasswordProtection(stringCcnic.toCharArray());
        keyStore.setEntry(stringCcnic, keyStoreEntry, keyPassword);
        keyStore.store(new FileOutputStream(keyStoreFile), "javaci123".toCharArray());

	}
	
	
	@Transactional 
	public SecretKey retrieveSecretKey(long ccnic) throws Exception 
	{ 
		String stringCcnic= Long.toString(ccnic);
		PasswordProtection keyPassword = new PasswordProtection(stringCcnic.toCharArray());
		final String keyStoreFile = "C:/Users/Saba Saeed/workspace/FinalFYP/javacirecep.keystore";
		KeyStore keyStore = createKeyStore(keyStoreFile, "javaci123");
        KeyStore.Entry entry = keyStore.getEntry(Long.toString(ccnic), keyPassword);
        SecretKey keyFound = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
        return keyFound;
	}
	
	@Transactional 
	public SecretKey VoterretrieveSecretKey(long ccnic) throws Exception 
	{ 
		String stringCcnic= Long.toString(ccnic);
		PasswordProtection keyPassword = new PasswordProtection(stringCcnic.toCharArray());
		final String keyStoreFile = "C:/Users/Saba Saeed/workspace/FinalFYP/voter.keystore";
		KeyStore keyStore = createKeyStore(keyStoreFile, "javaci123");
        KeyStore.Entry entry = keyStore.getEntry(Long.toString(ccnic), keyPassword);
        SecretKey keyFound = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
        return keyFound;
	}
	
	@Transactional
	public List getAllNatCandidates()
	{
		return voterdao.getAllNatCandidates();
	}
	
	@Transactional
	public List getAllProCandidates()
	{
		return voterdao.getAllProCandidates();
	}

	@Transactional
	public void updateNatCand(candidates cand)
	{
		voterdao.updateNatCand(cand);
	}

	
	@Transactional
	public void updateProCand(provisionalcandidates cand)
	{
		voterdao.updateProCand(cand);
	}
	
	@Transactional
	public List getNatCandName(candidates candidate)
	{
		//return voterdao.getNatCandName(candidate.getCandidatename());
		return voterdao.getNatCandName(candidate.getPartyname());
		
	}
	
	@Transactional
	public List getNatParty(candidates candidate)
	{
		//System.out.println(candidate.getPartyname());
		return voterdao.getNatParty(candidate.getPartyname());
		
	}
	
	@Transactional
	public List getNatConstituency(candidates candidate)
	{
		return voterdao.getNatConstituency(candidate.getPartyname());
	}

	@Transactional
	public List getProCandName(provisionalcandidates candidate)
	{
		//return voterdao.getNatCandName(candidate.getCandidatename());
		return voterdao.getProCandName(candidate.getPartyname());
		
	}
	
	@Transactional
	public List getProParty(provisionalcandidates candidate)
	{
		//System.out.println(candidate.getPartyname());
		return voterdao.getProParty(candidate.getPartyname());
		
	}
	
	@Transactional
	public List getProConstituency(provisionalcandidates candidate)
	{
		return voterdao.getProConstituency(candidate.getPartyname());
	}
	
	@Transactional
	public void deleteNatCand(long ccnic)
	{
		voterdao.deleteNatCand(ccnic);
	}
	
	@Transactional
	public void deleteProCand(long ccnic)
	{
		voterdao.deleteProCand(ccnic);
	}
	
	@Transactional
	public candidates getNatSingleCandidateFromCnic(long ccnic)
	{
		return voterdao.getNatSingleCandidateFromCnic(ccnic);
		
	}
	
	@Transactional
	public provisionalcandidates getProSingleCandidateFromCnic(long ccnic)
	{
		return voterdao.getProSingleCandidateFromCnic(ccnic);
		
	}
	@Transactional
	public int getPidFromPollingname(String polling)
	{
		pollingstation po= voterdao.getPidFromPollingname(polling);
		int pid= po.getPid();
		return pid;
	}
	
	@Transactional
	public void addVoter(voter vt)
	{
		voterdao.addVoter(vt);
		
	}
	
	@Transactional
	public byte[] getVoterFg1(long vtcnic)
	{
		
		voter voter=voterdao.getVoterFg1(vtcnic);
		return voter.getFg1();
		
	}
	
	@Transactional
	public byte[] getVoterFg2(long vtcnic)
	{
		
		voter voter=voterdao.getVoterFg2(vtcnic);
		return voter.getFg2();
		
	}
	
	
	
}

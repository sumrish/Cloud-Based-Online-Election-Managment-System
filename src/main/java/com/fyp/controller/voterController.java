package com.fyp.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fyp.model.Candi;
import com.fyp.model.admin;
import com.fyp.model.adminReq;
import com.fyp.model.candidates;
import com.fyp.model.constituency;
import com.fyp.model.pollingstation;
import com.fyp.model.provisionalcandidates;
import com.fyp.model.voter;
import com.fyp.service.voterService;

import SecuGen.FDxSDKPro.jni.JSGFPLib;
import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxDeviceName;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFDxSecurityLevel;
import SecuGen.FDxSDKPro.jni.SGFDxTemplateFormat;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;
import SecuGen.FDxSDKPro.jni.SGFingerPosition;
import SecuGen.FDxSDKPro.jni.SGImpressionType;
import SecuGen.FDxSDKPro.jni.SGPPPortAddr;



/*import SecuGen.FDxSDKPro.jni.JSGFPLib;
import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxDeviceName;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFDxSecurityLevel;
import SecuGen.FDxSDKPro.jni.SGFDxTemplateFormat;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;
import SecuGen.FDxSDKPro.jni.SGFingerPosition;
import SecuGen.FDxSDKPro.jni.SGImpressionType;
import SecuGen.FDxSDKPro.jni.SGPPPortAddr;
*/











@Controller
//@SessionAttributes({"votercnic","natCnic","proCnic"})
@SessionAttributes({"votercnic","natCnic","proCnic","adminusername","homePage","voterdata"})
//@SessionAttributes("natCnic")
//@SessionAttributes("proCnic")
public class voterController {
	
	@Autowired
	private voterService voterservice;
	JSGFPLib sgfplib = new JSGFPLib();
	//private long natCnic=1;
	//private long proCnic=1;
	//private voter vot;
	
	//long id;
	
	@RequestMapping("/index")
	public String first(final ModelMap model,HttpSession session)
	{
		voter voter1=new voter();
		admin admin1=new admin();
		 model.addAttribute("voter",voter1);
		 model.addAttribute("admin1",admin1);
		 String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		model.put("votercnic",0L);
		 
		 if(ad==null || pg==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		 
		return "home";
	}
	
	@RequestMapping("/team")
	public String team()
	{
	          return "team";
	}
	
	@RequestMapping("/about")
	public String about()
	{
	          return "about";
	}
	
	@RequestMapping("/home")
	public String error1(final ModelMap model,HttpSession session)
	{
		voter voter1=new voter();
		 model.addAttribute("voter",voter1);
		 String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 model.put("votercnic",0L);
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		 
		return "home";
	}
	
	@RequestMapping("/castvote")
	public String error(final ModelMap model)
	{
		voter voter1=new voter();
		 model.addAttribute("voter",voter1);
		 
		 
		return "verifyvoter";
	}
	
	@RequestMapping(value="/processing", method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("voter") voter voter ,  BindingResult result, final ModelMap model) {
		  
			long id=voter.getCnic();
		      String next=voterservice.getVoter(id);
		      model.put("natCnic", 0L);
			  model.put("proCnic", 0L);
		      candidates cand=new candidates();
		     
		      if(next=="verifyFinger" || next=="index")
		      {
		    	 model.put("votercnic",id);
		    	// vot=voterservice.getSingleVoter(id);
		    	//vot=new voter(voter);
		    	
		    	 System.out.println("Voter Cnic: "+id);
		    	 System.out.println("Voter Adreess: "+voter.getAddress());
		    	 
		    	// System.out.println("Vot Cnic: "+vot.getCnic());
		    	 
		    	  return next;
		      }
		      
		      else if(next=="index?cnic=error")
		      {
		    	  model.addAttribute("error","CNIC does not exist,Try Again!");
		      }
		      
		      else if(next=="index?flag=error")
		      {
		    	  model.addAttribute("error","You have already casted the vote.");
		      }
		      
		     
		      
		      
		        return "home";
		    }
	
	
	@RequestMapping(value="/voting.do", method=RequestMethod.POST)
	public String doActions(@ModelAttribute voter voter,  BindingResult result, @RequestParam String action, Map<String, Object> map){
		String page="";
		//System.out.println(action);
		
		candidates cand=new candidates();
		map.put("candidate", cand);
		switch(action.toLowerCase()){
		case "national":
			page="national";
			map.put("candidate", cand);
			map.put("CandList", voterservice.getCandidates());
			
			break;
		case "provisional":
			page="provisional";
			map.put("candidate", cand);
			map.put("CandList1", voterservice.getProCandidates());
			
			break;
		}
		
		return page;
	}
	
	@RequestMapping(value="/national.do", method=RequestMethod.POST)
	public String CastVoteNational(@ModelAttribute candidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
	
		 long natCnic;
		
		natCnic=candidate.getCcnic();
		//voterservice.updateVotes(natCnic);
		map.put("natCnic", natCnic);
		
		map.put("done", "Your vote has been casted for National Assembly.");
		
		return "proceed";
	}
	
	@RequestMapping(value="/provisional.do", method=RequestMethod.POST)
	public String CastVoteProvisional(@ModelAttribute provisionalcandidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
		long proCnic;
		proCnic=candidate.getCcnic();
		//System.out.println(cnic);
		//voterservice.updateProVotes(cnic);
		map.put("proCnic", proCnic);
		
	
		map.put("done", "Your vote has been casted for Provisional Assembly.");
		
		return "proceed";
	}

	@RequestMapping(value="/votecast.do", method=RequestMethod.POST)
	//public String CastVotefinal(@ModelAttribute provisionalcandidates candidate,ModelMap model){
		
	public String CastVotefinal(HttpSession session, SessionStatus status,ModelMap model){
	
		//candidates cand=(candidates) session.getAttribute("natCnic");
		//provisionalcandidates pro=(provisionalcandidates)session.getAttribute("proCnic");
		
		//long natCnic=cand.getCcnic();
		//long proCnic=pro.getCcnic();
		long natCnic= (long) session.getAttribute("natCnic");
		long proCnic= (long) session.getAttribute("proCnic");
		//System.out.println("natCnic before submission: "+natCnic);
		//System.out.println("proCnic before submission: "+proCnic);
		
		if(natCnic!=0L && proCnic!=0L)
		//if(cand!=null && pro!=null)
		{
			//status.setComplete();
			voterservice.updateVotes(natCnic);
			System.out.println("National Candidate: "+natCnic);
			//System.out.println(proCnic);
			System.out.println("Provisional Candidate: "+proCnic);
			
			voterservice.updateProVotes(proCnic);
			//voterservice.updateFlag();
			long id= (long) session.getAttribute("votercnic");
			voter vo=voterservice.getSingleVoter(id);
			
			voterservice.updateFlag(vo);
			System.out.println("vo cnic: "+vo.getCnic());
		//	System.out.println("vottttt address: "+vot.getAddress());
			System.out.println("Flag updated of : " + vo.getCnic());
			
			status.setComplete();
			//natCnic=1;
			//proCnic=1;
			
			return "done";
		}
		
		else if(natCnic==0L && proCnic!=0L)
		//else if(cand==null && pro!=null)
		{
			model.put("notcomplete","Please cast vote for National Assembly");
			return "proceed";
		}
		
		else if(natCnic!=0L && proCnic==0L)
		//else if(cand!=null && pro==null)
		{
			model.put("notcomplete","Please cast vote for Provisional Assembly");
			return "proceed";
		}
		else if(natCnic==0L && proCnic==0L)
		//else if(cand==null && pro==null)
		{
		
			model.put("notcomplete","Please cast vote for both National Assembly and Provisional Assembly");
			return "proceed";
		}
		
		else 
			return "proceed";
	}
	
	
	@RequestMapping("/login")
	public String show(HttpSession session,ModelMap model)
	{
		 String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		admin admin1=new admin();
		model.addAttribute("admin1", admin1);
		//model.put("adminusername", "no");
		 //adminReq adm=new adminReq();
		// model.put("admin1",adm);
		return "login";
	}
	
@RequestMapping(value="/login.do", method=RequestMethod.POST)
public String Admin(@ModelAttribute admin admin1, BindingResult result,HttpSession session, @RequestParam String action,Map<String, Object> map){
	
	admin admin2=new admin();
	map.put("admin1", admin2);
	//adminReq adm=new adminReq();
	// map.put("admin1",adm);
	 
		String result1="";
		result1=voterservice.check(admin1);
		//result1=voterservice.checkOne(admin1);
		if(result1=="login")
		{
			map.put("loginFailed","Username or Password invalid.");
		}
		
		else
		{
			map.put("adminusername", admin1.getUsername());
			String user =admin1.getUsername();
			String one=user.split("\\.")[1];
			map.put("homePage", one);
		}
			
	
	return result1;
}

@RequestMapping("/logout")
public String logout(HttpSession session,SessionStatus status,ModelMap model)
{
	voter voter1=new voter();
	 model.addAttribute("voter",voter1);
	status.setComplete();
	admin admin1=new admin();
	model.addAttribute("admin1", admin1);
	return "login";

}

/*@RequestMapping(value="/login.do", method=RequestMethod.GET)
public String Admin(HttpSession session,Map<String, Object> map){
	String ad=(String)session.getAttribute("adminusername");
	 String pg=(String)session.getAttribute("homePage");
	 if(ad==null)
	 {
		 map.put("adminusername", "no");
		 map.put("homePage","no");
	 }
	 
	 else
	 {
		 map.put("adminusername", ad);
		 map.put("homePage",pg);
	 }
	admin admin2=new admin();
	map.put("admin1", admin2);
	String one=ad.split("\\.")[1];
	System.out.println(one);
	if(one.equals("returning"))
	
		return "returningHome";
	
	else
		return "presidingHome";
			
	
	//return result1;
}*/


	@RequestMapping("/add_national")
	public String add(HttpSession session,ModelMap model)
	{
		
		 String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		 
		candidates cand=new candidates();
		model.addAttribute("candidate", cand);
	
			
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		model.addAttribute("constList",candid);
		//}
		return "add_national";
	}
	
	@RequestMapping(value="/add_national.do", method=RequestMethod.POST)
	public String AddCandidNat(@ModelAttribute candidates cand, BindingResult result, @RequestParam("pic") MultipartFile pic,@RequestParam("symbol") MultipartFile symbol, @RequestParam("action") String action, Map<String, Object> map){
	 
		try {
			Blob blob = Hibernate.createBlob(pic.getInputStream());
			byte[] by=pic.getBytes();
			System.out.println("Image blob: "+blob);
			System.out.println("Image byte: "+by);
			System.out.println("pic: "+pic.getInputStream());
			System.out.println("multipartpic: "+pic);
			cand.setPic(by);
			
			Blob blob2 = Hibernate.createBlob(symbol.getInputStream());
			byte[] symbolbytes=symbol.getBytes();
			cand.setSymbol(symbolbytes);
			
			System.out.println("Symbol byte: "+symbolbytes);
		
			
			if(cand.getCcnic()!=0 && cand.getCandidatename()!="" && cand.getPartyname()!="" && cand.getSymbol()!=null && cand.getCid()!="" && cand.getPic()!=null && pic.getInputStream()!=null )
			{
				voterservice.AddNationalCand(cand);
				voterservice.AddNatToVotes(cand.getCcnic());
				map.put("added", "National Candidate Added!");
			}
			
			else
			{
				map.put("added", "Please Fill out all the empty fields.");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Image: "+cand.getPic());
		
		
		System.out.println("Image: "+cand.getCandidatename());
		//voterservice.AddNationalCand(cand);
		
	//	voterservice.AddNatToVotes(cand.getCcnic());
		
		candidates cando=new candidates();
		map.put("candidate", cando);
		
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		map.put("constList",candid);
		return "add_national";
	
	}
	
	@RequestMapping("/add_provisional")
	public String add_pro(HttpSession session,ModelMap model)
	{
		 String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		 
		provisionalcandidates cand=new provisionalcandidates();
		model.addAttribute("candidate", cand);
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		model.addAttribute("constList1",candid);
		return "add_provisional";
	}
	
	@RequestMapping(value="/add_provisional.do", method=RequestMethod.POST)
	public String AddCandidPro(@ModelAttribute provisionalcandidates cand, BindingResult result, @RequestParam("pic") MultipartFile pic,@RequestParam("symbol") MultipartFile symbol, @RequestParam("action") String action, Map<String, Object> map){
	 
		try {
			Blob blob = Hibernate.createBlob(pic.getInputStream());
			byte[] by=pic.getBytes();
			System.out.println("Image blob: "+blob);
			System.out.println("Image byte: "+by);
			cand.setPic(by);
			
			Blob blob2 = Hibernate.createBlob(symbol.getInputStream());
			byte[] symbolbyte=symbol.getBytes();
			cand.setSymbol(symbolbyte);
			
			if(cand.getCcnic()!=0 && cand.getCandidatename()!="" && cand.getPartyname()!="" && cand.getSymbol()!=null && cand.getCid()!="" && cand.getPic()!=null && pic.getInputStream()!=null )
			{
				voterservice.AddProvisionalCand(cand);
				voterservice.AddProToVotes(cand.getCcnic());
				map.put("added", "Provisional Candidate Added!");
			}
			else
			{
				map.put("added", "Please fill out all the empty fields.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		provisionalcandidates candi=new provisionalcandidates();
		map.put("candidate", candi);
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		map.put("constList1",candid);
		return "add_provisional";
	
	}
	
	@RequestMapping("/update_national")
	public String updateNat(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		candidates cand=new candidates();
		model.addAttribute("candidate", cand);
		model.addAttribute("NatCandidatesList",voterservice.getAllNatCandidates());
		
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		model.addAttribute("constList",candid);
		return "update_national";
	}
	
	@RequestMapping(value="/update_national.do", method=RequestMethod.POST)
	public String UpdateNat(@ModelAttribute candidates cand, BindingResult result, @RequestParam("pic") MultipartFile pic,@RequestParam("symbol") MultipartFile symbol, @RequestParam("action") String action, Map<String, Object> map){
	 
		candidates cand1=new candidates();
		map.put("candidate", cand1);
		try {
		Blob blob = Hibernate.createBlob(pic.getInputStream());
		byte[] by=pic.getBytes();
		System.out.println("Image blob: "+blob);
		System.out.println("Image byte: "+by);
		cand.setPic(by);
		
		Blob blob2 = Hibernate.createBlob(symbol.getInputStream());
		byte[] symbolbytes=symbol.getBytes();
		cand.setSymbol(symbolbytes);
		
		if(cand.getCcnic()!=0 && cand.getCandidatename()!="" && cand.getPartyname()!="" && cand.getSymbol()!=null && cand.getCid()!="" && cand.getPic()!=null && pic.getInputStream()!=null )
		{
			System.out.println("Candidate name:"+cand.getCandidatename());
			System.out.println("Candidate party: "+cand.getPartyname());
			voterservice.updateNatCand(cand);
			map.put("updated", "National Candidate "+cand.getCcnic()+" Updated!");
		}
		
		else
		{
			map.put("updated", "Please Fill out all the empty fields.");
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("NatCandidatesList",voterservice.getAllNatCandidates());
		
		
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		map.put("constList",candid);
		
		return "update_national";
	}

	
	@RequestMapping("/update_provisional")
	public String updatePro(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		provisionalcandidates cand=new provisionalcandidates();
		model.addAttribute("candidate", cand);
		model.addAttribute("ProCandidatesList",voterservice.getAllProCandidates());
		
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		model.addAttribute("constList",candid);
		return "update_provisional";
	}
	
	@RequestMapping(value="/update_provisional.do", method=RequestMethod.POST)
	public String UpdatePro(@ModelAttribute provisionalcandidates cand, BindingResult result, @RequestParam("pic") MultipartFile pic,  @RequestParam("symbol") MultipartFile symbol, @RequestParam("action") String action, Map<String, Object> map){
	 
		provisionalcandidates cand1=new provisionalcandidates();
		map.put("candidate", cand1);
		try {
			Blob blob = Hibernate.createBlob(pic.getInputStream());
			byte[] by=pic.getBytes();
			System.out.println("Image blob: "+blob);
			System.out.println("Image byte: "+by);
			cand.setPic(by);
			
			Blob blob2 = Hibernate.createBlob(symbol.getInputStream());
			byte[] symbolbyte=symbol.getBytes();
			cand.setSymbol(symbolbyte);
			
			voterservice.updateProCand(cand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("ProCandidatesList",voterservice.getAllProCandidates());
		map.put("updated", "Provisional Candidate "+cand.getCcnic()+" Updated!");
		
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		map.put("constList",candid);
		
		return "update_provisional";
	}
	
	@RequestMapping("/returningHome")
	public String showReturningHome(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		
		return "returningHome";
	}
	
	@RequestMapping("/electioncommissionHome")
	public String showElectCommissionHome(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		
		return "electioncommissionHome";
	}
	
	@RequestMapping("/backbtn.do")
	public String BackBtn()
	{
	          return "returningHome";
	}
	
	@RequestMapping("/backbtn-e.do")
	public String BackBtnE()
	{
	          return "electioncommissionHome";
	}
	@RequestMapping("/presidingHome")
	public String showPresidingHome(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		
		return "presidingHome";
	}
	
	
	@RequestMapping("/search_national")
	public String searchNat(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		candidates cand1=new candidates();
		model.put("candidate", cand1);
		List<String> messages = Arrays.asList("Select Search By","Candidate Name", "Party Name", "Constituency");
		model.put("listToSearch", messages);
		return "search_national";
	}
	
/*	@RequestMapping(value="/search_national.do", method=RequestMethod.POST)
	public String SearchCandidate(@ModelAttribute candidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
		
		candidates cand1=new candidates();
		map.put("candidate", cand1);
		
		String search="";
		switch(action.toLowerCase()){
		case "search by candidate name":
			search="search_NatCandName";
			break;
		case "search by party name":
			search="search_NatParty";
			break;
		case "search by constituency":
			search="search_NatConst";
			break;
		}
		return search;
	}
	
	@RequestMapping("/search_NatCandName")
	public String searchNatCandName(ModelMap model)
	{
		candidates cand1=new candidates();
		model.put("candidate", cand1);
		List<String> messages = Arrays.asList("Candidate Name", "Party Name", "Constituency");
		model.put("listToSearch", messages);
		return "search_NatCandName";
	}
	
	
	@RequestMapping(value="/search_NatCandName.do", method=RequestMethod.POST)
	public String searchNationalCandName(@ModelAttribute candidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
	
		candidates cand1=new candidates();
		map.put("candidate", cand1);
		map.put("NatCandName", voterservice.getNatCandName(candidate));
		return "search_NatCandName";
	}
	
	@RequestMapping("/search_NatParty")
	public String searchPartyName(ModelMap model)
	{
		candidates cand1=new candidates();
		model.put("candidate", cand1);
		//List<String> messages = Arrays.asList("Candidate Name", "Party Name", "Constituency");
		//model.put("listToSearch", messages);
		return "search_NatParty";
	}
	
	@RequestMapping(value="/search_NatParty.do", method=RequestMethod.POST)
	public String searchPartyName(@ModelAttribute candidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
	
		candidates cand1=new candidates();
		map.put("candidate", cand1);
		map.put("NatCandParty", voterservice.getNatParty(candidate));
		return "search_NatParty";
	}*/
	
	@RequestMapping(value="/search_national.do", method=RequestMethod.POST)
	public String SearchCandidate1(@ModelAttribute candidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
	
		candidates cand1=new candidates();
		map.put("candidate", cand1);
		List<String> messages = Arrays.asList("Select Search By","Candidate Name", "Party Name", "Constituency");
		map.put("listToSearch", messages);
		String searchfor=candidate.getCandidatename();
		System.out.println(searchfor);
		//System.out.println(candidate.getPartyname());
		if(searchfor.equals("Candidate Name"))
		{
			
			map.put("NatSearch", voterservice.getNatCandName(candidate));
			//return "search_national";
		}
		else if(searchfor.equals("Party Name"))
		{
			map.put("NatSearch", voterservice.getNatParty(candidate));
			//return "search_national";
			
		}
		
		else if(searchfor.equals("Constituency"))
		{
			map.put("NatSearch", voterservice.getNatConstituency(candidate));
			//return "search_national";
			
		}
		else if(searchfor.equals("Select Search By"))
		{
			map.put("select","Please select one Search By option.");
		}
		
		else
		{
			return "search_national";
		}
		return "search_national";
	}
	
	@RequestMapping("/search_provisional")
	public String searchPro(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		provisionalcandidates cand1=new provisionalcandidates();
		model.put("candidate", cand1);
		List<String> messages = Arrays.asList("Select Search By","Candidate Name", "Party Name", "Constituency");
		model.put("listToSearch", messages);
		return "search_provisional";
	}
	
	@RequestMapping(value="/search_provisional.do", method=RequestMethod.POST)
	public String SearchCandidate2(@ModelAttribute provisionalcandidates candidate, BindingResult result, @RequestParam String action, Map<String, Object> map){
	
		provisionalcandidates cand1=new provisionalcandidates();
		map.put("candidate", cand1);
		List<String> messages = Arrays.asList("Select Search By","Candidate Name", "Party Name", "Constituency");
		map.put("listToSearch", messages);
		String searchfor=candidate.getCandidatename();
		System.out.println(searchfor);
		//System.out.println(candidate.getPartyname());
		if(searchfor.equals("Candidate Name"))
		{
			
			map.put("ProSearch", voterservice.getProCandName(candidate));
			//return "search_national";
		}
		else if(searchfor.equals("Party Name"))
		{
			map.put("ProSearch", voterservice.getProParty(candidate));
			//return "search_national";
			
		}
		
		else if(searchfor.equals("Constituency"))
		{
			map.put("ProSearch", voterservice.getProConstituency(candidate));
			//return "search_national";
			
		}
		else if(searchfor.equals("Select Search By"))
		{
			map.put("select","Please select one Search By option.");
		}
		
		else
		{
			return "search_provisional";
		}
		return "search_provisional";
	}
	
	@RequestMapping("/delete_national")
	public String deleteNat(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		Candi cand1=new Candi();
		model.put("candidate", cand1);
		model.put("NatcandidatesList", voterservice.getAllNatCandidates());
	
		return "delete_national";
	}
	
	@RequestMapping(value="/delete_national.do", method=RequestMethod.POST)
	public String DeleteNatCand(@ModelAttribute Candi candidate, BindingResult result, @RequestParam String action,Map<String, Object> map){
		
		//long[] list1=candidate.getCcnic1();
		//System.out.println(list);
		//System.out.println(candidate.getCcnic());
		long[] li=candidate.getCcnic();
		for(int i=0; i<li.length; i++)
		{
			System.out.println(li[i]);
			voterservice.deleteNatCand(li[i]);
		}
		
		
		
		Candi cand1=new Candi();
		map.put("candidate", cand1);
		map.put("NatcandidatesList", voterservice.getAllNatCandidates());
		map.put("done", "Deleted!");
		
		return "delete_national";
	
	}
	
	@RequestMapping("/delete_provisional")
	public String deletePro(HttpSession session,ModelMap model)
	{
		String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		Candi cand1=new Candi();
		model.put("candidate", cand1);
		model.put("ProcandidatesList", voterservice.getAllProCandidates());
	
		return "delete_provisional";
	}
	
	@RequestMapping(value="/delete_provisional.do", method=RequestMethod.POST)
	public String DeleteProCand(@ModelAttribute Candi candidate, BindingResult result, @RequestParam String action,Map<String, Object> map){
		
		//long[] list1=candidate.getCcnic1();
		//System.out.println(list);
		//System.out.println(candidate.getCcnic());
		long[] li=candidate.getCcnic();
		for(int i=0; i<li.length; i++)
		{
			System.out.println(li[i]);
			voterservice.deleteProCand(li[i]);
		}
		
		
		
		Candi cand1=new Candi();
		map.put("candidate", cand1);
		map.put("ProcandidatesList", voterservice.getAllProCandidates());
		map.put("done", "Deleted!");
		
		return "delete_provisional";
	
	}
	
	@RequestMapping("/add_voter")
	public String add_voter(HttpSession session,ModelMap model)
	{
		 String ad=(String)session.getAttribute("adminusername");
		 String pg=(String)session.getAttribute("homePage");
		 if(ad==null)
		 {
			 model.put("adminusername", "no");
			 model.put("homePage","no");
		 }
		 
		 else
		 {
			 model.put("adminusername", ad);
			 model.put("homePage",pg);
		 }
		 
		voter vt=new voter();
		model.addAttribute("voter", vt);
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		model.addAttribute("constList1",candid);
		List<pollingstation> pi=voterservice.getPollingStations();
		 List<String> pollList = new ArrayList<String>();
		for(int i=0; i<pi.size(); i++)
		{
			pollList.add(pi.get(i).getPollingaddress() );
			
		}
		for(int j=0; j<pollList.size(); j++)
		{
			System.out.println(pollList.get(j));
		}
		
		//List<pollingstation> pid=voterservice.getPollingStations();
		/* int [] pollid=new int[pollList.size()];
		for(int i=0; i<pi.size(); i++)
		{
			pollid[i]=pi.get(i).getPid();
			System.out.println("Polling id: "+pollid[i]);
			
		}*/
		
		
		model.addAttribute("polling",pollList);
	//	model.addAttribute("pollid",pollid);
		return "add_voter";
	}
	
	@RequestMapping(value="/add_voter.do", method=RequestMethod.POST)
	public String AddVoter(@ModelAttribute voter voter, BindingResult result, @RequestParam("pic") MultipartFile pic, @RequestParam("action") String action, Map<String, Object> map){
	 
		voter vt=new voter();
		map.put("voter",vt);
		try {
			Blob blob = Hibernate.createBlob(pic.getInputStream());
			byte[] by=pic.getBytes();
			System.out.println("Image blob: "+blob);
			System.out.println("Image byte: "+by);
			System.out.println("pic: "+pic.getInputStream());
			System.out.println("multipartpic: "+pic);
			voter.setPic(by);
			byte[] flagByte=voterservice.addFlag(voter.getCnic());
			voter.setFlag(flagByte);
			int pid=voterservice.getPidFromPollingname(voter.getPolling());
			voter.setPid(pid);
			System.out.println("cnic: "+ voter.getCnic());
			System.out.println("firstname: "+ voter.getFirstname());
			System.out.println("lastname: "+ voter.getLastname());
			System.out.println("gender: "+ voter.getGender());
			System.out.println("city: "+ voter.getCity());
			System.out.println("address: "+ voter.getAddress());
			System.out.println("voteno: "+ voter.getVoteno());
			System.out.println("cid: "+ voter.getCid());
			//System.out.println("polling: "+ voter.getPolling());
		
			map.put("voterdata",voter);
			
			
			/*if(cand.getCcnic()!=0 && cand.getCandidatename()!="" && cand.getPartyname()!="" && cand.getSymbol()!="" && cand.getCid()!="" && cand.getPic()!=null && pic.getInputStream()!=null )
			{
				voterservice.AddNationalCand(cand);
				voterservice.AddNatToVotes(cand.getCcnic());
				map.put("added", "National Candidate Added!");
			}
			
			else
			{
				map.put("added", "Please Fill out all the empty fields.");
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Image: "+cand.getPic());
		
		
	//	System.out.println("Image: "+cand.getCandidatename());
		//voterservice.AddNationalCand(cand);
		
	//	voterservice.AddNatToVotes(cand.getCcnic());
		map.put("vt",voter.getGender());
		candidates cando=new candidates();
		map.put("candidate", cando);
		
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		map.put("constList1",candid);
		
		List<pollingstation> pi=voterservice.getPollingStations();
		 List<String> pollList = new ArrayList<String>();
		for(int i=0; i<pi.size(); i++)
		{
			pollList.add(pi.get(i).getPollingaddress() );
			
		}
		for(int j=0; j<pollList.size(); j++)
		{
			System.out.println(pollList.get(j));
		}
		map.put("polling",pollList);
		return "registerFinger";
		
	
	}
	
	@RequestMapping(value="/register.do", method=RequestMethod.POST)
	public String RegisterFinger( HttpSession session,@ModelAttribute voter voter,BindingResult result,@RequestParam String action,Map<String, Object> map){
	

		voter vot=new voter();
		map.put("voter", vot);
		List<constituency> li=voterservice.getConstituencies();
		 List<String> candid = new ArrayList<String>();
		for(int i=0; i<li.size(); i++)
		{
			candid.add(li.get(i).getCid());
			
		}
		for(int j=0; j<candid.size(); j++)
		{
			System.out.println(candid.get(j));
		}
		map.put("constList1",candid);
		
		List<pollingstation> pi=voterservice.getPollingStations();
		 List<String> pollList = new ArrayList<String>();
		for(int i=0; i<pi.size(); i++)
		{
			pollList.add(pi.get(i).getPollingaddress() );
			
		}
		for(int j=0; j<pollList.size(); j++)
		{
			System.out.println(pollList.get(j));
		}
		map.put("polling",pollList);
		long err;
        byte kbBuffer[] = new byte[100];
        byte kbWhichFinger[] = new byte[100];
        int fingerLength = 0;
        String finger = new String("Finger");
        byte[] imageBuffer1;
        byte[] imageBuffer2;
        byte[] SG400minutiaeBuffer1;
        byte[] SG400minutiaeBuffer2;
        FileOutputStream fout = null;
        PrintStream fp = null;

        //Initialize fingerprint prompt buffer
        for (int i=0; i < kbWhichFinger.length; ++i)
           kbWhichFinger[i] = 0x00;

        System.out.println("");
        System.out.println("###############################");
        System.out.println("SecuGen FDx SDK Pro for Java");
        System.out.println("JSGFPLib JNI Library Test Program");
        System.out.println("###############################");
        System.out.println("");
        
        
        ///////////////////////////////////////////////
        // Instantiate JSGFPLib object
        System.out.println("Instantiate JSGFPLib Object");
       // JSGFPLib sgfplib = new JSGFPLib();
        if ((sgfplib != null) && (sgfplib.jniLoadStatus != SGFDxErrorCode.SGFDX_ERROR_JNI_DLLLOAD_FAILED))
        {
            System.out.println(sgfplib);
        }
        else
        {
            System.out.println("An error occurred while loading JSGFPLIB.DLL JNI Wrapper");
            //return;
        }

        ///////////////////////////////////////////////
        // Init()
        System.out.println("Call Init(SGFDxDeviceName.SG_DEV_AUTO)");
        err = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
        System.out.println("Init returned : [" + err + "]");

        ///////////////////////////////////////////////
        // GetLastError()
        System.out.println("Call GetLastError()");
        err = sgfplib.GetLastError();
        System.out.println("Last error returned : [" + err + "]");

        ///////////////////////////////////////////////
        // GetMinexVersion()
        int[] extractorVersion = new int[1];
        int[] matcherVersion = new int[1];
        System.out.println("Call GetMinexVersion()");
        err = sgfplib.GetMinexVersion(extractorVersion, matcherVersion);
        System.out.println("GetMinexVersion returned : [" + err + "]");
        System.out.println("Extractor version : [" + extractorVersion[0] + "]");
        System.out.println("Matcher version : [" + matcherVersion[0] + "]");
        
        ///////////////////////////////////////////////
        // OpenDevice()
        System.out.println("Call OpenDevice(SGPPPortAddr.AUTO_DETECT)");
        err = sgfplib.OpenDevice(SGPPPortAddr.AUTO_DETECT);
        System.out.println("OpenDevice returned : [" + err + "]");

        ///////////////////////////////////////////////
        // GetError()
        System.out.println("Call GetLastError()");
        err = sgfplib.GetLastError();
        System.out.println("GetLastError returned : [" + err + "]");

        ///////////////////////////////////////////////
        // GetDeviceInfo()
        System.out.println("Call GetDeviceInfo()");
        SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
        err = sgfplib.GetDeviceInfo(deviceInfo);
        System.out.println( "GetDeviceInfo returned : [" + err + "]");
        System.out.println("\tdeviceInfo.DeviceSN:    [" + new String(deviceInfo.deviceSN()) + "]");
        System.out.println("\tdeviceInfo.Brightness:  [" + deviceInfo.brightness + "]");
        System.out.println("\tdeviceInfo.ComPort:     [" + deviceInfo.comPort + "]");
        System.out.println("\tdeviceInfo.ComSpeed:    [" + deviceInfo.comSpeed + "]");
        System.out.println("\tdeviceInfo.Contrast:    [" + deviceInfo.contrast + "]");
        System.out.println("\tdeviceInfo.DeviceID:    [" + deviceInfo.deviceID + "]");
        System.out.println("\tdeviceInfo.FWVersion:   [" + deviceInfo.FWVersion + "]");
        System.out.println("\tdeviceInfo.Gain:        [" + deviceInfo.gain + "]");
        System.out.println("\tdeviceInfo.ImageDPI:    [" + deviceInfo.imageDPI + "]");
        System.out.println("\tdeviceInfo.ImageHeight: [" + deviceInfo.imageHeight + "]");
        System.out.println("\tdeviceInfo.ImageWidth:  [" + deviceInfo.imageWidth + "]");
        
        
        int[] quality = new int[1];
        int[] maxSize = new int[1];
        int[] size = new int[1];
        SGFingerInfo fingerInfo = new SGFingerInfo();
        fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
        fingerInfo.ImageQuality = quality[0];
        fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
        fingerInfo.ViewNumber = 1;



//////////////////////////////////////////////////////////////////////////////
// Finger 1
        ///////////////////////////////////////////////
        // getImage() - 1st Capture
        System.out.println("Call SetLedOn(true)");
        err =sgfplib.SetLedOn(true);
        System.out.println("SetLedOn returned : [" + err + "]");
        System.out.print("Capture 1. Please place [" + finger + "] on sensor with LEDs on and press <ENTER> ");
        imageBuffer1 = new byte[deviceInfo.imageHeight*deviceInfo.imageWidth];
        // System.in.read(kbBuffer);
            System.out.println("Call GetImage()");
            err = sgfplib.GetImage(imageBuffer1);
            System.out.println("GetImage returned : [" + err + "]");
            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
            {
                err = sgfplib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight, imageBuffer1, quality);
                System.out.println("GetImageQuality returned : [" + err + "]");
                System.out.println("Image Quality is : [" + quality[0] + "]");
               // fout = new FileOutputStream(finger + "1.raw");
                //fp = new PrintStream(fout);
                //fp.write(imageBuffer1,0, imageBuffer1.length);
                //fp.close();
                //fout.close();
                //fp = null;
                //fout = null;
            }
            else
            {
                System.out.println("ERROR: Fingerprint image capture failed for sample1.");
               // return; //Cannot continue test if image not captured
            }

        ///////////////////////////////////////////////
        // Set Template format SG400
        System.out.println("Call SetTemplateFormat(SG400)");
        err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
        System.out.println("SetTemplateFormat returned : [" + err + "]");

        ///////////////////////////////////////////////
        // Get Max Template Size for SG400
        System.out.println("Call GetMaxTemplateSize()");
        err = sgfplib.GetMaxTemplateSize(maxSize);
        System.out.println("GetMaxTemplateSize returned : [" + err + "]");
        System.out.println("Max SG400 Template Size is : [" + maxSize[0] + "]");

        ///////////////////////////////////////////////
        // Greate SG400 Template for Finger 1
        SG400minutiaeBuffer1 = new byte[maxSize[0]];
        System.out.println("Call CreateTemplate()");
        err = sgfplib.CreateTemplate(fingerInfo, imageBuffer1, SG400minutiaeBuffer1);
        System.out.println("CreateTemplate returned : [" + err + "]");
        err = sgfplib.GetTemplateSize(SG400minutiaeBuffer1, size);
        System.out.println("GetTemplateSize returned : [" + err + "]");
        System.out.println("SG400 Template Size is : [" + size[0] + "]");
       /* try
        {
            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
            {
                fout = new FileOutputStream(finger +"1.sg400");
                fp = new PrintStream(fout);
                fp.write(SG400minutiaeBuffer1,0, size[0]);
                fp.close();
                fout.close();
                fp = null;
                fout = null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Exception writing minutiae file : " + e);
        }*/

       
//////////////////////////////////////////////////////////////////////////////
// Finger 2
        ///////////////////////////////////////////////
        // getImage() - 2nd Capture
        System.out.println("Call SetLedOn(true)");
        err =sgfplib.SetLedOn(true);
        System.out.println("SetLedOn returned : [" + err + "]");
        System.out.print("Capture 2. Please place [" + finger + "] on sensor with LEDs on and press <ENTER> ");
        imageBuffer2 = new byte[deviceInfo.imageHeight*deviceInfo.imageWidth];
        // System.in.read(kbBuffer);
            System.out.println("Call GetImage()");
            err = sgfplib.GetImage(imageBuffer2);
            System.out.println("GetImage returned : [" + err + "]");
            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
            {
                err = sgfplib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight, imageBuffer2, quality);
                System.out.println("GetImageQuality returned : [" + err + "]");
                System.out.println("Image Quality is : [" + quality[0] + "]");
                //fout = new FileOutputStream(finger + "2.raw");
                //fp = new PrintStream(fout);
                //fp.write(imageBuffer2,0, imageBuffer2.length);
                //fp.close();
                //fout.close();
                //fp = null;
                //fout = null;
            }
            else
            {
                System.out.println("ERROR: Fingerprint image capture failed for sample2.");
               // return; //Cannot continue test if image not captured
            }

        ///////////////////////////////////////////////
        // Set Template format SG400
        System.out.println("--------");
        System.out.println("Call SetTemplateFormat(SG400)");
        err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
        System.out.println("SetTemplateFormat returned : [" + err + "]");

        ///////////////////////////////////////////////
        // Get Max Template Size for SG400
        System.out.println("Call GetMaxTemplateSize()");
        err = sgfplib.GetMaxTemplateSize(maxSize);
        System.out.println("GetMaxTemplateSize returned : [" + err + "]");
        System.out.println("Max SG400 Template Size is : [" + maxSize[0] + "]");

        ///////////////////////////////////////////////
        // Greate SG400 Template for Finger 2
        SG400minutiaeBuffer2 = new byte[maxSize[0]];
        System.out.println("Call CreateTemplate()");
        err = sgfplib.CreateTemplate(fingerInfo, imageBuffer2, SG400minutiaeBuffer2);
        System.out.println("CreateTemplate returned : [" + err + "]");
        err = sgfplib.GetTemplateSize(SG400minutiaeBuffer2, size);
        System.out.println("GetTemplateSize returned : [" + err + "]");
        System.out.println("SG400 Template Size is : [" + size[0] + "]");
       /* try
        {
            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
            {
                fout = new FileOutputStream(finger +"2.sg400");
                fp = new PrintStream(fout);
                fp.write(SG400minutiaeBuffer2,0, size[0]);
                fp.close();
                fout.close();
                fp = null;
                fout = null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Exception writing minutiae file : " + e);
        }*/

        boolean[] matched = new boolean[1];
        int[] score = new int[1];
        ///////////////////////////////////
        //Match SG400 Templates
        System.out.println("--------");
        matched[0] = false;
        score[0] = 0;
        System.out.println("Call SetTemplateFormat(SG400)");
        err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
        System.out.println("SetTemplateFormat returned : [" + err + "]");
        System.out.println("Call MatchTemplate()");
        err = sgfplib.MatchTemplate(SG400minutiaeBuffer1, SG400minutiaeBuffer2, SGFDxSecurityLevel.SL_NORMAL, matched);
        System.out.println("MatchTemplate returned : [" + err + "]");
        System.out.println("SG400-1 <> SG400-2 Match Result : [" + matched[0] + "]");
        System.out.println("Call GetMatchingScore()");
        err = sgfplib.GetMatchingScore(SG400minutiaeBuffer1, SG400minutiaeBuffer2, score);
        System.out.println("GetMatchingScore returned : [" + err + "]");
        System.out.println("SG400-1  <> SG400-2 Match Score : [" + score[0] + "]");
        String returnString="";
	   if(score[0]>=80)
	   {
		   ///////////////////////////////////////////////
	        // CloseDevice()
	       // System.out.println("Call CloseDevice()");
	        err = sgfplib.CloseDevice();
	        //System.out.println("CloseDevice returned : [" + err + "]");


	        ///////////////////////////////////////////////
	        // Close JSGFPLib native library
	        //System.out.println("Call Close()");
	        sgfplib.Close();
	       // System.out.println("Close returned : [" + err + "]");
		   voter vt=(voter)session.getAttribute("voterdata");
		   System.out.println("SG4001 :"+SG400minutiaeBuffer1);
		   System.out.println("SG4002 :"+SG400minutiaeBuffer2);
		   vt.setFg1(SG400minutiaeBuffer1);
		   vt.setFg2(SG400minutiaeBuffer2);
		   voterservice.addVoter(vt);
		  returnString= "add_voter";
		   
	   }
	   
	   else
	   {
		   System.out.println("can't save to database");
		   returnString= "registerFinger";
		   
	   }

       
	   
      

        sgfplib = null;
        imageBuffer1 = null;
        imageBuffer2 = null;
        SG400minutiaeBuffer1 = null;
        SG400minutiaeBuffer2 = null;


		
		
		
	return returnString;
	}
	
	@RequestMapping(value="/verify_voter.do", method=RequestMethod.POST)
	public String VerifyFinger( HttpSession session,@ModelAttribute voter voter,BindingResult result,@RequestParam String action,Map<String, Object> map){
	
		voter v= new voter();
		map.put("voter", v);
		String result1="";
		 long vtcnic=(long)session.getAttribute("votercnic");
		 
		 byte[] fg1=voterservice.getVoterFg1(vtcnic);
		 byte[] fg2=voterservice.getVoterFg2(vtcnic);
		 System.out.println("voter cnic " + vtcnic);
		 System.out.println("FG1 " + fg1);
		 System.out.println("FG2 " + fg2);
		 
		 long err;
	        byte kbBuffer[] = new byte[100];
	        byte kbWhichFinger[] = new byte[100];
	        int fingerLength = 0;
	        String finger = new String("Finger");
	        byte[] imageBuffer1;
	        byte[] imageBuffer2;
	        byte[] SG400minutiaeBuffer1;
	        byte[] SG400minutiaeBuffer2;
	        FileOutputStream fout = null;
	        PrintStream fp = null;

	        //Initialize fingerprint prompt buffer
	        for (int i=0; i < kbWhichFinger.length; ++i)
	           kbWhichFinger[i] = 0x00;

	        System.out.println("");
	        System.out.println("###############################");
	        System.out.println("SecuGen FDx SDK Pro for Java");
	        System.out.println("JSGFPLib JNI Library Test Program");
	        System.out.println("###############################");
	        System.out.println("");
	        
	        
	        ///////////////////////////////////////////////
	        // Instantiate JSGFPLib object
	        System.out.println("Instantiate JSGFPLib Object");
	        JSGFPLib sgfplib = new JSGFPLib();
	        if ((sgfplib != null) && (sgfplib.jniLoadStatus != SGFDxErrorCode.SGFDX_ERROR_JNI_DLLLOAD_FAILED))
	        {
	            System.out.println(sgfplib);
	        }
	        else
	        {
	            System.out.println("An error occurred while loading JSGFPLIB.DLL JNI Wrapper");
	            //return;
	        }

	        ///////////////////////////////////////////////
	        // Init()
	        System.out.println("Call Init(SGFDxDeviceName.SG_DEV_AUTO)");
	        err = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
	        System.out.println("Init returned : [" + err + "]");

	        ///////////////////////////////////////////////
	        // GetLastError()
	        System.out.println("Call GetLastError()");
	        err = sgfplib.GetLastError();
	        System.out.println("Last error returned : [" + err + "]");

	        ///////////////////////////////////////////////
	        // GetMinexVersion()
	        int[] extractorVersion = new int[1];
	        int[] matcherVersion = new int[1];
	        System.out.println("Call GetMinexVersion()");
	        err = sgfplib.GetMinexVersion(extractorVersion, matcherVersion);
	        System.out.println("GetMinexVersion returned : [" + err + "]");
	        System.out.println("Extractor version : [" + extractorVersion[0] + "]");
	        System.out.println("Matcher version : [" + matcherVersion[0] + "]");
	        
	        ///////////////////////////////////////////////
	        // OpenDevice()
	        System.out.println("Call OpenDevice(SGPPPortAddr.AUTO_DETECT)");
	        err = sgfplib.OpenDevice(SGPPPortAddr.AUTO_DETECT);
	        System.out.println("OpenDevice returned : [" + err + "]");

	        ///////////////////////////////////////////////
	        // GetError()
	        System.out.println("Call GetLastError()");
	        err = sgfplib.GetLastError();
	        System.out.println("GetLastError returned : [" + err + "]");

	        ///////////////////////////////////////////////
	        // GetDeviceInfo()
	        System.out.println("Call GetDeviceInfo()");
	        SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
	        err = sgfplib.GetDeviceInfo(deviceInfo);
	        System.out.println( "GetDeviceInfo returned : [" + err + "]");
	        System.out.println("\tdeviceInfo.DeviceSN:    [" + new String(deviceInfo.deviceSN()) + "]");
	        System.out.println("\tdeviceInfo.Brightness:  [" + deviceInfo.brightness + "]");
	        System.out.println("\tdeviceInfo.ComPort:     [" + deviceInfo.comPort + "]");
	        System.out.println("\tdeviceInfo.ComSpeed:    [" + deviceInfo.comSpeed + "]");
	        System.out.println("\tdeviceInfo.Contrast:    [" + deviceInfo.contrast + "]");
	        System.out.println("\tdeviceInfo.DeviceID:    [" + deviceInfo.deviceID + "]");
	        System.out.println("\tdeviceInfo.FWVersion:   [" + deviceInfo.FWVersion + "]");
	        System.out.println("\tdeviceInfo.Gain:        [" + deviceInfo.gain + "]");
	        System.out.println("\tdeviceInfo.ImageDPI:    [" + deviceInfo.imageDPI + "]");
	        System.out.println("\tdeviceInfo.ImageHeight: [" + deviceInfo.imageHeight + "]");
	        System.out.println("\tdeviceInfo.ImageWidth:  [" + deviceInfo.imageWidth + "]");
	        
	        
	        int[] quality = new int[1];
	        int[] maxSize = new int[1];
	        int[] size = new int[1];
	        SGFingerInfo fingerInfo = new SGFingerInfo();
	        fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
	        fingerInfo.ImageQuality = quality[0];
	        fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
	        fingerInfo.ViewNumber = 1;



	//////////////////////////////////////////////////////////////////////////////
	// Finger 1
	        ///////////////////////////////////////////////
	        // getImage() - 1st Capture
	        System.out.println("Call SetLedOn(true)");
	        err =sgfplib.SetLedOn(true);
	        System.out.println("SetLedOn returned : [" + err + "]");
	        System.out.print("Capture 1. Please place [" + finger + "] on sensor with LEDs on and press <ENTER> ");
	        imageBuffer1 = new byte[deviceInfo.imageHeight*deviceInfo.imageWidth];
	        // System.in.read(kbBuffer);
	            System.out.println("Call GetImage()");
	            err = sgfplib.GetImage(imageBuffer1);
	            System.out.println("GetImage returned : [" + err + "]");
	            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
	            {
	                err = sgfplib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight, imageBuffer1, quality);
	                System.out.println("GetImageQuality returned : [" + err + "]");
	                System.out.println("Image Quality is : [" + quality[0] + "]");
	               // fout = new FileOutputStream(finger + "1.raw");
	                //fp = new PrintStream(fout);
	                //fp.write(imageBuffer1,0, imageBuffer1.length);
	                //fp.close();
	                //fout.close();
	                //fp = null;
	                //fout = null;
	            }
	            else
	            {
	                System.out.println("ERROR: Fingerprint image capture failed for sample1.");
	               // return; //Cannot continue test if image not captured
	            }

	        ///////////////////////////////////////////////
	        // Set Template format SG400
	        System.out.println("Call SetTemplateFormat(SG400)");
	        err = sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
	        System.out.println("SetTemplateFormat returned : [" + err + "]");

	        ///////////////////////////////////////////////
	        // Get Max Template Size for SG400
	        System.out.println("Call GetMaxTemplateSize()");
	        err = sgfplib.GetMaxTemplateSize(maxSize);
	        System.out.println("GetMaxTemplateSize returned : [" + err + "]");
	        System.out.println("Max SG400 Template Size is : [" + maxSize[0] + "]");

	        ///////////////////////////////////////////////
	        // Greate SG400 Template for Finger 1
	        SG400minutiaeBuffer1 = new byte[maxSize[0]];
	        System.out.println("Call CreateTemplate()");
	        err = sgfplib.CreateTemplate(fingerInfo, imageBuffer1, SG400minutiaeBuffer1);
	        System.out.println("CreateTemplate returned : [" + err + "]");
	        err = sgfplib.GetTemplateSize(SG400minutiaeBuffer1, size);
	        System.out.println("GetTemplateSize returned : [" + err + "]");
	        System.out.println("SG400 Template Size is : [" + size[0] + "]");
	       /* try
	        {
	            if (err == SGFDxErrorCode.SGFDX_ERROR_NONE)
	            {
	                fout = new FileOutputStream(finger +"1.sg400");
	                fp = new PrintStream(fout);
	                fp.write(SG400minutiaeBuffer1,0, size[0]);
	                fp.close();
	                fout.close();
	                fp = null;
	                fout = null;
	            }
	        }
	        catch (IOException e)
	        {
	            System.out.println("Exception writing minutiae file : " + e);
	        }*/
	        boolean[] matched = new boolean[1];
	        boolean[] matched1 = new boolean[1];
	        matched[0] = false;
	        matched1[0] = false;
	        System.out.println("Call MatchTemplate()");
	        err = sgfplib.MatchTemplate(fg1, SG400minutiaeBuffer1, SGFDxSecurityLevel.SL_NORMAL, matched);
	        System.out.println("MatchTemplate returned : [" + err + "]");
	        System.out.println("SG400-1 <> SG400-2 Match Result : [" + matched[0] + "]");
	        err = sgfplib.MatchTemplate(fg2, SG400minutiaeBuffer1, SGFDxSecurityLevel.SL_NORMAL, matched1);
		
	        if(matched[0]==true && matched1[0]==true)
	        {	result1="proceed";
	        	///////////////////////////////////////////////
				// CloseDevice()
				// System.out.println("Call CloseDevice()");
				err = sgfplib.CloseDevice();
				//System.out.println("CloseDevice returned : [" + err + "]");
				
				
				///////////////////////////////////////////////
				// Close JSGFPLib native library
				//System.out.println("Call Close()");
				sgfplib.Close();
				// System.out.println("Close returned : [" + err + "]");
				
	        }
	        
	        else
	        	{
	        	result1="verifyFinger";
	        	 map.put("failed","Failed to verify!");
	        	}
	       
		return result1;
	}
	}


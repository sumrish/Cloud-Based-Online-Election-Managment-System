package com.fyp.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fyp.model.candidates;
import com.fyp.model.provisionalcandidates;
import com.fyp.service.voterService;

@Controller
@RequestMapping("/myImage")
public class ImageController {

	@Autowired
	private voterService voterservice;
	
	@RequestMapping(value = "/NationalImageDisplay", method = RequestMethod.GET)
	  public void showImage(@RequestParam("ccnic") long ccnic, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{

		candidates candi=voterservice.getNatSingleCandidateFromCnic(ccnic);
	         
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(candi.getPic());


	    response.getOutputStream().close();
	}
	
	
	@RequestMapping(value = "/NationalSymbolDisplay", method = RequestMethod.GET)
	  public void showNatSymbol(@RequestParam("ccnic") long ccnic, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{

		candidates candi=voterservice.getNatSingleCandidateFromCnic(ccnic);
	         
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(candi.getSymbol());


	    response.getOutputStream().close();
	}
	
	
	
	@RequestMapping(value = "/ProvisionalImageDisplay", method = RequestMethod.GET)
	  public void showImage1(@RequestParam("ccnic") long ccnic, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{

		provisionalcandidates candi=voterservice.getProSingleCandidateFromCnic(ccnic);
	         
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(candi.getPic());


	    response.getOutputStream().close();
	}
	
	@RequestMapping(value = "/ProvisionalSymbolDisplay", method = RequestMethod.GET)
	  public void showProSymbol(@RequestParam("ccnic") long ccnic, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{

		provisionalcandidates candi=voterservice.getProSingleCandidateFromCnic(ccnic);
	         
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(candi.getSymbol());


	    response.getOutputStream().close();
	}
}

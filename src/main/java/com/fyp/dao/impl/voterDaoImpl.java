package com.fyp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fyp.dao.voterDao;
import com.fyp.model.Votes;
import com.fyp.model.admin;
import com.fyp.model.candidates;
import com.fyp.model.pollingstation;
import com.fyp.model.provisionalcandidates;
import com.fyp.model.provisionalvotes;
import com.fyp.model.voter;

@Repository
public class voterDaoImpl implements voterDao {

	@Autowired
	private SessionFactory session;
	@Override
	public voter getVoter(long cnic) {
	
		return (voter)session.getCurrentSession().get(voter.class,cnic);
	}
	
	@Override
	public List getCandidates(String cid)
	{
		//session.getCurrentSession().createQuery("from com.fyp.model.candidates where cid= :"+cid).list();
		//return session.getCurrentSession().createQuery("from candidates where ccid= :"+cid).list();
		
		Query query=session.getCurrentSession().createQuery("from candidates where cid=:cid");
		query.setParameter("cid", cid);
		List list=query.list();
		return list;
	}
	
	@Override
	public List getProCandidates(String cid)
	{
		//session.getCurrentSession().createQuery("from com.fyp.model.candidates where cid= :"+cid).list();
		//return session.getCurrentSession().createQuery("from candidates where ccid= :"+cid).list();
		
		Query query=session.getCurrentSession().createQuery("from provisionalcandidates where cid=:cid");
		query.setParameter("cid", cid);
		List list=query.list();
		return list;
	}
	

	@Override
	public Votes getCand(long ccnic)
	{
		Query query=session.getCurrentSession().createQuery("from Votes where ccnic=:ccnic");
		query.setParameter("ccnic", ccnic);
		return (Votes)query.uniqueResult();
	}
	
	@Override
	public provisionalvotes getProCand(long ccnic)
	{
		Query query=session.getCurrentSession().createQuery("from provisionalvotes where ccnic=:ccnic");
		query.setParameter("ccnic", ccnic);
		return (provisionalvotes)query.uniqueResult();
	}
	
	@Override
	public void edit(Votes votes)
	{
		session.getCurrentSession().update(votes);
	}
	
	@Override
	public void editPro(provisionalvotes votes)
	{
		session.getCurrentSession().update(votes);
	}

	@Override
	public void updateFlag(voter voter)
	{
		session.getCurrentSession().update(voter);
	}
	
	@Override
	public admin check(String username , String password)
	{
		Query query = session.getCurrentSession().createQuery("from admin where username=:username and password=:password");
		query.setParameter("username",username);
		query.setParameter("password",password);
		return (admin)query.uniqueResult();
	}
	
	/*@Override
	public admin checkOne(String username )
	{
		Query query = session.getCurrentSession().createQuery("from admin where username=:username");
		query.setParameter("username",username);

		return (admin)query.uniqueResult();
	}*/
	
	
	
	
	@Override
	public void AddNationalCand(candidates cand){
		
		session.getCurrentSession().save(cand);
	}
	
	@Override
	public void AddProvisionalCand(provisionalcandidates cand){
		
		session.getCurrentSession().save(cand);
	}
	
	@Override
	public List getConstituencies()
	{
		return session.getCurrentSession().createQuery("from constituency").list();
	}
	
	@Override
	public List getPollingStations()
	{
		return session.getCurrentSession().createQuery("from pollingstation").list();
	}
	
	@Override
	public void AddNationalCandToVotes(Votes votes)
	{
		session.getCurrentSession().save(votes);
	}
	
	@Override
	public void AddProvisionalCandToVotes(provisionalvotes votes)
	{
		session.getCurrentSession().save(votes);
	}
	
	@Override
	public List getAllNatCandidates()
	{
		return session.getCurrentSession().createQuery("from candidates").list();
	}
	
	@Override
	public List getAllProCandidates()
	{
		return session.getCurrentSession().createQuery("from provisionalcandidates").list();
	}
	
	@Override
	public void updateNatCand(candidates cand)
	{
		session.getCurrentSession().update(cand);
	}
	
	@Override
	public void updateProCand(provisionalcandidates cand)
	{
		session.getCurrentSession().update(cand);
	}
	
	@Override
	public List getNatCandName(String candidatename)
	{
		Query query = session.getCurrentSession().createQuery("from candidates where candidatename LIKE :candidatename");
		query.setParameter("candidatename", "%"+candidatename+"%");
		List list = query.list();
		return list;
	}
	
	@Override
	public List getNatParty(String partyname)
	{
		//System.out.println(partyname);
		Query query = session.getCurrentSession().createQuery("from candidates where partyname LIKE :partyname");
		query.setParameter("partyname", "%"+partyname+"%");
		List list = query.list();
		return list;
	}
	
	@Override
	public List getNatConstituency(String cid)
	{
		Query query = session.getCurrentSession().createQuery("from candidates where cid LIKE :cid");
		query.setParameter("cid", "%"+cid+"%");
		List list = query.list();
		return list;
		
	}

	@Override
	public List getProCandName(String candidatename)
	{
		Query query = session.getCurrentSession().createQuery("from provisionalcandidates where candidatename LIKE :candidatename");
		query.setParameter("candidatename", "%"+candidatename+"%");
		List list = query.list();
		return list;
	}
	
	@Override
	public List getProParty(String partyname)
	{
		//System.out.println(partyname);
		Query query = session.getCurrentSession().createQuery("from provisionalcandidates where partyname LIKE :partyname");
		query.setParameter("partyname", "%"+partyname+"%");
		List list = query.list();
		return list;
	}
	
	@Override
	public List getProConstituency(String cid)
	{
		Query query = session.getCurrentSession().createQuery("from provisionalcandidates where cid LIKE :cid");
		query.setParameter("cid", "%"+cid+"%");
		List list = query.list();
		return list;
		
	}
	
	@Override
	public void deleteNatCand(long ccnic)
	{
		Query query = session.getCurrentSession().createQuery("delete Votes where ccnic = :ccnic");
		query.setParameter("ccnic", ccnic);
		int result = query.executeUpdate();
		
		Query query1 = session.getCurrentSession().createQuery("delete candidates where ccnic = :ccnic");
		query1.setParameter("ccnic", ccnic);
		int result1 = query1.executeUpdate();
	}
	
	@Override
	public void deleteProCand(long ccnic)
	{
		Query query = session.getCurrentSession().createQuery("delete provisionalvotes where ccnic = :ccnic");
		query.setParameter("ccnic", ccnic);
		int result = query.executeUpdate();
		
		Query query1 = session.getCurrentSession().createQuery("delete provisionalcandidates where ccnic = :ccnic");
		query1.setParameter("ccnic", ccnic);
		int result1 = query1.executeUpdate();
	}

	@Override
	public candidates getNatSingleCandidateFromCnic(long ccnic)
	{
		return (candidates)session.getCurrentSession().get(candidates.class,ccnic);
	}
	
	@Override
	public provisionalcandidates getProSingleCandidateFromCnic(long ccnic)
	{
		return (provisionalcandidates)session.getCurrentSession().get(provisionalcandidates.class,ccnic);
	}
	
	@Override
	public pollingstation getPidFromPollingname(String pollingaddress)
	{
		Query query = session.getCurrentSession().createQuery("from pollingstation where pollingaddress=:pollingaddress");
		query.setParameter("pollingaddress",pollingaddress);
		
		return (pollingstation)query.uniqueResult();
	}
	
	@Override
	public void addVoter(voter vt)
	{
		//Query query = session.getCurrentSession().createQuery("insert into voter(cnic, firstname,lastname,gender,city,address,pid,cid,voteno,flag,pic,fg1,fg2)" +
    		//	"select cnic, firstname,lastname,gender,city,address,pid,cid,voteno,flag,pic,fg1,fg2 from vt");
		//query.executeUpdate();
		//session.getCurrentSession().save(vt);
		
		session.getCurrentSession().save(vt);
	}
	
	@Override
	public voter getVoterFg1(long vtcnic)
	{
		return (voter)session.getCurrentSession().get(voter.class,vtcnic); 
	}
	
	@Override
	public voter getVoterFg2(long vtcnic)
	{
		return (voter)session.getCurrentSession().get(voter.class,vtcnic); 
	}
}

package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.ICWCallbackDao;
import com.orasoft.data.link.models.entity.CWCallback;

@Service
public class CWCallbackServiceImpl implements ICWCallbackService{

	@Autowired
	private ICWCallbackDao callbackDao;
	
	@Override
	public List<CWCallback> findAll() {
		// TODO Auto-generated method stub
		return (List<CWCallback>) this.callbackDao.findAll();
	}

	@Override
	public CWCallback save(CWCallback cwCallback) {
		// TODO Auto-generated method stub
		return this.callbackDao.save(cwCallback);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.callbackDao.deleteById(id);
	}

	@Override
	public CWCallback findOne(Long id) {
		// TODO Auto-generated method stub
		return this.callbackDao.findById(id).orElse(null);
	}

}

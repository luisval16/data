package com.orasoft.data.link.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IErrorsDao;
import com.orasoft.data.link.models.entity.Errors;

@Service
public class IErrorsServiceImpl implements IErrorsService {

	@Autowired
	private IErrorsDao errorsDao;
	
	@Override
	public Errors save(Errors error) {
		// TODO Auto-generated method stub
		return this.errorsDao.save(error);
	}

}

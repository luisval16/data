package com.orasoft.data.link.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IInfusionTokenDao;
import com.orasoft.data.link.models.entity.InfusionToken;

@Service
public class InfusionTokenImpl implements IInfusionTokenService{

	@Autowired
	private IInfusionTokenDao infusionDao;
	
	@Override
	public List<InfusionToken> findAll() {
		// TODO Auto-generated method stub
		return (List<InfusionToken>) infusionDao.findAll();
	}

	@Override
	@Transactional
	public InfusionToken save(InfusionToken infusionToken) {
		// TODO Auto-generated method stub
		return infusionDao.save(infusionToken);
	}

	@Override
	public InfusionToken findOne(Long id) {
		// TODO Auto-generated method stub
		return infusionDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		infusionDao.deleteById(id);
	}

	@Override
	public Page<InfusionToken> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return infusionDao.findAll(pageable);
	}
	
}

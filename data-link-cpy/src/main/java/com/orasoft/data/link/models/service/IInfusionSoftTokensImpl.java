package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.orasoft.data.link.models.dao.IInfusionSoftTokensDao;
import com.orasoft.data.link.models.entity.InfusionSoftToken;

public class IInfusionSoftTokensImpl implements IInfusionSoftTokensService {

	@Autowired
	private IInfusionSoftTokensDao infusionTokensDao;
	
	@Override
	public List<InfusionSoftToken> findAll() {
		// TODO Auto-generated method stub
		return (List<InfusionSoftToken>) this.infusionTokensDao.findAll();
	}

	@Override
	public InfusionSoftToken save(InfusionSoftToken infusionSoftToken) {
		// TODO Auto-generated method stub
		return this.infusionTokensDao.save(infusionSoftToken);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
       this.infusionTokensDao.deleteById(id);
	}

	@Override
	public InfusionSoftToken findOne(Long id) {
		// TODO Auto-generated method stub
		return this.infusionTokensDao.findById(id).orElse(null);
	}

}

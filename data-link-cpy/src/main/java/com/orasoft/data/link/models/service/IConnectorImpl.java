package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IConnectorDao;
import com.orasoft.data.link.models.entity.Connector;

@Service
public class IConnectorImpl implements IConnectorService {

	@Autowired
	private IConnectorDao connectorDao;
	
	@Override
	public Page<Connector> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.connectorDao.findAll(pageable);
	}

	@Override
	public Connector save(Connector connector) {
		// TODO Auto-generated method stub
		return this.connectorDao.save(connector);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
        this.connectorDao.deleteById(id);
	}

	@Override
	public Connector findOne(Long id) {
		// TODO Auto-generated method stub
		return this.connectorDao.findById(id).orElse(null);
	}

	@Override
	public Page<Connector> findByUserId(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return this.connectorDao.findByUserId(id, pageable);
	}

	@Override
	public Long countByTypeAndUser(String type, Long id) {
		// TODO Auto-generated method stub
		return this.connectorDao.countByTypeAndUser(type, id);
	}

	@Override
	public List<Connector> findAll() {
		// TODO Auto-generated method stub
		return (List<Connector>) this.connectorDao.findAll();
	}

	@Override
	public List<Connector> findByUserId(Long id) {
		// TODO Auto-generated method stub
		return this.connectorDao.findByUserId(id);
	}

}

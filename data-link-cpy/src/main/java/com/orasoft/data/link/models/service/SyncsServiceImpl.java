package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.ISyncDao;
import com.orasoft.data.link.models.entity.Syncs;

@Service
public class SyncsServiceImpl implements ISyncsService {

	@Autowired
	private ISyncDao syncDao;
	
	@Override
	public List<Syncs> findAll() {
		// TODO Auto-generated method stub
		return (List<Syncs>) this.syncDao.findAll();
	}

	@Override
	public Page<Syncs> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.syncDao.findAll(pageable);
	}

	@Override
	public Syncs save(Syncs sync) {
		// TODO Auto-generated method stub
		return this.syncDao.save(sync);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.syncDao.deleteById(id);
	}

	@Override
	public Syncs findOne(Long id) {
		// TODO Auto-generated method stub
		return this.syncDao.findById(id).orElse(null);
	}

}

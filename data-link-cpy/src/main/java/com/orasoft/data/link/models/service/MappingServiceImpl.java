package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IMappingDao;
import com.orasoft.data.link.models.entity.Mapping;

@Service
public class MappingServiceImpl implements IMappingService {

	@Autowired
	private IMappingDao mappingDao;
	
	@Override
	public List<Mapping> findAll() {
		// TODO Auto-generated method stub
		return (List<Mapping>) this.mappingDao.findAll();
	}

	@Override
	public Page<Mapping> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.mappingDao.findAll(pageable);
	}

	@Override
	public Mapping save(Mapping mapping) {
		// TODO Auto-generated method stub
		return this.mappingDao.save(mapping);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.mappingDao.deleteById(id);
	}

	@Override
	public Mapping findOne(Long id) {
		// TODO Auto-generated method stub
		return this.mappingDao.findById(id).orElse(null);
	}

	@Override
	public Page<Mapping> findByUserId(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return this.mappingDao.findByUserId(id, pageable);
	}

	@Override
	public Long countByTypeAndUserId(String type, Long id, Long idConnFirst, Long idConnSecond) {
		// TODO Auto-generated method stub
		return this.mappingDao.countByMappingTypeAndUserId(type, id, idConnFirst, idConnSecond);
	}

}

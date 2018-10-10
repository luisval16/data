package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IFieldDao;
import com.orasoft.data.link.models.entity.Field;

@Service
public class FieldServiceImpl implements IFieldService {

	@Autowired
	private IFieldDao fieldDao;
	
	@Override
	public List<Field> findAll() {
		// TODO Auto-generated method stub
		return (List<Field>) this.fieldDao.findAll();
	}

	@Override
	public Page<Field> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.fieldDao.findAll(pageable);
	}

	@Override
	public Field save(Field field) {
		// TODO Auto-generated method stub
		return this.fieldDao.save(field);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.fieldDao.deleteById(id);
	}

	@Override
	public Field findOne(Long id) {
		// TODO Auto-generated method stub
		return this.fieldDao.findById(id).orElse(null);
	}

}

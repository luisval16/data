package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.Field;

public interface IFieldService {
	
	public List<Field> findAll();
	
	public Page<Field> findAll(Pageable pageable);
	
	public Field save(Field field);
	
	public void delete(Long id);
	
	public Field findOne(Long id);

}

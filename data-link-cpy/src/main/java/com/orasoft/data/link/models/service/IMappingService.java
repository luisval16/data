package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.Mapping;

public interface IMappingService {
	
	public List<Mapping> findAll();
	
	public Page<Mapping> findAll(Pageable pageable);
	
	public Mapping save(Mapping mapping);
	
	public void delete(Long id);
	
	public Mapping findOne(Long id);
	
	public Page<Mapping> findByUserId(Long id,Pageable pageable);
	
	public Long countByTypeAndUserId(String type, Long id, Long idConnFirst, Long idConnSecond);

}

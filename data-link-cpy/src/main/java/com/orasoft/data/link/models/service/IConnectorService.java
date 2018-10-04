package com.orasoft.data.link.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.Connector;

public interface IConnectorService {

	public Page<Connector> findAll(Pageable pageable);
	
	public Connector save(Connector connector);
	
	public void delete(Long id);
	
	public Connector findOne(Long id);
	
	public Page<Connector> findByUserId(Long id, Pageable pageable);
	
}

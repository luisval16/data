package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.Syncs;

public interface ISyncsService {

	public List<Syncs> findAll();
	
	public Page<Syncs> findAll(Pageable pageable);
	
	public Syncs save(Syncs sync);
	
	public void delete(Long id);
	
	public Syncs findOne(Long id);
}

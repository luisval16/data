package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.InfusionToken;

public interface IInfusionTokenService {
	public List<InfusionToken> findAll();
	
	public Page<InfusionToken> findAll(Pageable pageable);
	
	public InfusionToken save(InfusionToken infusionToken);
	
	public InfusionToken findOne(Long id);
	
	public void delete(Long id);
}

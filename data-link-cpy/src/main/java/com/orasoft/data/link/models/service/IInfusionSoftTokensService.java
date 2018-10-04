package com.orasoft.data.link.models.service;

import java.util.List;

import com.orasoft.data.link.models.entity.InfusionSoftToken;

public interface IInfusionSoftTokensService {

	public List<InfusionSoftToken> findAll();
	
	public InfusionSoftToken save(InfusionSoftToken infusionSoftToken);
	
	public void delete(Long id);
	
	public InfusionSoftToken findOne(Long id);
}

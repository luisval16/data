package com.orasoft.data.link.models.service;

import java.util.List;

import com.orasoft.data.link.models.entity.CWCallback;

public interface ICWCallbackService {

	public List<CWCallback> findAll();
	
	public CWCallback save(CWCallback cwCallback);
	
	public void delete(Long id);

	public CWCallback findOne(Long id);
	
}

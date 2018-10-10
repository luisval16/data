package com.orasoft.data.link.models.service;

import java.util.List;

import com.orasoft.data.link.models.entity.ISWebhook;

public interface IISWebhookService {

	public List<ISWebhook> findAll();
	
	public ISWebhook save(ISWebhook isWebhook);
	
	public void delete(Long id);
	
	public ISWebhook findOne(Long id);
}

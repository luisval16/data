package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IISWebhookDao;
import com.orasoft.data.link.models.entity.ISWebhook;

@Service
public class ISWebhookServiceImpl implements IISWebhookService {

	@Autowired
	private IISWebhookDao webhookDao;
	
	@Override
	public List<ISWebhook> findAll() {
		// TODO Auto-generated method stub
		return (List<ISWebhook>) this.webhookDao.findAll();
	}

	@Override
	public ISWebhook save(ISWebhook isWebhook) {
		// TODO Auto-generated method stub
		return this.webhookDao.save(isWebhook);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.webhookDao.deleteById(id);
	}

	@Override
	public ISWebhook findOne(Long id) {
		// TODO Auto-generated method stub
		return this.webhookDao.findById(id).orElse(null);
	}

}

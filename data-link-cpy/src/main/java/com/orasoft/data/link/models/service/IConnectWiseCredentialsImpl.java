package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IConnectWiseCredentialsDao;
import com.orasoft.data.link.models.entity.ConnectWiseCredentials;

@Service
public class IConnectWiseCredentialsImpl implements IConnectWiseCredentialsService{

	@Autowired
	private IConnectWiseCredentialsDao connectWiseCredentialsDao;
	
	@Override
	public List<ConnectWiseCredentials> findAll() {
		// TODO Auto-generated method stub
		return (List<ConnectWiseCredentials>) this.connectWiseCredentialsDao.findAll();
	}

	@Override
	public ConnectWiseCredentials save(ConnectWiseCredentials connectWiseCredentials) {
		// TODO Auto-generated method stub
		return this.connectWiseCredentialsDao.save(connectWiseCredentials);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.connectWiseCredentialsDao.deleteById(id);
	}

	@Override
	public ConnectWiseCredentials findOne(Long id) {
		// TODO Auto-generated method stub
		return this.connectWiseCredentialsDao.findById(id).orElse(null);
	}

}

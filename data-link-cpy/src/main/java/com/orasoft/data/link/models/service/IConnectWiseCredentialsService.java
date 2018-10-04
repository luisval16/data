package com.orasoft.data.link.models.service;

import java.util.List;

import com.orasoft.data.link.models.entity.ConnectWiseCredentials;

public interface IConnectWiseCredentialsService {

	public List<ConnectWiseCredentials> findAll();
	
	public ConnectWiseCredentials save(ConnectWiseCredentials connectWiseCredentials);
	
	public void delete(Long id);
	
	public ConnectWiseCredentials findOne(Long id);
}

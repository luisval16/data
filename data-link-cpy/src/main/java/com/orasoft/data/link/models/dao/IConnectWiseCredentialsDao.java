package com.orasoft.data.link.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.orasoft.data.link.models.entity.ConnectWiseCredentials;

public interface IConnectWiseCredentialsDao extends CrudRepository<ConnectWiseCredentials, Long>{

	@Query("select COUNT(c) from ConnectWiseCredentials c where c.publicKey = ?1 and c.secretKey = ?2 and c.company = ?3")
	public Long CountByPublicKeyAndSecretKeyAndCompany(String publicKey, String secretKey, String company);
}

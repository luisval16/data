package com.orasoft.data.link.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.Connector;

public interface IConnectorDao extends PagingAndSortingRepository<Connector, Long>{

	@Query("select c from Connector c where c.user.id = ?1 and c.active = 1")
	public Page<Connector> findByUserId(Long id, Pageable pageable);
}

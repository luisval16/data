package com.orasoft.data.link.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.Mapping;

public interface IMappingDao extends PagingAndSortingRepository<Mapping, Long>{

	@Query("select m from Mapping m where m.firstConnector.user.id = ?1 and m.secondConnector.user.id = ?1")
	public Page<Mapping> findByUserId(Long id,Pageable pageable);
	
	@Query("select COUNT(m) from Mapping m where m.type = ?1 and m.firstConnector.user.id = ?2 and m.secondConnector.user.id = ?2 and (m.firstConnector.id = ?3 or m.firstConnector.id = ?4) and (m.secondConnector.id = ?4 or m.secondConnector.id = ?3 )")
	public Long countByMappingTypeAndUserId(String type, Long id, Long idConnFirst, Long idConnSecond);
	
}

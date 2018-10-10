package com.orasoft.data.link.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.Syncs;

public interface ISyncDao extends PagingAndSortingRepository<Syncs, Long>{

}

package com.orasoft.data.link.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.Field;

public interface IFieldDao extends PagingAndSortingRepository<Field, Long>{

}

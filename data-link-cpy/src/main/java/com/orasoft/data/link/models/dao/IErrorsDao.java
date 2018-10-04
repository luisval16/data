package com.orasoft.data.link.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.Errors;

public interface IErrorsDao extends PagingAndSortingRepository<Errors, Long>{

}

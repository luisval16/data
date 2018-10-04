package com.orasoft.data.link.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.User;

public interface IUserDao extends PagingAndSortingRepository<User, Long>{

	@Query("select COUNT(u) from User u where u.email = ?1")
	public Long CountByEmailEqual(String email);
	
	@Query("select u from User u where u.email = ?1 and u.active = 1")
	public User findByEmail(String email);
	
}

package com.orasoft.data.link.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.Role;

public interface IRoleDao extends PagingAndSortingRepository<Role, Long>{

	@Query("select COUNT(r) from Role r where r.roleName = ?1")
	public Long CountByRoleNameEqual(String roleName);
	
	@Query("select r from Role r where r.roleName = ?1")
	public Role FindByRoleNameEqual(String roleName);
	
}

package com.orasoft.data.link.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.orasoft.data.link.models.entity.UserXRole;

public interface IUserXRoleDao extends PagingAndSortingRepository<UserXRole, Long>{

	@Query("select COUNT(a) from UserXRole a where a.user.id = ?1 and a.role.id = ?2")
	public Long countByUserAndRole(Long idUser,Long idRole);
	
}

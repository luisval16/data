package com.orasoft.data.link.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.UserXRole;

public interface IUserXRoleService {

	public Page<UserXRole> findAll(Pageable pageable);
	
	public UserXRole save(UserXRole userXRole);
	
	public UserXRole findOne(Long id);
	
	public Long countByUserAndRole(Long idUser,Long idRole);
}

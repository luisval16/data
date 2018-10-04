package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.Role;


public interface IRoleService {

    public List<Role> findAll();
    
    public Page<Role> findAll(Pageable pageable);
    
	public Role save(Role role);

	public Role findOne(Long id);

	public void delete(Long id);
	
	public Long CountByRoleNameEqual(String roleName);
	
	public Role FindByRoleNameEqual(String roleName);
	
	
}

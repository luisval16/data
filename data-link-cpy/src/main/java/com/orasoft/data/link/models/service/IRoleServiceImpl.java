package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IRoleDao;
import com.orasoft.data.link.models.entity.Role;

@Service
public class IRoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) this.roleDao.findAll();
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.roleDao.findAll(pageable);
	}

	@Override
	public Role save(Role role) {
		// TODO Auto-generated method stub
		return this.roleDao.save(role);
	}

	@Override
	public Role findOne(Long id) {
		// TODO Auto-generated method stub
		return this.roleDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
        this.roleDao.deleteById(id);
	}

	@Override
	public Long CountByRoleNameEqual(String roleName) {
		// TODO Auto-generated method stub
		return this.roleDao.CountByRoleNameEqual(roleName);
	}

	@Override
	public Role FindByRoleNameEqual(String roleName) {
		// TODO Auto-generated method stub
		return this.roleDao.FindByRoleNameEqual(roleName);
	}

}

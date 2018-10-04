package com.orasoft.data.link.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IUserXRoleDao;
import com.orasoft.data.link.models.entity.UserXRole;

@Service
public class IUserXRoleImpl implements IUserXRoleService{

	@Autowired
	private IUserXRoleDao userXRoleDao;
	
	@Override
	public UserXRole save(UserXRole userXRole) {
		// TODO Auto-generated method stub
		return this.userXRoleDao.save(userXRole);
	}

	@Override
	public Page<UserXRole> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.userXRoleDao.findAll(pageable);
	}

	@Override
	public UserXRole findOne(Long id) {
		// TODO Auto-generated method stub
		return this.userXRoleDao.findById(id).orElse(null);
	}

	@Override
	public Long countByUserAndRole(Long idUser, Long idRole) {
		// TODO Auto-generated method stub
		return this.userXRoleDao.countByUserAndRole(idUser, idRole);
	}

}

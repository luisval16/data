package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IRoleXModuleDao;
import com.orasoft.data.link.models.entity.RoleXModule;

@Service
public class IRoleXModuleImpl implements IRoleXModuleService{

	@Autowired
	private IRoleXModuleDao roleXModuleDao;
	
	@Override
	public List<RoleXModule> findAll() {
		// TODO Auto-generated method stub
		return (List<RoleXModule>) this.roleXModuleDao.findAll();
	}

}

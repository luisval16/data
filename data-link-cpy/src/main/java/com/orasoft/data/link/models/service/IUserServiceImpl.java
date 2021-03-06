package com.orasoft.data.link.models.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IUserDao;
import com.orasoft.data.link.models.entity.User;

@Service
public class IUserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List<User>) this.userDao.findAll();
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return this.userDao.save(user);
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return this.userDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.userDao.deleteById(id);
	}

	@Override
	public Long CountByEmailEqual(String email) {
		// TODO Auto-generated method stub
		return this.userDao.CountByEmailEqual(email);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.userDao.findAll(pageable);
	}

	@Override
	public User findUserByEncoder(String code) {
		// TODO Auto-generated method stub
		User u = null;
		List<User> users =  this.findAll();
		for (User user : users) {
			if (encoder.matches(user.getEmail()+user.getId(), code)) {
				u = user;
			}
		}
		return u;
	}

}

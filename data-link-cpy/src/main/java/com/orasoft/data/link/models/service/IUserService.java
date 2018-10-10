package com.orasoft.data.link.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orasoft.data.link.models.entity.User;


public interface IUserService {

    public List<User> findAll();
    
    public Page<User> findAll(Pageable pageable);
    
	public User save(User user);

	public User findOne(Long id);

	public void delete(Long id);
	
	public Long CountByEmailEqual(String email);
	
	public User findUserByEncoder(String code);
}

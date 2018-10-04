package com.orasoft.data.link.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orasoft.data.link.auth.handler.UserPrincipal;
import com.orasoft.data.link.models.dao.IUserDao;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.entity.UserXRole;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDao.findByEmail(email);
		
		if (user == null) {
			logger.error("Error login: user does not exist '"+email+"'");
			throw new UsernameNotFoundException("User '"+email+"' does not exist!");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(UserXRole userXRole: user.getRoles()) {
			logger.info("Role: '"+userXRole.getRole().getRoleName()+"'");
			authorities.add(new SimpleGrantedAuthority(userXRole.getRole().getRoleName()));
		}
		
		if (authorities.isEmpty()) {
			logger.error("Error login: user does not have roles '"+email+"'");
			throw new UsernameNotFoundException("User '"+email+"' does not have roles!");
		}
		
		
		boolean enabled = user.getActive() == 1? true:false;
		
		
		UserPrincipal principal = new UserPrincipal(user, enabled, true, true, true, authorities);
		
		return principal;
		//return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),enabled,true,true,true,authorities);
	}

}

package com.orasoft.data.link.auth.handler;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserPrincipal extends User{
	
	private final com.orasoft.data.link.models.entity.User user;
	
	public UserPrincipal(com.orasoft.data.link.models.entity.User user,Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(),user.getPassword(),authorities);
		this.user = user;
	}
	
	public UserPrincipal(com.orasoft.data.link.models.entity.User user, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(),user.getPassword(),
				enabled, accountNonExpired, credentialsNonExpired,accountNonLocked, authorities );
		this.user = user;
	}
	
	public com.orasoft.data.link.models.entity.User getUser(){
		return this.user;
	}
	

}

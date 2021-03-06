package com.orasoft.data.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.orasoft.data.link.auth.handler.LoginSuccessHandler;
import com.orasoft.data.link.models.service.JpaUserDetailsService;



@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	
	/*@Autowired
	private DataSource dataSource;*/
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService jpaUserDetailService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null) {
				Collection<? extends GrantedAuthority> authorities =  auth.getAuthorities();
				
				for(GrantedAuthority authority: authorities) {
					System.out.println("rol " +authority.getAuthority());
					
					
				}
			}
		}*/
		
		
		http.authorizeRequests().antMatchers("/signup","/css/**","/js/**","/images/**","/media/**","/fonts/**","/history").permitAll()
		
		 .antMatchers("/").hasAnyAuthority("user")
		.antMatchers("/dashboard").hasAnyAuthority("user")
		.antMatchers("/connectors").hasAnyAuthority("user")
		.antMatchers("/connectwise/connector").hasAnyAuthority("user")
		.antMatchers("/delete/conn/**").hasAnyAuthority("user")
		.antMatchers("/infusionsoft/redirect").hasAnyAuthority("user")
		.antMatchers("/infusionsoft/auth").hasAnyAuthority("user")
		.antMatchers("/mappings").hasAnyAuthority("user")
		.antMatchers("/save/mapping").hasAnyAuthority("user")
		.antMatchers("/addMapping").hasAnyAuthority("user")
		.antMatchers("/delete/map/**").hasAnyAuthority("user")
		.antMatchers("/user/**").hasAnyAuthority("admin")
		.antMatchers("/users").hasAnyAuthority("admin")
		.antMatchers("/delete/**").hasAnyAuthority("admin")
		.antMatchers("/disable/**").hasAnyAuthority("admin")
		.antMatchers("/enable/**").hasAnyAuthority("admin")
		.antMatchers("/roles").hasAnyAuthority("admin")
		.antMatchers("/role/**").hasAnyAuthority("admin")
		.antMatchers("/role/enable/**").hasAnyAuthority("admin")
		.antMatchers("/role/delete/**").hasAnyAuthority("admin")
		.antMatchers("/role/disable/**").hasAnyAuthority("admin")
		.antMatchers("/assignrole").hasAnyAuthority("admin")
		.antMatchers("/assignroleuser").hasAnyAuthority("admin")
		.antMatchers("/tokens").hasAnyAuthority("admin")
		.antMatchers("/token/**").hasAnyAuthority("admin")
		.antMatchers("/eliminar/**").hasAnyAuthority("admin")
		
		.anyRequest().authenticated()
		.and()
		.formLogin().successHandler(successHandler)
		.loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
		
		
		//http.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/companies").permitAll();
		
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		/*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(users.username("luis").password("12345").roles("USER"));*/
		
		/*build.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select email,user_pass, active from dl_user where email=?")
		.authoritiesByUsernameQuery("SELECT u.email,r.rol_name FROM dl_role r inner join dl_user_x_role ur on ur.id_role = r.id_role\r\n" + 
				"	 inner join dl_user u on u.id_user = ur.id_user where u.email=?");
		*/
		
		build.userDetailsService(jpaUserDetailService)
		.passwordEncoder(passwordEncoder);
		
		
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/api/**");
	}
	
	
	
}

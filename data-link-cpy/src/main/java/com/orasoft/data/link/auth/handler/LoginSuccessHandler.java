package com.orasoft.data.link.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		flashMap.put("success", "Welcome " +authentication.getName()+ ", you have started session succesfully!");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		if (authentication != null) {
			logger.info("The user '"+authentication.getName()+"' has started session!");
			for(GrantedAuthority authority: authentication.getAuthorities()) {
				logger.info("The user '"+authentication.getName()+"' with role "+authority.getAuthority()+"!" + authority.getAuthority().equals("ROLE_USER"));
			}
		}
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

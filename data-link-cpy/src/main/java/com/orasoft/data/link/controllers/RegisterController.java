package com.orasoft.data.link.controllers;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.models.entity.Role;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.entity.UserXRole;
import com.orasoft.data.link.models.service.IRoleService;
import com.orasoft.data.link.models.service.IUserService;
import com.orasoft.data.link.models.service.IUserXRoleService;

@Controller
public class RegisterController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUserXRoleService userXRoleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String register(Map<String, Object> model,Principal principal,RedirectAttributes flash) {
		if (principal != null) {
			flash.addFlashAttribute("info", "You are already logged in!");
			return "redirect:/";
		}
		
		User user = new User();
		model.put("user", user);
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String registerNewUser(@Valid User user, BindingResult result,
			@RequestParam(name = "verifyPassword", required = false) String resetPass, Map<String, Object> model,
			RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
        	return "signup";	
		}
        
        Long count = this.userService.CountByEmailEqual(user.getEmail());
        if (count != 0) {
			model.put("error", "That email is already in use!");
			return "signup";
		}
		if (!user.getPassword().equals(user.getRepassword())) {
			model.put("error", "The passwords dont match!");
			return "signup";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User newUser = this.userService.save(user);
		Role role = this.roleService.FindByRoleNameEqual("user");
		UserXRole userXRole = new UserXRole();
		userXRole.setRole(role);
		userXRole.setUser(newUser);
		this.userXRoleService.save(userXRole);
		status.setComplete();
		flash.addFlashAttribute("success",
				"Login email " + user.getEmail() + " registered succesful, you can login now!");
		flash.addFlashAttribute("loginModal","open");
		
		return "redirect:/login";
	}
}

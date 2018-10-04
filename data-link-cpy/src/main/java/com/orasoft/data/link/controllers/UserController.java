package com.orasoft.data.link.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.service.IUserService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
@SessionAttributes("userForm")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = { "/users"}, method = RequestMethod.GET)
	public String users(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication) {
		// List<User> users = this.userService.findAll();
		// System.out.println("SIZE: " +
		// users.get(0).getRoles().get(0).getRole().getRoleName());

		if (authentication != null) {
			System.out.println(authentication.getName());
			if (hasRole("admin")) {
				System.out.println("Hola ".concat(authentication.getName()));
			}else {
				System.out.println("Hola fail ".concat(authentication.getName()));
			}
		}
		
		
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<User> users = this.userService.findAll(pageRequest);
		PageRender<User> pageRender = new PageRender<>("/users", users);
		model.addAttribute("users", users);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Manage users");
		return "users";
	}

	@RequestMapping(value = "/user")
	public String crear(Map<String, Object> model) {

		User user = new User();
		model.put("user", user);
		model.put("titulo", "Add new user");
		model.put("op", "add");
		return "user";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String guardar(@Valid User user, BindingResult result,
			@RequestParam(name = "resetPass", required = false) String resetPass, Map<String, Object> model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			if (user.getId() != null) {
				model.put("titulo", "Update user " + user.getFirstName() + " " + user.getLastName());
				model.put("op", "edit");

				if (resetPass == null) {
					// no va resetear pass
					List<FieldError> errors = result.getFieldErrors();
					int c = 0;
					for (FieldError error : errors) {
						if (error.getField().equals("password")) {
							c++;
						}
					}
					if (!(errors.size() == 1 && c != 0)) {

						return "user";
					}
				} else {
					// va a resetear el pass
					model.put("checked", true);
					return "user";
				}

			} else {
				model.put("titulo", "Add new user");
				model.put("op", "add");
				return "user";
			}

			// String error = result.getFieldError("password").toString();

		}

		if (user.getId() != null) {
			User userOG = this.userService.findOne(user.getId());
			user.setRegDate(userOG.getRegDate());
			user.setActive(userOG.getActive());

			if (!userOG.getEmail().equals(user.getEmail())) {
				Long count = this.userService.CountByEmailEqual(user.getEmail());
				if (count != 0) {
					model.put("titulo", "Update user " + user.getFirstName() + " " + user.getLastName());
					model.put("op", "edit");
					model.put("error", "That email is already in use!");
					return "user";
				}
			}

			if (resetPass != null) {
				if (!user.getPassword().equals(user.getRepassword())) {
					model.put("titulo", "Update user " + user.getFirstName() + " " + user.getLastName());
					model.put("op", "edit");
					model.put("error", "The passwords dont match!");
					model.put("checked", true);
					return "user";
				}
			} else {
				user.setPassword(userOG.getPassword());
				user.setRepassword(userOG.getPassword());
			}

		} else {
			Long count = this.userService.CountByEmailEqual(user.getEmail());
			if (count != 0) {
				model.put("titulo", "Add new user");
				model.put("error", "That email is already in use!");
				model.put("op", "add");
				return "user";
			}
			if (!user.getPassword().equals(user.getRepassword())) {
				model.put("titulo", "Add new user");
				model.put("error", "The passwords dont match!");
				model.put("op", "add");
				return "user";
			}

		}
		String mensaje = user.getId() == null ? "added" : "updated";
		// System.out.println(user.getPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userService.save(user);
		status.setComplete();
		flash.addFlashAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " " + mensaje + " succesfully!");

		return "redirect:users";
	}

	@RequestMapping(value = "/user/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		User user = null;

		if (id > 0) {
			user = this.userService.findOne(id);
			if (user == null) {
				flash.addFlashAttribute("error", "The user does not exist!");
				return "redirect:/users";
			}
		} else {
			flash.addFlashAttribute("error", "The user id is no valid!");
			return "redirect:/users";

		}

		model.put("user", user);
		model.put("titulo", "Update user " + user.getFirstName() + " " + user.getLastName());
		model.put("op", "edit");
		return "user";
	}

	@RequestMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		User user = null;
		if (id > 0) {
			user = this.userService.findOne(id);
			if (user == null) {
				flash.addFlashAttribute("error", "The user does not exist!");
				return "redirect:/users";
			} else {

				this.userService.delete(id);
				flash.addFlashAttribute("success",
						"User " + user.getFirstName() + " " + user.getLastName() + " deleted succesfully!");
			}
		} else {
			flash.addFlashAttribute("error", "The user id is no valid!");
			return "redirect:/users";

		}

		return "redirect:/users";
	}

	@RequestMapping(value = "/disable/{id}")
	public String desactivar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		User user = null;
		if (id > 0) {
			user = this.userService.findOne(id);
			if (user == null) {
				flash.addFlashAttribute("error", "The user does not exist!");
				return "redirect:/users";
			} else {
				user.setActive((long) 0);
				this.userService.save(user);
				flash.addFlashAttribute("success",
						"User " + user.getFirstName() + " " + user.getLastName() + " disabled succesfully!");
			}
		} else {
			flash.addFlashAttribute("error", "The user id is no valid!");
			return "redirect:/users";

		}

		return "redirect:/users";
	}

	@RequestMapping(value = "/enable/{id}")
	public String activar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		User user = null;
		if (id > 0) {
			user = this.userService.findOne(id);
			if (user == null) {
				flash.addFlashAttribute("error", "The user does not exist!");
				return "redirect:/users";
			} else {
				user.setActive((long) 1);
				this.userService.save(user);
				flash.addFlashAttribute("success",
						"User " + user.getFirstName() + " " + user.getLastName() + " enabled succesfully!");
			}
		} else {
			flash.addFlashAttribute("error", "The user id is no valid!");
			return "redirect:/users";

		}

		return "redirect:/users";
	}
	
	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if (auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities =  auth.getAuthorities();
		
		for(GrantedAuthority authority: authorities) {
			System.out.println("rol " +authority.getAuthority());
			if (role.equals(authority.getAuthority())) {
				return true;
			}
			
		}
		return false;
	}

}

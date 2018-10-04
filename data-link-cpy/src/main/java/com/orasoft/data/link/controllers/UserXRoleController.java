package com.orasoft.data.link.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.models.entity.Role;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.entity.UserXRole;
import com.orasoft.data.link.models.service.IRoleService;
import com.orasoft.data.link.models.service.IUserService;
import com.orasoft.data.link.models.service.IUserXRoleService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
public class UserXRoleController {

	@Autowired
	private IUserXRoleService userXRoleService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserService userService;

	@GetMapping(value = "/assignrole")
	public String listaAssignRole(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 10);
		Page<UserXRole> list = this.userXRoleService.findAll(pageRequest);
		PageRender<UserXRole> pageRender = new PageRender<>("/assignrole", list);
		model.addAttribute("list", list);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Assign roles to users");
		model.addAttribute("userValue", 0);
		model.addAttribute("roleValue", 0);
		return "userXRole/assignrole";
	}

	@GetMapping(value = "/assignroleuser")
	public String listaAssignRole(Model model) {
		List<User> userList = this.userService.findAll();
		List<Role> roleList = this.roleService.findAll();
		UserXRole userxrole = new UserXRole();

		model.addAttribute("userList", userList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("userxrole", userxrole);
		model.addAttribute("titulo", "Assign new role to user");
		return "userXRole/assignroleuser";
	}

	@PostMapping(value = "/assignroleuser")
	public String assignRoleUser(@RequestParam(name = "id") String id, @RequestParam(name = "user") String userId,
			@RequestParam(name = "role") String roleId, Model model, RedirectAttributes flash) {
		System.out.println("id: " + id);
		String message = "You must select";
		boolean flag = false;
		if (userId.equals("0")) {
			flag = true;
			message += " an user";
			if (!roleId.equals("0")) {
				message += "!";
			}

		}

		if (roleId.equals("0")) {
			flag = true;
			if (userId.equals("0")) {
				message += " and a role!";
			} else {
				message += " a role!";
			}

		}

		if (!(userId.equals("0") && roleId.equals("0"))) {

			if (id.isEmpty()) {
				Long count = this.userXRoleService.countByUserAndRole(Long.parseLong(userId), Long.parseLong(roleId));
				if (count != 0) {
					flag = true;
					Role role = this.roleService.findOne(Long.parseLong(roleId));
					User user = this.userService.findOne(Long.parseLong(userId));
					message = "The role " + role.getRoleName() + " is already assigned to the user " + user.getEmail()
							+ "!";

				}

			} else {
				UserXRole userxroleOG = this.userXRoleService.findOne(Long.parseLong(id));
				boolean sameUser = userxroleOG.getUser().getId() == Long.parseLong(userId);
				boolean sameRole = userxroleOG.getRole().getId() == Long.parseLong(roleId);
				if (!(sameRole && sameUser)) {
					Long count = this.userXRoleService.countByUserAndRole(Long.parseLong(userId),
							Long.parseLong(roleId));
					if (count != 0) {
						flag = true;
						Role role = this.roleService.findOne(Long.parseLong(roleId));
						User user = this.userService.findOne(Long.parseLong(userId));
						message = "The role " + role.getRoleName() + " is already assigned to the user "
								+ user.getEmail() + "!";

					}
				}

			}
		}

		if (flag) {
			model.addAttribute("titulo", "Assign new role to user");
			model.addAttribute("error", message);
			List<User> userList = this.userService.findAll();
			List<Role> roleList = this.roleService.findAll();
			UserXRole userxrole = id.isEmpty() ? new UserXRole() : this.userXRoleService.findOne(Long.parseLong(id));

			model.addAttribute("userxrole", userxrole);
			model.addAttribute("userList", userList);
			model.addAttribute("roleList", roleList);
			model.addAttribute("roleValue", Integer.parseInt(roleId));
			model.addAttribute("userValue", Integer.parseInt(userId));
			return "userXRole/assignroleuser";
		} else {

			User user = this.userService.findOne(Long.parseLong(userId));
			Role role = this.roleService.findOne(Long.parseLong(roleId));
			UserXRole userxrole = new UserXRole();
			userxrole.setRole(role);
			userxrole.setUser(user);
			if (!id.isEmpty()) {
				userxrole.setId(Long.parseLong(id));
				UserXRole userxroleOG = this.userXRoleService.findOne(Long.parseLong(id));
				userxrole.setActive(userxroleOG.getActive());
				userxrole.setRegDate(userxroleOG.getRegDate());
			}
			this.userXRoleService.save(userxrole);
			flash.addFlashAttribute("success",
					"User " + user.getEmail() + " assigned role " + role.getRoleName() + " succesfully!");
		}

		return "redirect:/assignrole";
	}

	@RequestMapping(value = "/assignroleuser/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		UserXRole userxrole = null;
		if (id > 0) {
			userxrole = this.userXRoleService.findOne(id);
			if (userxrole == null) {
				flash.addFlashAttribute("error", "The role assignment does not exist!");
				return "redirect:/assignrole";
			}
		} else {
			flash.addFlashAttribute("error", "The role assignment id is no valid!");
			return "redirect:/assignrole";

		}

		model.put("userxrole", userxrole);
		List<User> userList = this.userService.findAll();
		List<Role> roleList = this.roleService.findAll();
		model.put("userList", userList);
		model.put("roleList", roleList);
		model.put("roleValue", userxrole.getRole().getId());
		model.put("userValue", userxrole.getUser().getId());
		model.put("titulo", "Update role to user");

		return "userXRole/assignroleuser";
	}
	
	

}

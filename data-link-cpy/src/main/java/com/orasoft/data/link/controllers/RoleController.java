package com.orasoft.data.link.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.models.entity.Role;
import com.orasoft.data.link.models.service.IRoleService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
@SessionAttributes("roleForm")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public String roles(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Role> roles = this.roleService.findAll(pageRequest);
		PageRender<Role> pageRender = new PageRender<>("/roles", roles);
		model.addAttribute("roles", roles);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Manage roles");
		return "roles";
	}

	@RequestMapping(value = "/role")
	public String crear(Map<String, Object> model) {

		Role role = new Role();
		model.put("role", role);
		model.put("titulo", "Add new role");
		return "role";
	}

	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public String guardar(@Valid Role role, BindingResult result, Map<String, Object> model, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			if (role.getId() != null) {
				model.put("titulo", "Update role " + role.getRoleName());

			} else {
				model.put("titulo", "Add new user");

			}
			return "role";
		}
		
		
		role.setRoleName(role.getRoleName().toLowerCase().replace(" ", ""));
        if (role.getId() != null) {
        	Role roleOG = this.roleService.findOne(role.getId());
        	role.setActive(roleOG.getActive());
        	role.setRegDate(roleOG.getRegDate());
			
        	if (!roleOG.getRoleName().equals(role.getRoleName())) {
        		Long count = this.roleService.CountByRoleNameEqual(role.getRoleName());
    			if (count != 0) {
    				model.put("titulo", "Update role " + role.getRoleName());
    				model.put("error", "The role name " + role.getRoleName()+" is already in the database!");
    				return "role";
    			}	
			}
        	
		}else {
			Long count = this.roleService.CountByRoleNameEqual(role.getRoleName());
			if (count != 0) {
				model.put("titulo", "Add new role");
				model.put("error", "The role name " + role.getRoleName()+" is already in the database!");
				return "role";
			}
		}
		String mensaje = role.getId() == null ? "added" : "updated";
		this.roleService.save(role);
		status.setComplete();
		flash.addFlashAttribute("success",
				"Role " + role.getRoleName()+ " " + mensaje + " succesfully!");

		return "redirect:/roles";
	}
	
	@RequestMapping(value = "/role/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Role role = null;

		if (id > 0) {
			role = this.roleService.findOne(id);
			if (role == null) {
				flash.addFlashAttribute("error", "The role does not exist!");
				return "redirect:/roles";
			}
		} else {
			flash.addFlashAttribute("error", "The role id is no valid!");
			return "redirect:/roles";

		}

		model.put("role", role);
		model.put("titulo", "Update role " + role.getRoleName());
		return "role";
	}
	
	@RequestMapping(value = "/role/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Role role = null;
		if (id > 0) {
			role = this.roleService.findOne(id);
			if (role == null) {
				flash.addFlashAttribute("error", "The role does not exist!");
				return "redirect:/roles";
			} else {

				this.roleService.delete(id);
				flash.addFlashAttribute("success",
						"Role " + role.getRoleName()+ " deleted succesfully!");
			}
		} else {
			flash.addFlashAttribute("error", "The role id is no valid!");
			return "redirect:/roles";

		}

		return "redirect:/roles";
	}
	
	@RequestMapping(value = "/role/disable/{id}")
	public String disable(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Role role = null;
		if (id > 0) {
			role = this.roleService.findOne(id);
			if (role == null) {
				flash.addFlashAttribute("error", "The role does not exist!");
				return "redirect:/roles";
			} else {
                role.setActive((long)0);
				this.roleService.save(role);
				flash.addFlashAttribute("success",
						"Role " + role.getRoleName()+ " disabled succesfully!");
			}
		} else {
			flash.addFlashAttribute("error", "The role id is no valid!");
			return "redirect:/roles";

		}

		return "redirect:/roles";
	}	
	@RequestMapping(value = "/role/enable/{id}")
	public String enable(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Role role = null;
		if (id > 0) {
			role = this.roleService.findOne(id);
			if (role == null) {
				flash.addFlashAttribute("error", "The role does not exist!");
				return "redirect:/roles";
			} else {
                role.setActive((long)1);
				this.roleService.save(role);
				flash.addFlashAttribute("success",
						"Role " + role.getRoleName()+ " enabled succesfully!");
			}
		} else {
			flash.addFlashAttribute("error", "The role id is no valid!");
			return "redirect:/roles";

		}

		return "redirect:/roles";
	}
}

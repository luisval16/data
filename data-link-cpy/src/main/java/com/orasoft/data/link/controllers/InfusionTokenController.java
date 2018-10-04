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

import com.orasoft.data.link.models.entity.InfusionToken;
import com.orasoft.data.link.models.entity.SyncHistory;
import com.orasoft.data.link.models.service.IInfusionTokenService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
@SessionAttributes("infusionToken")
public class InfusionTokenController {

	@Autowired
	private IInfusionTokenService infusionTokenDao;

	@RequestMapping(value = "/tokens", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page",defaultValue="0") int page,Model model) {
		model.addAttribute("titulo", "Listado de tokens");
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<InfusionToken> list =  infusionTokenDao.findAll(pageRequest);
		PageRender<InfusionToken> pageRender =  new PageRender<>("/tokens", list);
		model.addAttribute("tokens", list);
		model.addAttribute("page",pageRender);
		
		return "listar";
	}

	@RequestMapping(value = "/token")
	public String crear(Map<String, Object> model) {

		InfusionToken infusionToken = new InfusionToken();
		model.put("infusionToken", infusionToken);
		model.put("titulo", "Añadir nuevo token");

		return "token";
	}

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public String guardar(@Valid InfusionToken infusionToken, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Añadir nuevo token");
			return "token";
		}
		String mensaje = infusionToken.getId() == null? "agregado" : "editado";
		infusionTokenDao.save(infusionToken);
		status.setComplete();
		
			flash.addFlashAttribute("success", "Token " + infusionToken.getTokenActual() + " "+mensaje+" exitosamente");
		
		return "redirect:tokens";
	}

	@RequestMapping(value = "/token/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		InfusionToken infusionToken = null;
		if (id > 0) {
			infusionToken = infusionTokenDao.findOne(id);
			if (infusionToken == null) {
				flash.addFlashAttribute("error", "Token no existe");
				return "redirect:/tokens";
			}
		} else {
			flash.addFlashAttribute("error", "Token no puede ser cero");
			return "redirect:/tokens";

		}
		model.put("infusionToken", infusionToken);
		model.put("titulo", "Editar token");
		return "token";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			infusionTokenDao.delete(id);
			flash.addFlashAttribute("success", "Token eliminado exitosamente");
		}
		return "redirect:/tokens";
	}
}

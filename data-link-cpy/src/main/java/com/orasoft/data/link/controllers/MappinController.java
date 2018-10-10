package com.orasoft.data.link.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.auth.handler.UserPrincipal;
import com.orasoft.data.link.connectors.ConnectCW;
import com.orasoft.data.link.connectors.ConnectIS;
import com.orasoft.data.link.models.entity.CWCallback;
import com.orasoft.data.link.models.entity.Connector;
import com.orasoft.data.link.models.entity.ISWebhook;
import com.orasoft.data.link.models.entity.Mapping;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.service.IConnectorService;
import com.orasoft.data.link.models.service.IMappingService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
public class MappinController {

	@Autowired
	private IMappingService mappingService;

	@Autowired
	private IConnectorService connectorService;

	@Autowired
	private ConnectCW cw;

	@Autowired
	private ConnectIS is;

	private final String imgCom = "/images/logos/business-work_11-512.png";

	private final String imgCon = "/images/logos/contacts.png";

	private final String imgOpp = "/images/logos/SalesOpportunityIcon.png";
	
	@Autowired
	private PasswordEncoder encoder;

	@GetMapping(value = "/mappings")
	public String mappingsListar(@RequestParam(name = "page", defaultValue = "0") int page, Map<String, Object> model,
			Authentication auth) {
		model.put("activeIndexClassMapp", "active");
		model.put("titulo", "Mappings");
		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Mapping> list = this.mappingService.findByUserId(user.getId(), pageRequest);
		PageRender<Mapping> pageRender = new PageRender<>("/mappings", list);
		model.put("list", list);
		System.out.println("Size: " + list.getSize());
		return "mappings/mappings";
	}

	@GetMapping(value = "/addMapping")
	public String addMapping(Map<String, Object> model, Authentication auth, RedirectAttributes flash) {
		model.put("titulo", "Add new mapping");
		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();

		List<Connector> conns = this.connectorService.findByUserId(user.getId());
		model.put("conns", conns);
		if (conns.size() < 2) {
			flash.addFlashAttribute("warning", "You must create atleast two connectors to add a mapping!");
			return "redirect:/connectors";
		}
		// model.put("companyCount", this.mappingService.countByTypeAndUserId("Company",
		// user.getId()));
		// model.put("contactCount", this.mappingService.countByTypeAndUserId("Contact",
		// user.getId()));
		// model.put("oppCount", this.mappingService.countByTypeAndUserId("Opportunity",
		// user.getId()));
		return "mappings/addMapping";
	}

	@PostMapping(value = "/save/mapping")
	public String saveMapping(@RequestParam(name = "firstConn") Long idFirst,
			@RequestParam(name = "secondConn") Long idSecond, @RequestParam(name = "object") String object,
			Map<String, Object> model, Authentication auth, RedirectAttributes flash, HttpServletRequest request) {
		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();
		System.out.print("params: " + idFirst + idSecond + object);
		Long count = this.mappingService.countByTypeAndUserId(object, user.getId(), idFirst, idSecond);
		boolean flag = true;
		if (object.equals("Company")) {
			if (count != 0) {
				flag = false;
				flash.addFlashAttribute("error", "There is already a company mapping for these connectors!");
			}
		}

		if (object.equals("Contact")) {
			Long countCom = this.mappingService.countByTypeAndUserId("Company", user.getId(), idFirst, idSecond);
			if (countCom == 0) {
				flag = false;
				flash.addFlashAttribute("error",
						"You must have a company mapping for these connectors to make a contact mapping!");

			} else {
				if (count != 0) {
					flag = false;
					flash.addFlashAttribute("error", "There is already a contact mapping for these connectors!");
				}
			}

		}

		if (object.equals("Opportunity")) {
			Long countOpp = this.mappingService.countByTypeAndUserId("Opportunity", user.getId(), idFirst, idSecond);
			if (countOpp == 0) {
				flag = false;
				flash.addFlashAttribute("error",
						"You must have a contact mapping for these connectors to make a opportunity mapping!");

			} else {
				if (count != 0) {
					flag = false;
					flash.addFlashAttribute("error", "There is already an opportunity mapping for these connectors!");
				}
			}

		}

		if (flag) {
			Mapping map = new Mapping();
			Connector fConn = this.connectorService.findOne(idFirst);
			map.setFirstConnector(fConn);
			Connector sConn = this.connectorService.findOne(idSecond);
			map.setSecondConnector(sConn);
			map.setType(object);
			if (object.equals("Company")) {
				map.setImg(imgCom);
			} else if (object.equals("Contact")) {
				map.setImg(imgCon);
			} else if (object.equals("Opportunity")) {
				map.setImg(imgOpp);
			}

			if (object.equals("Company")) {
				map = this.mappingService.save(map);
				CWCallback callback = new CWCallback();
				if (fConn.getType().equals("cw")) {
					callback.setConnector(fConn);
				} else if (sConn.getType().equals("cw")) {
					callback.setConnector(sConn);
				}
				String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ request.getContextPath();
				String code = encoder.encode(user.getEmail() + user.getId()); 
				callback.setUrl(url + "/api/companies?code=" + code);
				callback.setType(object.toLowerCase());
				callback.setLevel("owner");
				callback.setObjectId((long) 1);
				callback.setMapping(map);
				boolean isCWSuccess = this.cw.createCallback(callback);

				/* WEBHOOK SETUP ADD */
				ISWebhook isAddWebhook = new ISWebhook();
				if (fConn.getType().equals("is")) {
					isAddWebhook.setConnector(fConn);
				} else if (sConn.getType().equals("is")) {
					isAddWebhook.setConnector(sConn);
				}
				isAddWebhook.setEventKey("company.add");
				isAddWebhook.setHookUrl(url + "/api/hooks/companies?code=" + code);
				isAddWebhook.setMapping(map);
				ISWebhook addHook = this.is.createWebhook(isAddWebhook);
				boolean isISAddSuccess = addHook == null ? false : true;
				/* WEBHOOK SETUP UPDATE */
				ISWebhook isEditWebhook = new ISWebhook();
				if (fConn.getType().equals("is")) {
					isEditWebhook.setConnector(fConn);
				} else if (sConn.getType().equals("is")) {
					isEditWebhook.setConnector(sConn);
				}
				isEditWebhook.setEventKey("company.edit");
				isEditWebhook.setHookUrl(url + "/api/hooks/companies");
				isEditWebhook.setMapping(map);
				ISWebhook editHook = this.is.createWebhook(isEditWebhook);
				boolean isISUpdateSuccess = editHook == null ? false : true;

				if (isCWSuccess && isISAddSuccess && isISUpdateSuccess) {
					flash.addFlashAttribute("success", object
							+ " mapping created with success, now every time you add or update, it will synchronize on both platform!");
				} else {
					String message = "";
					if (!isCWSuccess) {
						message = "- Something went wrong while creating the callback to "
								+ callback.getConnector().getPlatform() + "!\n";
					}
					if (!isISAddSuccess) {
						message += "- Something went wrong while creating the callback to "
								+ isAddWebhook.getConnector().getPlatform() + "!\n";
					}
					if (!isISUpdateSuccess) {
						message += "- Something went wrong while creating the callback to "
								+ isEditWebhook.getConnector().getPlatform() + "!\n";
					}
					this.mappingService.delete(map.getId());
					flash.addFlashAttribute("error", message);

				}
			} else {

				flash.addFlashAttribute("info", object + " option not supported yet!");
			}

		}

		return "redirect:/mappings";
	}

	@GetMapping("/delete/map/{id}")
	public String deleteMap(@PathVariable(name = "id") Long id, RedirectAttributes flash) {
		Mapping map = this.mappingService.findOne(id);
		boolean isCWSuccess = this.cw.deleteCallback(map.getCallbacks().get(0));
		List<ISWebhook> hooks = map.getWebhooks();
		Long count = (long) 0;
		for (ISWebhook hook : hooks) {
			if (this.is.deleteWebHook(hook)) {
				count++;
			}
		}
		if (isCWSuccess && count == hooks.size()) {
			this.mappingService.delete(map.getId());
			flash.addFlashAttribute("success", map.getType() + " mapping deleted with success!");
		} else {
			String message = "";
			if (count != hooks.size()) {
				message += "Something went wrong while deleting the webhooks in InfusionSoft!\n";
				

			}
			if (!isCWSuccess) {
				message += "Something went wrong while deleting the callback in ConnectWise!";

			}
			flash.addFlashAttribute("error",message);
		}

		return "redirect:/mappings";
	}

}

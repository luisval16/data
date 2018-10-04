package com.orasoft.data.link.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.auth.handler.UserPrincipal;
import com.orasoft.data.link.connectors.ConnectCW;
import com.orasoft.data.link.models.entity.ConnectWiseCredentials;
import com.orasoft.data.link.models.entity.Connector;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.service.IConnectWiseCredentialsService;
import com.orasoft.data.link.models.service.IConnectorService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
public class ConnectorController {

	@Autowired
	private ConnectCW cw;

	@Autowired
	private IConnectWiseCredentialsService cwcService;

	@Autowired
	private IConnectorService connectorService;

	@GetMapping(value = "/connectors")
	public String connectorsLista(@RequestParam(name = "page", defaultValue = "0") int page, Map<String, Object> model,
			Authentication auth) {
		ConnectWiseCredentials cwc = new ConnectWiseCredentials();
		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();
		System.out.println("User: " + user.getEmail());
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Connector> conns = this.connectorService.findByUserId(user.getId(), pageRequest);
		PageRender<Connector> pageRender = new PageRender<>("/connectors", conns);
		model.put("conns", conns);
		model.put("page", pageRender);
		model.put("cwc", cwc);
		model.put("activeIndexClassConn", "active");
		model.put("titulo", "Connectors");
		return "connectors";
	}

	@RequestMapping(value = "/connectwise/connector", method = RequestMethod.POST)
	public String connectwiseConnector(ConnectWiseCredentials cwc, Map<String, Object> model, Authentication auth,
			RedirectAttributes flash) {

		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();
		System.out.println("User: " + user.getEmail());
		cwc.setUser(user);

		Long count = this.cwcService.CountByPublicKeyAndSecretKeyAndCompany(cwc.getPublicKey(), cwc.getSecretKey(),
				cwc.getCompany());
		if (count > 0) {
			flash.addFlashAttribute("error",
					"There is already a connection registered with that public key, private key and company!");
			return "redirect:/connectors";
		}
		Long countConn = this.connectorService.countByTypeAndUser("cw", user.getId());
		if (countConn > 0) {

			flash.addFlashAttribute("error", "There is already a connection with ConnectWise");
			return "redirect:/connectors";
		}

		try {
			if (this.cw.testConn(cwc)) {
				ConnectWiseCredentials cwcNew = this.cwcService.save(cwc);
				if (cwcNew != null) {
					Connector con = new Connector();
					con.setPlatform("ConnectWise");
					con.setType("cw");
					con.setUser(user);
					con.setIdCred(cwcNew.getId());
					this.connectorService.save(con);
					model.put("titulo", "Connectors");
					flash.addFlashAttribute("success", "Connection created with success!");
				} else {
					model.put("titulo", "Connectors");
					flash.addFlashAttribute("error", "Unexpected error check the errors log!");
				}

			} else {
				model.put("titulo", "Connectors");
				flash.addFlashAttribute("error",
						"Connection failed: company, public key, private key or url is wrong, please review it and try again!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("titulo", "Connectors");
			flash.addFlashAttribute("error",
					"Connection failed: company, public key, private key or url is wrong, please review it and try again!");

		} finally {
			return "redirect:/connectors";
		}

	}

	@RequestMapping(value = "/delete/conn/{id}", method = RequestMethod.GET)
	public String deleteConn(@PathVariable(name = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Connector conn = this.connectorService.findOne(id);
			if (conn != null) {
				if (conn.getType().equals("cw")) {
					this.cwcService.delete(conn.getIdCred());
					this.connectorService.delete(conn.getId());
					flash.addFlashAttribute("success", "Connector for ConnectWise deleted succesfully!");
				}
			} else {

				flash.addFlashAttribute("error", "Invalid connector id!");
			}

		} else {
			flash.addFlashAttribute("error", "Invalid connector id!");
		}

		return "redirect:/connectors";
	}

}

package com.orasoft.data.link.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orasoft.data.link.auth.handler.UserPrincipal;
import com.orasoft.data.link.connectors.ConnectCW;
import com.orasoft.data.link.connectors.ConnectIS;
import com.orasoft.data.link.models.entity.ConnectWiseCredentials;
import com.orasoft.data.link.models.entity.Connector;
import com.orasoft.data.link.models.entity.InfusionSoftToken;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.service.IConnectWiseCredentialsService;
import com.orasoft.data.link.models.service.IConnectorService;
import com.orasoft.data.link.models.service.IInfusionSoftTokensService;
import com.orasoft.data.link.models.service.IInfusionTokenService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
public class ConnectorController {

	@Autowired
	private ConnectCW cw;

	@Autowired
	private ConnectIS is;

	@Autowired
	private IConnectWiseCredentialsService cwcService;

	@Autowired
	private IInfusionSoftTokensService infusionTokenService;

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
			if (!cwc.getUrl().endsWith("/")) {
	            cwc.setUrl(cwc.getUrl() + "/");
	        }
			cwc.setUrl(cwc.getUrl().substring(0, 8) + "api-"+ cwc.getUrl().substring(8, cwc.getUrl().length()));
			if (this.cw.testConn(cwc)) {
				ConnectWiseCredentials cwcNew = this.cwcService.save(cwc);
				if (cwcNew != null) {
					Connector con = new Connector();
					con.setPlatform("ConnectWise");
					con.setType("cw");
					con.setUser(user);
					con.setImg("/images/logos/ConnectWiseManage.png");
					con.setIdCred(cwcNew.getId());
					this.connectorService.save(con);
					model.put("titulo", "Connectors");
					flash.addFlashAttribute("success", "Connection to ConnectWise created with success!");
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
				} else if (conn.getType().equals("is")) {
					this.infusionTokenService.delete(conn.getIdCred());
					this.connectorService.delete(conn.getId());
					flash.addFlashAttribute("success", "Connector for InfusionSoft deleted succesfully!");

				}
			} else {

				flash.addFlashAttribute("error", "Invalid connector id!");
			}

		} else {
			flash.addFlashAttribute("error", "Invalid connector id!");
		}

		return "redirect:/connectors";
	}

	@GetMapping(value = "/test/conn/{id}", produces = { "application/json" })
	public @ResponseBody boolean testConn(@PathVariable(name = "id") Long id) {
		System.out.println("id: " + id);
		Connector conn = this.connectorService.findOne(id);
		boolean flag = false;
		if (conn != null) {
			if (conn.getType().equals("cw")) {
				ConnectWiseCredentials cwc = this.cwcService.findOne(conn.getIdCred());
				if (cwc != null) {
					flag = this.cw.testConn(cwc);
					this.connectorService.save(conn);
				}
			} else if (conn.getType().equals("is")) {
				InfusionSoftToken ist = this.infusionTokenService.findOne(conn.getIdCred());
				if (ist != null) {
					flag = this.is.refreshTokens(conn, true);
					this.connectorService.save(conn);
				}
			}
		}

		return flag;

	}

	@GetMapping(value = "/infusionsoft/redirect")
	public String infusionSoftRedirect(HttpServletRequest request, Authentication auth, RedirectAttributes flash) {

		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();

		Long count = this.connectorService.countByTypeAndUser("is", user.getId());
		if (count == 0) {
			String redirectUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/infusionsoft/auth";
			System.out.println("URI: " + redirectUrl);
			return "redirect:https://signin.infusionsoft.com/app/oauth/authorize?client_id=ru4xs4ezqrvag27jw78qrezc&redirect_uri="
					+ redirectUrl + "&response_type=code&scope=full";
		} else {
			flash.addFlashAttribute("error", "You already have a connection with InfusionSoft!");
			return "redirect:/connectors";
		}

	}

	// @PathVariable(name="code") String code,
	@GetMapping(value = "/infusionsoft/auth")
	public String infusionSoftAuth(HttpServletRequest request, Authentication auth, RedirectAttributes flash) {
		String redirectUri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/infusionsoft/auth";
		String code = request.getParameter("code");
		System.out.println("Code: " + code);
		System.out.println("URI auth: " + redirectUri);
		if (auth == null) {
			return "login";
		}
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();
		System.out.println("User: " + user.getEmail());

		Long count = this.connectorService.countByTypeAndUser("is", user.getId());
		if (count == 0) {
			if (code != null) {
				Long idToken = this.is.generateNewTokens(user, code, redirectUri);
				if (idToken != 0) {
					Connector conn = new Connector();
					conn.setUser(user);
					conn.setIdCred(idToken);
					conn.setPlatform("InfusionSoft");
					conn.setType("is");
					conn.setImg("/images/logos/InfusionSoftLogo.png");
					this.connectorService.save(conn);
					flash.addFlashAttribute("success", "Connection to InfusionSoft created with success!");
				} else {
					flash.addFlashAttribute("error", "Connection failed, try again later!");
				}
			} else {
				flash.addFlashAttribute("error", "Parameter code is missing!");
			}
		} else {
			flash.addFlashAttribute("error", "You already have a connection with InfusionSoft!");

		}

		return "redirect:/connectors";
	}

}

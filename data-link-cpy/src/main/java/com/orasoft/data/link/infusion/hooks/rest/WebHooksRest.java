package com.orasoft.data.link.infusion.hooks.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orasoft.data.link.models.entity.Errors;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.service.IErrorsService;
import com.orasoft.data.link.models.service.IUserService;

@RestController
@RequestMapping("/api")
public class WebHooksRest {

	@Autowired
	private IErrorsService errorsService;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/hooks/companies", method = RequestMethod.POST, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public void receiveHook(@RequestBody String string,HttpServletResponse response, HttpServletRequest request) {
		System.out.println("Body: " + string);
		String code = request.getParameter("code");
		System.out.println("Code: " + code);
		Errors error = new Errors();
		error.setMyClass("CallbackRestApi");
		error.setDescrip("method:cacllbackCompany()");
		if (code != null) {
			User user = this.userService.findUserByEncoder(code);
			if (user != null) {
				System.out.println("User: " + user.getEmail());
				error.setBody(string);
				error.setIdUser(user.getId());
			}else {
				error.setBody("No user: " + code + " " + string);
				error.setIdUser(null);
			}
			
			
		}else {
		error.setBody("No code " + string);
		error.setIdUser(null);
		}
		this.errorsService.save(error);
		response .addHeader("X-Hook-Secret", request.getHeader("X-Hook-Secret"));
	}
	
}

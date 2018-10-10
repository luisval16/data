package com.orasoft.data.link.models.service.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.service.IErrorsService;

@Service
public class HttpInfusionCrudService {
	
	@Autowired
	private IErrorsService errorService;
	
	

}

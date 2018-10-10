package com.orasoft.data.link.connectors;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.entity.CWCallback;
import com.orasoft.data.link.models.entity.ConnectWiseCredentials;
import com.orasoft.data.link.models.entity.Errors;
import com.orasoft.data.link.models.service.ICWCallbackService;
import com.orasoft.data.link.models.service.IConnectWiseCredentialsService;
import com.orasoft.data.link.models.service.IConnectorService;
import com.orasoft.data.link.models.service.IErrorsService;
import com.orasoft.data.link.models.service.http.HttpCrudService;

@Service
public class ConnectCW {
	
	@Autowired
	private IErrorsService errorService;
	
	@Autowired
	private HttpCrudService cwService;
	
	@Autowired
	private IConnectWiseCredentialsService cwcService;
	
	@Autowired
	private ICWCallbackService callbackService;
	

	private String getVersion(ConnectWiseCredentials cwc) {
		String codebase = "";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(cwc.getUrl() + "/login/companyinfo/connectwise");
			request.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(request);
			System.out.println(response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			String jsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			
			JSONObject myResp = new JSONObject(jsonResp);
			codebase = myResp.getString("Codebase");
			
			
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("ConnectCW");
			error.setDescrip("method:GetVersion()");
			error.setIdUser((Long)cwc.getUser().getId());
			this.errorService.save(error);
			e.printStackTrace();
			
			// TODO: handle exception
		}finally {
			return codebase;
		}
		
		
		
	}
	
	
	private String getAuth(ConnectWiseCredentials cwc) {
		String auth = "Basic ";
        String publicKey = cwc.getPublicKey();
        String privateKey = cwc.getSecretKey();
        String cia = cwc.getCompany();
        String toCode = cia + "+" + publicKey + ":" + privateKey;
        try {
            byte[] encodedBytes = Base64.getEncoder().encode(toCode.getBytes());
            //System.out.println("encodedBytes " + new String(encodedBytes));
            auth += new String(encodedBytes);
            //byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
            //System.out.println("decodedBytes " + new String(decodedBytes));
        } catch (Exception e) {
        	Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("ConnectCW");
			error.setDescrip("method:GetAuth()");
			error.setIdUser((Long)cwc.getUser().getId());
			this.errorService.save(error);
            e.printStackTrace();
        }finally {
        	return auth;
		}

        
	}
	
	public boolean testConn(ConnectWiseCredentials cwc) {
		String restURL = "apis/3.0/company/companies";
		String urlStr = cwc.getUrl() + this.getVersion(cwc) + restURL;
		boolean flag = false;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(urlStr);
			request.setHeader("Authorization", this.getAuth(cwc));
			request.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(request);
			System.out.println(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 401) {
				return false;
			}
			HttpEntity entity = response.getEntity();
			String jsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			
			JSONArray myResp = new JSONArray(jsonResp);
			if (myResp != null && myResp.length() > 0 ) {
				flag = true;
			}
			
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("ConnectCW");
			error.setDescrip("method:TestConn()");
			error.setIdUser((Long)cwc.getUser().getId());
			this.errorService.save(error);
            e.printStackTrace();
		}finally {
			return flag;
		}
		
		
		
	}
	
	public boolean createCallback(CWCallback cwCallback) {
		ConnectWiseCredentials cwc = this.cwcService.findOne(cwCallback.getConnector().getIdCred());
		boolean flag = false;
		try {
			JSONObject callback  =  new JSONObject();
			callback.put("url", cwCallback.getUrl());
			callback.put("objectId", cwCallback.getObjectId());
			callback.put("type",cwCallback.getType());
			callback.put("level", cwCallback.getLevel());
			String response = this.cwService.create(callback.toString(), this.getAuth(cwc), cwc.getUrl() + this.getVersion(cwc) + "apis/3.0/system/callbacks",cwc.getUser().getId());
			callback = new JSONObject(response);
			if (callback.has("message")) {
				Errors error = new Errors();
				error.setBody(callback.toString());
				error.setMyClass("ConnectCW");
				error.setDescrip("method:createCallback()");
				error.setIdUser((Long)cwc.getUser().getId());
				this.errorService.save(error);
			}else {
				cwCallback.setIdCw(callback.getLong("id"));
				cwCallback.setMemberId(callback.getLong("memberId"));
				cwCallback.setInfo(callback.getJSONObject("_info").toString());
				this.callbackService.save(cwCallback);
				flag = true;
			}
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("ConnectCW");
			error.setDescrip("method:createCallback()");
			error.setIdUser((Long)cwc.getUser().getId());
			this.errorService.save(error);
			e.printStackTrace();
		}finally {
			return flag;	
		}
		
		
		
		
	}
	
	
	public boolean deleteCallback(CWCallback cwCallback) {
		boolean flag = false;
		ConnectWiseCredentials cwc = this.cwcService.findOne(cwCallback.getConnector().getIdCred());
		try {
			this.cwService.delete(this.getAuth(cwc),cwc.getUrl()+this.getVersion(cwc)+"apis/3.0/system/callbacks", String.valueOf(cwCallback.getIdCw()), cwc.getUser().getId());
			this.callbackService.delete(cwCallback.getId());
			flag = true;
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("ConnectCW");
			error.setDescrip("method:createCallback()");
			error.setIdUser((Long)cwc.getUser().getId());
			this.errorService.save(error);
			e.printStackTrace();
		}finally {
			return flag;
		}
	}
	
	
}

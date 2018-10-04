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

import com.orasoft.data.link.models.entity.ConnectWiseCredentials;
import com.orasoft.data.link.models.entity.Errors;
import com.orasoft.data.link.models.service.IErrorsService;

@Service
public class ConnectCW {
	
	@Autowired
	private IErrorsService errorService;

	private String getVersion(String url,ConnectWiseCredentials cwc) {
		String codebase = "";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url + "/login/companyinfo/connectwise");
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
		String urlStr = "https://api-na.myconnectwise.net/" + this.getVersion("https://api-na.myconnectwise.net/", cwc) + restURL;
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
	
}

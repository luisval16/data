package com.orasoft.data.link.connectors;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.entity.Connector;
import com.orasoft.data.link.models.entity.Errors;
import com.orasoft.data.link.models.entity.ISWebhook;
import com.orasoft.data.link.models.entity.InfusionSoftToken;
import com.orasoft.data.link.models.entity.User;
import com.orasoft.data.link.models.service.IErrorsService;
import com.orasoft.data.link.models.service.IISWebhookService;
import com.orasoft.data.link.models.service.IInfusionSoftTokensService;
import com.orasoft.data.link.models.service.IInfusionTokenService;
import com.orasoft.data.link.models.service.http.HttpCrudService;

@Service
public class ConnectIS {

	@Autowired
	private IInfusionSoftTokensService infusionSoftTokenService;

	@Autowired
	private IErrorsService errorService;
	
	@Autowired
	private HttpCrudService crudService;
	
	@Autowired
	private IISWebhookService webhookService;

	private final String clientId = "ru4xs4ezqrvag27jw78qrezc";
	private final String clientSecret = "AnMdcrG9Rv";
	private final String baseUrl = "https://api.infusionsoft.com/crm/rest/v1";
	private final String tokenUrl = "https://api.infusionsoft.com/token";

	public void authenticate() {

		try {

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}
	}

	public Long generateNewTokens(User user, String code, String redirectUrl) {
		Long flag = (long) 0;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(this.tokenUrl);
			ArrayList<NameValuePair> postParameters;
			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("client_id", this.clientId));
			postParameters.add(new BasicNameValuePair("client_secret", this.clientSecret));
			postParameters.add(new BasicNameValuePair("code", code));
			postParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
			postParameters.add(new BasicNameValuePair("redirect_uri", redirectUrl));
			request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
			HttpResponse response = httpClient.execute(request);
			System.out.println("Response " + response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			String JsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			JSONObject myResponse = new JSONObject(JsonResp);
			InfusionSoftToken ist = new InfusionSoftToken();
			ist.setUser(user);
			ist.setActualToken(myResponse.getString("access_token"));
			ist.setRefreshToken(myResponse.getString("refresh_token"));
			InfusionSoftToken istNew = this.infusionSoftTokenService.save(ist);
			flag = istNew.getId();
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\"" + e.toString() + "\",\"message\":\"" + e.getMessage() + "\"}");
			error.setMyClass("ConnectIS");
			error.setDescrip("method:generateNewTokens()");
			error.setIdUser(user.getId());
			this.errorService.save(error);
			e.printStackTrace();
		} finally {
			return flag;
		}

	}

	public boolean refreshTokens(Connector conn, boolean test) {
		boolean flag = false;
		try {
			InfusionSoftToken ist = this.infusionSoftTokenService.findOne(conn.getIdCred());
			if (ist != null) {

				DateTime startTime = new DateTime(ist.getLastUpdated());
				DateTime endTime = DateTime.now();
				Period p = new Period(startTime, endTime);
				long milis = (startTime.getMillis() - endTime.getMillis()) * -1;
				double hours = milis / (1000 * 60 * 60);
				System.err.println("Hours between last token " + hours);
				if (hours >= 24  || test) {
					System.out.println("token debe refrescarse");
					HttpClient httpClient = HttpClientBuilder.create().build();
					HttpPost request = new HttpPost(this.tokenUrl);
					ArrayList<NameValuePair> postParameters;
					postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
					postParameters.add(new BasicNameValuePair("refresh_token", ist.getRefreshToken()));
					request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
					request.setHeader("Authorization", this.getAuth(conn.getUser().getId()));
					HttpResponse response = httpClient.execute(request);
					System.out.println("Response " + response.getStatusLine().getStatusCode());
					HttpEntity entity = response.getEntity();
					String JsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
					JSONObject myResponse = new JSONObject(JsonResp);
					System.out.println("Response " + myResponse);
					ist.setActualToken(myResponse.getString("access_token"));
					ist.setRefreshToken(myResponse.getString("refresh_token"));
					this.infusionSoftTokenService.save(ist);
				} else {
					System.out.println("token no se refresco");
				}

				flag = true;
			} else {
				Errors error = new Errors();
				error.setBody("{\"error\":\"InfusionSoftToken is null\",\"message\":\"\"}");
				error.setMyClass("ConnectIS");
				error.setDescrip("method:refreshTokens()");
				error.setIdUser(conn.getUser().getId());
				this.errorService.save(error);
			}

		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\"" + e.toString() + "\",\"message\":\"" + e.getMessage() + "\"}");
			error.setMyClass("ConnectIS");
			error.setDescrip("method:refreshTokens()");
			error.setIdUser(conn.getUser().getId());
			this.errorService.save(error);
			e.printStackTrace();
		} finally {
			return flag;
		}
	}

	public String getAuth(Long idUser) {
		String auth = "";
		String toCode = this.clientId + ":" + this.clientSecret;
		try {
			byte[] encodedBytes = Base64.getEncoder().encode(toCode.getBytes());
			auth = "Basic " + new String(encodedBytes);
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\"" + e.toString() + "\",\"message\":\"" + e.getMessage() + "\"}");
			error.setMyClass("ConnectIS");
			error.setDescrip("method:getAuth()");
			error.setIdUser(idUser);
			this.errorService.save(error);
			e.printStackTrace();
		}

		return auth;
	}
	
	
	
	public ISWebhook createWebhook(ISWebhook isWebhook) {
		ISWebhook res = null;
		Connector conn = isWebhook.getConnector();
		User user = conn.getUser();
		this.refreshTokens(conn, false);
		InfusionSoftToken ist = this.infusionSoftTokenService.findOne(conn.getIdCred());
		try {
			JSONObject obj = new JSONObject();
			obj.put("eventKey", isWebhook.getEventKey());
			obj.put("hookUrl", isWebhook.getHookUrl());
			String auth = "Bearer " + ist.getActualToken();
			obj = new JSONObject(this.crudService.create(obj.toString(), auth,  this.baseUrl + "/hooks" , user.getId()));
			if (!obj.has("message")) {
				isWebhook.setKey(obj.getString("key"));
				isWebhook.setStatus(obj.getString("status"));
				ISWebhook addedHook = this.webhookService.save(isWebhook);
				obj = new JSONObject(this.crudService.getById(auth, this.baseUrl + "/hooks", String.valueOf(addedHook.getId()), user.getId()));
				String status = obj.has("status")?obj.getString("status"):"too fast";
				addedHook.setStatus(status);
				res = this.webhookService.save(addedHook);
			}
		} catch (Exception e) {
			res = null;
			Errors error = new Errors();
			error.setBody("{\"error\":\"" + e.toString() + "\",\"message\":\"" + e.getMessage() + "\"}");
			error.setMyClass("ConnectIS");
			error.setDescrip("method:createWebhook()");
			error.setIdUser(user.getId());
			this.errorService.save(error);
			e.printStackTrace();
		}finally {
			return res;
		}
		
		
	}
	
	public boolean deleteWebHook(ISWebhook isWebhook) {
		boolean flag = false;
		Connector conn = isWebhook.getConnector();
		User user = conn.getUser();
		this.refreshTokens(conn, false);
		InfusionSoftToken ist = this.infusionSoftTokenService.findOne(conn.getIdCred());
		try {
			String auth = "Bearer " + ist.getActualToken();
			this.crudService.delete(auth,this.baseUrl + "/hooks", isWebhook.getKey(), user.getId());
			this.webhookService.delete(isWebhook.getId());
			flag = true;
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\"" + e.toString() + "\",\"message\":\"" + e.getMessage() + "\"}");
			error.setMyClass("ConnectIS");
			error.setDescrip("method:deleteWebhook()");
			error.setIdUser(user.getId());
			this.errorService.save(error);
			e.printStackTrace();
		}finally {
			return flag;
		}
		
		
	}
	
	/*public boolean isWebHookVerified(ISWebhook isWebhook) {
		boolean flag = false;
		try {
			this.crudService.getById(auth, url, id, idUser)
			
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\"" + e.toString() + "\",\"message\":\"" + e.getMessage() + "\"}");
			error.setMyClass("ConnectIS");
			error.setDescrip("method:isWebHookVerified()");
			error.setIdUser(isWebhook.getConnector().getUser().getId());
			this.errorService.save(error);
			e.printStackTrace();
		}finally {
		return flag;	
		}
		
		
	}*/
	

}

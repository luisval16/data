package com.orasoft.data.link.models.service.http;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.IErrorsDao;
import com.orasoft.data.link.models.entity.Errors;
import com.orasoft.data.link.models.entity.Syncs;
import com.orasoft.data.link.models.service.IErrorsService;
import com.orasoft.data.link.models.service.ISyncsService;
import com.orasoft.data.link.models.service.IUserService;

@Service
public class HttpCrudService {

	@Autowired
	private IErrorsService errorService;
	
	@Autowired
	private ISyncsService syncService;
	
	@Autowired
	private IUserService userService;
	
	public String create(String json, String auth, String url, Long id) {
		String res = "";
		String jsonResp = "";
		
		
		try {
			String payload = json;
			StringEntity entiti = new StringEntity(payload, ContentType.APPLICATION_JSON);

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);
			request.setEntity(entiti);
			request.setHeader("Authorization", auth);
			request.setHeader("Content-type", "application/json");

			HttpResponse response = httpClient.execute(request);
			System.out.println(response.getStatusLine().getStatusCode());

			HttpEntity entity = response.getEntity();
			Header encodingHeader = entity.getContentEncoding();

			// you need to know the encoding to parse correctly
			Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8
					: Charsets.toCharset(encodingHeader.getValue());

			// use org.apache.http.util.EntityUtils to read json as string
			jsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			// System.out.println(jsonResp);
			res = jsonResp;

			// insercion en base
			// this.registerSync(jsonResp, url, "insert");
			this.registrar(jsonResp, url, "Add", id);
		} catch (Exception e) {
			Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("HttpCrudService");
			error.setDescrip("method:create()");
			error.setIdUser(id);
			this.errorService.save(error);
			e.printStackTrace();
		}
		return res;
	}
	
	
    public String edit(String json, String auth, String url, String id, Long idUser) {
        String res = "";
        String jsonResp = "";
        try {
            String payload = json;
            StringEntity entiti = new StringEntity(payload,
                    ContentType.APPLICATION_JSON);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut request = new HttpPut(url + "/" + id);
            request.setEntity(entiti);
            request.setHeader("Authorization", auth);
            request.setHeader("Content-type", "application/json");

            
                HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            Header encodingHeader = entity.getContentEncoding();

            // you need to know the encoding to parse correctly
            Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8
                    : Charsets.toCharset(encodingHeader.getValue());

            // use org.apache.http.util.EntityUtils to read json as string
            jsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            //System.out.println(jsonResp);
            res = jsonResp;
            
            //insercion en base
            //this.registerSync(jsonResp, url, "edit");
            this.registrar(jsonResp, url, "Update", idUser);
            res = jsonResp;
        } catch (Exception e) {
        	Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("HttpCrudService");
			error.setDescrip("method:edit()");
			error.setIdUser(idUser);
			this.errorService.save(error);
            e.printStackTrace();
        }

        return res;
    }
    
    public void delete(String auth, String url, String id, Long idUser) {
        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete request = new HttpDelete(url + "/" + id );
            //+ "?id=" + id)
            request.setHeader("Authorization", auth);
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            //insercion en base
            //this.registerSync("no content", url, "delete");
            this.registrar("{\"content\":\"no content\",\"id_delete\":\""+id+"\"}", url, "Delete", idUser);
        } catch (Exception e) {
        	Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("HttpCrudService");
			error.setDescrip("method:delete()");
			error.setIdUser(idUser);
			this.errorService.save(error);
        	e.printStackTrace();
        }
    }
    
    
    
    public String getById(String auth, String url, String id, Long idUser) {
        String res = "";
        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url + "/" + id);

            request.setHeader("Authorization", auth);
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);
            //System.out.println(response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            Header encodingHeader = entity.getContentEncoding();

            // you need to know the encoding to parse correctly
            Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8
                    : Charsets.toCharset(encodingHeader.getValue());

            // use org.apache.http.util.EntityUtils to read json as string
            String jsonResp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
           // System.out.println(jsonResp);
            res = jsonResp;
        } catch (Exception e) {
        	Errors error = new Errors();
			error.setBody("{\"error\":\""+e.toString()+"\",\"message\":\""+e.getMessage()+"\"}");
			error.setMyClass("HttpCrudService");
			error.setDescrip("method:getById()");
			error.setIdUser(idUser);
			this.errorService.save(error);
        	e.printStackTrace();
        }

        return res;
    }
    
    
    
    
    
    public void registrar(String json, String url, String operation, Long idUser) {
    	Syncs sync = new Syncs();
    	sync.setBody(json);
    	sync.setUrl(url);
    	sync.setOperation(operation);
    	sync.setUser(this.userService.findOne(idUser));
    	if (url.contains("infusionsoft")) {
			sync.setPlatform("InfusionSoft");
		}else if(url.contains("connectwise")){
			sync.setPlatform("ConnectWise");
		}else {
			sync.setPlatform("fail");
		}
		
		if (url.contains("companies")) {
			sync.setEntity("Company");
		}else if(url.contains("contacts")) {
			sync.setEntity("Contact");
		}else if(url.contains("opportunities")) {
			sync.setEntity("Opportunity");
		}else if(url.contains("webhook")) {
			sync.setEntity("Webhook");
		}else if(url.contains("callback")) {
			sync.setEntity("Callback");
		}else {
			sync.setEntity("fail");
		}
    	
    }

}

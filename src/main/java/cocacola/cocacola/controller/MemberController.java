/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cocacola.cocacola.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.br.maymone.projetococacola.model.ClasseJsonCoca;
import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.model.Member;
import org.br.maymone.projetococacola.model.filhos.Video1;
import org.br.maymone.projetococacola.mvc.businnes.CocaColaManager;
import org.br.maymone.projetococacola.mvc.businnes.VideoManager;
import org.br.maymone.projetococacola.mvc.businnes.YouTubeManager;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import cocacola.cocacola.rest.CocaColaRest;
import cocacola.cocacola.service.MemberRegistration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class MemberController {

	@Inject
	private FacesContext facesContext;

	@Inject
	YouTubeManager youtubeManager;

	@Inject
	CocaColaManager cocaManager;

	@Inject
	VideoManager videoManager;

	@Inject
	private Logger log;

	@Inject
	private MemberRegistration memberRegistration;

	@Produces
	@Named
	private Member newMember;

	@Produces
	@Named
	private CocaCola cocaCola;

	@Produces
	@Named
	private Part imagem;

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
		cocaCola = new CocaCola();
		
	}

	public void register() throws Exception {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			CocaCola c = new CocaCola(cal, "asdasdasdasd", "url1", "token2	");

			memberRegistration.register(c);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Registered!", "Registration successful");
			facesContext.addMessage(null, m);

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

	public void teste() throws FileNotFoundException {
		log.info("Testando ");
		
		testarRestCoca();

		/*
		CocaCola coca = new CocaCola();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		CocaCola c = new CocaCola(cal, "asdasdasdasd", "url1", "Paul", null,
				Status.RECEBIDA);
		
		c.setCor(cocaCola.getCor());
		c.setNome(cocaCola.getNome());
		c.setSexo(cocaCola.getSexo());
		System.out.println(cocaCola.toString());
		try {
			 videoManager.gerarVideos(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	public void testarArquivo() {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("nando.txt").getPath());
		log.info(file.getAbsolutePath());

	}

	public void gerarVideoTeste() {

		try {
			Video1 v = new Video1();
			v.gerarVideo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void inserirElemento() {

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		CocaCola c = new CocaCola(cal, "respostas", "url", "token");

		cocaManager.salvarUsuario(c);

	}

	public void testarBuffered(InputStream in) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				// sb.append(line);
				log.info(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public void upload(){
		 
		InputStream inputStream;
		try {
			inputStream = imagem.getInputStream();
			FileOutputStream outputStream = new FileOutputStream("tempIm");  
	          
	        byte[] buffer = new byte[4096];          
	        int bytesRead = 0;  
	        while(true) {                          
	            bytesRead = inputStream.read(buffer);  
	            if(bytesRead > 0) {  
	                outputStream.write(buffer, 0, bytesRead);  
	            }else {  
	                break;  
	            }                         
	        }  
	        outputStream.close();  
	        inputStream.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}          
        
         
         
    }
	
	public void testarRestCoca(){
		
		
		 
		 try {
			 
			
			 
			ClientRequest request = new ClientRequest("http://festivaldomeujeito.com.br/server/index.php/festival/user/format/json");
			 
			 
			ClientResponse<String> response = request.get(String.class);
			
			
			Gson gson = new Gson();
		    JsonParser parser = new JsonParser();
		    JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
		    
		    for(JsonElement obj : jArray ){
		    	
		    	
		    	System.out.println(obj.toString());
		    	
		    	JsonElement jelem = gson.fromJson(obj, JsonElement.class);
		    	JsonObject jobj = jelem.getAsJsonObject();
		    	System.out.println(jobj.get("mon_pergunta_1"));
		    	ClasseJsonCoca coca = gson.fromJson(obj, ClasseJsonCoca.class);
		    	System.out.println(coca.getMonPergunta1());
		    	
		    	
		    	
		    	
		    }
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
		
		
	


}
	
}

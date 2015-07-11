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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.br.maymone.projetococacola.model.Member;
import org.br.maymone.projetococacola.model.filhos.Video1;
import org.br.maymone.projetococacola.mvc.businnes.FacebookManager;
import org.br.maymone.projetococacola.mvc.businnes.YouTubeManager;
import org.br.maymone.projetococacola.util.GeradorPosicoes;

import cocacola.cocacola.service.MemberRegistration;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class MemberController {

	@Inject
	private FacesContext facesContext;
	
	@Inject YouTubeManager youtubeManager;

	@Inject
	private Logger log;

	@Inject
	private MemberRegistration memberRegistration;

	@Produces
	@Named
	private Member newMember;

	@Inject
	FacebookManager facebookManager;

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
	}

	public void register() throws Exception {
		try {
			memberRegistration.register(newMember);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewMember();
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

		log.info("Olar");

		/*InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("timeline/video1");*/
		
		try {
			Video1 v = new Video1("olar", "Marilia boba");
			v.gerarVideo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			GeradorPosicoes g = new GeradorPosicoes(1);
			testarBuffered(g.getUrlArquivoPropriedades());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	

	}

	public void testarBuffered(InputStream in) {
		
		
		
		
		
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				//sb.append(line);
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
		

	}


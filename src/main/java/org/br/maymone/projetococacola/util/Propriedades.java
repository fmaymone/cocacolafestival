package org.br.maymone.projetococacola.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.ejb.Stateless;

@Stateless
public class Propriedades {
	private String login;
	private String host;
	private String password;
	private String[] urlVideosRespostas;
	private Integer numeroCenas;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String[] getUrlVideosRespostas() {
		return urlVideosRespostas;
	}

	public void setUrlVideosRespostas(String[] urlVideosRespostas) {
		this.urlVideosRespostas = urlVideosRespostas;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		
		String s = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties").toString();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		return props;
	}

	public Propriedades() {
		System.out
				.println("************Teste de leitura do arquivo de propriedades************");
		Properties prop;
		try {
			prop = getProp();
			login = prop.getProperty("prop.server.login");
			host = prop.getProperty("prop.server.host");
			password = prop.getProperty("prop.server.password");
			urlVideosRespostas = new String[5];
			// array com todas as respostas
			urlVideosRespostas[0] = prop
					.getProperty("prop.pergunta.1.resposta");
			urlVideosRespostas[1] = prop
					.getProperty("prop.pergunta.2.resposta");
			urlVideosRespostas[2] = prop
					.getProperty("prop.pergunta.3.resposta");
			urlVideosRespostas[3] = prop
					.getProperty("prop.pergunta.4.resposta");
			urlVideosRespostas[4] = prop
					.getProperty("prop.pergunta.5.resposta");

			numeroCenas = new Integer(prop.getProperty("prop.numeroCenas"));

			System.out.println(this.toString());

		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		//

		Propriedades p = new Propriedades();

	}

	public Integer getNumeroCenas() {
		return numeroCenas;
	}

	public void setNumeroCenas(Integer numeroCenas) {
		this.numeroCenas = numeroCenas;
	}

	@Override
	public String toString() {
		return "Propriedades [login=" + login + ", host=" + host
				+ ", password=" + password + ", urlVideosRespostas="
				+ Arrays.toString(urlVideosRespostas) + ", numeroCenas="
				+ numeroCenas + "]";
	}

}

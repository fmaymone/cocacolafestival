package org.br.maymone.projetococaola.mvc.businnes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.br.maymone.projetococacola.util.Propriedades;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.User;

public class FacebookManager {

	@Inject
	Propriedades prop;
	private static String TOKEN;

	private static FacebookClient facebookClient;
	private static User user;
	private static Page page;
	private static BufferedImage img;

	public static FacebookClient getFacebookClient() {
		return facebookClient;
	}

	public static void setFacebookClient(FacebookClient facebookClient) {
		FacebookManager.facebookClient = facebookClient;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		FacebookManager.user = user;
	}

	public static Page getPage() {
		return page;
	}

	public static void setPage(Page page) {
		FacebookManager.page = page;
	}

	public static BufferedImage getImg() {
		return img;
	}

	public static void setImg(BufferedImage img) {
		FacebookManager.img = img;
	}

	public static String getToken() {
		return TOKEN;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Usuario Logadouser.getFirstName()");

	}

	public void setToken(String token) throws IOException {

		
		facebookClient = new DefaultFacebookClient(token) {
			@Override
			protected String getFacebookGraphEndpointUrl() {
				return "https://graph.facebook.com/v2.0";
			}
		};

		user = facebookClient.fetchObject("me", User.class);

		URL url = new URL("https://graph.facebook.com/" + user.getId()
				+ "/picture?type=normal");

		InputStream is = url.openStream();

		img = ImageIO.read(url);

		System.out.println("Usuario " + user.getFirstName());

	}

	public FacebookManager() throws IOException {
		
		try {
			this.TOKEN =prop.getProp().getProperty("prop.token.teste");

			facebookClient = new DefaultFacebookClient(TOKEN) {
				@Override
				protected String getFacebookGraphEndpointUrl() {
					return "https://graph.facebook.com/v2.0";
				}
			};

			user = facebookClient.fetchObject("me", User.class);
			page = facebookClient.fetchObject("cocacola", Page.class);

			URL url;
			url = new URL("https://graph.facebook.com/" + user.getId()
					+ "/picture?type=normal");
			
			InputStream is = url.openStream();

			img = ImageIO.read(url);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// System.out.println("Usuario Logadouser.getFirstName());

	}

	// metodo para testar com meu token
	public FacebookManager(String token, boolean teste) throws IOException {

		facebookClient = new DefaultFacebookClient(token) {
			@Override
			protected String getFacebookGraphEndpointUrl() {
				return "https://graph.facebook.com/v2.0";
			}
		};

		user = facebookClient.fetchObject("me", User.class);
		page = facebookClient.fetchObject("cocacola", Page.class);

		URL url = new URL("https://graph.facebook.com/" + user.getId()
				+ "/picture?type=normal");

		InputStream is = url.openStream();

		img = ImageIO.read(url);

		// System.out.println("Usuario Logadouser.getFirstName());

	}

	@Override
	public String toString() {
		return "FacebookManager [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}

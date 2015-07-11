package org.br.maymone.projetococacola.model;

import java.awt.image.BufferedImage;
import java.util.List;

import com.restfb.types.User;

public class FacebookUser {
	
	private static BufferedImage imagemUsuario;
	private static User user;
	private static List<String> friends;
	
	
	
	
	public static BufferedImage getImagemUsuario() {
		return imagemUsuario;
	}
	public static void setImagemUsuario(BufferedImage imagemUsuario) {
		FacebookUser.imagemUsuario = imagemUsuario;
	}
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		FacebookUser.user = user;
	}
	public static List<String> getFriends() {
		return friends;
	}
	public static void setFriends(List<String> friends) {
		FacebookUser.friends = friends;
	}
	@Override
	public String toString() {
		return "FacebookUser [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}

package org.br.maymone.projetococacola.model;

import org.br.maymone.projetococacola.util.GeradorPosicoes;

import com.xuggle.mediatool.IMediaReader;

public class VideoGerado {

	// se o video eh o 1,2,3 da lista
	private static Integer idVideo;
	private static IMediaReader video;
	private static GeradorPosicoes gPosicoes;
	private static String idUsuario;
	public static String getIdUsuario() {
		return idUsuario;
	}

	public static void setIdUsuario(String idUsuario) {
		VideoGerado.idUsuario = idUsuario;
	}

	public void gerarVideo() throws Exception{}

	public static Integer getIdVideo() {
		return idVideo;
	}

	public static void setIdVideo(Integer idVideo) {
		VideoGerado.idVideo = idVideo;
	}

	public static IMediaReader getVideo() {
		return video;
	}

	public static void setVideo(IMediaReader video) {
		VideoGerado.video = video;
	}

	public static GeradorPosicoes getgPosicoes() {
		return gPosicoes;
	}

	public static void setgPosicoes(GeradorPosicoes gPosicoes) {
		VideoGerado.gPosicoes = gPosicoes;
	}

	

}


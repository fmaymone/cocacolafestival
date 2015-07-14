package org.br.maymone.projetococacola.model;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.br.maymone.projetococacola.model.filhos.Video1;
import org.br.maymone.projetococacola.model.filhos.Video2;
import org.br.maymone.projetococacola.util.GeradorPosicoes;

import com.xuggle.mediatool.IMediaReader;

@Stateless
public class VideoGerado {

	// se o video eh o 1,2,3 da lista
	private static Integer idVideo;
	private static IMediaReader video;
	private static GeradorPosicoes gPosicoes;
	private static String idUsuario;
	


	public static CocaCola getCocaCola() {
		return cocaCola;
	}

	public static void setCocaCola(CocaCola cocaCola) {
		VideoGerado.cocaCola = cocaCola;
	}

	@Inject
	protected Logger log;
	// um video pertence a um objeto cocaCola
	private static CocaCola cocaCola;

	public static String getIdUsuario() {
		return idUsuario;
	}

	public VideoGerado(){
		
		
	}
	public static void setIdUsuario(String idUsuario) {
		VideoGerado.idUsuario = idUsuario;
	}

	public void gerarVideo() throws Exception {
	}

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

	public void setgPosicoes(GeradorPosicoes gPosicoes) {
		this.gPosicoes = gPosicoes;
	}

	public void gerar(int i) {

		switch (i) {
		case 1:
			//log.info("Gerando video 1");

			try {
				Video1 video;
				video = new Video1(cocaCola.getNome(),
						cocaCola.getNome());
				video.gerar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 2:


			try {
				Video2 video2;
				video2 = new Video2();
				video2.gerar();
			} catch (Exception e) {

				e.printStackTrace();
			}

			break;
		case 3:
			System.out.println("Terça-feira");
			break;
		case 4:
			System.out.println("Quarta-feira");
			break;
		case 5:
			System.out.println("Quinta-feira");
			break;
		case 6:
			System.out.println("Sexta-feira");
			break;

		}
	}

	public void gerar() throws Exception {
		// TODO Auto-generated method stub
		
	}

}

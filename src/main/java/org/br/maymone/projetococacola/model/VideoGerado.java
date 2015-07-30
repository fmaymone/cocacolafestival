package org.br.maymone.projetococacola.model;

import java.io.File;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.br.maymone.projetococacola.model.filhos.Video1;
import org.br.maymone.projetococacola.model.filhos.Video2;
import org.br.maymone.projetococacola.model.filhos.Video3;
import org.br.maymone.projetococacola.model.filhos.Video4;
import org.br.maymone.projetococacola.model.filhos.Video5;
import org.br.maymone.projetococacola.model.filhos.Video6;
import org.br.maymone.projetococacola.util.GeradorPosicoes;

import com.xuggle.mediatool.IMediaReader;

public class VideoGerado {

	// se o video eh o 1,2,3 da lista
	private static Integer idVideo;
	private static IMediaReader video;
	private static GeradorPosicoes gPosicoes;
	private static String idUsuario;
	private static String urlBaseVideos;

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

	public VideoGerado() {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("videos/").getPath());
		this.urlBaseVideos = file.getAbsolutePath();

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
			// log.info("Gerando video 1");

			try {
				Video1 video;
				video = new Video1();
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
			// log.info("Gerando video 1");

			try {
				Video3 video;
				video = new Video3(cocaCola);
				video.gerar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 4:
			try {
				Video4 video;
				video = new Video4(cocaCola);
				video.gerar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			try {
				Video5 video;
				video = new Video5(cocaCola);
				video.gerar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		case 6:
			try {
				Video6 video;
				video = new Video6(cocaCola);
				video.gerar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void gerar() throws Exception {
		// TODO Auto-generated method stub

	}
	public String getUrlCena1() {
		String retorno = "";
		// cena + sexo + camisa
		//Cena 2 - Escolha camisa
		ClasseJsonCoca c = this.getCocaCola().getJsonCoca();

		retorno += urlBaseVideos +"//";
		retorno += "cena1" + "//";
		retorno +=  "video1.mov";
			
		File file = new File(retorno);
		System.out.println(retorno);
		System.out.println(file.exists());

		return retorno;
	}
	public String getUrlCena2() {
		String retorno = "";
		// cena + sexo + camisa
		//Cena 2 - Escolha camisa
		ClasseJsonCoca c = this.getCocaCola().getJsonCoca();

		retorno += urlBaseVideos +"//";
		retorno += "cena2" + "//";
		retorno += c.getUsuGender() + "//";
		retorno += c.getMonPergunta3() + "//";
		retorno +=  "video1.mov";
		
		File file = new File(retorno);
		System.out.println(retorno);
		System.out.println(file.exists());

		return retorno;
	}

	public String getUrlCena3() {
		String retorno = "";
		// Van - Feminino - Camisa 1
		ClasseJsonCoca c = this.getCocaCola().getJsonCoca();

		retorno += urlBaseVideos +"//";
		retorno += "cena3" + "//";
		retorno += c.getMonPergunta1() + "//";
		retorno += c.getUsuGender() + "//";
		retorno += c.getMonPergunta3() + "//";
		retorno += "video1.mov";
		
		File file = new File(retorno);
		System.out.println(retorno);
		System.out.println(file.exists());

		
		return retorno;
	}

	public String getUrlCena4() {
		String retorno = "";
		retorno += urlBaseVideos +"//";
		ClasseJsonCoca c = this.getCocaCola().getJsonCoca();
		// Feminino - Camisa 1
		retorno += "cena4" + "//";
		retorno += c.getUsuGender() + "//";
		retorno += c.getMonPergunta3() + "//";
		retorno += "video1.mov";
		
		File file = new File(retorno);
		System.out.println(retorno);
		System.out.println(file.exists());
		System.out.println(retorno);
		return retorno;
	}

	public String getUrlCena5() {
		String retorno = "";
		// de onde assistir
		// Palco - Feminino - Camisa 1
		ClasseJsonCoca c = this.getCocaCola().getJsonCoca();
		
		retorno += urlBaseVideos +"//";
		retorno += "cena5" + "//";
		retorno += c.getMonAmbiente() + "//";
		retorno += c.getUsuGender() + "//";
		retorno += c.getMonPergunta3() + "//";
		retorno += "video1.mov";
		
		File file = new File(retorno);
		System.out.println(retorno);
		System.out.println(file.exists());
		
	
		return retorno;
	}

	public String getUrlCena6() {
		String retorno = "";
		ClasseJsonCoca c = this.getCocaCola().getJsonCoca();
		//Cena 6 - presente do festival
		//Pôster - Feminino - Camisa 1
		retorno += urlBaseVideos +"//";
		retorno += "cena6" + "//";
		retorno += c.getMonPergunta5() + "//";
		retorno += c.getUsuGender() + "//";
		retorno += c.getMonPergunta3() + "//";
		retorno +=  "video1.mov";
		
		File file = new File(retorno);
		System.out.println(retorno);
		System.out.println(file.exists());
		
		return retorno;
	}

}

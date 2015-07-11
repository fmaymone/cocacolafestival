package org.br.maymone.projetococacola.util;



import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GeradorPosicoes {

	private static Integer idVideo;
	
	
	
	private static InputStream urlArquivoPropriedades;
	private static ArrayList<DadosImagem> dadosImagem;

	public GeradorPosicoes(Integer idVideo) throws Exception {

		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("timeline/video"+ idVideo);
		
		urlArquivoPropriedades = in;
		dadosImagem = new ArrayList<DadosImagem>();
		
		//logger.debug("Olar");
		this.read();

	}

	public static InputStream getUrlArquivoPropriedades() {
		return urlArquivoPropriedades;
	}

	public static void setUrlArquivoPropriedades(InputStream urlArquivoPropriedades) {
		GeradorPosicoes.urlArquivoPropriedades = urlArquivoPropriedades;
	}

	public void read() throws Exception {
		
		
		
		InputStreamReader reader = new InputStreamReader(urlArquivoPropriedades);

		

		BufferedReader br = new BufferedReader(reader);
		String s = "";
		
		while (s != null) {

			s = br.readLine();
			if (s != null) {

				DadosImagem temp = new DadosImagem();
				String[] split = s.split(";");
				temp.setTempoInicial(new Long(split[0]));
				temp.setTempoFinal(new Long(split[1]));
				temp.setX(new Integer(split[2]));
				temp.setY(new Integer(split[3]));
				
				
				dadosImagem.add(temp);
				
				
			}

		}
	

	}

	private static DadosImagem parse(BufferedReader br) throws Exception {

		DadosImagem retorno = new DadosImagem();

		String nString = br.readLine();
		if (nString == null) {
			throw new EOFException();
		}

		
		String[] split = nString.split(";");
		Integer x = new Integer(split[0]);
		Integer y = new Integer(split[1]);

		

		return retorno;
	}

	public static Integer getIdVideo() {
		return idVideo;
	}

	public static void setIdVideo(Integer idVideo) {
		GeradorPosicoes.idVideo = idVideo;
	}

	
	


	public static ArrayList<DadosImagem> getDadosImagem() {
		return dadosImagem;
	}

	public static void setDadosImagem(ArrayList<DadosImagem> dadosImagem) {
		GeradorPosicoes.dadosImagem = dadosImagem;
	}

}

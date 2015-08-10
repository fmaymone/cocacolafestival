package org.br.maymone.projetococacola.model.filhos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;

import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.model.CocaColaException;
import org.br.maymone.projetococacola.model.VideoGerado;
import org.br.maymone.projetococacola.util.DadosImagem;
import org.br.maymone.projetococacola.util.GeradorPosicoes;
import org.br.maymone.projetococacola.util.Propriedades;
import org.br.maymone.projetococacola.util.TextToImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class Video1 extends VideoGerado {

	private String nomeGerar;
	private String idVideoTemp;

	public Video1() throws Exception {
		
		super.setIdVideo(1);
		this.setgPosicoes(new GeradorPosicoes(1));
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Video1 v = new Video1();
			v.gerar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void gerar() throws Exception, CocaColaException {

		Propriedades prop = new Propriedades();
		// pegar as posicoes
		GeradorPosicoes g = getgPosicoes();
		// abrir o video original
		// create a media reader
		String nomeUsuario = super.getCocaCola().getJsonCoca().getUsuFirstName().toString();
		
		if (nomeUsuario == null){
			
			throw new CocaColaException("Nome de Usuario Invalido" ,"nullpointer");
		}
		String idVideoTemp = "1";
		String idUsuarioPasta = super.getCocaCola().getJsonCoca().getUsuFaceId().toString();
		
		//IMediaReader mediaReader = ToolFactory.makeReader(prop
			//	.getUrlVideosRespostas()[0]);
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(super.getUrlCena1());
		
		if(file == null){
			throw new CocaColaException("Problema na URL da Cena1" ,"nullpointer");
		}
		
		
		IMediaReader mediaReader = ToolFactory.makeReader(file.getAbsolutePath());

		
		// configure it to generate BufferImages
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		
		String nomeGerar = super.getCocaCola().getNome();

				
		File folder = new File(classLoader.getResource("temp/").getPath());		
		
		
		
		
		boolean success = (new File(folder.getAbsolutePath() + "/" + idUsuarioPasta)).mkdirs();
		if (!success) {
			System.out.println("Erro ao criar folder");
		}
		
		System.out.println("Pasta temporária: "+ folder.getAbsolutePath() + "/" + idUsuarioPasta);
		
		String urlTemp = folder.getAbsolutePath() +  "\\"+ idUsuarioPasta + "/"+"video1.mov"; 

		IMediaWriter mediaWriter = ToolFactory.makeWriter(urlTemp, mediaReader);

		String url = "http://festivaldomeujeito.com.br/site/uploads/festival/" + super.getCocaCola().getJsonCoca().getUsuFaceId()+"/avatar_crop.png";
		System.out.println(url);
		super.getCocaCola().setImagem(url);
		
		
		System.out.println("Usuario a ser impresso:" + nomeUsuario);

		IMediaTool imageMediaTool = new StaticImageMediaTool(nomeUsuario, super.getCocaCola());
		// Adicionou um listener com a imagem estatica
		mediaReader.addListener(imageMediaTool);
		mediaReader.addListener(mediaWriter);

		// Adiciona o listener do nome

		// Adiciona o listener do nome

		while (mediaReader.readPacket() == null)
			;

		
		System.out.println(mediaReader.getUrl());
		mediaReader.close();
		
		
		System.out.println("------------------------Video 1 GERADO-------------- ");

	}

	private static class StaticImageMediaTool extends MediaToolAdapter {

		private BufferedImage logoImage;

		private GeradorPosicoes gp;

		public int indice = 0;

		public StaticImageMediaTool(String texto, CocaCola coca) throws Exception {

			System.out.println("Entrou no laço de pegar a imagem");

			gp = getgPosicoes();

			TextToImage ti = new TextToImage();

			String sexo = coca.getJsonCoca().getUsuGender().toString();
			String preposicao = "do";
				
			if(sexo == "female"){
				preposicao = "da";
				
			}
				
				
			
			logoImage = ti.gerarImagemTexto("Festival do "+ texto);

			System.out.println("Pegou Imagem corretamente de texto");

		}

		@Override
		public void onVideoPicture(IVideoPictureEvent event) {

			BufferedImage image = event.getImage();

			gp.getDadosImagem();

			// get the graphics for the image
			Graphics2D g = image.createGraphics();
			System.out.println(event.getTimeStamp());

			Long now = event.getTimeStamp();

			Collections.sort(gp.getDadosImagem());
			List<DadosImagem> dados = gp.getDadosImagem();

			DadosImagem dadosTemp = new DadosImagem();

			System.out.println("------------------------Gerando Video 1-------------- ");
			

			int size = dados.size();
			
			
			
			int xAjuste = 25;
			int yAjuste = -160;
			if(indice < size){
			dadosTemp = dados.get(indice);
			
		
			System.out.println("tempo do video:"+ now);
			System.out.println("tempo do gerad:"+ dadosTemp.getTempoInicial());
			
					if((dadosTemp.getX() <= 1280) &&(dadosTemp.getY() <= 720) ){
			
						System.out.println("Riscou");
						g.drawImage(logoImage, dadosTemp.getX() + xAjuste, dadosTemp.getY() + yAjuste,
					
							null);
						}
					System.out.println("posicao gerada" + dadosTemp.getX() +","+ dadosTemp.getY());
					System.out.println("---------");
					
					indice++;

			}
			

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}
	

}

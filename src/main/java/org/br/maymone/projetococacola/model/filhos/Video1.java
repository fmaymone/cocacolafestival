package org.br.maymone.projetococacola.model.filhos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;

import org.br.maymone.projetococacola.model.VideoGerado;
import org.br.maymone.projetococacola.util.DadosImagem;
import org.br.maymone.projetococacola.util.GeradorPosicoes;
import org.br.maymone.projetococacola.util.Propriedades;

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
	public void gerar() throws Exception {

		Propriedades prop = new Propriedades();
		// pegar as posicoes
		GeradorPosicoes g = getgPosicoes();
		// abrir o video original
		// create a media reader
		
		String idVideoTemp = "1";
		
		
		//IMediaReader mediaReader = ToolFactory.makeReader(prop
			//	.getUrlVideosRespostas()[0]);
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(prop.getProp().getProperty("prop.pergunta.1.resposta")).getPath());
		
		
		IMediaReader mediaReader = ToolFactory.makeReader(file.getAbsolutePath());

		
		// configure it to generate BufferImages
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		
		String nomeGerar = super.getCocaCola().getNome();

				
		File folder = new File(classLoader.getResource("temp/").getPath());		
		
		
		String id = "Olar";
		
		boolean success = (new File(folder.getAbsolutePath() + "/" + super.getCocaCola().getNome())).mkdirs();
		if (!success) {
			System.out.println("Erro ao criar folder");
		}
		
		System.out.println("Pasta temporária: "+ folder.getAbsolutePath() + "/" + super.getCocaCola().getNome());
		
		String urlTemp = folder.getAbsolutePath() +  "\\"+ super.getCocaCola().getNome() + "/"+"video1.mov"; 

		IMediaWriter mediaWriter = ToolFactory.makeWriter(urlTemp, mediaReader);

		
		super.getCocaCola().setImagem("http://festivaldomeujeito.com.br/site/uploads/festival/10205026925565317/avatar_crop.jpg");
		

		IMediaTool imageMediaTool = new StaticImageMediaTool(super.getCocaCola().getImagem());
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

		public StaticImageMediaTool(BufferedImage i) throws Exception {

			System.out.println("Entrou no laço de pegar a imagem");

			gp = getgPosicoes();
		

			logoImage = i;

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
			
			int xAjuste = -100;
			int yAjuste = -150;
			if(indice < size){
			dadosTemp = dados.get(indice);
			
			Graphics2D g2d=(Graphics2D)g;       // Create a Java2D version of g.
			g2d.translate(170, 0);              // Translate the center of our coordinates.
			g2d.rotate(1);                      // Rotate the image by 1 radian.
			
			
					g2d.drawImage(logoImage, dadosTemp.getX() + xAjuste, dadosTemp.getY() + yAjuste,
							null);
			
					
					indice++;

			}else{
				g.drawImage(logoImage, 0, 0,
						null);
				
			}
			

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}
	

}

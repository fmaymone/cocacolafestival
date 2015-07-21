package org.br.maymone.projetococacola.model.filhos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;

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
		URL in = Thread.currentThread().getContextClassLoader().getResource(prop.getProp().getProperty("prop.pergunta.2.resposta"));
		
		IMediaReader mediaReader = ToolFactory.makeReader(file.getAbsolutePath());

		
		// configure it to generate BufferImages
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		//System.out.println()
		String folderBase = prop.getProp().getProperty("prop.video.pasta.temp");
		
		
		File folder = new File(classLoader.getResource("temp/").getPath());		
		
		String id = super.getCocaCola().getNome();
		
		
		boolean success = (new File(folder.getAbsolutePath() + "/" + super.getCocaCola().getNome())).mkdirs();
		if (!success) {
			System.out.println("Erro ao criar folder");
		}
		
		System.out.println("Pasta temporária: "+ folder.getAbsolutePath() + "/" + super.getCocaCola().getNome());
		
		String urlTemp = folder.getAbsolutePath() +  "/"+ super.getCocaCola().getNome() + "/"+"video1.mp4"; 

		IMediaWriter mediaWriter = ToolFactory.makeWriter(urlTemp, mediaReader);

		

		IMediaTool imageMediaTool = new StaticImageMediaTool(super.getCocaCola().getNome());
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

		public StaticImageMediaTool(String texto) throws Exception {

			System.out.println("Entrou no laço de pegar a imagem");

			gp = getgPosicoes();

			TextToImage ti = new TextToImage();

			logoImage = ti.gerarImagemTexto(texto);

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
			if (indice < dados.size()) {

				dadosTemp = dados.get(indice);
			} else {

				dadosTemp = new DadosImagem(new Long(0), new Long(0),
						new Integer(0), new Integer(0));
			}

			// se o tempo for maior que o inicial, verifica se foi maior que o
			// final. Se for, avança
			// na lista. Senao, está no intervalo, ou seja, desenhará.
			if (dadosTemp.getTempoInicial().longValue()  <= now) {

				/*System.out.println("Tempo do Video: " + now + " Elemento: "
						+ dadosTemp.toString());*/
				if (dadosTemp.getTempoFinal().intValue() >= now) {
					System.out.println("Desenhou, pois está no tempo " + now
							+ dadosTemp.toString());
					g.drawImage(logoImage, dadosTemp.getX(), dadosTemp.getY(),
							null);
				} else {
					// passou do final, entao anda a lista
				//	
					indice++;

				}
			}

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}

}

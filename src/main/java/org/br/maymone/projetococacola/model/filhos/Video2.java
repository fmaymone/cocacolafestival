package org.br.maymone.projetococacola.model.filhos;

import java.awt.AlphaComposite;
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

public class Video2 extends VideoGerado {

	private String nomeGerar;
	private String idVideoTemp;

	public Video2() throws Exception {
		super.setIdVideo(2);
		super.setgPosicoes(new GeradorPosicoes(2));

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

		String idVideoTemp = "2";
		String idUsuarioPasta = super.getCocaCola().getJsonCoca()
				.getUsuFaceId().toString();

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(super.getUrlCena2());

		IMediaReader mediaReader = ToolFactory.makeReader(file
				.getAbsolutePath());

		// configure it to generate BufferImages
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		// System.out.println()
		String folderBase = prop.getProp().getProperty("prop.video.pasta.temp");

		File folder = new File(classLoader.getResource("temp/").getPath());

		boolean success = (new File(folder.getAbsolutePath() + "/"
				+ idUsuarioPasta)).mkdirs();
		if (!success) {
			System.out.println("Erro ao criar folder");
		}

		System.out.println("Pasta temporária: " + folder.getAbsolutePath()
				+ "/" + idUsuarioPasta);

		String urlTemp = folder.getAbsolutePath() + "\\" + idUsuarioPasta + "/"
				+ "video2.mov";

		IMediaWriter mediaWriter = ToolFactory.makeWriter(urlTemp, mediaReader);

		String url = "http://festivaldomeujeito.com.br/site/uploads/festival/"
				+ super.getCocaCola().getJsonCoca().getUsuFaceId()
				+ "/avatar_crop.png";
		System.out.println(url);
		super.getCocaCola().setImagem(url);

		IMediaTool imageMediaTool = new StaticImageMediaTool(super
				.getCocaCola().getImagem());
		
		mediaReader
		.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		// Adicionou um listener com a imagem estatica
		mediaReader.addListener(imageMediaTool);
		mediaReader.addListener(mediaWriter);

		// Adiciona o listener do nome

		// Adiciona o listener do nome

		// Adiciona o listener do nome
		System.out
				.println("------------------------Gerando Video 2 -------------- ");
		while (mediaReader.readPacket() == null)
			;

		System.out
				.println("------------------------Video 2 GERADO-------------- ");

	}

	private static class StaticImageMediaTool extends MediaToolAdapter {

		private BufferedImage logoImage;

		private GeradorPosicoes gp;

		public int indice = 0;

		public StaticImageMediaTool(BufferedImage im) throws Exception {

			System.out.println("Entrou no laço de pegar a imagem");

			gp = getgPosicoes();

			logoImage = im;

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

			System.out
					.println("------------------------Gerando Video 2-------------- ");

			int size = dados.size();

			int xAjuste = -34;
			int yAjuste = -38;
			if (indice < size) {
				dadosTemp = dados.get(indice);

				System.out.println("tempo do video:" + now);
				System.out.println("tempo do gerad:"
						+ dadosTemp.getTempoInicial());

				if ((dadosTemp.getX() <= 1280) && (dadosTemp.getY() <= 720)) {

					System.out.println("Riscou");

					if (now < 5638967) {

						g.drawImage(logoImage, dadosTemp.getX() + xAjuste,
								dadosTemp.getY() + yAjuste, 78, 78, null);
					}else{
						
						 xAjuste = -25;
						 yAjuste = -28;
						g.drawImage(logoImage, dadosTemp.getX() + xAjuste,
								dadosTemp.getY() + yAjuste, 50, 50, null);
						
					}
				}
				System.out.println("posicao gerada" + dadosTemp.getX() + ","
						+ dadosTemp.getY());
				System.out.println("---------");

				indice++;

			}

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}

}

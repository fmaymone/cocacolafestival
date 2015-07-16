package org.br.maymone.projetococacola.mvc.businnes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.br.maymone.projetococacola.media.Concatenate;
import org.br.maymone.projetococacola.model.CocaCola;
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
import com.xuggle.mediatool.demos.ConcatenateAudioAndVideo;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;


@Stateless
public class VideoManager {

	
	private static IMediaWriter mediaWriter;
	@Inject
	private static Propriedades prop;
	
	private static
	Integer numCenas;

	public FacebookManager getFacebookManager() {
		return facebookManager;
	}

	public void setFacebookManager(FacebookManager facebookManager) {
		this.facebookManager = facebookManager;
	}

	private static CocaCola cocaCola;
	public static CocaCola getCocaCola() {
		return cocaCola;
	}

	public static void setCocaCola(CocaCola cocaCola) {
		VideoManager.cocaCola = cocaCola;
	}

	private FacebookManager facebookManager;

	public void gerarVideos(CocaCola c) throws Exception {

		// FacebookUser fbu = c.getFacebookUser();

		//LinkedHashSet<VideoGerado> listaVideosGerados = new LinkedHashSet<VideoGerado>();
		Propriedades prop = new Propriedades();

		this.setCocaCola(c);
		numCenas = prop.getNumeroCenas();

		// pra cada cena vou gerar o video
		for (int i = 0; i < numCenas.intValue(); i++) {

			VideoGerado temp = new VideoGerado();
			GeradorPosicoes g = new GeradorPosicoes(i + 1);
			temp.setgPosicoes(g);
			temp.setCocaCola(c);
			temp.setIdUsuario(new Integer(100 + i).toString());
			temp.gerar(i+1);

		}
		
		concatenarVideos();

	}

	public static Integer getNumCenas() {
		return numCenas;
	}

	public static void setNumCenas(Integer numCenas) {
		VideoManager.numCenas = numCenas;
	}

	public VideoManager() {

		
		

	}
	
	public void concatenarVideos(){
		
		//pega o numero de cenas
		int numCenas = getNumCenas().intValue();
		
		Concatenate conc = new Concatenate();
	
		
		//vê se é impar (vou trabalhar com 3 como exemplo)
		try {
			conc.concatenar(1, 2, getCocaCola());
			conc.concatenar(3, 4, getCocaCola());
			conc.concatenar(5, 6, getCocaCola());
			conc.concatenar(12, 34, getCocaCola());
			conc.concatenar(1234, 56, getCocaCola());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	// metodo para testes somente
	public VideoManager(boolean teste) throws Exception {
		prop = new Propriedades();
		gerarVideo1();

	}

	public void getDadosVideo(String url) {

		// first we create a Xuggler container object
		IContainer container = IContainer.make();

		// we attempt to open up the container
		int result = container.open(url, IContainer.Type.READ, null);

		// check if the operation was successful
		if (result < 0)
			throw new RuntimeException("Failed to open media file");

		// query how many streams the call to open found
		int numStreams = container.getNumStreams();

		// query for the total duration
		long duration = container.getDuration();

		// query for the file size
		long fileSize = container.getFileSize();

		// query for the bit rate
		long bitRate = container.getBitRate();

		System.out.println("Number of streams: " + numStreams);
		System.out.println("Duration (ms): " + duration);
		System.out.println("File Size (bytes): " + fileSize);
		System.out.println("Bit Rate: " + bitRate);

		// iterate through the streams to print their meta data
		for (int i = 0; i < numStreams; i++) {

			// find the stream object
			IStream stream = container.getStream(i);

			// get the pre-configured decoder that can decode this stream;
			IStreamCoder coder = stream.getStreamCoder();

			System.out.println("*** Start of Stream Info ***");

			System.out.printf("stream %d: ", i);
			System.out.printf("type: %s; ", coder.getCodecType());
			System.out.printf("codec: %s; ", coder.getCodecID());
			System.out.printf("duration: %s; ", stream.getDuration());
			System.out.printf("start time: %s; ", container.getStartTime());
			System.out.printf("timebase: %d/%d; ", stream.getTimeBase()
					.getNumerator(), stream.getTimeBase().getDenominator());
			System.out.printf("coder tb: %d/%d; ", coder.getTimeBase()
					.getNumerator(), coder.getTimeBase().getDenominator());
			System.out.println();

			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				System.out.printf("sample rate: %d; ", coder.getSampleRate());
				System.out.printf("channels: %d; ", coder.getChannels());
				System.out.printf("format: %s", coder.getSampleFormat());
			} else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				System.out.printf("width: %d; ", coder.getWidth());
				System.out.printf("height: %d; ", coder.getHeight());
				System.out.printf("format: %s; ", coder.getPixelType());
				System.out.printf("frame-rate: %5.2f; ", coder.getFrameRate()
						.getDouble());
			}

			System.out.println();
			System.out.println("*** End of Stream Info ***");

		}

	}

	public static IMediaWriter getMediaWriter() {
		return mediaWriter;
	}

	public static void setMediaWriter(IMediaWriter mediaWriter) {
		VideoManager.mediaWriter = mediaWriter;
	}

	public static Propriedades getProp() {
		return prop;
	}

	public static void setProp(Propriedades prop) {
		VideoManager.prop = prop;
	}





	// video com o nome do cara
	public void gerarVideo1() throws Exception {

		// pegar as posicoes
		GeradorPosicoes g = new GeradorPosicoes(1);
		// abrir o video original
		// create a media reader
		IMediaReader mediaReader = ToolFactory.makeReader(prop
				.getUrlVideosRespostas()[0]);

		// configure it to generate BufferImages
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		IMediaWriter mediaWriter = ToolFactory.makeWriter("video1temp.mov",
				mediaReader);

		String nomeGerado = "Teste";
		if (facebookManager != null) {

			nomeGerado = facebookManager.getUser().getFirstName();
		}

		IMediaTool imageMediaTool = new StaticImageMediaTool(nomeGerado);
		// Adicionou um listener com a imagem estatica
		mediaReader.addListener(imageMediaTool);
		mediaReader.addListener(mediaWriter);

		// Adiciona o listener do nome

		while (mediaReader.readPacket() == null)
			;

		System.out.println("Acaboooouuuu");

	}

	private static class StaticImageMediaTool extends MediaToolAdapter {

		private BufferedImage logoImage;

		private GeradorPosicoes gp;

		public int indice = 0;

		public StaticImageMediaTool(String texto) throws Exception {

			System.out.println("Entrou no laço de pegar a imagem");

			gp = new GeradorPosicoes(1);

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

			if (indice < dados.size()) {

				dadosTemp = dados.get(indice);
			} else {

				dadosTemp = new DadosImagem(new Long(0), new Long(0),
						new Integer(0), new Integer(0));
			}

			// se o tempo for maior que o inicial, verifica se foi maior que o
			// final. Se for, avança
			// na lista. Senao, está no intervalo, ou seja, desenhará.
			if (dadosTemp.getTempoInicial().longValue() * 1000 <= now) {

				System.out.println("Tempo do Video: " + now + " Elemento: "
						+ dadosTemp.toString());
				if (dadosTemp.getTempoFinal().intValue() * 1000 >= now) {
					System.out.println("Desenhou, pois está no tempo " + now
							+ dadosTemp.toString());
					g.drawImage(logoImage, dadosTemp.getX(), dadosTemp.getY(),
							null);
				} else {
					// passou do final, entao anda a lista
					System.out
							.println("Não desenhou, pois passou do tempo e vai agora andar o temp "
									+ now + dadosTemp.toString());
					indice++;

				}
			}

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}

}

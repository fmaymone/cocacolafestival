package org.br.maymone.projetococacola.media;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.Random;

import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.mvc.businnes.FacebookManager;
import org.br.maymone.projetococacola.util.TextToImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class VideoPersonalizado {

	private static final String inputFilename = "src/main/resources/videos/cena1.mov";
	private static final String outputFilename = "marca1.mp4";
	private static final String imageFilename = "images.jpg";

	private static FacebookManager facebookManager;

	private static IMediaReader[] videos;

	public VideoPersonalizado(String token) {

		try {
			facebookManager = new FacebookManager(token, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static IMediaReader[] getVideos() {
		return videos;
	}

	public static void setVideos(IMediaReader[] videos) {
		VideoPersonalizado.videos = videos;
	}

	public static FacebookManager getFacebookManager() {
		return facebookManager;
	}

	public static void setFacebookManager(FacebookManager facebookManager) {
		VideoPersonalizado.facebookManager = facebookManager;
	}

	public static Integer getCount() {
		return count;
	}

	public static void setCount(Integer count) {
		VideoPersonalizado.count = count;
	}

	public static String getInputfilename() {
		return inputFilename;
	}

	public static String getOutputfilename() {
		return outputFilename;
	}

	public static String getImagefilename() {
		return imageFilename;
	}

	private static Integer count;

	public static void main(String[] args) throws IOException {

		count = new Integer(0);

		VideoPersonalizado v = new VideoPersonalizado(
				"CAACEdEose0cBAIEI4zzjCXKPVleFEw5VBAf6Xc8fNaeUqgx72msF2wJt1FHzywBsKti5ssgn33igRi2PheXM6XjrKu9jfDD1WitvXYsBHoIZCRpZAjToq0tnfz81X2j4EOkyIA75fDz7WRCZBdkXNRfZCZAvDVTAOfynyRKZAzkHYtE0aqKwjyis6gLl1n9ltjZAaOXo7oVACYPZBcbZBW33E");

		IMediaWriter m = v.gerarVideoUsuario(null);
		System.out.println("Esta aberto?" + m.isOpen());
	}

	public IMediaWriter gerarVideoUsuario(CocaCola coca) {

	

		// create a media reader
		IMediaReader mediaReader = ToolFactory.makeReader(inputFilename);

		// configure it to generate BufferImages
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		IMediaWriter mediaWriter = ToolFactory.makeWriter(outputFilename,
				mediaReader);

		IMediaTool imageMediaTool = new StaticImageMediaTool(facebookManager.getUser().getFirstName());
		IMediaTool audioVolumeMediaTool = new VolumeAdjustMediaTool(0.1);
		
		  IMediaTool imageMediaToolTexto = new StaticImageMediaToolTexto(
		  facebookManager.getUser().getFirstName());
		 

		// Adicionou um listener com a imagem estatica
		mediaReader.addListener(imageMediaTool);
		mediaReader.addListener(mediaWriter);
		//mediaReader.addListener(imageMediaToolTexto);
		//imageMediaTool.addListener(audioVolumeMediaTool);
		//imageMediaTool.addListener(imageMediaToolTexto);

		

		// Adiciona o listener do nome

		while (mediaReader.readPacket() == null)
			;

		System.out.println("Acaboooouuuu");
		// ediaWriter.close();
		return mediaWriter;

	}

	private static class StaticImageMediaTool extends MediaToolAdapter {

		private BufferedImage logoImage;

		public StaticImageMediaTool(String texto) {

			System.out.println("Entrou no laço de pegar a imagem");
			
			TextToImage ti = new TextToImage();
			logoImage = ti.gerarImagemTexto(texto);
			System.out.println("Pegou");

		}

		@Override
		public void onVideoPicture(IVideoPictureEvent event) {
			System.out.println("Entrou no evento");
			BufferedImage image = event.getImage();

			// get the graphics for the image
			Graphics2D g = image.createGraphics();

			Random r = new Random();
			int x = r.nextInt(100);
			int y = r.nextInt(100);

			g.drawImage(logoImage, x, y, null);

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}
	private static class StaticImageMediaToolTexto extends MediaToolAdapter {

		private BufferedImage textoImagem;

		public StaticImageMediaToolTexto(String texto) {

			System.out.println("Entrou no laço de pegaro texto");
			TextToImage ti = new TextToImage();
			textoImagem = ti.gerarImagemTexto(texto);
			System.out.println("Pegou");

		}

		@Override
		public void onVideoPicture(IVideoPictureEvent event) {
			System.out.println("Entrou no evento");
			BufferedImage image = event.getImage();

			// get the graphics for the image
			Graphics2D g = image.createGraphics();

			Random r = new Random();
			int x = r.nextInt(100);
			int y = r.nextInt(100);

			g.drawImage(textoImagem, x, y, null);

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}

	private static class VolumeAdjustMediaTool extends MediaToolAdapter {

		// the amount to adjust the volume by
		private double mVolume;

		public VolumeAdjustMediaTool(double volume) {
			mVolume = volume;
		}

		@Override
		public void onAudioSamples(IAudioSamplesEvent event) {

			// get the raw audio bytes and adjust it's value
			ShortBuffer buffer = event.getAudioSamples().getByteBuffer()
					.asShortBuffer();

			for (int i = 0; i < buffer.limit(); ++i) {
				buffer.put(i, (short) (buffer.get(i) * mVolume));
			}

			// call parent which will pass the audio onto next tool in chain
			super.onAudioSamples(event);

		}

	}

}
package org.br.maymone.projetococacola.mvc.businnes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.br.maymone.projetococacola.media.Concatenate;
import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.model.VideoGerado;
import org.br.maymone.projetococacola.model.filhos.Video1;
import org.br.maymone.projetococacola.util.GeradorPosicoes;
import org.br.maymone.projetococacola.util.Propriedades;
import org.br.maymone.projetococacola.util.VideoAudioConcatenator;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

@Model
public class VideoManager {

	private static IMediaWriter mediaWriter;

	private static Propriedades prop;

	private static Integer numCenas;

	@Inject
	private Logger log;

	@Inject
	private static YouTubeManager ytm;

	private static CocaCola cocaCola;

	public static CocaCola getCocaCola() {
		return cocaCola;
	}

	public static void setCocaCola(CocaCola cocaCola) {
		VideoManager.cocaCola = cocaCola;
	}

	private FacebookManager facebookManager;

	public void gerarVideos(CocaCola c) throws Exception {

		Propriedades prop = new Propriedades();

		this.setCocaCola(c);
		numCenas = prop.getNumeroCenas();
		numCenas = 5;
		// pra cada cena vou gerar o video
		for (int i = 0; i <= numCenas.intValue(); i++) {

			try {
				VideoGerado temp = new VideoGerado();
				GeradorPosicoes g = new GeradorPosicoes(i + 1);
				temp.setCocaCola(c);
				temp.gerar(i+1);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Problema ao gerar o video " + i);
				e.printStackTrace();
			}

		}

		
		concatenarVideos();
		
		YouTubeManager ytm = new YouTubeManager();

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("temp/").getPath());
		File audioFile = new File(classLoader.getResource("jingle.aif")
				.getPath());

		String base = file.getAbsolutePath();

		String source1 = base + "\\"
				+ c.getJsonCoca().getUsuFaceId().toString() + "\\"
				+ "video12345.mov";

		String out = base + "\\" + c.getJsonCoca().getUsuFaceId().toString()
				+ "\\" + "video12345audio.mov";
		VideoAudioConcatenator vac = new VideoAudioConcatenator();
		vac.concatenar(source1, audioFile.getPath(), out);

		InputStream videoPublicar = classLoader.getResourceAsStream("temp\\"
				+ c.getJsonCoca().getUsuFaceId().toString() + "\\"
				+ "video12345audio.mov");
		/*
		String s = ytm.publicarVideo(videoPublicar, c);

		c.setUrlVideo(s);
		System.out.println("Video Publicado , com id:" + s);

		// enviar arquivos pra base
		 CocaColaManager cm = new CocaColaManager(false);
		 cm.enviarLinkUsuario(c);
		 System.out.println("Video Enviado :" + c.toString());
*/
	}

	public static Integer getNumCenas() {
		return numCenas;
	}

	public static void setNumCenas(Integer numCenas) {
		VideoManager.numCenas = numCenas;
	}

	public VideoManager() {

	}

	public void concatenarVideos() {

		// pega o numero de cenas
		int numCenas = getNumCenas().intValue();

		Concatenate conc = new Concatenate();

		// vê se é impar (vou trabalhar com 3 como exemplo)
		try {
			conc.concatenar(1, 2, getCocaCola());
			conc.concatenar(3, 4, getCocaCola());

			conc.concatenar(12, 34, getCocaCola());
			conc.concatenar(1234, 5, getCocaCola());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// metodo para testes somente
	public VideoManager(boolean teste) throws Exception {
		prop = new Propriedades();

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

}

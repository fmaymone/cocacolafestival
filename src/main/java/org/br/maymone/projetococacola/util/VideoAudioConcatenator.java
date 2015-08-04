package org.br.maymone.projetococacola.util;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;

public class VideoAudioConcatenator {
	public void concatenar(String video, String audio, String out)
	  {

	
	    String inputVideoFilePath = video;
	    String inputAudioFilePath = audio;
	    String outputVideoFilePath = out;

	    IMediaWriter mWriter = ToolFactory.makeWriter(outputVideoFilePath);

	    IContainer containerVideo = IContainer.make();
	    IContainer containerAudio = IContainer.make();

	    // check files are readable
	    if (containerVideo.open(inputVideoFilePath, IContainer.Type.READ, null) < 0)
	        throw new IllegalArgumentException("Cant find " + inputVideoFilePath);
	    if (containerAudio.open(inputAudioFilePath, IContainer.Type.READ, null) < 0)
	        throw new IllegalArgumentException("Cant find " + inputAudioFilePath);

	    // read video file and create stream
	    IStreamCoder coderVideo = containerVideo.getStream(0).getStreamCoder();
	    if (coderVideo.open(null, null) < 0)
	        throw new RuntimeException("Cant open video coder");
	    IPacket packetvideo = IPacket.make();
	    int width = coderVideo.getWidth();
	    int height = coderVideo.getHeight();

	    // read audio file and create stream
	    IStreamCoder coderAudio = containerAudio.getStream(0).getStreamCoder();
	    if (coderAudio.open(null, null) < 0)
	        throw new RuntimeException("Cant open audio coder");
	    IPacket packetaudio = IPacket.make();

	    mWriter.addAudioStream(1, 0, coderAudio.getChannels(), coderAudio.getSampleRate());
	    mWriter.addVideoStream(0, 0, width, height);

	    while (containerVideo.readNextPacket(packetvideo) >= 0) {

	        containerAudio.readNextPacket(packetaudio);

	        // video packet
	        IVideoPicture picture = IVideoPicture.make(coderVideo.getPixelType(), width, height);
	        coderVideo.decodeVideo(picture, packetvideo, 0);
	        if (picture.isComplete()) 
	            mWriter.encodeVideo(0, picture);

	        // audio packet 
	        IAudioSamples samples = IAudioSamples.make(512, coderAudio.getChannels(), IAudioSamples.Format.FMT_S32);
	        coderAudio.decodeAudio(samples, packetaudio, 0);
	        if (samples.isComplete()) 
	            mWriter.encodeAudio(1, samples);
	        while (samples.isComplete() ) {
		        containerAudio.readNextPacket(packetaudio);
		        coderAudio.decodeAudio(samples, packetaudio, 0);
		        mWriter.encodeAudio(1, samples);
		                    
		    }

	    }
	   

	    coderAudio.close();
	    coderVideo.close();
	    containerAudio.close();
	    containerVideo.close();
	    mWriter.close();


	  }

}

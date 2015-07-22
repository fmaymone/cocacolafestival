/*******************************************************************************
 * Copyright (c) 2008, 2010 Xuggle Inc.  All rights reserved.
 *  
 * This file is part of Xuggle-Xuggler-Main.
 *
 * Xuggle-Xuggler-Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Xuggle-Xuggler-Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Xuggle-Xuggler-Main.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package org.br.maymone.projetococacola.media;

import static java.lang.System.exit;
import static java.lang.System.out;

import java.io.File;
import java.io.IOException;

import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.util.Propriedades;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.ICloseCoderEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.mediatool.event.IOpenEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IVideoPicture;

/**
 * A very simple media transcoder which uses {@link IMediaReader},
 * {@link IMediaWriter} and {@link IMediaViewer}.
 */

public class Concatenate {
	/**
	 * Concatenate two files.
	 * 
	 * @param args
	 *            3 strings; an input file 1, input file 2, and an output file.
	 * @return
	 */
	public Concatenate() {

	}

	public void concatenar(int video1, int video2, CocaCola coca) throws IOException {

		Propriedades prop = new Propriedades();

		String retorno = "";
		
		
				
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource("temp/").getPath());		
		
		String base = file.getAbsolutePath();

		String source1 = base + "\\" + coca.getNome()+ "\\" + "video"
				+ new Integer(video1).toString() +"."
				+ prop.getProp().getProperty("prop.formato.video.input");
		File a = new File(source1);
		
		String source2 = base + "\\" + coca.getNome() + "\\" + "video"
				+ new Integer(video2).toString() +"."
				+ prop.getProp().getProperty("prop.formato.video.input");
		
		File b = new File(source2);
		
		//gera um resultado como video12 com a concatenacao de 1 e 2
		String resultado =  base + "\\" + coca.getNome()  + "\\" + "video"
				+ new Integer(video1).toString() + new Integer(video2).toString()+"."
				+ prop.getProp().getProperty("prop.formato.video.input");
		File c = new File(resultado);
		if (!a.exists()) {
			out.println("Source file does not exist: " + source1);
			exit(0);
		}

		if (!b.exists()) {
			out.println("Source file does not exist: " + source2);
			exit(0);
		}

		/*concatenate(source1.getAbsolutePath(), source2.getAbsolutePath(),
				resultado.getAbsolutePath());*/
		
		concatenate(a.getAbsolutePath(), b.getAbsolutePath(),
				c.getAbsolutePath());
		
		
		
	}

	/**
	 * Concatenate two source files into one destination file.
	 * 
	 * @param sourceUrl1
	 *            the file which will appear first in the output
	 * @param sourceUrl2
	 *            the file which will appear second in the output
	 * @param destinationUrl
	 *            the file which will be produced
	 */

	public static void concatenate(String sourceUrl1, String sourceUrl2,
			String destinationUrl) {
		out.printf("transcode %s + %s -> %s\n", sourceUrl1, sourceUrl2,
				destinationUrl);

		// ////////////////////////////////////////////////////////////////////
		// //
		// NOTE: be sure that the audio and video parameters match those of //
		// your input media //
		// //
		// ////////////////////////////////////////////////////////////////////

		// video parameters

		final int videoStreamIndex = 0;
		final int videoStreamId = 0;
		final int width = 1920;
		final int height = 1080;

		// audio parameters

		final int audioStreamIndex = 1;
		final int audioStreamId = 0;
		final int channelCount = 2;
		final int sampleRate = 48000; // Hz

		// create the first media reader

		IMediaReader reader1 = ToolFactory.makeReader(sourceUrl1);

		// create the second media reader

		IMediaReader reader2 = ToolFactory.makeReader(sourceUrl2);

		// create the media concatenator

		MediaConcatenator concatenator = new MediaConcatenator(
				audioStreamIndex, videoStreamIndex);

		// concatenator listens to both readers

		reader1.addListener(concatenator);
		reader2.addListener(concatenator);

		// create the media writer which listens to the concatenator

		IMediaWriter writer = ToolFactory.makeWriter(destinationUrl);

		concatenator.addListener(writer);

		// add the video stream

		writer.addVideoStream(videoStreamIndex, videoStreamId, width, height);

		// add the audio stream

		writer.addAudioStream(audioStreamIndex, audioStreamId, channelCount,
				sampleRate);

		// read packets from the first source file until done

		while (reader1.readPacket() == null)
			;

		// read packets from the second source file until done

		while (reader2.readPacket() == null)
			;

		// close the writer

		writer.close();
		
		System.out.println("Concatenou" );
	}

	static class MediaConcatenator extends MediaToolAdapter {
		// the current offset

		private long mOffset = 0;

		// the next video timestamp

		private long mNextVideo = 0;

		// the next audio timestamp

		private long mNextAudio = 0;

		// the index of the audio stream

		private final int mAudoStreamIndex;

		// the index of the video stream

		private final int mVideoStreamIndex;

		/**
		 * Create a concatenator.
		 * 
		 * @param audioStreamIndex
		 *            index of audio stream
		 * @param videoStreamIndex
		 *            index of video stream
		 */

		public MediaConcatenator(int audioStreamIndex, int videoStreamIndex) {
			mAudoStreamIndex = audioStreamIndex;
			mVideoStreamIndex = videoStreamIndex;
		}

		public void onAudioSamples(IAudioSamplesEvent event) {
			IAudioSamples samples = event.getAudioSamples();

			// set the new time stamp to the original plus the offset
			// established
			// for this media file

			long newTimeStamp = samples.getTimeStamp() + mOffset;

			// keep track of predicted time of the next audio samples, if the
			// end
			// of the media file is encountered, then the offset will be
			// adjusted
			// to this time.

			mNextAudio = samples.getNextPts();

			// set the new timestamp on audio samples

			samples.setTimeStamp(newTimeStamp);

			// create a new audio samples event with the one true audio stream
			// index

			super.onAudioSamples(new AudioSamplesEvent(this, samples,
					mAudoStreamIndex));
		}

		public void onVideoPicture(IVideoPictureEvent event) {
			IVideoPicture picture = event.getMediaData();
			long originalTimeStamp = picture.getTimeStamp();

			// set the new time stamp to the original plus the offset
			// established
			// for this media file

			long newTimeStamp = originalTimeStamp + mOffset;

			// keep track of predicted time of the next video picture, if the
			// end
			// of the media file is encountered, then the offset will be
			// adjusted
			// to this this time.
			//
			// You'll note in the audio samples listener above we used
			// a method called getNextPts(). Video pictures don't have
			// a similar method because frame-rates can be variable, so
			// we don't now. The minimum thing we do know though (since
			// all media containers require media to have monotonically
			// increasing time stamps), is that the next video timestamp
			// should be at least one tick ahead. So, we fake it.

			mNextVideo = originalTimeStamp + 1;

			// set the new timestamp on video samples

			picture.setTimeStamp(newTimeStamp);

			// create a new video picture event with the one true video stream
			// index

			super.onVideoPicture(new VideoPictureEvent(this, picture,
					mVideoStreamIndex));
		}

		public void onClose(ICloseEvent event) {
			// update the offset by the larger of the next expected audio or
			// video
			// frame time

			mOffset = Math.max(mNextVideo, mNextAudio);

			if (mNextAudio < mNextVideo) {
				// In this case we know that there is more video in the
				// last file that we read than audio. Technically you
				// should pad the audio in the output file with enough
				// samples to fill that gap, as many media players (e.g.
				// Quicktime, Microsoft Media Player, MPlayer) actually
				// ignore audio time stamps and just play audio sequentially.
				// If you don't pad, in those players it may look like
				// audio and video is getting out of sync.

				// However kiddies, this is demo code, so that code
				// is left as an exercise for the readers. As a hint,
				// see the IAudioSamples.defaultPtsToSamples(...) methods.
			}
		}

		public void onAddStream(IAddStreamEvent event) {
			// overridden to ensure that add stream events are not passed down
			// the tool chain to the writer, which could cause problems
		}

		public void onOpen(IOpenEvent event) {
			// overridden to ensure that open events are not passed down the
			// tool
			// chain to the writer, which could cause problems
		}

		public void onOpenCoder(IOpenCoderEvent event) {
			// overridden to ensure that open coder events are not passed down
			// the
			// tool chain to the writer, which could cause problems
		}

		public void onCloseCoder(ICloseCoderEvent event) {
			// overridden to ensure that close coder events are not passed down
			// the
			// tool chain to the writer, which could cause problems
		}
	}
}

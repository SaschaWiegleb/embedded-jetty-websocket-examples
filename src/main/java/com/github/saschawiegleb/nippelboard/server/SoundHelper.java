package com.github.saschawiegleb.nippelboard.server;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.github.saschawiegleb.nippelboard.Conf;

public class SoundHelper {

	static List<File> getAllSounds() {
		File folder = new File(Conf.soundFolder);
		if (!folder.exists()) {
			folder.mkdir();
			return Arrays.asList();
		}
		return new ArrayList<>(Arrays.asList(folder.listFiles()));
	}

	public static String possibleNamesAsCsv() {
		StringBuilder builder = new StringBuilder();
		for (File sound : getAllSounds()) {
			builder.append(sound.getName()).append(",");
		}
		if (builder.length() == 0) {
			return "";
		}
		return builder.delete(builder.length() - 1, builder.length()).toString();
	}

	public static void downloadSound(String urlString) {
		InputStream in = null;
		try {
			URL url = new URL(urlString);
			String fileName = urlString.substring(urlString.lastIndexOf('/') + 1, urlString.length());

			in = url.openStream();
			Files.copy(in, Paths.get(Conf.soundFolder + fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static File getSpecificSoundfile(String name) {
		name = name.toLowerCase();
		List<File> allSounds = getAllSounds();
		for (File sound : allSounds) {
			if (sound.getName().toLowerCase().contains(name)) {
				return sound;
			}
		}
		return null;
	}

	public static synchronized void playSound(final File file) {
		if (file == null)
			return;

		new Thread(new Runnable() {
			@Override
			public void run() {
				try (final AudioInputStream in = getAudioInputStream(file)) {
					final AudioFormat outFormat = getOutFormat(in.getFormat());
					final Info info = new Info(SourceDataLine.class, outFormat);

					final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

					if (line != null) {
						line.open(outFormat);
						line.start();
						stream(getAudioInputStream(outFormat, in), line);
						line.drain();
						line.stop();
					}
				} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}).start();
	}

	private static AudioFormat getOutFormat(AudioFormat inFormat) {
		final int ch = inFormat.getChannels();
		final float rate = inFormat.getSampleRate();
		return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
	}

	private static void stream(AudioInputStream in, SourceDataLine line) throws IOException {
		final byte[] buffer = new byte[4096];
		for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
			line.write(buffer, 0, n);
		}
	}
}

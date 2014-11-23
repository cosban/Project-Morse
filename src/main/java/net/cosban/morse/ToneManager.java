package net.cosban.morse;

import com.sun.media.sound.WaveFileWriter;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;

public class ToneManager {
	private byte[] data;
	private int    index;
	private int    sampleRate;
	private int    framesPerCycle;

	public ToneManager(char[] codes) throws LineUnavailableException {
		sampleRate = 22500;
		framesPerCycle = 60;
		// max array size anyway
		data = new byte[framesPerCycle * 120 * codes.length * 2];
		index = 0;
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] == '.') {
				generateTone(20, false);
			} else if (codes[i] == '-') {
				generateTone(60, false);
			} else if (codes[i] == ' ') {
				generateTone(60, true);
			} else if (codes[i] == '/') {
				generateTone(100, true);
			}
			generateTone(15, true);
		}
	}

	public void generateTone(int cycles, boolean pause) throws LineUnavailableException {
		for (int i = 0; i < framesPerCycle * cycles; i++) {
			double angle = ((float) (i * 2) / ((float) framesPerCycle)) * (Math.PI);
			data[index] = getByteValue(angle, pause);
			index++;
			data[index] = data[index - 1];
			index++;
		}
	}

	private byte getByteValue(double angle, boolean pause) {
		return ((Integer) (int) Math.round(Math.sin(angle) * (pause ? 0 : 127))).byteValue();
	}

	public void write(File file) throws IOException {
		AudioFormat af = new AudioFormat(sampleRate, 8, 2, true, false);
		WaveFileWriter writer = new WaveFileWriter();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(new AudioInputStream(new ByteArrayInputStream(data), af, data.length/2), AudioFileFormat.Type.WAVE,
				out);
	}
}

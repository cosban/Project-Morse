package net.cosban.morse;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ToneManager {

	private ArrayList<Byte> bytes;
	private int             index;
	private int             sampleRate;
	private int             framesPerCycle;

	public ToneManager(char[] codes) throws LineUnavailableException {
		bytes = new ArrayList<>();
		sampleRate = 22500;
		framesPerCycle = 60;
		index = 0;

		for (int i = 0; i < codes.length * 2; i += 2) {
			if (codes[i / 2] == '.') {
				generateTone(20, false);
			} else if (codes[i / 2] == '-') {
				generateTone(60, false);
			} else if (codes[i / 2] == ' ') {
				generateTone(60, true);
			} else if (codes[i / 2] == '/') {
				generateTone(100, true);
			}
			generateTone(20, true);
		}
	}

	public void generateTone(int cycles, boolean pause) throws LineUnavailableException {
		for (int i = 0; i < framesPerCycle * cycles; i++) {
			double angle = ((float) (i * 2) / ((float) framesPerCycle)) * (Math.PI);
			bytes.add(i * 2, getByteValue(angle, pause));
			bytes.add((i * 2) + 1, bytes.get(i * 2));
		}
	}

	private byte getByteValue(double angle, boolean pause) {
		return ((Integer) (int) Math.round(Math.sin(angle) * (pause ? 0 : 127))).byteValue();
	}

	public void play() throws LineUnavailableException {
		Clip clip = AudioSystem.getClip();
		AudioFormat af = new AudioFormat(sampleRate, 8, 2, true, false);
	}
}

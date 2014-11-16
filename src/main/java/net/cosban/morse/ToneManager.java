package net.cosban.morse;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayInputStream;

public class ToneManager implements LineListener {

	private Clip[] clips;
	private Clip   current;
	private int    index;
	private int    sampleRate;
	private int    framesPerCycle;

	public ToneManager(char[] codes) throws LineUnavailableException {
		sampleRate = 22500;
		framesPerCycle = 60;
		index = 0;
		clips = new Clip[codes.length * 2];
		for (int i = 0; i < clips.length; i += 2) {
			if (codes[i / 2] == '.') {
				clips[(i / 2)] = generateTone(20, false);
			} else if (codes[i / 2] == '-') {
				clips[i / 2] = generateTone(60, false);
			} else if (codes[i / 2] == ' ') {
				clips[(i / 2)] = generateTone(60, true);
			} else if (codes[i / 2] == '/') {
				clips[(i / 2)] = generateTone(100, true);
			}
			clips[(i / 2) + 1] = generateTone(20, true);
		}
		current = clips[index];
	}

	public Clip generateTone(int cycles, boolean pause) throws LineUnavailableException {
		Clip clip = AudioSystem.getClip();

		byte[] buf = new byte[2 * framesPerCycle * cycles];
		AudioFormat af = new AudioFormat(sampleRate, 8, 2, true, false);

		for (int i = 0; i < framesPerCycle * cycles; i++) {
			double angle = ((float) (i * 2) / ((float) framesPerCycle)) * (Math.PI);
			buf[i * 2] = getByteValue(angle, pause);
			buf[(i * 2) + 1] = buf[i * 2];
		}

		try {
			clip.open(new AudioInputStream(new ByteArrayInputStream(buf), af, buf.length / 2));
			return clip;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte getByteValue(double angle, boolean pause) {
		return ((Integer) (int) Math.round(Math.sin(angle) * (pause ? 0 : 127))).byteValue();
	}

	public void play() throws LineUnavailableException {
		current.setFramePosition(0);
		current.addLineListener(this);
		SwingUtilities.invokeLater(current::start);
	}

	public void update(LineEvent event) {
		if (event.getType().equals(LineEvent.Type.STOP)) {
			current.close();
			current = clips[++index];
			if (current != null) {
				try {
					play();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

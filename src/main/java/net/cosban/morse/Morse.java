package net.cosban.morse;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.nio.charset.Charset;

public class Morse {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("usage: Morse <input file>");
			return;
		}

		File input = new File(args[0]);
		Reader reader;
		try {
			InputStream in = new FileInputStream(input);
			reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file at " + input.getAbsolutePath());
			return;
		}

		char[] message = new char[(int) input.length()];

		try {
			int i = 0;
			int c;
			while ((c = reader.read()) != -1) {
				if (!(c == 10 || c == 13 || c == 32 || c == 44 || c == 46 || (c > 47 && c < 58) ||
						c == 63 || (c > 64 && c < 91) || (c > 96 && c < 123))) {
					System.out.println("Invalid character used! a-z, A-Z, 0-9 ?., and spaces only!");
					System.exit(1);
				} else if (c > 96) {
					message[i] = (char) (c - 32);
				} else {
					message[i] = (char) c;
				}
				i++;
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Unable to read input file at " + input.getAbsolutePath());
			return;
		}

		String encoding = "";
		for (char c : message) {
			encoding += StandardEncoding.getCode(c);
		}
		try {
			ToneManager tones = new ToneManager(encoding.toCharArray());
			tones.play();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}


	}
}

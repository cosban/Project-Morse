package net.cosban.morse;

import java.util.HashMap;
import java.util.Map;

public enum Encoding {
	A('A', "012"),
	B('B', "10002"),
	C('C', "10102"),
	D('D', "1002"),
	E('E', "02"),
	F('F', "00102"),
	G('G', "1102"),
	H('H', "00002"),
	I('I', "002"),
	J('J', "01112"),
	K('K', "1012"),
	L('L', "01002"),
	M('M', "112"),
	N('N', "102"),
	O('O', "1112"),
	P('P', "01102"),
	Q('Q', "11012"),
	R('R', "0102"),
	S('S', "0002"),
	T('T', "12"),
	U('U', "0012"),
	V('V', "00012"),
	W('W', "0112"),
	X('X', "10012"),
	Y('Y', "10112"),
	Z('Z', "11002"),
	ONE('1', "011112"),
	TWO('2', "001112"),
	THREE('3', "000112"),
	FOUR('4', "000012"),
	FIVE('5', "000002"),
	SIX('6', "100002"),
	SEVEN('7', "110002"),
	EIGHT('8', "111002"),
	NINE('9', "111102"),
	ZERO('0', "111112"),
	SPACE(' ', "3"),
	QUESTION('?', "0011002"),
	STOP('.', "0101012"),
	COMMA(',', "1100112");

	private static final Map<Character, String> pairs;
	private              String                 code;
	private              char                   value;

	private Encoding(char value, String code) {
		this.value = value;
		this.code = code;
	}

	public static String getCode(char value) {
		return pairs.get(value);
	}

	static {
		pairs = new HashMap<>();
		for (Encoding e : values())
			pairs.put(e.value, e.code);
	}
}

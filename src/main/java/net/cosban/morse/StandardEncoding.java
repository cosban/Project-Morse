package net.cosban.morse;

import java.util.HashMap;
import java.util.Map;

public enum StandardEncoding {
	A('A', ".- "),
	B('B', "-... "),
	C('C', "-.-. "),
	D('D', "-.. "),
	E('E', ". "),
	F('F', "..-. "),
	G('G', "--. "),
	H('H', ".... "),
	I('I', ".. "),
	J('J', ".--- "),
	K('K', "-.- "),
	L('L', ".-.. "),
	M('M', "-- "),
	N('N', "-. "),
	O('O', "--- "),
	P('P', ".--. "),
	Q('Q', "--.- "),
	R('R', ".-. "),
	S('S', "... "),
	T('T', "- "),
	U('U', "..- "),
	V('V', "...- "),
	W('W', ".-- "),
	X('X', "-..- "),
	Y('Y', "-.-- "),
	Z('Z', "--.. "),
	ONE('1', ".---- "),
	TWO('2', "..--- "),
	THREE('3', "...-- "),
	FOUR('4', "....- "),
	FIVE('5', "..... "),
	SIX('6', "-.... "),
	SEVEN('7', "--... "),
	EIGHT('8', "---.. "),
	NINE('9', "----. "),
	ZERO('0', "----- "),
	SPACE(' ', "/"),
	LINE('\n', "/"),
	QUESTION('?', "..--.. "),
	STOP('.', ".-.-.- "),
	COMMA(',', "--..-- ");

	private static final Map<Character, String> pairs;
	private              String                 code;
	private              char                   value;

	private StandardEncoding(char value, String code) {
		this.value = value;
		this.code = code;
	}

	public static String getCode(char value) {
		return pairs.get(value);
	}

	static {
		pairs = new HashMap<>();
		for (StandardEncoding e : values())
			pairs.put(e.value, e.code);
	}
}

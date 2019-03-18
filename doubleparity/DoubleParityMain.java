package com.dfa.doubleparity;

public class DoubleParityMain {

	public static void main(String[] args) {
		new DoubleParityDFA("0").run();
		new DoubleParityDFA("01").run();
		new DoubleParityDFA("00").run();
		new DoubleParityDFA("001").run();
		new DoubleParityDFA("1010").run();
		new DoubleParityDFA("001100").run();;
	}
}

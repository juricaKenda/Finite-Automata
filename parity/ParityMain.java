package com.dfa.parity;

public class ParityMain {

	public static void main(String[] args) {
		new ParityDFA("11101").run();
		new ParityDFA("11").run();
		new ParityDFA("0").run();
		new ParityDFA("1010").run();
		new ParityDFA("1011").run();
		new ParityDFA("01").run();
	}
}

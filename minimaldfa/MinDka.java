import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;





public class MinDka {

	public static void main(String[] args) throws IOException {
		MinDka min = new MinDka();
	}
	
	public MinDka()throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringBuffer input = new StringBuffer();
		
		String line = reader.readLine();
		while(line != null) {
			input.append(line+"\n");
			line = reader.readLine();
		}
		//Parser
		DFAInputFileParser parser = new DFAInputFileParser(input.toString());
		DFA machine = parser.getDFA();
		machine.minimise();
		machine.pretty();
	}

}

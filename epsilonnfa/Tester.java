package com.nfa.epsilon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
	
	private File folder;
	
	public Tester(String folderLocation) {
		folder = new File(folderLocation);
		System.out.println("Folder exists:" + folder.exists());
	}
	
	public void createSolutions() {
		for(File subFolder : folder.listFiles()) {
			//Locate the definition file
			if(subFolder.isDirectory()) {
				for(File actualFile : subFolder.listFiles()) {
					if(actualFile.getName().endsWith(".a")) {
						File definitionFile = actualFile;
						String solutionFileLoc = definitionFile.getAbsolutePath()+"solution.txt";
						NfaInputFileParser parser = new NfaInputFileParser(definitionFile);
						NFA stateMachine = parser.getNFA();
						stateMachine.runThroughInputs(solutionFileLoc);
						
					}
				}
			}
		}
	}
	
	public void testSolutions() {
		for(File subFolder : folder.listFiles()) {
			//Locate the definition file
			if(subFolder.isDirectory()) {
				File theirSolution = null,mySolution = null;
				for(File actualFile : subFolder.listFiles()) {
					if(actualFile.getName().endsWith(".b")) {
						theirSolution = actualFile;
					}else if(actualFile.getName().contains("solution")) {
						mySolution = actualFile;
					}
				}
				if(compareSolutions(theirSolution,mySolution)) {
					System.out.println("PASS");
				}else {
					System.out.println("FAIL");
				}
			}
		}
	}
	private boolean compareSolutions(File theirSolution, File mySolution) {
		try {
			Scanner myScanner = new Scanner(mySolution);
			Scanner theirScanner = new Scanner(theirSolution);
			while(theirScanner.hasNext()) {
				String theirLine = theirScanner.nextLine().trim();
				String myLine = myScanner.nextLine().trim();
				if(!myLine.equals(theirLine)) {
					return false;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		Tester folderTester = new Tester("C:\\Users\\Korisnik\\Desktop\\FAKS Cetvrti_sem\\Uvod u teoriju računarstva\\Labosi\\lab1_primjeri");
		folderTester.createSolutions();
	}
}

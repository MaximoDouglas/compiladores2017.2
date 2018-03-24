package br.ufal.ic.compilator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {

	private static File file;
	private static int linesQtt;	
	private boolean empty = false;
	
	public Lexer(String fileName) {
		TokenService.fillExpressions();
		file = new File(fileName);		
		linesQtt = setNumberOfLines();
		
		if(file.length() == 0) {
			empty = true;
		} 
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public Token getNextToken() {
		return Token.nextToken();
	}
	
	public static String getLine(int line) {
		BufferedReader reader = null;

		try {

			String sCurrentLine;

			reader = new BufferedReader(new FileReader(file));
			sCurrentLine = reader.readLine();
			
			int i = 0;
			
			while (sCurrentLine != null) {
				
				if (i == line) {
					reader.close();
					return sCurrentLine;
				}
				
				sCurrentLine = reader.readLine();
				i++;
			}

		} 

		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static int setNumberOfLines() {
		BufferedReader reader = null;
		int i = 0;
		
		try {

			String sCurrentLine;

			reader = new BufferedReader(new FileReader(file));
			sCurrentLine = reader.readLine();
			
			while (sCurrentLine != null) {
				i++;
				sCurrentLine = reader.readLine();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return i;
	
	}
	
	
	public static int getNumberOfLines() {
		return linesQtt;
	}
	
}

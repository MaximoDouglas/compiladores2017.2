package br.ufal.ic.compilator.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufal.ic.compilator.runner.Runner;

public class Token {

	private static int stopPositionY = 0;
	private static int stopPositionX = 0;

	private int positionX;

	private int positionY;

	private Categories categorie;
	private int categorieNumber;

	private String lexema;

	private Token(int positionX) {
		this.positionX = positionX;
		this.lexema = lexema;
	}

	public static Token nextToken() {
		String linha = Runner.getNextLine(stopPositionY);

		if (stopPositionX >= linha.length()) {
			stopPositionY++;
			linha = Runner.getNextLine(stopPositionY);
			stopPositionX = 0;
		}
		
		int initialPositionX = stopPositionX;
		Token firstTk = new Token(linha.length());
		
		
		for (int i = 0; i < TokenService.getExpressoes().size(); i++) {
			Token tk = regexChecker(TokenService.getExpressoes().get(i), linha.substring(stopPositionX));
			
			if (tk != null) {
				tk.categorie = Categories.values()[i];
				tk.categorieNumber = tk.categorie.ordinal();
				tk.positionY = stopPositionY;
				
				if (tk.positionX < firstTk.positionX) {
					firstTk = tk;
					initialPositionX = stopPositionX;
					stopPositionX = 0;
				}	
			}
		}
		
		stopPositionX = initialPositionX;
		return firstTk;
	}

	private static Token regexChecker(String theRegex, String theString) {
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(theString);

		String lexema;
		int posX;

		if(regexMatcher.find()) {
			lexema = regexMatcher.group();
			posX = regexMatcher.start();
			stopPositionX = regexMatcher.end() + 1;
			Token tk = new Token(posX);
			tk.lexema = lexema;
			return tk;
		}

		return null;
	}

	public String toString() {
		String string = "["+this.positionY+", "+this.positionX+"] ("+this.categorieNumber+", "+this.categorie.name()+") {"+this.lexema+"}";

		return string;
	}

}

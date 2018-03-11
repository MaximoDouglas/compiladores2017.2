package br.ufal.ic.compilator.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufal.ic.compilator.runner.Runner;

public class Token {

	private static int stopPositionY = 0;
	private static int stopPositionX = 0;
	private static Token lastTk = null;

	private int positionX;
	private int positionY;

	private Categories categorie;
	private int categorieNumber;

	private String lexema;

	private Token(int positionX) {
		this.positionX = positionX;
	}
	
	//Conta espaços em branco no começo da linha
	public static int getSpaces(String str) {
		int i = 0, count = 0;
		while(str.length() != 0 && str.charAt(i) == ' ') {
			count ++;
			i++;
		}
		return count;
	}
	
	//Remove espaços em branco no final da linha
	public static String removeFinalSpaces(String str) {
	    if(str == null) return null;
	    
	    int len = str.length();
	    
	    for( ; len > 0; len--) {
	      if(!Character.isWhitespace(str.charAt(len - 1))) break;
	    }
	    return str.substring(0, len);
	}

	public static Token nextToken() {
		String linha = Runner.getNextLine(stopPositionY);
		int spaces;

		while(linha.trim().equals("")) {
			stopPositionY++;
			linha = Runner.getNextLine(stopPositionY);
		}
		linha = removeFinalSpaces(linha);
		spaces = getSpaces(linha);
				
		while(stopPositionX >= linha.length() && stopPositionY < Runner.getLines() - 1)	 {
			stopPositionY++;
			linha = Runner.getNextLine(stopPositionY);
			
			while(linha.trim().equals("")) {
				stopPositionY++;
				linha = Runner.getNextLine(stopPositionY);
			}
			
			linha = removeFinalSpaces(linha);
			spaces = getSpaces(linha);
			stopPositionX = spaces;
			System.out.println("---------------------------------------------"); //pra ficar mais claro os tokens por linha
		}	

		if (stopPositionX >= linha.length() && stopPositionY == Runner.getLines() - 1) {
			return null;
		}

		int initialPositionX = stopPositionX;
		int finalPositionX = stopPositionX;

		Token firstTk = new Token(linha.length());

		for (int i = 0; i < TokenService.getExpressoes().size(); i++) {
			Token tk = regexChecker(TokenService.getExpressoes().get(Categories.values()[i]), linha.substring(stopPositionX));

			if (tk != null) {
				if (TokenService.getReserved().containsKey(tk.lexema)) {
					tk.categorie = TokenService.getReserved().get(tk.lexema);
				} else {
					tk.categorie = Categories.values()[i];
				}
				
				tk.positionY = stopPositionY;
				
				if(lastTk != null && tk.categorie == Categories.OPA_SUB && (lastTk.categorie == Categories.AB_PARENTE 
					|| lastTk.categorie == Categories.ATRIBUICAO || 
					lastTk.categorie == Categories.AB_COLCHET)) {
					tk.lexema = "-";
					tk.categorie = Categories.OPA_NEGA;
				}
				
				if (tk.categorie != Categories.OPA_NEGA && (lastTk != null && tk.categorie == Categories.CTE_INT && (lastTk.categorie == Categories.CTE_INT || lastTk.categorie == Categories.ID)
						&& tk.positionY == lastTk.positionY) 
						|| (lastTk != null && tk.categorie == Categories.CTE_FLOAT && (lastTk.categorie == Categories.CTE_FLOAT || lastTk.categorie == Categories.ID)
						&& tk.positionY == lastTk.positionY)) {
					tk.categorie = Categories.OPA_SUB;
					tk.lexema = "-";
					stopPositionX = tk.positionX + 1;
				}
				
				tk.categorieNumber = tk.categorie.ordinal();

				if (tk.positionX < firstTk.positionX) {
					firstTk = tk;
					finalPositionX = stopPositionX;
				}	
			}
			
			stopPositionX = initialPositionX;
		}

		stopPositionX = finalPositionX;
		lastTk = firstTk;
		return firstTk;
	}

	private static Token regexChecker(String theRegex, String theString) {
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(theString);

		String lexema;
		int posX;

		if(regexMatcher.find()) {
			lexema = regexMatcher.group();
			posX = regexMatcher.start() + stopPositionX;

			Token tk = new Token(posX);
			tk.lexema = lexema;

			stopPositionX = stopPositionX + regexMatcher.end();
			return tk;
		}

		return null;
	}

	public String toString() {
		String string = "["+this.positionY+", "+this.positionX+"] ("+this.categorieNumber+", "+this.categorie.name()+") {"+this.lexema.trim()+"}";

		return string;
	}

}

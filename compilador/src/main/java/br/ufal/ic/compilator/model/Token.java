package br.ufal.ic.compilator.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {

	private static int stopLineIndex = 0;
	private static int stopColIndex = 0;
	private static Token lastTk = null;
	private int lineIndex;
	private int colIndex;

	private Categories categorie;
	private int categorieNumber;

	private String lexema;

	private static int tabAux = 0;

	private Token(int colIndex) {
		this.colIndex = colIndex;
	}

	//conta n�mero de espa�os em branco no come�o da linha
	public static int getSpaces(String str) {
		int i = 0, count = 0;
		while (str.length() != 0) {
			if(str.charAt(i) == ' ') {
				count++;
			} else if(str.charAt(i) == '\t') {
				count++;
				tabAux += 3;
			} else {
				break;
			}
			i++;
		}
		return count;
	}

	//remove os espa�os em branco no final das linhas
	public static String removeFinalSpaces(String str) {
		if (str == null)
			return null;

		int len = str.length();

		for (; len > 0; len--) {
			if (!Character.isWhitespace(str.charAt(len - 1)))
				break;
		}
		return str.substring(0, len);
	}
	
	//reconhece e retorna o pr�ximo token
	public static Token nextToken() {
		String line = Lexer.getLine(stopLineIndex);
		int spaces;
		tabAux = 0;
		if(stopColIndex == 0) { 
			System.out.println("[" + String.format("%04d", stopLineIndex + 1)
			+ "]  " + line.trim());
		}

		while (line.trim().equals("")) {
			stopLineIndex++;
			line = Lexer.getLine(stopLineIndex);
		}
		
		line = removeFinalSpaces(line);
		spaces = getSpaces(line);
		
		while (stopColIndex >= line.length() && stopLineIndex < Lexer.getNumberOfLines() - 1) {
			stopLineIndex++;
			tabAux = 0;
			line = Lexer.getLine(stopLineIndex);

			while (line.trim().equals("") && stopLineIndex < Lexer.getNumberOfLines() - 1) {
				stopLineIndex++;
				tabAux = 0;
				line = Lexer.getLine(stopLineIndex);
			}
			System.out.println("[" + String.format("%04d", stopLineIndex + 1)
			+ "]  " + line.trim());
		
			line = removeFinalSpaces(line);
			spaces = getSpaces(line);
			stopColIndex = spaces;

		}

		if (stopColIndex >= line.length() && stopLineIndex == Lexer.getNumberOfLines() - 1) {
			return null;
		}

		int initialColIndex = stopColIndex;
		int finalColIndex = stopColIndex;

		Token firstTk = new Token(line.length());
		//System.out.println(line);

		for (int i = 0; i < TokenService.getExpressions().size(); i++) {
			Token tk = regexChecker(TokenService.getExpressions().get(Categories.values()[i]),
					line.substring(stopColIndex));

			if (tk != null) {
				if (TokenService.getReserved().containsKey(tk.lexema)) {
					tk.categorie = TokenService.getReserved().get(tk.lexema);
				} else {
					tk.categorie = Categories.values()[i];
				}

				tk.lineIndex = stopLineIndex;

				if ((tk.categorie == Categories.CTE_CAD_CH)
						|| tk.categorie == Categories.CTE_CHAR) {
					tk.lexema = tk.lexema.substring(1, tk.lexema.length() - 1);
				}
				
				if(lastTk != null && tk.categorie == Categories.OPA_NEGA && (lastTk.categorie == Categories.ID ||
						lastTk.categorie == Categories.CTE_INT || lastTk.categorie == Categories.CTE_FLOAT)) {
					tk.categorie = Categories.OPA_AD;
				}
				
				if(lastTk != null && tk.categorie == Categories.OPA_POSI && (lastTk.categorie == Categories.ID ||
						lastTk.categorie == Categories.CTE_INT || lastTk.categorie == Categories.CTE_FLOAT)) {
					tk.categorie = Categories.OPA_AD;
				}

				tk.categorieNumber = tk.categorie.ordinal();

				if (tk.colIndex < firstTk.colIndex) {
					firstTk = tk;
					finalColIndex = stopColIndex;
				}
			}

			stopColIndex = initialColIndex;
		}

		stopColIndex = finalColIndex;
		lastTk = firstTk;

		//if que checa carcatere de escape \
		if (firstTk.categorie == Categories.CTE_CAD_CH) {
			String auxStr = firstTk.lexema;
			for (int i = 1; i < auxStr.length(); i++) {
				if (auxStr.charAt(i) == '"' && auxStr.charAt(i - 1) != '\\') {
					stopColIndex -= auxStr.length() - i;
					firstTk.lexema = auxStr.substring(0, i);
					break;
				}
			}
		}

		if (firstTk.categorie == Categories.TK_ER_STR || firstTk.categorie == Categories.TK_ER_CH) {
			firstTk = characterErrorCollector(firstTk, line.substring(firstTk.colIndex + 1));
		}

		firstTk.colIndex += tabAux;
		return firstTk;
	}

	private static Token characterErrorCollector(Token tk, String str) {

		if (tk.categorie == Categories.TK_ER_STR) {
			tk.lexema += str;
			stopColIndex += str.length();
		}

		if (tk.categorie == Categories.TK_ER_CH && str.length() >= 1) {

			tk.lexema += str.charAt(0);
			stopColIndex += 1;
		}

		return tk;
	}

	private static Token regexChecker(String theRegex, String theString) {
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(theString);

		String lexema;
		int posX;

		if (regexMatcher.find()) {
			lexema = regexMatcher.group();
			posX = regexMatcher.start() + stopColIndex;

			Token tk = new Token(posX);
			tk.lexema = lexema;

			stopColIndex = stopColIndex + regexMatcher.end();
			return tk;
		}

		return null;
	}

	public String toString() {
		String string = "[" + String.format("%03d", this.lineIndex + 1) + ", "
				+ String.format("%03d", this.colIndex + 1) + "] (" + String.format("%04d", this.categorieNumber) + ", "
				+ String.format("%1$10s", this.categorie.name()) + ") {" + this.lexema + "}";

		if(this.categorie == Categories.TK_ER_CH) {
			string += ". Erro: ' esperado na posição [" + String.format("%03d", this.lineIndex + 1) + ", " + String.format("%03d", this.colIndex + this.lexema.length() + 1) + "]";
		} else if(this.categorie == Categories.TK_ER_STR) {
			string += ". Erro: \" esperado na posição [" + String.format("%03d", this.lineIndex + 1) + ", " + String.format("%03d", this.colIndex + this.lexema.length() + 1) + "]";
		} else if(this.categorie == Categories.TK_ER_NID) {
			string += ". Erro: Token desconhecido.";
		}

		return string;
	}

}
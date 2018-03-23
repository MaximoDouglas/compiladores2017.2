package br.ufal.ic.compilator.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static int getSpaces(String str) {
		int i = 0, count = 0;
		while (str.length() != 0 && str.charAt(i) == ' ') {
			count++;
			i++;
		}
		return count;
	}

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

	public static Token nextToken() {
		String linha = Lexer.getLine(stopPositionY);
		int spaces;

		while (linha.trim().equals("")) {
			stopPositionY++;
			linha = Lexer.getLine(stopPositionY);
		}
		linha = removeFinalSpaces(linha);
		spaces = getSpaces(linha);

		while (stopPositionX >= linha.length() && stopPositionY < Lexer.getNumberOfLines() - 1) {
			stopPositionY++;
			linha = Lexer.getLine(stopPositionY);

			while (linha.trim().equals("") && stopPositionY < Lexer.getNumberOfLines() - 1) {
				stopPositionY++;
				linha = Lexer.getLine(stopPositionY);
			}

			linha = removeFinalSpaces(linha);
			spaces = getSpaces(linha);
			stopPositionX = spaces;

		}

		if (stopPositionX >= linha.length() && stopPositionY == Lexer.getNumberOfLines() - 1) {
			return null;
		}

		int initialPositionX = stopPositionX;
		int finalPositionX = stopPositionX;

		Token firstTk = new Token(linha.length());

		for (int i = 0; i < TokenService.getExpressions().size(); i++) {
			Token tk = regexChecker(TokenService.getExpressions().get(Categories.values()[i]),
					linha.substring(stopPositionX));

			if (tk != null) {
				if (TokenService.getReserved().containsKey(tk.lexema)) {
					tk.categorie = TokenService.getReserved().get(tk.lexema);
				} else {
					tk.categorie = Categories.values()[i];
				}

				tk.positionY = stopPositionY;

				if ((tk.categorie == Categories.CTE_CAD_CH && tk.lexema.charAt(0) == '"')
						|| tk.categorie == Categories.CTE_CHAR) {
					tk.lexema = tk.lexema.substring(1, tk.lexema.length() - 1);
				}

				if ((stopPositionX == 1 && tk.categorie == Categories.OPA_SUB)
						|| (lastTk != null && tk.categorie == Categories.OPA_SUB
								&& (lastTk.categorie == Categories.AB_PARENTE
										|| lastTk.categorie == Categories.ATRIBUICAO
										|| lastTk.categorie == Categories.AB_COLCHET))) {
					tk.lexema = "-";
					tk.categorie = Categories.OPA_NEGA;
				}

				if (tk.categorie != Categories.OPA_NEGA
						&& (lastTk != null && tk.categorie == Categories.CTE_INT
								&& (lastTk.categorie == Categories.CTE_INT || lastTk.categorie == Categories.ID)
								&& tk.positionY == lastTk.positionY)
						|| (lastTk != null && tk.categorie == Categories.CTE_FLOAT
								&& (lastTk.categorie == Categories.CTE_FLOAT || lastTk.categorie == Categories.ID)
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

		if (firstTk.categorie == Categories.COMENTARIO) {
			return nextToken();
		}

		if (firstTk.categorie == Categories.CTE_CAD_CH) {
			String auxStr = firstTk.lexema;
			for (int i = 1; i < auxStr.length(); i++) {
				if (auxStr.charAt(i) == '"' && auxStr.charAt(i - 1) != '\\') {
					stopPositionX -= auxStr.length() - i;
					firstTk.lexema = auxStr.substring(0, i);
					break;
				}
			}
		}

		if (firstTk.categorie == Categories.TK_ER_STR || firstTk.categorie == Categories.TK_ER_CH) {
			firstTk = characterErrorCollector(firstTk, linha.substring(firstTk.positionX + 1));
		}

		return firstTk;
	}

	private static Token characterErrorCollector(Token tk, String str) {

		if (tk.categorie == Categories.TK_ER_STR) {
			tk.lexema += str;
			stopPositionX += str.length();
		}

		if (tk.categorie == Categories.TK_ER_CH && str.length() >= 1) {

			tk.lexema += str.charAt(0);
			stopPositionX += 1;
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
			posX = regexMatcher.start() + stopPositionX;

			Token tk = new Token(posX);
			tk.lexema = lexema;

			stopPositionX = stopPositionX + regexMatcher.end();
			return tk;
		}

		return null;
	}

	public String toString() {
		String string = "[" + String.format("%03d", this.positionY + 1) + ", "
				+ String.format("%03d", this.positionX + 1) + "] (" + String.format("%04d", this.categorieNumber) + ", "
				+ String.format("%1$10s", this.categorie.name()) + ") {" + this.lexema + "}";

		if(this.categorie == Categories.TK_ER_CH) {
			string += ". Erro: ' esperado na posição [" + String.format("%03d", this.positionY + 1) + ", " + String.format("%03d", this.positionX + this.lexema.length() + 1) + "]";
		} else if(this.categorie == Categories.TK_ER_STR) {
			string += ". Erro: \" esperado na posição [" + String.format("%03d", this.positionY + 1) + ", " + String.format("%03d", this.positionX + this.lexema.length() + 1) + "]";
		} else if(this.categorie == Categories.TK_ER_NID) {
			string += ". Erro: Token desconhecido.";
		}
		
		return string;
	}

}
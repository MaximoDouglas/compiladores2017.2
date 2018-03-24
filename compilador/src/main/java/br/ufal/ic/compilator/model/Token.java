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
		String line = Lexer.getLine(stopLineIndex);
		int spaces;
		tabAux = 0;

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

			line = removeFinalSpaces(line);
			spaces = getSpaces(line);
			stopColIndex = spaces;

		}

		if (stopColIndex >= line.length() && stopLineIndex == Lexer.getNumberOfLines() - 1) {
			return null;
		}

		int initialcolIndex = stopColIndex;
		int finalcolIndex = stopColIndex;

		Token firstTk = new Token(line.length());

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

				if ((tk.categorie == Categories.CTE_CAD_CH && tk.lexema.charAt(0) == '"')
						|| tk.categorie == Categories.CTE_CHAR) {
					tk.lexema = tk.lexema.substring(1, tk.lexema.length() - 1);
				}

				if ((stopColIndex == 1 && tk.categorie == Categories.OPA_SUB)
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
						&& tk.lineIndex == lastTk.lineIndex)
						|| (lastTk != null && tk.categorie == Categories.CTE_FLOAT
						&& (lastTk.categorie == Categories.CTE_FLOAT || lastTk.categorie == Categories.ID)
						&& tk.lineIndex == lastTk.lineIndex)) {
					tk.categorie = Categories.OPA_SUB;
					tk.lexema = "-";
					stopColIndex = tk.colIndex + 1;
				}

				tk.categorieNumber = tk.categorie.ordinal();

				if (tk.colIndex < firstTk.colIndex) {
					firstTk = tk;
					finalcolIndex = stopColIndex;
				}
			}

			stopColIndex = initialcolIndex;
		}

		stopColIndex = finalcolIndex;
		lastTk = firstTk;

		if (firstTk.categorie == Categories.COMENTARIO) {
			return nextToken();
		}

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
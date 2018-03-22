package br.ufal.ic.compilator.model;

import java.util.ArrayList;
import java.util.List;
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

	// Conta espaços em branco no começo da linha
	public static int getSpaces(String str) {
		int i = 0, count = 0;
		while (str.length() != 0 && str.charAt(i) == ' ') {
			count++;
			i++;
		}
		return count;
	}

	// Remove espaços em branco no final da linha
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
		String linha = Runner.getNextLine(stopPositionY);
		int spaces;

		while (linha.trim().equals("")) {
			stopPositionY++;
			linha = Runner.getNextLine(stopPositionY);
		}
		linha = removeFinalSpaces(linha);
		spaces = getSpaces(linha);

		while (stopPositionX >= linha.length() && stopPositionY < Runner.getLines() - 1) {
			stopPositionY++;
			linha = Runner.getNextLine(stopPositionY);

			while (linha.trim().equals("") && stopPositionY < Runner.getLines() - 1) {
				stopPositionY++;
				linha = Runner.getNextLine(stopPositionY);
			}

			linha = removeFinalSpaces(linha);
			spaces = getSpaces(linha);
			stopPositionX = spaces;
			System.out.println("---------------------------------------------"); // pra ficar mais claro os tokens por
			// linha
		}

		if (stopPositionX >= linha.length() && stopPositionY == Runner.getLines() - 1) {
			return null;
		}

		int initialPositionX = stopPositionX;
		int finalPositionX = stopPositionX;

		Token firstTk = new Token(linha.length());
		Token tempTK = firstTk;

		for (int i = 0; i < TokenService.getExpressoes().size(); i++) {
			Token tk = regexChecker(TokenService.getExpressoes().get(Categories.values()[i]),
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

		// Testa se o token que vai ser retornado é o primeiro (lastTk ainda é null). Se
		// for, verifica se ele começa no começo da linha, caso contrário, pode haver
		// algo antes dele que não foi identificado.
		// Nesse caso, manda esse pedaço da linha para análise
		if (lastTk == null && firstTk.positionX > 0) {
			
			System.out.println("aqui" + linha.substring(0, firstTk.positionX));
			Token testTk = notIdentifiedErrorCollector(linha.substring(0, firstTk.positionX));
			if (testTk != null) {
				firstTk = testTk;
			}
		} else if (firstTk == tempTK) { // Verifica se o firstTk não foi modificado. Caso não tenha sido (ainda é igual
			// a antes das verificações) significa que nenhum token foi encontrado nessa
			// passagem. Manda para análise o mesmo trecho de linha que recebeu.
			firstTk = notIdentifiedErrorCollector(linha.substring(stopPositionX));
		} else if (firstTk.positionX > stopPositionX) { // Verifica se o firstTk não foi modificado. Caso não tenha sido (ainda é igual
			System.out.println("Aqui");
			firstTk = notIdentifiedErrorCollector(linha.substring(stopPositionX, firstTk.positionX));
		} else { // Esse else serve apenas para modificar o stopPosition. Caso não ocorra
			// problemas, ele modifica a posição adequadamente, como era feito antes dessas
			// modificações.
			stopPositionX = finalPositionX;
		}

		//stopPositionX = finalPositionX;
		lastTk = firstTk;

		if (firstTk.categorie == Categories.COMENTARIO) {
			return nextToken();
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

	private static Token notIdentifiedErrorCollector(String str) {
		String tempStr = str.trim();
		if (tempStr.length() > 0) {
			Token tk = new Token(stopPositionX + getSpaces(str));
			tk.categorie = Categories.TK_ER_NID;
			tk.categorieNumber = tk.categorie.ordinal();
			stopPositionX += str.length();
			tk.lexema = str.trim();
			tk.positionY = stopPositionY;
			return tk;
		}

		return null;
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
				+ this.categorie.name() + ") {" + this.lexema + "}";

		return string;
	}

}
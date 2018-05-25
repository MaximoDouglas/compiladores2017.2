package br.ufal.ic.compilator.runner;

import java.io.IOException;

import br.ufal.ic.compilator.model.Lexer;
import br.ufal.ic.compilator.model.Table;
import br.ufal.ic.compilator.model.Token;

public class Runner {

	public static void main(String[] args) {
		Table tab = new Table();
		try {
			tab.populateTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (args.length > 0) {
			Lexer lexer = new Lexer(args[0]);

			if (lexer.isEmpty()) {
				System.out.println("Arquivo vazio!");
				return;
			}

			getTokens(lexer, tab);
		} else {
			System.out.println("Nenhum arquivo passado!");
		}

	}

	private static void getTokens(Lexer lexer, Table tab) {
		Token tk = lexer.getNextToken();
		while (tk != null) {
			tk = lexer.getNextToken();
		}
	}
}
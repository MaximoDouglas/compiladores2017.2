package br.ufal.ic.compilator.runner;

import br.ufal.ic.compilator.model.Lexer;
import br.ufal.ic.compilator.model.Token;

public class Runner {

	public static void main(String[] args){
		if (args.length > 0) {
			Lexer lexer = new Lexer(args[0]);
			
			if(lexer.isEmpty()) {
				System.out.println("Arquivo vazio!");
				return;
			}
			
			getTokens(lexer);
		} else {
			System.out.println("Nenhum arquivo passado!");
		}
		
	}

	private static void getTokens(Lexer lexer) {
		Token tk = lexer.getNextToken();
		
		while(tk != null) {
			System.out.println(tk.toString());
			tk = lexer.getNextToken();
		}
	}
}
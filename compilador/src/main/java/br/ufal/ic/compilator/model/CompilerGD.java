package br.ufal.ic.compilator.model;

import java.util.Stack;

public class CompilerGD {

	private Lexer lexer;
	Table tab;

	public CompilerGD(String[] args) {
		tab = new Table();
		lexer = new Lexer(args[0]);
	}

	@SuppressWarnings({"unused"})
	public void run() {
		Stack<String> pilha = new Stack<String>();
		pilha.push("Programa");

		Token tokenAtual = lexer.getNextToken();
		String producao = null;

		while (!pilha.isEmpty() && tokenAtual != null) {
			String topoPilha = pilha.peek();

			if (topoPilha.equals(tokenAtual.getCategorie().toString().toLowerCase())) {

				pilha.pop();
				tokenAtual = lexer.getNextToken();
				
			} else {
				producao = tab.getDerivation(topoPilha, tokenAtual.getCategorie().toString().toLowerCase());
				
				System.out.println("          " + producao);
				
				String[] prodAux = producao.split("=");
				String[] itensProducao = prodAux[1].trim().split(" ");

				if (!itensProducao[0].equals("epsilon")) {
					pilha.pop();
					
					for (int i = itensProducao.length - 1; i >= 0 ; i--) {
						
						if (itensProducao[i].endsWith("'")) {
							itensProducao[i] = itensProducao[i].substring(1, itensProducao[i].length() - 1).trim();
						}
						
						pilha.push(itensProducao[i]);
					}
				} else {
					pilha.pop();
				}
			}						
		}
	}

	public boolean isEmpty() {
		return lexer.isEmpty();
	}

}

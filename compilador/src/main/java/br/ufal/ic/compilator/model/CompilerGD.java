package br.ufal.ic.compilator.model;

import java.util.Stack;

import javafx.util.Pair;

public class CompilerGD {
	private boolean accepted = true;
	private Lexer lexer;
	private Table tab;

	public CompilerGD(String[] args) {
		tab = new Table();
		lexer = new Lexer(args[0]);
	}

	public void run() {
		Stack<String> pilha = new Stack<String>();
		pilha.push("Programa");

		Token tokenAtual = lexer.getNextToken();
		String producao = null;

		while (!pilha.isEmpty() && tokenAtual != null) {
			String topoPilha = pilha.peek();

			if (topoPilha.equals(tokenAtual.getCategorie().toString().toLowerCase())) {
				System.out.println("              " + tokenAtual.toString());
				pilha.pop();
				tokenAtual = lexer.getNextToken();
			} else {				
				producao = tab.getDerivation(topoPilha, tokenAtual.getCategorie().toString().toLowerCase());
								
				if(producao == null) {
					System.out.println();
					System.out.println("ERRO: sintaxe incorreta! Análise encerrada!");
					accepted = false;
					break;
				}
				
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
		
		if(accepted) {
			System.out.println();
			System.out.println("Sintaxe correta! Análise encerrada!");
		}
	}

	public boolean isEmpty() {
		return lexer.isEmpty();
	}

}

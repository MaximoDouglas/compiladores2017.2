package br.ufal.ic.compilator.runner;

import br.ufal.ic.compilator.model.CompilerGD;

public class Runner {

	public static void main(String[] args) {
		CompilerGD compilador = null;
		
		if (args.length > 0) {
			
			compilador = new CompilerGD(args);
			
			if (compilador.isEmpty()) {
				System.out.println("Arquivo vazio!");
				return;
			}
		} else {
			System.out.println("Nenhum arquivo passado!");
		}
		
		if (compilador != null) {
			compilador.run();			
		}

	}
}
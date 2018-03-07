package br.ufal.ic.compilator.model;

import java.util.ArrayList;

public class TokenService {
	private static ArrayList<String> expressoes = new ArrayList<String>();
	
	public static void fillExpressoes () {
		
		for (int i = 0; i < Categories.values().length; i++) {
			expressoes.add("-");
		}
		
		expressoes.add(Categories.MAIN.ordinal(), "main");
		expressoes.add(Categories.AB_CHAVE.ordinal(), "[{]");
		expressoes.add(Categories.AB_COLCHET.ordinal(), "[[]");
		expressoes.add(Categories.AB_PARENTE.ordinal(), "[(]");
		expressoes.add(Categories.TIPO_VOID.ordinal(), "void");
	}
	
	public static ArrayList<String> getExpressoes(){
		return expressoes;
	}
	
}

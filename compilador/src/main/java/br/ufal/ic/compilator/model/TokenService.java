package br.ufal.ic.compilator.model;

import java.util.ArrayList;

public class TokenService {
	private static ArrayList<String> expressoes = new ArrayList();
	
	public static void fillExpressoes () {
		
		expressoes.add("main".toString());
		expressoes.add("[;]".toString());
		expressoes.add("[,]".toString());
		expressoes.add("[(]".toString());
		expressoes.add("[)]".toString());
		expressoes.add("[{]".toString());
		expressoes.add("[}]".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("[=]".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("-".toString());
		expressoes.add("int".toString());
		expressoes.add("float".toString());
		expressoes.add("boolean".toString());
		expressoes.add("char".toString());
		expressoes.add("void".toString());
		expressoes.add("[+]".toString());
		expressoes.add("[-]".toString());
		expressoes.add("[*]".toString());
		expressoes.add("[/]".toString());
		expressoes.add("==".toString());
		expressoes.add("!=".toString());
		expressoes.add("[<]".toString());
		expressoes.add("<=".toString());
		expressoes.add("[>]".toString());
		expressoes.add(">=".toString());
		expressoes.add("-".toString());
		expressoes.add("[&]".toString());
		expressoes.add("[!]".toString());
		expressoes.add("-".toString());
		expressoes.add("return".toString());
		expressoes.add("if".toString());
		expressoes.add("else".toString());
		expressoes.add("for".toString());
		expressoes.add("while".toString());
		expressoes.add("read".toString());
		expressoes.add("print".toString());
		expressoes.add("println".toString());
		expressoes.add("-".toString());
	}
	
	public static ArrayList<String> getExpressoes(){
		return expressoes;
	}
	
}

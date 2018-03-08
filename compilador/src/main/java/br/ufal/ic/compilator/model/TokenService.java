package br.ufal.ic.compilator.model;

import java.util.ArrayList;

public class TokenService {
	private static ArrayList<String> expressoes = new ArrayList();
	
	public static void fillExpressoes () {
		
		expressoes.add("main"); //MAIN
		expressoes.add("[;]"); //PONT_VIRG
		expressoes.add("[,]"); //VIRGULA
		expressoes.add("[(]"); //AB_PARENTE
		expressoes.add("[)]"); //FE_PARENTE
		expressoes.add("[{]"); //AB_CHAVE
		expressoes.add("[}]"); //FE_CHAVE
		expressoes.add("-"); //AB_COLCHET
		expressoes.add("-"); //FE_COLCHET
		expressoes.add("-"); //ID
		expressoes.add("[=]"); //ATRIBUICAO
		expressoes.add("-"); //CTE_INT
		expressoes.add("-"); //CTE_FLOAT
		expressoes.add("-"); //CTE_BOOL
		expressoes.add("-"); //CTE_CHAR
		expressoes.add("-"); //CTE_CAD_IN
		expressoes.add("-"); //CTE_CAD_FL
		expressoes.add("-"); //CTE_CAD_BO
		expressoes.add("\"[^\\r\\n]*\""); //CTE_CAD_CH
		expressoes.add("int"); //TIPO_INT
		expressoes.add("float"); //TIPO_FLOAT
		expressoes.add("boolean"); //TIPO_BOOL
		expressoes.add("char"); //TIPO_CHAR
		expressoes.add("void"); //TIPO_VOID
		expressoes.add("[+]"); //OPA_AD
		expressoes.add("[-]"); //OPA_SUB
		expressoes.add("[*]"); //OPA_MULT
		expressoes.add("-"); //OPA_DIV
		expressoes.add("=="); //OPR_IGUAL
		expressoes.add("!="); //OPR_DIF
		expressoes.add("[<]"); //OPR_MEN
		expressoes.add("<="); //OPR_MEN_IG
		expressoes.add("[>]"); //OPR_MAI
		expressoes.add(">="); //OPR_MAI_IG
		expressoes.add("\\|"); //OPL_OU
		expressoes.add("[&]"); //OPL_E
		expressoes.add("[!]"); //OPL_NAO
		expressoes.add("-"); //OP_CONC
		expressoes.add("return"); //RETORNO
		expressoes.add("if"); //SE
		expressoes.add("else"); //SENAO
		expressoes.add("for"); //FOR
		expressoes.add("while"); //WHILE
		expressoes.add("read"); //ENTRADA
		expressoes.add("print"); //SAIDA
		expressoes.add("println"); //SAIDA_LN
		expressoes.add("//.*$"); //COMENTARIO
	}
	
	public static ArrayList<String> getExpressoes(){
		return expressoes;
	}
	
}

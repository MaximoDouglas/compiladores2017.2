package br.ufal.ic.compilator.model;

import java.util.ArrayList;

public class TokenService {
	private static ArrayList<String> expressoes = new ArrayList();
	
	public static void fillExpressoes () {
		// | 
		
		expressoes.add("main"); //MAIN
		expressoes.add("[;]"); //PONT_VIRG
		expressoes.add("[,]"); //VIRGULA
		expressoes.add("[(]"); //AB_PARENTE
		expressoes.add("[)]"); //FE_PARENTE
		expressoes.add("[{]"); //AB_CHAVE
		expressoes.add("[}]"); //FE_CHAVE
		expressoes.add("[x]"); //ID               EM FALTA
		expressoes.add("=="); //OPR_IGUAL
		expressoes.add("[=]"); //ATRIBUICAO
		expressoes.add("(\"[^\\r\\n]*\")|(\\['\\w'([,]'\\w')*\\])"); //CTE_CAD_CH
		expressoes.add("'\\w'"); //CTE_CHAR
		expressoes.add("\\[((\\-)?\\d+\\.(\\d+)?)|((\\-)?(\\d+)?\\.\\d+)(,((\\-)?\\d+\\.(\\d+)?)|((\\-)?(\\d+)?\\.\\d+))*\\]"); //CTE_CAD_FL   consertar!!!!
		expressoes.add("((\\-)?\\d+\\.(\\d+)?)|((\\-)?(\\d+)?\\.\\d+)"); //CTE_FLOAT
		expressoes.add("\\[(\\-)?\\d+(,(\\-)?\\d+)*\\]"); //CTE_CAD_IN
		expressoes.add("(\\-)?\\d+"); //CTE_INT
		expressoes.add("\\[(true|false)(,(true|false))*\\]"); //CTE_CAD_BO
		expressoes.add("true|false"); //CTE_BOOL
		expressoes.add("\\["); //AB_COLCHET
		expressoes.add("\\]"); //FE_COLCHET
		expressoes.add("int"); //TIPO_INT
		expressoes.add("float"); //TIPO_FLOAT
		expressoes.add("boolean"); //TIPO_BOOL
		expressoes.add("char"); //TIPO_CHAR
		expressoes.add("void"); //TIPO_VOID
		expressoes.add("\\+\\+"); //OP_CONC
		expressoes.add("[+]"); //OPA_AD
		expressoes.add("$-$"); //OPA_SUB
		expressoes.add("[*]"); //OPA_MULT
		expressoes.add("\\/"); //OPA_DIV
		expressoes.add("!="); //OPR_DIF
		expressoes.add("[<]"); //OPR_MEN
		expressoes.add("<="); //OPR_MEN_IG
		expressoes.add("[>]"); //OPR_MAI
		expressoes.add(">="); //OPR_MAI_IG
		expressoes.add("\\|"); //OPL_OU
		expressoes.add("[&]"); //OPL_E
		expressoes.add("[!]"); //OPL_NAO
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

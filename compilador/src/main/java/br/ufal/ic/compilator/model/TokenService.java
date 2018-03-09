package br.ufal.ic.compilator.model;

import java.util.HashMap;
import java.util.Map;

public class TokenService {

	private static Map<Categories, String> expressoesECategorias = new HashMap<Categories, String>();
	private static Map<String, Categories> reservedWords = new HashMap<String, Categories>();

	public static void fillExpressoes () {
		expressoesECategorias.put(Categories.MAIN, "main");
		expressoesECategorias.put(Categories.PONT_VIRG, "[;]"); 
		expressoesECategorias.put(Categories.VIRGULA, "[,]"); 
		expressoesECategorias.put(Categories.AB_PARENTE, "[(]"); 
		expressoesECategorias.put(Categories.FE_PARENTE, "[)]");
		expressoesECategorias.put(Categories.AB_CHAVE, "[{]"); 
		expressoesECategorias.put(Categories.FE_CHAVE, "[}]"); 
		expressoesECategorias.put(Categories.OPR_IGUAL, "==");
		expressoesECategorias.put(Categories.ATRIBUICAO, "[=]"); 
		expressoesECategorias.put(Categories.CTE_CAD_CH, "(\"[^\\r\\n]*\")|(\\['\\w'([,]'\\w')*\\])"); 
		expressoesECategorias.put(Categories.CTE_CHAR, "'\\w'"); 
		expressoesECategorias.put(Categories.CTE_CAD_FL, "\\[((\\-)?\\d+\\.(\\d+)?)|((\\-)?(\\d+)?\\.\\d+)(,((\\-)?\\d+\\.(\\d+)?)|((\\-)?(\\d+)?\\.\\d+))*\\]");	//consertar!!!!
		expressoesECategorias.put(Categories.CTE_FLOAT, "((\\-)?\\d+\\.(\\d+)?)|((\\-)?(\\d+)?\\.\\d+)"); 
		expressoesECategorias.put(Categories.CTE_CAD_IN, "\\[(\\-)?\\d+(,(\\-)?\\d+)*\\]"); 
		expressoesECategorias.put(Categories.CTE_INT, "(\\-)?\\d+"); 
		expressoesECategorias.put(Categories.CTE_CAD_BO, "\\[(true|false)(,(true|false))*\\]"); 
		expressoesECategorias.put(Categories.CTE_BOOL, "true|false"); 
		expressoesECategorias.put(Categories.AB_COLCHET, "\\["); 
		expressoesECategorias.put(Categories.FE_COLCHET, "\\]"); 
		expressoesECategorias.put(Categories.TIPO_INT, "int"); 
		expressoesECategorias.put(Categories.TIPO_FLOAT, "float"); 
		expressoesECategorias.put(Categories.TIPO_BOOL, "boolean"); 
		expressoesECategorias.put(Categories.TIPO_CHAR, "char"); 
		expressoesECategorias.put(Categories.TIPO_VOID, "void"); 
		expressoesECategorias.put(Categories.OP_CONC, "\\+\\+"); 
		expressoesECategorias.put(Categories.OPA_AD, "[+]"); 
		expressoesECategorias.put(Categories.OPA_SUB, "$-$"); 
		expressoesECategorias.put(Categories.OPA_MULT, "[*]"); 
		expressoesECategorias.put(Categories.COMENTARIO, "//.*$"); 
		expressoesECategorias.put(Categories.OPA_DIV, "\\/"); 
		expressoesECategorias.put(Categories.OPR_DIF, "!="); 
		expressoesECategorias.put(Categories.OPR_MEN, "[<]"); 
		expressoesECategorias.put(Categories.OPR_MEN_IG, "<="); 
		expressoesECategorias.put(Categories.OPR_MAI, "[>]"); 
		expressoesECategorias.put(Categories.OPR_MAI_IG, ">="); 
		expressoesECategorias.put(Categories.OPL_OU, "\\|"); 
		expressoesECategorias.put(Categories.OPL_E, "[&]"); 
		expressoesECategorias.put(Categories.OPL_NAO, "[!]");
		expressoesECategorias.put(Categories.SE, "if");
		expressoesECategorias.put(Categories.RETORNO, "return");
		expressoesECategorias.put(Categories.SENAO, "else"); 
		expressoesECategorias.put(Categories.FOR, "for"); 
		expressoesECategorias.put(Categories.WHILE, "while"); 
		expressoesECategorias.put(Categories.ENTRADA, "read"); 
		expressoesECategorias.put(Categories.SAIDA, "print"); 
		expressoesECategorias.put(Categories.SAIDA_LN, "println");
		expressoesECategorias.put(Categories.ID, "[a-zA-Z_][a-zA-Z0-9_]{0,30}");

		reservedWords.put("main", Categories.MAIN);
		reservedWords.put("true", Categories.CTE_BOOL);
		reservedWords.put("false", Categories.CTE_BOOL);
		reservedWords.put("int", Categories.TIPO_INT); 
		reservedWords.put("float", Categories.TIPO_FLOAT ); 
		reservedWords.put("boolean", Categories.TIPO_BOOL ); 
		reservedWords.put("char", Categories.TIPO_CHAR ); 
		reservedWords.put("void", Categories.TIPO_VOID );
		reservedWords.put("return", Categories.RETORNO );
		reservedWords.put("else", Categories.SENAO ); 
		reservedWords.put("for", Categories.FOR); 
		reservedWords.put("while", Categories.WHILE ); 
		reservedWords.put("read", Categories.ENTRADA ); 
		reservedWords.put("print", Categories.SAIDA ); 
		reservedWords.put("println", Categories.SAIDA_LN );
		reservedWords.put("if", Categories.SE);
	}

	public static Map<Categories, String> getExpressoes(){
		return expressoesECategorias;
	}
	
	public static Map<String, Categories> getReserved(){
		return reservedWords;
	}
}

package br.ufal.ic.compilator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.util.Pair;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@SuppressWarnings({ "unused", "restriction" })
public class Table {
	// A chave é no formao "NaoTerminal-Terminal", o valor é a produção
	@SuppressWarnings("rawtypes")
	Map<Pair, String> table = new HashMap<Pair, String>();
	File file = new File("Exemplos/tabela.txt"); // Para planilha
	// File file = new File("../Exemplos/tabela.txt"); // Para preenchimento da hash

	private String inputFile;

	public Table() {
		this.populateTable();
	}

	// populateTable(), getStack() e getDerivation() são importantes para a execução
	// do
	// analisador
	// main(0 e read() servem apenas para ler planilha (DEVEM SER RETIRADOS QUANDO A
	// GRAMÁTICA ESTIVER FINALIZADO)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTable() {
		table.put(new Pair("Bloco","'id'"),"Bloco = 'id' BlocoAux");
		table.put(new Pair("Range","'id'"),"Range = ExpArit RangeAux");
		table.put(new Pair("Else","'id'"),"Else = epsilon");
		table.put(new Pair("LArgs","'id'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'id'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'id'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'id'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'id'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'id'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'id'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'id'"),"FatAritRes = 'id' CFuncAux");
		table.put(new Pair("LParV","'ab_parente'"),"LParV = epsilon");
		table.put(new Pair("Range","'ab_parente'"),"Range = ExpArit RangeAux");
		table.put(new Pair("BlocoAux","'ab_parente'"),"BlocoAux = CompletaFc 'pont_virg' Bloco");
		table.put(new Pair("CompletaFc","'ab_parente'"),"CompletaFc = 'ab_parente' LArgs 'fe_parente'");
		table.put(new Pair("LArgs","'ab_parente'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'ab_parente'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'ab_parente'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'ab_parente'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'ab_parente'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'ab_parente'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'ab_parente'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'ab_parente'"),"FatAritRes = 'ab_parente' ExpBool 'fe_parente'");
		table.put(new Pair("CFuncAux","'ab_parente'"),"CFuncAux = CompletaFc");
		table.put(new Pair("LParam","'fe_parente'"),"LParam = epsilon");
		table.put(new Pair("LParAux","'fe_parente'"),"LParAux = LParV LParAuxRes");
		table.put(new Pair("LParAuxRes","'fe_parente'"),"LParAuxRes = epsilon");
		table.put(new Pair("LParV","'fe_parente'"),"LParV = epsilon");
		table.put(new Pair("PrintAux","'fe_parente'"),"PrintAux = epsilon");
		table.put(new Pair("LId","'fe_parente'"),"LId = epsilon");
		table.put(new Pair("LIdAux","'fe_parente'"),"LIdAux = LId");
		table.put(new Pair("RangeAux","'fe_parente'"),"RangeAux = epsilon");
		table.put(new Pair("LArgs","'fe_parente'"),"LArgs = epsilon");
		table.put(new Pair("LArgsAux","'fe_parente'"),"LArgsAux = epsilon");
		table.put(new Pair("TermConcat","'fe_parente'"),"TermConcat = epsilon");
		table.put(new Pair("FatBool","'fe_parente'"),"FatBool = epsilon");
		table.put(new Pair("TermBoRes","'fe_parente'"),"TermBoRes = epsilon");
		table.put(new Pair("TermRel","'fe_parente'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'fe_parente'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'fe_parente'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'fe_parente'"),"CFuncAux = epsilon");
		table.put(new Pair("Programa","'tipo_void'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_void'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_void'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_void'"),"Funcao = 'tipo_void' 'id' LParV 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco Retorno");
		table.put(new Pair("Retorno","'return'"),"Retorno = 'return' ExpConcat 'pont_virg' 'fe_chave'");
		table.put(new Pair("Bloco","'return'"),"Bloco = epsilon");
		table.put(new Pair("Else","'return'"),"Else = epsilon");
		table.put(new Pair("DeclFim","'pont_virg'"),"DeclFim = 'pont_virg'");
		table.put(new Pair("TermConcat","'pont_virg'"),"TermConcat = epsilon");
		table.put(new Pair("FatBool","'pont_virg'"),"FatBool = epsilon");
		table.put(new Pair("TermBoRes","'pont_virg'"),"TermBoRes = epsilon");
		table.put(new Pair("TermRel","'pont_virg'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'pont_virg'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'pont_virg'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'pont_virg'"),"CFuncAux = epsilon");
		table.put(new Pair("Retorno","'fe_chave'"),"Retorno = 'fe_chave'");
		table.put(new Pair("Bloco","'fe_chave'"),"Bloco = epsilon");
		table.put(new Pair("Else","'fe_chave'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_boolean'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_boolean'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_boolean'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_boolean'"),"Funcao = Tipo 'id' LParV 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco Retorno");
		table.put(new Pair("Tipo","'tipo_boolean'"),"Tipo = 'tipo_boolean'");
		table.put(new Pair("LParam","'tipo_boolean'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_boolean'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_boolean'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_boolean'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_int'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_int'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_int'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_int'"),"Funcao = Tipo 'id' LParV 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco Retorno");
		table.put(new Pair("Tipo","'tipo_int'"),"Tipo = 'tipo_int'");
		table.put(new Pair("LParam","'tipo_int'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_int'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_int'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_int'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_float'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_float'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_float'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_float'"),"Funcao = Tipo 'id' LParV 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco Retorno");
		table.put(new Pair("Tipo","'tipo_float'"),"Tipo = 'tipo_float'");
		table.put(new Pair("LParam","'tipo_float'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_float'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_float'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_float'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_char'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_char'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_char'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_char'"),"Funcao = Tipo 'id' LParV 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco Retorno");
		table.put(new Pair("Tipo","'tipo_char'"),"Tipo = 'tipo_char'");
		table.put(new Pair("LParam","'tipo_char'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_char'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_char'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_char'"),"Else = epsilon");
		table.put(new Pair("LParAux","'virgula'"),"LParAux = LParV LParAuxRes");
		table.put(new Pair("LParAuxRes","'virgula'"),"LParAuxRes = 'virgula' Tipo 'id' LParAux");
		table.put(new Pair("LParV","'virgula'"),"LParV = epsilon");
		table.put(new Pair("PrintAux","'virgula'"),"PrintAux = 'virgula' LArgs");
		table.put(new Pair("LId","'virgula'"),"LId = 'virgula' 'id' LIdAux");
		table.put(new Pair("LIdAux","'virgula'"),"LIdAux = LId");
		table.put(new Pair("RangeAux","'virgula'"),"RangeAux = 'virgula' ExpArit");
		table.put(new Pair("LArgsAux","'virgula'"),"LArgsAux = 'virgula' ExpConcat LArgsAux");
		table.put(new Pair("TermConcat","'virgula'"),"TermConcat = epsilon");
		table.put(new Pair("FatBool","'virgula'"),"FatBool = epsilon");
		table.put(new Pair("TermBoRes","'virgula'"),"TermBoRes = epsilon");
		table.put(new Pair("TermRel","'virgula'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'virgula'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'virgula'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'virgula'"),"CFuncAux = epsilon");
		table.put(new Pair("LParAux","'ab_colchet'"),"LParAux = LParV LParAuxRes");
		table.put(new Pair("LParV","'ab_colchet'"),"LParV = 'ab_colchet' 'fe_colchet'");
		table.put(new Pair("DeclFim","'ab_colchet'"),"DeclFim = 'ab_colchet' ExpConcat 'fe_colchet' 'pont_virg'");
		table.put(new Pair("LIdAux","'ab_colchet'"),"LIdAux = 'ab_colchet' ExpConcat 'fe_colchet' LId");
		table.put(new Pair("BlocoAux","'ab_colchet'"),"BlocoAux = 'ab_colchet' ExpArit 'fe_colchet' Atribuicao Bloco");
		table.put(new Pair("CFuncAux","'ab_colchet'"),"CFuncAux = 'ab_colchet' ExpConcat 'fe_colchet'");
		table.put(new Pair("TermConcat","'fe_colchet'"),"TermConcat = epsilon");
		table.put(new Pair("FatBool","'fe_colchet'"),"FatBool = epsilon");
		table.put(new Pair("TermBoRes","'fe_colchet'"),"TermBoRes = epsilon");
		table.put(new Pair("TermRel","'fe_colchet'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'fe_colchet'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'fe_colchet'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'fe_colchet'"),"CFuncAux = epsilon");
		table.put(new Pair("Bloco","'print'"),"Bloco = Print Bloco");
		table.put(new Pair("Print","'print'"),"Print = 'print' 'ab_parente' 'cte_cad_ch' PrintAux 'fe_parente' 'pont_virg'");
		table.put(new Pair("Else","'print'"),"Else = epsilon");
		table.put(new Pair("Range","'cte_cad_ch'"),"Range = ExpArit RangeAux");
		table.put(new Pair("LArgs","'cte_cad_ch'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_cad_ch'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'cte_cad_ch'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'cte_cad_ch'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'cte_cad_ch'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'cte_cad_ch'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'cte_cad_ch'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'cte_cad_ch'"),"FatAritRes = 'cte_cad_ch'");
		table.put(new Pair("Bloco","'println'"),"Bloco = Print Bloco");
		table.put(new Pair("Print","'println'"),"Print = 'println' 'ab_parente' 'cte_cad_ch' PrintAux 'fe_parente' 'pont_virg'");
		table.put(new Pair("Else","'println'"),"Else = epsilon");
		table.put(new Pair("Bloco","'read'"),"Bloco = Read Bloco");
		table.put(new Pair("Read","'read'"),"Read = 'read' 'ab_parente' 'cte_cad_ch' LId 'fe_parente' 'pont_virg'");
		table.put(new Pair("Else","'read'"),"Else = epsilon");
		table.put(new Pair("Bloco","'for'"),"Bloco = For Bloco");
		table.put(new Pair("For","'for'"),"For = 'for' 'id' 'in' 'ab_parente' Range 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Else","'for'"),"Else = epsilon");
		table.put(new Pair("Bloco","'while'"),"Bloco = While Bloco");
		table.put(new Pair("While","'while'"),"While = 'while' 'ab_parente' ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Else","'while'"),"Else = epsilon");
		table.put(new Pair("Bloco","'if'"),"Bloco = If Bloco");
		table.put(new Pair("If","'if'"),"If = 'if' 'ab_parente' ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave' Else");
		table.put(new Pair("Else","'if'"),"Else = epsilon");
		table.put(new Pair("Else","'else'"),"Else = 'else' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("BlocoAux","'atribuicao'"),"BlocoAux = Atribuicao Bloco");
		table.put(new Pair("Atribuicao","'atribuicao'"),"Atribuicao = 'atribuicao' ExpConcat 'pont_virg'");
		table.put(new Pair("TermConcat","'op_conc'"),"TermConcat = 'op_conc' ExpBool TermConcat");
		table.put(new Pair("FatBool","'op_conc'"),"FatBool = epsilon");
		table.put(new Pair("TermBoRes","'op_conc'"),"TermBoRes = epsilon");
		table.put(new Pair("TermRel","'op_conc'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'op_conc'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'op_conc'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'op_conc'"),"CFuncAux = epsilon");
		table.put(new Pair("FatBool","'opl_ou'"),"FatBool = 'opl_ou' TermBool FatBool");
		table.put(new Pair("TermBoRes","'opl_ou'"),"TermBoRes = epsilon");
		table.put(new Pair("TermRel","'opl_ou'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'opl_ou'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'opl_ou'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'opl_ou'"),"CFuncAux = epsilon");
		table.put(new Pair("TermBoRes","'opl_e'"),"TermBoRes = 'opl_e' ExpRel TermBoRes");
		table.put(new Pair("TermRel","'opl_e'"),"TermRel = epsilon");
		table.put(new Pair("FatArit","'opl_e'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'opl_e'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'opl_e'"),"CFuncAux = epsilon");
		table.put(new Pair("LArgs","'opl_nao'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'opl_nao'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'opl_nao'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'opl_nao'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'opl_nao'"),"ExpRel = 'opl_nao' ExpRel");
		table.put(new Pair("TermRel","'opr'"),"TermRel = 'opr' ExpArit TermRel");
		table.put(new Pair("FatArit","'opr'"),"FatArit = epsilon");
		table.put(new Pair("TermAriRes","'opr'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'opr'"),"CFuncAux = epsilon");
		table.put(new Pair("FatArit","'opa_ad'"),"FatArit = 'opa_ad' TermArit FatArit");
		table.put(new Pair("TermAriRes","'opa_ad'"),"TermAriRes = epsilon");
		table.put(new Pair("CFuncAux","'opa_ad'"),"CFuncAux = epsilon");
		table.put(new Pair("TermAriRes","'opa_mult'"),"TermAriRes = 'opa_mult' FatAritRes TermAriRes");
		table.put(new Pair("CFuncAux","'opa_mult'"),"CFuncAux = epsilon");
		table.put(new Pair("Range","'opa_nega'"),"Range = ExpArit RangeAux");
		table.put(new Pair("LArgs","'opa_nega'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'opa_nega'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'opa_nega'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'opa_nega'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'opa_nega'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'opa_nega'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'opa_nega'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'opa_nega'"),"FatAritRes = 'opa_nega' FatAritRes");
		table.put(new Pair("Range","'cte_int'"),"Range = ExpArit RangeAux");
		table.put(new Pair("LArgs","'cte_int'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_int'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'cte_int'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'cte_int'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'cte_int'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'cte_int'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'cte_int'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'cte_int'"),"FatAritRes = 'cte_int'");
		table.put(new Pair("Range","'cte_float'"),"Range = ExpArit RangeAux");
		table.put(new Pair("LArgs","'cte_float'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_float'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'cte_float'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'cte_float'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'cte_float'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'cte_float'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'cte_float'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'cte_float'"),"FatAritRes = 'cte_float'");
		table.put(new Pair("Range","'cte_char'"),"Range = ExpArit RangeAux");
		table.put(new Pair("LArgs","'cte_char'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_char'"),"ExpConcat = ExpBool TermConcat");
		table.put(new Pair("ExpBool","'cte_char'"),"ExpBool = TermBool FatBool");
		table.put(new Pair("TermBool","'cte_char'"),"TermBool = ExpRel TermBoRes");
		table.put(new Pair("ExpRel","'cte_char'"),"ExpRel = ExpArit TermRel");
		table.put(new Pair("ExpArit","'cte_char'"),"ExpArit = TermArit FatArit");
		table.put(new Pair("TermArit","'cte_char'"),"TermArit = FatAritRes TermAriRes");
		table.put(new Pair("FatAritRes","'cte_char'"),"FatAritRes = 'cte_char'");
		table.put(new Pair("CodAux","$"),"CodAux = epsilon");

		//
		// for (String entry: table.values()) {
		// System.out.println(entry);
		// }
	}

	public String getDerivation(String nTerm, String term) {
		term = "'" + term + "'";
		Pair<String, String> p = new Pair<String, String>(nTerm, term);
		String production = table.get(p);
		return production;
	}

	public void read() throws IOException {
		File inputWorkbook = new File("Exemplos/tabela.xls");
		Workbook w;
		PrintWriter writer = new PrintWriter("Exemplos/tabela.txt", "UTF-8");
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);

			for (int j = 1; j < sheet.getColumns(); j++) {
				for (int i = 1; i < sheet.getRows(); i++) {
					Cell nTerm = sheet.getCell(0, i);
					Cell term = sheet.getCell(j, 0);
					Cell producao = sheet.getCell(j, i);
					if (!nTerm.getContents().equals("") && !term.getContents().equals("")
							&& !producao.getContents().equals("")) {
						String line = "table.put(new Pair(\"" + nTerm.getContents().toString() + "\"," + "\""
								+ term.getContents().toString() + "\"),\"" + producao.getContents().toString() + "\");";
						System.out.println(line);
					}
				}
			}
			writer.close();
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Table test = new Table();
		test.read();
	}

}
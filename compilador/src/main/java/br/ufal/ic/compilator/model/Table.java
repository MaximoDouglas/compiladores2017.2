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
	//File file = new File("../Exemplos/tabela.txt"); // Para preenchimento da hash

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
		table.put(new Pair("LArgs","'id'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'id'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'id'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'id'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'id'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'id'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'id'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'id'"),"ExpAritRes = 'id' CFuncAux");
		table.put(new Pair("Else","'id'"),"Else = epsilon");
		table.put(new Pair("BlocoAux","'ab_parente'"),"BlocoAux = ChamaFunc 'pont_virg' Bloco");
		table.put(new Pair("ChamaFunc","'ab_parente'"),"ChamaFunc = 'ab_parente' LArgs 'fe_parente'");
		table.put(new Pair("LArgs","'ab_parente'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'ab_parente'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'ab_parente'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'ab_parente'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'ab_parente'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'ab_parente'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'ab_parente'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'ab_parente'"),"ExpAritRes = 'ab_parente' ExpBool 'fe_parente'");
		table.put(new Pair("CFuncAux","'ab_parente'"),"CFuncAux = ChamaFunc");
		table.put(new Pair("LParam","'fe_parente'"),"LParam = epsilon");
		table.put(new Pair("LParAux","'fe_parente'"),"LParAux = LParV LParAuxRes");
		table.put(new Pair("LParAuxRes","'fe_parente'"),"LParAuxRes = epsilon");
		table.put(new Pair("LParV","'fe_parente'"),"LParV = epsilon");
		table.put(new Pair("LArgs","'fe_parente'"),"LArgs = epsilon");
		table.put(new Pair("LArgsAux","'fe_parente'"),"LArgsAux = epsilon");
		table.put(new Pair("ExpConAux","'fe_parente'"),"ExpConAux = epsilon");
		table.put(new Pair("ExpBoAux","'fe_parente'"),"ExpBoAux = epsilon");
		table.put(new Pair("ExpBoOuAux","'fe_parente'"),"ExpBoOuAux = epsilon");
		table.put(new Pair("ExpRelAux","'fe_parente'"),"ExpRelAux = epsilon");
		table.put(new Pair("ExpAritAux","'fe_parente'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'fe_parente'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'fe_parente'"),"CFuncAux = epsilon");
		table.put(new Pair("PrintAux","'fe_parente'"),"PrintAux = epsilon");
		table.put(new Pair("LId","'fe_parente'"),"LId = epsilon");
		table.put(new Pair("LIdAux","'fe_parente'"),"LIdAux = LId");
		table.put(new Pair("Bloco","'fe_chave'"),"Bloco = epsilon");
		table.put(new Pair("Else","'fe_chave'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_void'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_void'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_void'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_void'"),"Funcao = 'tipo_void' 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Programa","'tipo_bool'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_bool'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_bool'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_bool'"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Tipo","'tipo_bool'"),"Tipo = 'tipo_bool'");
		table.put(new Pair("LParam","'tipo_bool'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_bool'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_bool'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_bool'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_int'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_int'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_int'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_int'"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Tipo","'tipo_int'"),"Tipo = 'tipo_int'");
		table.put(new Pair("LParam","'tipo_int'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_int'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_int'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_int'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_float'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_float'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_float'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_float'"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Tipo","'tipo_float'"),"Tipo = 'tipo_float'");
		table.put(new Pair("LParam","'tipo_float'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_float'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_float'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_float'"),"Else = epsilon");
		table.put(new Pair("Programa","'tipo_char'"),"Programa = Cod");
		table.put(new Pair("Cod","'tipo_char'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'tipo_char'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'tipo_char'"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Tipo","'tipo_char'"),"Tipo = 'tipo_char'");
		table.put(new Pair("LParam","'tipo_char'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'tipo_char'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'tipo_char'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'tipo_char'"),"Else = epsilon");
		table.put(new Pair("Programa","'retorno'"),"Programa = Cod");
		table.put(new Pair("Cod","'retorno'"),"Cod = Funcao CodAux");
		table.put(new Pair("CodAux","'retorno'"),"CodAux = Funcao CodAux");
		table.put(new Pair("Funcao","'retorno'"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Tipo","'retorno'"),"Tipo = 'retorno'");
		table.put(new Pair("LParam","'retorno'"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair("Bloco","'retorno'"),"Bloco = Declaracao Bloco");
		table.put(new Pair("Declaracao","'retorno'"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair("Else","'retorno'"),"Else = epsilon");
		table.put(new Pair("LParAux","'virgula'"),"LParAux = LParV LParAuxRes");
		table.put(new Pair("LParAuxRes","'virgula'"),"LParAuxRes = 'virgula' Tipo 'id' LParAux");
		table.put(new Pair("LParV","'virgula'"),"LParV = epsilon");
		table.put(new Pair("LArgsAux","'virgula'"),"LArgsAux = 'virgula' ExpConcat LArgsAux");
		table.put(new Pair("ExpConAux","'virgula'"),"ExpConAux = epsilon");
		table.put(new Pair("ExpBoAux","'virgula'"),"ExpBoAux = epsilon");
		table.put(new Pair("ExpBoOuAux","'virgula'"),"ExpBoOuAux = epsilon");
		table.put(new Pair("ExpRelAux","'virgula'"),"ExpRelAux = epsilon");
		table.put(new Pair("ExpAritAux","'virgula'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'virgula'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'virgula'"),"CFuncAux = epsilon");
		table.put(new Pair("PrintAux","'virgula'"),"PrintAux = 'virgula' LArgs");
		table.put(new Pair("LId","'virgula'"),"LId = 'virgula' 'id' LIdAux");
		table.put(new Pair("LIdAux","'virgula'"),"LIdAux = LId");
		table.put(new Pair("LParAux","'ab_colchet'"),"LParAux = LParV LParAuxRes");
		table.put(new Pair("LParV","'ab_colchet'"),"LParV = 'ab_colchet' 'fe_colchet'");
		table.put(new Pair("BlocoAux","'ab_colchet'"),"BlocoAux = 'ab_colchet' ExpArit 'fe_colchet' Atribuicao Bloco");
		table.put(new Pair("DeclFim","'ab_colchet'"),"DeclFim = 'ab_colchet' ExpConcat 'fe_colchet' 'pont_virg'");
		table.put(new Pair("CFuncAux","'ab_colchet'"),"CFuncAux = 'ab_colchet' ExpConcat 'fe_colchet'");
		table.put(new Pair("LIdAux","'ab_colchet'"),"LIdAux = 'ab_colchet' ExpConcat 'fe_colchet' LId");
		table.put(new Pair("ExpConAux","'fe_colchet'"),"ExpConAux = epsilon");
		table.put(new Pair("ExpBoAux","'fe_colchet'"),"ExpBoAux = epsilon");
		table.put(new Pair("ExpBoOuAux","'fe_colchet'"),"ExpBoOuAux = epsilon");
		table.put(new Pair("ExpRelAux","'fe_colchet'"),"ExpRelAux = epsilon");
		table.put(new Pair("ExpAritAux","'fe_colchet'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'fe_colchet'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'fe_colchet'"),"CFuncAux = epsilon");
		table.put(new Pair("DeclFim","'pont_virg'"),"DeclFim = 'pont_virg'");
		table.put(new Pair("ExpConAux","'pont_virg'"),"ExpConAux = epsilon");
		table.put(new Pair("ExpBoAux","'pont_virg'"),"ExpBoAux = epsilon");
		table.put(new Pair("ExpBoOuAux","'pont_virg'"),"ExpBoOuAux = epsilon");
		table.put(new Pair("ExpRelAux","'pont_virg'"),"ExpRelAux = epsilon");
		table.put(new Pair("ExpAritAux","'pont_virg'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'pont_virg'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'pont_virg'"),"CFuncAux = epsilon");
		table.put(new Pair("BlocoAux","'atribuicao'"),"BlocoAux = Atribuicao Bloco");
		table.put(new Pair("Atribuicao","'atribuicao'"),"Atribuicao = 'atribuicao' ExpConcat 'pont_virg'");
		table.put(new Pair("ExpBoAux","'opl_ou'"),"ExpBoAux = 'opl_ou' ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOuAux","'opl_ou'"),"ExpBoOuAux = epsilon");
		table.put(new Pair("ExpRelAux","'opl_ou'"),"ExpRelAux = epsilon");
		table.put(new Pair("ExpAritAux","'opl_ou'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'opl_ou'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'opl_ou'"),"CFuncAux = epsilon");
		table.put(new Pair("ExpBoOuAux","'opl_e'"),"ExpBoOuAux = 'opl_e' ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRelAux","'opl_e'"),"ExpRelAux = epsilon");
		table.put(new Pair("ExpAritAux","'opl_e'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'opl_e'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'opl_e'"),"CFuncAux = epsilon");
		table.put(new Pair("LArgs","'opl_nao'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'opl_nao'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'opl_nao'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'opl_nao'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'opl_nao'"),"ExpRel = 'opl_nao' ExpRel");
		table.put(new Pair("ExpRelAux","'opr'"),"ExpRelAux = 'opr' ExpArit ExpRelAux");
		table.put(new Pair("ExpAritAux","'opr'"),"ExpAritAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'opr'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'opr'"),"CFuncAux = epsilon");
		table.put(new Pair("ExpAritAux","'opa_ad'"),"ExpAritAux = 'opa_ad' ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMulAux","'opa_ad'"),"ExpAritMulAux = epsilon");
		table.put(new Pair("CFuncAux","'opa_ad'"),"CFuncAux = epsilon");
		table.put(new Pair("ExpAritMulAux","'opa_mult'"),"ExpAritMulAux = 'opa_mult' ExpAritRes ExpAritMulAux");
		table.put(new Pair("CFuncAux","'opa_mult'"),"CFuncAux = epsilon");
		table.put(new Pair("LArgs","'opa_nega'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'opa_nega'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'opa_nega'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'opa_nega'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'opa_nega'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'opa_nega'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'opa_nega'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'opa_nega'"),"ExpAritRes = 'opa_nega' ExpAritRes");
		table.put(new Pair("LArgs","'opa_posi'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'opa_posi'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'opa_posi'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'opa_posi'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'opa_posi'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'opa_posi'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'opa_posi'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'opa_posi'"),"ExpAritRes = 'opa_posi' ExpAritRes");
		table.put(new Pair("LArgs","'cte_int'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_int'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'cte_int'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'cte_int'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'cte_int'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'cte_int'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'cte_int'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'cte_int'"),"ExpAritRes = 'cte_int'");
		table.put(new Pair("LArgs","'cte_float'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_float'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'cte_float'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'cte_float'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'cte_float'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'cte_float'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'cte_float'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'cte_float'"),"ExpAritRes = 'cte_float'");
		table.put(new Pair("LArgs","'cte_char'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_char'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'cte_char'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'cte_char'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'cte_char'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'cte_char'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'cte_char'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'cte_char'"),"ExpAritRes = 'cte_char'");
		table.put(new Pair("LArgs","'cte_cad_ch'"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair("ExpConcat","'cte_cad_ch'"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair("ExpBool","'cte_cad_ch'"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair("ExpBoOu","'cte_cad_ch'"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair("ExpRel","'cte_cad_ch'"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair("ExpArit","'cte_cad_ch'"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair("ExpAritMul","'cte_cad_ch'"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair("ExpAritRes","'cte_cad_ch'"),"ExpAritRes = 'cte_cad_ch'");
		table.put(new Pair("Bloco","'saida'"),"Bloco = Print Bloco");
		table.put(new Pair("Print","'saida'"),"Print = 'saida' 'ab_parente' 'cte_cad_ch' PrintAux 'fe_parente' 'pont_virg'");
		table.put(new Pair("Else","'saida'"),"Else = epsilon");
		table.put(new Pair("Bloco","'saida_ln'"),"Bloco = Print Bloco");
		table.put(new Pair("Print","'saida_ln'"),"Print = 'saida_ln' 'ab_parente' 'cte_cad_ch' PrintAux 'fe_parente' 'pont_virg'");
		table.put(new Pair("Else","'saida_ln'"),"Else = epsilon");
		table.put(new Pair("Bloco","'entrada'"),"Bloco = Read Bloco");
		table.put(new Pair("Read","'entrada'"),"Read = 'entrada' 'ab_parente' 'cte_cad_ch' LId 'fe_parente' 'pont_virg'");
		table.put(new Pair("Else","'entrada'"),"Else = epsilon");
		table.put(new Pair("Bloco","'for'"),"Bloco = For Bloco");
		table.put(new Pair("For","'for'"),"For = 'for' 'ab_parente' 'id' Atribuicao ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Else","'for'"),"Else = epsilon");
		table.put(new Pair("Bloco","'while'"),"Bloco = While Bloco");
		table.put(new Pair("While","'while'"),"While = 'while' 'ab_parente' ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("Else","'while'"),"Else = epsilon");
		table.put(new Pair("Bloco","'se'"),"Bloco = If Bloco");
		table.put(new Pair("If","'se'"),"If = 'se' 'ab_parente' ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave' Else");
		table.put(new Pair("Else","'se'"),"Else = epsilon");
		table.put(new Pair("Else","'senao'"),"Else = 'senao' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair("CodAux","$"),"CodAux = epsilon");
		//		
		//		for (String entry: table.values()) {
		//			System.out.println(entry);
		//		}
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

//	public static void main(String[] args) throws IOException{
//		Table test = new Table();
//		test.read();
//	}

}
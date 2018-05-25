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
	//File file = new File("Exemplos/tabela.txt"); // Para planilha
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
	private void populateTable() {
		table.put(new Pair<String, String>("Bloco","id"),"Bloco = 'id' BlocoAux");
		table.put(new Pair<String, String>("LArgs","id"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","id"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","id"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","id"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","id"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","id"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","id"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","id"),"ExpAritRes = 'id' CFuncAux");
		table.put(new Pair<String, String>("Else","id"),"Else = epsilon");
		table.put(new Pair<String, String>("BlocoAux","ab_parente"),"BlocoAux = ChamaFunc 'pont_virg' Bloco");
		table.put(new Pair<String, String>("ChamaFunc","ab_parente"),"ChamaFunc = 'ab_parente' LArgs 'fe_parente'");
		table.put(new Pair<String, String>("LArgs","ab_parente"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","ab_parente"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","ab_parente"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","ab_parente"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","ab_parente"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","ab_parente"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","ab_parente"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","ab_parente"),"ExpAritRes = 'ab_parente' ExpBool 'fe_parente'");
		table.put(new Pair<String, String>("CFuncAux","ab_parente"),"CFuncAux = ChamaFunc");
		table.put(new Pair<String, String>("LParam","fe_parente"),"LParam = epsilon");
		table.put(new Pair<String, String>("LParAux","fe_parente"),"LParAux = LParV LParAuxRes");
		table.put(new Pair<String, String>("LParAuxRes","fe_parente"),"LParAuxRes = epsilon");
		table.put(new Pair<String, String>("LParV","fe_parente"),"LParV = epsilon");
		table.put(new Pair<String, String>("LArgs","fe_parente"),"LArgs = epsilon");
		table.put(new Pair<String, String>("LArgsAux","fe_parente"),"LArgsAux = epsilon");
		table.put(new Pair<String, String>("ExpConAux","fe_parente"),"ExpConAux = epsilon");
		table.put(new Pair<String, String>("ExpBoAux","fe_parente"),"ExpBoAux = epsilon");
		table.put(new Pair<String, String>("ExpBoOuAux","fe_parente"),"ExpBoOuAux = epsilon");
		table.put(new Pair<String, String>("ExpRelAux","fe_parente"),"ExpRelAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","fe_parente"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","fe_parente"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","fe_parente"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("PrintAux","fe_parente"),"PrintAux = epsilon");
		table.put(new Pair<String, String>("LId","fe_parente"),"LId = epsilon");
		table.put(new Pair<String, String>("LIdAux","fe_parente"),"LIdAux = LId");
		table.put(new Pair<String, String>("Bloco","fe_chave"),"Bloco = epsilon");
		table.put(new Pair<String, String>("Else","fe_chave"),"Else = epsilon");
		table.put(new Pair<String, String>("Programa","tipo_void"),"Programa = Cod");
		table.put(new Pair<String, String>("Cod","tipo_void"),"Cod = Funcao CodAux");
		table.put(new Pair<String, String>("CodAux","tipo_void"),"CodAux = Funcao");
		table.put(new Pair<String, String>("Funcao","tipo_void"),"Funcao = 'tipo_void' 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Programa","tipo_bool"),"Programa = Cod");
		table.put(new Pair<String, String>("Cod","tipo_bool"),"Cod = Funcao CodAux");
		table.put(new Pair<String, String>("CodAux","tipo_bool"),"CodAux = Funcao");
		table.put(new Pair<String, String>("Funcao","tipo_bool"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Tipo","tipo_bool"),"Tipo = 'tipo_bool'");
		table.put(new Pair<String, String>("LParam","tipo_bool"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair<String, String>("Bloco","tipo_bool"),"Bloco = Declaracao Bloco");
		table.put(new Pair<String, String>("Declaracao","tipo_bool"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair<String, String>("Else","tipo_bool"),"Else = epsilon");
		table.put(new Pair<String, String>("Programa","tipo_int"),"Programa = Cod");
		table.put(new Pair<String, String>("Cod","tipo_int"),"Cod = Funcao CodAux");
		table.put(new Pair<String, String>("CodAux","tipo_int"),"CodAux = Funcao");
		table.put(new Pair<String, String>("Funcao","tipo_int"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Tipo","tipo_int"),"Tipo = 'tipo_int'");
		table.put(new Pair<String, String>("LParam","tipo_int"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair<String, String>("Bloco","tipo_int"),"Bloco = Declaracao Bloco");
		table.put(new Pair<String, String>("Declaracao","tipo_int"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair<String, String>("Else","tipo_int"),"Else = epsilon");
		table.put(new Pair<String, String>("Programa","tipo_float"),"Programa = Cod");
		table.put(new Pair<String, String>("Cod","tipo_float"),"Cod = Funcao CodAux");
		table.put(new Pair<String, String>("CodAux","tipo_float"),"CodAux = Funcao");
		table.put(new Pair<String, String>("Funcao","tipo_float"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Tipo","tipo_float"),"Tipo = 'tipo_float'");
		table.put(new Pair<String, String>("LParam","tipo_float"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair<String, String>("Bloco","tipo_float"),"Bloco = Declaracao Bloco");
		table.put(new Pair<String, String>("Declaracao","tipo_float"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair<String, String>("Else","tipo_float"),"Else = epsilon");
		table.put(new Pair<String, String>("Programa","tipo_char"),"Programa = Cod");
		table.put(new Pair<String, String>("Cod","tipo_char"),"Cod = Funcao CodAux");
		table.put(new Pair<String, String>("CodAux","tipo_char"),"CodAux = Funcao");
		table.put(new Pair<String, String>("Funcao","tipo_char"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Tipo","tipo_char"),"Tipo = 'tipo_char'");
		table.put(new Pair<String, String>("LParam","tipo_char"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair<String, String>("Bloco","tipo_char"),"Bloco = Declaracao Bloco");
		table.put(new Pair<String, String>("Declaracao","tipo_char"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair<String, String>("Else","tipo_char"),"Else = epsilon");
		table.put(new Pair<String, String>("Programa","‘retorno’"),"Programa = Cod");
		table.put(new Pair<String, String>("Cod","‘retorno’"),"Cod = Funcao CodAux");
		table.put(new Pair<String, String>("CodAux","‘retorno’"),"CodAux = Funcao");
		table.put(new Pair<String, String>("Funcao","‘retorno’"),"Funcao = Tipo 'id' 'ab_parente' LParam 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Tipo","‘retorno’"),"Tipo = ‘retorno’");
		table.put(new Pair<String, String>("LParam","‘retorno’"),"LParam = Tipo 'id' LParAux");
		table.put(new Pair<String, String>("Bloco","‘retorno’"),"Bloco = Declaracao Bloco");
		table.put(new Pair<String, String>("Declaracao","‘retorno’"),"Declaracao = Tipo 'id' DeclFim");
		table.put(new Pair<String, String>("Else","‘retorno’"),"Else = epsilon");
		table.put(new Pair<String, String>("LParAux","virgula"),"LParAux = LParV LParAuxRes");
		table.put(new Pair<String, String>("LParAuxRes","virgula"),"LParAuxRes = 'virgula' Tipo 'id' LParAux");
		table.put(new Pair<String, String>("LParV","virgula"),"LParV = epsilon");
		table.put(new Pair<String, String>("LArgsAux","virgula"),"LArgsAux = 'virgula' ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConAux","virgula"),"ExpConAux = epsilon");
		table.put(new Pair<String, String>("ExpBoAux","virgula"),"ExpBoAux = epsilon");
		table.put(new Pair<String, String>("ExpBoOuAux","virgula"),"ExpBoOuAux = epsilon");
		table.put(new Pair<String, String>("ExpRelAux","virgula"),"ExpRelAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","virgula"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","virgula"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","virgula"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("PrintAux","virgula"),"PrintAux = 'virgula' LArgs");
		table.put(new Pair<String, String>("LId","virgula"),"LId = 'virgula' 'id' LIdAux");
		table.put(new Pair<String, String>("LIdAux","virgula"),"LIdAux = LId");
		table.put(new Pair<String, String>("LParAux","ab_colchet"),"LParAux = LParV LParAuxRes");
		table.put(new Pair<String, String>("LParV","ab_colchet"),"LParV = 'ab_colchet' 'fe_colchet'");
		table.put(new Pair<String, String>("BlocoAux","ab_colchet"),"BlocoAux = 'ab_colchet' ExpArit 'fe_colchet' Atribuicao Bloco");
		table.put(new Pair<String, String>("DeclFim","ab_colchet"),"DeclFim = 'ab_colchet' ExpConcat 'fe_colchet' 'pont_virg'");
		table.put(new Pair<String, String>("CFuncAux","ab_colchet"),"CFuncAux = 'ab_colchet' ExpConcat 'fe_colchet'");
		table.put(new Pair<String, String>("LIdAux","ab_colchet"),"LIdAux = 'ab_colchet' ExpConcat 'fe_colchet' LId");
		table.put(new Pair<String, String>("ExpConAux","fe_colchet"),"ExpConAux = epsilon");
		table.put(new Pair<String, String>("ExpBoAux","fe_colchet"),"ExpBoAux = epsilon");
		table.put(new Pair<String, String>("ExpBoOuAux","fe_colchet"),"ExpBoOuAux = epsilon");
		table.put(new Pair<String, String>("ExpRelAux","fe_colchet"),"ExpRelAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","fe_colchet"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","fe_colchet"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","fe_colchet"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("DeclFim","pont_virg"),"DeclFim = 'pont_virg'");
		table.put(new Pair<String, String>("ExpConAux","pont_virg"),"ExpConAux = epsilon");
		table.put(new Pair<String, String>("ExpBoAux","pont_virg"),"ExpBoAux = epsilon");
		table.put(new Pair<String, String>("ExpBoOuAux","pont_virg"),"ExpBoOuAux = epsilon");
		table.put(new Pair<String, String>("ExpRelAux","pont_virg"),"ExpRelAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","pont_virg"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","pont_virg"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","pont_virg"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("BlocoAux","atribuicao"),"BlocoAux = Atribuicao Bloco");
		table.put(new Pair<String, String>("Atribuicao","atribuicao"),"Atribuicao = 'atribuicao' ExpConcat 'pont_virg'");
		table.put(new Pair<String, String>("ExpBoAux","opl_ou"),"ExpBoAux = 'opl_ou' ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOuAux","opl_ou"),"ExpBoOuAux = epsilon");
		table.put(new Pair<String, String>("ExpRelAux","opl_ou"),"ExpRelAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","opl_ou"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","opl_ou"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","opl_ou"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("ExpBoOuAux","opl_e"),"ExpBoOuAux = 'opl_e' ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRelAux","opl_e"),"ExpRelAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","opl_e"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","opl_e"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","opl_e"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("LArgs","opl_nao"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","opl_nao"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","opl_nao"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","opl_nao"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","opl_nao"),"ExpRel = 'opl_nao' ExpRel");
		table.put(new Pair<String, String>("ExpRelAux","opr"),"ExpRelAux = 'opr' ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpAritAux","opr"),"ExpAritAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","opr"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","opr"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("ExpAritAux","opa_ad"),"ExpAritAux = 'opa_ad' ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMulAux","opa_ad"),"ExpAritMulAux = epsilon");
		table.put(new Pair<String, String>("CFuncAux","opa_ad"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("ExpAritMulAux","opa_mult"),"ExpAritMulAux = 'opa_mult' ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("CFuncAux","opa_mult"),"CFuncAux = epsilon");
		table.put(new Pair<String, String>("LArgs","opa_nega"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","opa_nega"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","opa_nega"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","opa_nega"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","opa_nega"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","opa_nega"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","opa_nega"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","opa_nega"),"ExpAritRes = 'opa_nega' ExpAritRes");
		table.put(new Pair<String, String>("LArgs","opa_posi"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","opa_posi"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","opa_posi"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","opa_posi"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","opa_posi"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","opa_posi"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","opa_posi"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","opa_posi"),"ExpAritRes = 'opa_posi' ExpAritRes");
		table.put(new Pair<String, String>("LArgs","cte_int"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","cte_int"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","cte_int"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","cte_int"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","cte_int"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","cte_int"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","cte_int"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","cte_int"),"ExpAritRes = 'cte_int'");
		table.put(new Pair<String, String>("LArgs","cte_float"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","cte_float"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","cte_float"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","cte_float"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","cte_float"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","cte_float"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","cte_float"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","cte_float"),"ExpAritRes = 'cte_float'");
		table.put(new Pair<String, String>("LArgs","cte_char"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","cte_char"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","cte_char"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","cte_char"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","cte_char"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","cte_char"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","cte_char"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","cte_char"),"ExpAritRes = 'cte_char'");
		table.put(new Pair<String, String>("LArgs","cte_cad_ch"),"LArgs = ExpConcat LArgsAux");
		table.put(new Pair<String, String>("ExpConcat","cte_cad_ch"),"ExpConcat = ExpBool ExpConAux");
		table.put(new Pair<String, String>("ExpBool","cte_cad_ch"),"ExpBool = ExpBoOu ExpBoAux");
		table.put(new Pair<String, String>("ExpBoOu","cte_cad_ch"),"ExpBoOu = ExpRel ExpBoOuAux");
		table.put(new Pair<String, String>("ExpRel","cte_cad_ch"),"ExpRel = ExpArit ExpRelAux");
		table.put(new Pair<String, String>("ExpArit","cte_cad_ch"),"ExpArit = ExpAritMul ExpAritAux");
		table.put(new Pair<String, String>("ExpAritMul","cte_cad_ch"),"ExpAritMul = ExpAritRes ExpAritMulAux");
		table.put(new Pair<String, String>("ExpAritRes","cte_cad_ch"),"ExpAritRes = 'cte_cad_ch'");
		table.put(new Pair<String, String>("Bloco","saida"),"Bloco = Print Bloco");
		table.put(new Pair<String, String>("Print","saida"),"Print = 'saida' 'ab_parente' 'cte_cad_ch' PrintAux 'fe_parente' 'pont_virg'");
		table.put(new Pair<String, String>("Else","saida"),"Else = epsilon");
		table.put(new Pair<String, String>("Bloco","saida_ln"),"Bloco = Print Bloco");
		table.put(new Pair<String, String>("Print","saida_ln"),"Print = 'saida_ln' 'ab_parente' 'cte_cad_ch' PrintAux 'fe_parente' 'pont_virg'");
		table.put(new Pair<String, String>("Else","saida_ln"),"Else = epsilon");
		table.put(new Pair<String, String>("Bloco","entrada"),"Bloco = Read Bloco");
		table.put(new Pair<String, String>("Read","entrada"),"Read = 'entrada' 'ab_parente' 'cte_cad_ch' LId 'fe_parente' 'pont_virg'");
		table.put(new Pair<String, String>("Else","entrada"),"Else = epsilon");
		table.put(new Pair<String, String>("Bloco","for"),"Bloco = For Bloco");
		table.put(new Pair<String, String>("For","for"),"For = 'for' 'ab_parente' 'id' Atribuicao ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Else","for"),"Else = epsilon");
		table.put(new Pair<String, String>("Bloco","while"),"Bloco = While Bloco");
		table.put(new Pair<String, String>("While","while"),"While = 'while' 'ab_parente' ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("Else","while"),"Else = epsilon");
		table.put(new Pair<String, String>("Bloco","se"),"Bloco = If Bloco");
		table.put(new Pair<String, String>("If","se"),"If = 'se' 'ab_parente' ExpBool 'fe_parente' 'ab_chave' Bloco 'fe_chave' Else");
		table.put(new Pair<String, String>("Else","se"),"Else = epsilon");
		table.put(new Pair<String, String>("Else","senao"),"Else = 'senao' 'ab_chave' Bloco 'fe_chave'");
		table.put(new Pair<String, String>("CodAux","$"),"CodAux = epsilon");
		//		
		//		for (String entry: table.values()) {
		//			System.out.println(entry);
		//		}
	}

	public String getDerivation(String nTerm, String term) {
		Pair<String, String> p = new Pair<String, String>(nTerm, term);
		String production = table.get(p);
		return production;
	}

	//	public void read() throws IOException {
	//		File inputWorkbook = new File("Exemplos/tabela.xls");
	//		Workbook w;
	//		PrintWriter writer = new PrintWriter("Exemplos/tabela.txt", "UTF-8");
	//		try {
	//			w = Workbook.getWorkbook(inputWorkbook);
	//			Sheet sheet = w.getSheet(0);
	//
	//			for (int j = 1; j < sheet.getColumns(); j++) {
	//				for (int i = 1; i < sheet.getRows(); i++) {
	//					Cell nTerm = sheet.getCell(0, i);
	//					Cell term = sheet.getCell(j, 0);
	//					Cell producao = sheet.getCell(j, i);
	//					if (!nTerm.getContents().equals("") && !term.getContents().equals("")
	//							&& !producao.getContents().equals("")) {
	//						String line = "table.put(new Pair(\"" + nTerm.getContents().toString() + "\"," + "\""
	//								+ term.getContents().toString() + "\"),\"" + producao.getContents().toString() + "\");";
	//						System.out.println(line);
	//					}
	//				}
	//			}
	//			writer.close();
	//		} catch (BiffException e) {
	//			e.printStackTrace();
	//		}
	//	}

	//	public static void main(String[] args){
	//		Table test = new Table();
	//		test.populateTable();
	//	}

}
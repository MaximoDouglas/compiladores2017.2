package br.ufal.ic.compilator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.util.Pair;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Table {
	// A chave é no formao "NaoTerminal-Terminal", o valor é a produção
	Map<Pair, String> table = new HashMap<Pair, String>();
	// File file = new File("Exemplos/tabela.txt"); //Para planilha
	File file = new File("../Exemplos/tabela.txt"); // Para preenchimento da hash

	private String inputFile;

	//populateTable() e getDerivation() são importantes para a execução do analisador
	//main(0 e read() servem apenas para ler planilha (DEVEM SER RETIRADOS QUANDO A GRAMÁTICA ESTIVER FINALIZADO)
	public void populateTable() throws FileNotFoundException {
		Scanner sc = new Scanner(file);

		while (sc.hasNextLine()) {
			String vet[] = sc.nextLine().split(",");
			Pair p = new Pair(vet[0], vet[1].substring(0, vet[1].length() - 1));
			table.put(p, vet[2]);
		}

		// for (Map.Entry<Pair, String> entry : table.entrySet()) {
		// Pair key = entry.getKey();
		// String value = entry.getValue();
		//
		// System.out.println("Key: <" + key.getKey() + ", " + key.getValue() + ">
		// Value: " + value);
		// }
	}

	public String getDerivation(String nTerm, String term) {
		Pair p = new Pair(nTerm, term);
		return table.get(p);
	}
	// public void read() throws IOException {
	// File inputWorkbook = new File(inputFile);
	// Workbook w;
	// PrintWriter writer = new PrintWriter("Exemplos/tabela.txt", "UTF-8");
	// try {
	// w = Workbook.getWorkbook(inputWorkbook);
	// Sheet sheet = w.getSheet(0);
	//
	// for (int j = 1; j < sheet.getColumns(); j++) {
	// for (int i = 1; i < sheet.getRows(); i++) {
	// Cell nTerm = sheet.getCell(0, i);
	// Cell term = sheet.getCell(j, 0);
	// Cell producao = sheet.getCell(j, i);
	// if (!nTerm.getContents().equals("")) {
	// String aStr = nTerm.getContents().concat(",");
	// String bStr = aStr.concat(term.getContents().substring(0,
	// term.getContents().length()));
	// String cStr = bStr.concat(",");
	//
	// if (!producao.getContents().equals("")) {
	// writer.println(cStr.concat(producao.getContents()));
	// }
	// }
	// // if (nTerm.getType() == CellType.LABEL && term.getType() == CellType.LABEL
	// &&
	// // producao.getType() == CellType.LABEL) {
	// // Pair p = new Pair(nTerm.getContents(), term.getContents().substring(0,
	// // term.getContents().length() - 1));
	// // table.put(p, producao.getContents());
	// // }
	// }
	// }
	// writer.close();
	// } catch (BiffException e) {
	// e.printStackTrace();
	// }
	// }

	// public static void main(String[] args) throws IOException {
	// Table test = new Table();
	// test.setInputFile("Exemplos/tabela.xls");
	// test.read();
	// test.populateTable();
	// System.out.println(test.getDerivation("ExpAritMul", "-"));
	// }

}
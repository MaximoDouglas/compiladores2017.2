package br.ufal.ic.compilator.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Table {
	// A chave é no formao "NaoTerminal-Terminal", o valor é a produção
	Map<Pair, String> table = new HashMap<Pair, String>();

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException  {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            Sheet sheet = w.getSheet(0);

            for (int j = 1; j < sheet.getColumns(); j++) {
                for (int i = 1; i < sheet.getRows(); i++) {
                	Cell nTerm = sheet.getCell(0, i);
                    Cell term = sheet.getCell(j, 0);
                    Cell producao = sheet.getCell(j, i);

                    if (nTerm.getType() == CellType.LABEL && term.getType() == CellType.LABEL && producao.getType() == CellType.LABEL) { 
                    	Pair p = new Pair(nTerm.getContents(), term.getContents());
                    	table.put(p, producao.getContents());
                    }
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }
	
	public void printTable()  {
		for (Map.Entry<Pair, String> entry : table.entrySet()) {
		    Pair key = entry.getKey();
		    String value = entry.getValue();

		    System.out.println ("Key: <" + key.getKey() + ", " + key.getValue() + "> Value: " + value);
		}
	}
	
	public String getDerivation(String nTerm, String term) {
		Pair p = new Pair(nTerm, term);
		return table.get(p);
	}
	
	public static void main(String[] args) throws IOException {
		Table test = new Table();
		test.setInputFile("Exemplos/tabela.xls");
		test.read();
		test.printTable();
		System.out.println(test.getDerivation("ExpAritMul", "-"));
	}

}
package br.ufal.ic.compilator.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.ufal.ic.compilator.model.Token;
import br.ufal.ic.compilator.model.TokenService;

public class Runner {
	
	private static ArrayList<String> arquivo = new ArrayList<String>();

	public static void main(String[] args) throws IOException{
		TokenService.fillExpressoes();

		arquivoBuild(args);

		getNextToken();
	}

	private static void arquivoBuild(String[] args) {
		if (args.length > 0) {
			File file = new File(args[0]);
			BufferedReader reader = null;

			try {

				String sCurrentLine;

				reader = new BufferedReader(new FileReader(file));
				sCurrentLine = reader.readLine();

				while (sCurrentLine != null) {
					arquivo.add(sCurrentLine);
					sCurrentLine = reader.readLine();
				}

			} 

			catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Nenhum arquivo passado.");
		}

	}

	private static void getNextToken() {
		Token tk = Token.nextToken();
		
		while(tk != null) {
			System.out.println(tk.toString());
			tk = Token.nextToken();
		}
	}

	public static String getNextLine(int index) {
		return arquivo.get(index);
	}

	public static int getLines() {
		return arquivo.size();
	}

}
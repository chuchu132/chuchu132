package ar.uba.fi.robots;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import mimir.well;

public class InferEngine extends well {

	private static InferEngine instance = new InferEngine();

	private InferEngine() {

	}

	public static InferEngine getInstance() {
		return instance;
	}

	public void addRulesFromFile(String file) {
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				try {
					String[] rule = strLine.split(",");
					addrule(rule[0].trim(), rule[1].trim());
				} catch (Exception e) {
					System.out.println("Regla mal escrita: " + strLine);
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void addFactsFromFile(String file) {
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				addfact(strLine.trim());
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}

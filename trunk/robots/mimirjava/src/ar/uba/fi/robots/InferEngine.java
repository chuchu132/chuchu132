package ar.uba.fi.robots;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import mimir.well;

public class InferEngine extends well {

	private static InferEngine instance = new InferEngine();

	private ArrayList<Rule> rules =  new ArrayList<Rule>();
	private ArrayList<FactsSet> facts =  new ArrayList<FactsSet>();
	
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
					rules.add(new Rule(strLine));
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error: " + strLine);
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
			FactsSet tempFact = null;
			while ((strLine = br.readLine()) != null) {
				if(strLine.trim().length() == 0 || strLine.startsWith("#")){
					if(tempFact != null){
						facts.add(tempFact);
					}
					tempFact =  new FactsSet();
					tempFact.setname = strLine.trim();
				}else if( tempFact != null ){
					tempFact.facts.add(strLine.trim());
				}
			}
			if(tempFact != null)
				facts.add(tempFact);
			in.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	
	private void loadRules(){
		for(Rule rule : rules){
			try{
				addrule(rule.name, rule.rule);
			}catch(Exception e){
				System.out.println("Regla mal escrita : "+ rule.name);
			}
		}
	}
	
	private void loadFacts(FactsSet f){
		for(String fact : f.facts){
			try{
				addfact(fact);
			}catch(Exception e){
				System.out.println("Error cargando: "+ fact);
			}
		}
	}
	
	public void runCases(){
		if(rules.size() == 0){
			System.out.println("No hay reglas definidas.");
			return;
		}
		if(facts.size() == 0){
			System.out.println("No hay hechos cargados.");
			return;
		}
		for(FactsSet tempFacts : facts){
			reset();
			loadRules();
			loadFacts(tempFacts);
			String s = run();
			System.out.println(((tempFacts.setname.length() != 0)?tempFacts.setname:"CasoSinNombre") +": "+((s.trim().equals("#{}"))?"":"no ")+"es una mezcla válida." );
		}
	}

}

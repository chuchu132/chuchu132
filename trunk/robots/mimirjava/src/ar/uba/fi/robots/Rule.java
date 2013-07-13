package ar.uba.fi.robots;

public class Rule {
	public String name;
	public String rule;
	
	public Rule(String r){
		String[] rv = r.split(",");
		name = rv[0].trim();
		rule = rv[1].trim();
	}
	
}

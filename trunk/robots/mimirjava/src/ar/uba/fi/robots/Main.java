package ar.uba.fi.robots;

import java.util.HashMap;
import java.util.Map;

import ca.zmatrix.cli.ParseCmd;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String usage = "usage: -facts FACTS_FILE_URL  -rules RULES_FILE_URL";
		ParseCmd cmd = new ParseCmd.Builder().help(usage)
				.parm("-facts", "java.txt").req().parm("-rules", "java.txt")
				.req().build();

		Map<String, String> R = new HashMap<String, String>();
		String parseError = cmd.validate(args);
		if (cmd.isValid(args)) {
			R = cmd.parse(args);
			InferEngine w = InferEngine.getInstance();
			w.addFactsFromFile(R.get("-facts"));
			w.addRulesFromFile(R.get("-rules"));
			System.out.println("Resultados:");
			String s = w.run();
			System.out.println(s);
		} else {
			System.out.println(parseError);
			System.exit(1);
		}
	}

}

import mimir.well;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		well w = new well();
		w.dbg("Ale");
		w.init();
		w.addfacts("B1 on B2");
		w.addfacts("B1 on B3");
		w.addfacts("B1 color red");
		w.addfacts("B2 on table");
		w.addfacts("B2 left-of B3");
		w.addfacts("B2 color blue");
		w.addfacts("B3 left-of B4");
		w.addfacts("B3 on table");
		w.addfacts("B3 color red");
		w.addrule("find-stack-of-two-blocks-to-the-left-of-a-red-block", "?x on ?y ?y left-of ?z ?z color red => ?x is on-top");
		w.run();
	}

}

package ui;

public class cli {

	public static void main(String[] args) {

		boolean adding = ((args[0].compareTo("add") == 0) && args.length == 4);

		if (adding)
			add(args);

	}

	public static void add(String[] args) {

		System.out.println("Adding...");

		if (args[1].compareTo("link") == 0) {
			System.out.println("Found link\t:  " + args[2]);
			System.out.println("From source\t:  " + args[3]);

		} else if (args[1].compareTo("artist") == 0) {
			System.out.println("Found artist\t:  " + args[2]);
			System.out.println("With website\t:  " + args[3]);

		} else if (args[1].compareTo("song") == 0) {
			System.out.println("Found song\t:  " + args[2]);
			System.out.println("With length\t:  " + args[3]);

		} else {
			System.err.println("Bad format.");
		}
	}

	public void printArgs(String[] args) {

		for (String a : args) {
			System.out.print(" " + a);
		}
		System.out.println();
	}

}

public class Subset {

	public static void main(String[] args) {
		int K = Integer.parseInt(args[0]);
		String[] allStrings = StdIn.readAllStrings();
		RandomizedQueue<String> RQ = new RandomizedQueue<String>();

		for (String S : allStrings)
			RQ.enqueue(S);

		for (int i = 0; i < K; i++)
			StdOut.println(RQ.dequeue());
	}
}
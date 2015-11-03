import java.util.Arrays;

public class Brute {

	public static void main(String[] args) {
		In FIn = new In(args[0]);

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		int N = FIn.readInt();
		Point[] P = new Point[N];

		for (int i = 0; i < N; i++) {
			int x = FIn.readInt();
			int y = FIn.readInt();

			P[i] = new Point(x, y);

			P[i].draw();
		}

		Arrays.sort(P);

		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
				for (int k = j + 1; k < N; k++)
					for (int l = k + 1; l < N; l++) {
						double s1 = P[i].slopeTo(P[j]);
						double s2 = P[j].slopeTo(P[k]);
						double s3 = P[k].slopeTo(P[l]);

						if (s1 == s2 && s2 == s3) {
							StdOut.print(P[i].toString() + " -> ");
							StdOut.print(P[j].toString() + " -> ");
							StdOut.print(P[k].toString() + " -> ");
							StdOut.println(P[l].toString());

							P[i].drawTo(P[l]);
						}
					}
	}
}
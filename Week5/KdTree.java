public class KdTree {
	private Node root;
	private int size;
	private Point2D currNearest;

	private class Node {
		public Point2D point;
		public Node left;
		public Node right;

		public Node() {
			point = null;
			left = null;
			right = null;
		}
	}

	/**
	 * Construct an empty set of points.
	 */

	public KdTree() {
		root = new Node();
		size = 0;
	}

	/**
	 * @return <b>true</b> if the set is empty, <b>false</b> if it's not.
	 */

	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * @return The number of points in the set.
	 */

	public int size() {
		return size;
	}

	/**
	 * Check if the argument isn't null.
	 * 
	 * @param p
	 */

	private void validate(Object p) {
		if (p == null)
			throw new NullPointerException();
	}

	/**
	 * Add the point to the set (if it is not already in the set).
	 * 
	 * @param p
	 *            = the point to add in the set
	 */

	public void insert(Point2D p) {
		validate(p);

		recursiveInsert(root, true, p);
	}

	private void recursiveInsert(Node curr, boolean color, Point2D p) {
		if (curr.point == null) {
			curr.point = p;
			curr.left = new Node();
			curr.right = new Node();
			size++;
			return;
		}

		if (p.equals(curr.point))
			return;

		if (color == true) {
			if (p.x() < curr.point.x())
				recursiveInsert(curr.left, false, p);
			else
				recursiveInsert(curr.right, false, p);

		} else {
			if (p.y() < curr.point.y())
				recursiveInsert(curr.left, true, p);
			else
				recursiveInsert(curr.right, true, p);
		}
	}

	/**
	 * @param p
	 *            = the point to check
	 * @return <b>true</b> if the point belongs to the set, <b>false</b> if it
	 *         doesn't.
	 */

	public boolean contains(Point2D p) {
		validate(p);

		return recursiveContains(root, true, p);
	}

	private boolean recursiveContains(Node curr, boolean color, Point2D p) {
		if (curr.point == null)
			return false;

		if (p.equals(curr.point))
			return true;

		if (color == true) {
			if (p.x() < curr.point.x())
				return recursiveContains(curr.left, false, p);
			else
				return recursiveContains(curr.right, false, p);

		} else {
			if (p.y() < curr.point.y())
				return recursiveContains(curr.left, true, p);
			else
				return recursiveContains(curr.right, true, p);
		}
	}

	/**
	 * Draw all the points to the standard draw.
	 */

	public void draw() {
		recursiveDraw(root, true, new RectHV(0.0, 0.0, 1.0, 1.0));
	}

	private void recursiveDraw(Node curr, boolean color, RectHV rect) {
		if (curr.point == null)
			return;

		double x = curr.point.x();
		double y = curr.point.y();

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		curr.point.draw();

		if (color == true) {
			Point2D A = new Point2D(x, rect.ymin());
			Point2D B = new Point2D(x, rect.ymax());

			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(.001);
			A.drawTo(B);

			recursiveDraw(curr.left, false, new RectHV(rect.xmin(), rect.ymin(), x, rect.ymax()));
			recursiveDraw(curr.right, false, new RectHV(x, rect.ymin(), rect.xmax(), rect.ymax()));
		} else {
			Point2D A = new Point2D(rect.xmin(), y);
			Point2D B = new Point2D(rect.xmax(), y);

			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius(.001);
			A.drawTo(B);

			recursiveDraw(curr.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), y));
			recursiveDraw(curr.right, true, new RectHV(rect.xmin(), y, rect.xmax(), rect.ymax()));
		}
	}

	/**
	 * @param rect
	 *            = rectangle to check if points belong to it
	 * @return All the points that are inside the rectangle.
	 */

	public Iterable<Point2D> range(RectHV rect) {
		validate(rect);
		return recursiveRange(root, true, new RectHV(0.0, 0.0, 1.0, 1.0), rect);
	}

	private Iterable<Point2D> recursiveRange(Node curr, boolean color, RectHV rect, RectHV query) {
		if (curr.point == null)
			return new Stack<Point2D>();

		if (!query.intersects(rect))
			return new Stack<Point2D>();

		double x = curr.point.x();
		double y = curr.point.y();
		Stack<Point2D> S = new Stack<Point2D>();
		Stack<Point2D> A;
		Stack<Point2D> B;

		if (query.contains(curr.point))
			S.push(curr.point);

		if (color == true) {
			A = (Stack<Point2D>) recursiveRange(curr.left, false, new RectHV(rect.xmin(), rect.ymin(), x, rect.ymax()), query);
			B = (Stack<Point2D>) recursiveRange(curr.right, false, new RectHV(x, rect.ymin(), rect.xmax(), rect.ymax()), query);
		} else {
			A = (Stack<Point2D>) recursiveRange(curr.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), y), query);
			B = (Stack<Point2D>) recursiveRange(curr.right, true, new RectHV(rect.xmin(), y, rect.xmax(), rect.ymax()), query);
		}

		for (Point2D p : A)
			S.push(p);

		for (Point2D p : B)
			S.push(p);

		return S;
	}

	/**
	 * @param p
	 *            = point to find nearest neighbor
	 * @return A nearest neighbor in the set to point p; <b>null</b> if the set
	 *         is empty.
	 */

	public Point2D nearest(Point2D p) {
		validate(p);

		currNearest = null;

		recursiveNearest(root, true, new RectHV(0.0, 0.0, 1.0, 1.0), p);

		return currNearest;
	}

	private void recursiveNearest(Node curr, boolean color, RectHV rect, Point2D query) {
		if (curr.point == null)
			return;

		if ((currNearest == null) || (currNearest.distanceSquaredTo(query) > curr.point.distanceSquaredTo(query)))
			currNearest = curr.point;

		if (rect.distanceSquaredTo(query) >= currNearest.distanceSquaredTo(query))
			return;

		double x = curr.point.x();
		double y = curr.point.y();

		if (color == true) {
			if (query.x() < curr.point.x()) {
				recursiveNearest(curr.left, false, new RectHV(rect.xmin(), rect.ymin(), x, rect.ymax()), query);
				recursiveNearest(curr.right, false, new RectHV(x, rect.ymin(), rect.xmax(), rect.ymax()), query);
			} else {
				recursiveNearest(curr.right, false, new RectHV(x, rect.ymin(), rect.xmax(), rect.ymax()), query);
				recursiveNearest(curr.left, false, new RectHV(rect.xmin(), rect.ymin(), x, rect.ymax()), query);
			}

		} else {
			if (query.y() < curr.point.y()) {
				recursiveNearest(curr.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), y), query);
				recursiveNearest(curr.right, true, new RectHV(rect.xmin(), y, rect.xmax(), rect.ymax()), query);
			} else {
				recursiveNearest(curr.right, true, new RectHV(rect.xmin(), y, rect.xmax(), rect.ymax()), query);
				recursiveNearest(curr.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), y), query);
			}
		}
	}

	/**
	 * Unit testing of the methods (optional).
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

	}
}
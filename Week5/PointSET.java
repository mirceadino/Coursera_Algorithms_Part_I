public class PointSET {
	private SET<Point2D> set;

	/**
	 * Construct an empty set of points.
	 */

	public PointSET() {
		set = new SET<Point2D>();
	}

	/**
	 * @return <b>true</b> if the set is empty, <b>false</b> if it's not.
	 */

	public boolean isEmpty() {
		return set.isEmpty();
	}

	/**
	 * @return The number of points in the set.
	 */

	public int size() {
		return set.size();
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
		set.add(p);
	}

	/**
	 * @param p
	 *            = the point to check
	 * @return <b>true</b> if the point belongs to the set, <b>false</b> if it
	 *         doesn't.
	 */

	public boolean contains(Point2D p) {
		validate(p);
		return set.contains(p);
	}

	/**
	 * Draw all the points to the standard draw.
	 */

	public void draw() {
		for (Point2D p : set)
			p.draw();
	}

	/**
	 * @return All the points that are inside the rectangle.
	 */

	public Iterable<Point2D> range(RectHV rect) {
		validate(rect);

		Stack<Point2D> stackedSet = new Stack<Point2D>();

		for (Point2D p : set)
			if (rect.contains(p))
				stackedSet.push(p);

		return stackedSet;
	}

	/**
	 * @param p
	 *            = point to find nearest neighbor
	 * @return A nearest neighbor in the set to point p; <b>null</b> if the set
	 *         is empty.
	 */

	public Point2D nearest(Point2D p) {
		validate(p);

		Point2D answer = null;

		for (Point2D s : set)
			if ((answer == null) || (p.distanceSquaredTo(s) < p.distanceSquaredTo(answer)))
				answer = s;

		return answer;
	}

	/**
	 * Unit testing of the methods (optional).
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

	}
}
package a_Introductory;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;

	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);

		assertEquals("Adding p1 and p2 - x coordinate", 4, (int) res1.x);
		assertEquals("Adding p1 and p2 - y coordinate", -21, (int) res1.y);
		assertEquals("Adding p1 and p3 - x coordinate", -3, (int) res2.x);
		assertEquals("Adding p1 and p3 - y coordinate", 12, (int) res2.y);
	}

	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);

		assertEquals("Subtracting p2 from p1 - x coordinate", 10, (int) res1.x);
		assertEquals("Subtracting p2 from p1 - y coordinate", 39, (int) res1.y);
		assertEquals("Subtracting p3 from p1 - x coordinate", 17, (int) res2.x);
		assertEquals("Subtracting p3 from p1 - y coordinate", 6, (int) res2.y);
	}
}

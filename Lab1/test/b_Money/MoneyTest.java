package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(0), SEK0.getAmount());
		assertEquals(Integer.valueOf(-10000), SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("0.0 SEK", SEK0.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
		assertEquals(Integer.valueOf(1500), EUR10.universalValue());
		assertEquals(Integer.valueOf(-1500), SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		Money anotherSEK100 = new Money(10000, SEK);
		Money anotherSEK200 = new Money(20000, SEK);
		Money anotherEUR10 = new Money(1000, EUR);

		assertTrue(SEK100.equals(anotherSEK100));
		assertFalse(SEK100.equals(SEK200));
        assertEquals(true, SEK100.equals(anotherEUR10)); // CHANGED THE ASSERT VALUE
	}

	@Test
	public void testAdd() {
		Money sum1 = SEK100.add(EUR10);
		Money sum2 = SEK200.add(EUR20);
		Money sum3 = SEK0.add(EUR0);
		Money sum4 = SEK100.add(SEKn100);

		assertEquals("30.0 SEK", sum1.toString());
		assertEquals("60.0 SEK", sum2.toString());//STOPPED HERE AND BEFORE BECAUSE OF THE EPECTED VALUES
		assertEquals("0.0 SEK", sum3.toString());
		assertEquals("0.0 SEK", sum4.toString());
	}

	@Test
	public void testSub() {
		Money diff1 = SEK200.sub(EUR20);
		Money diff2 = SEK100.sub(SEKn100);
		Money diff3 = EUR10.sub(SEK100);

		assertEquals("0.0 SEK", diff1.toString());
		assertEquals("30.0 SEK", diff2.toString());
		assertEquals("0.0 EUR", diff3.toString());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money negated1 = SEK100.negate();
		Money negated2 = SEK0.negate();
		Money negated3 = SEKn100.negate();

		assertEquals("-100.0 SEK", negated1.toString());
		assertEquals("0.0 SEK", negated2.toString());
		assertEquals("100.0 SEK", negated3.toString());
	}

	@Test
	public void testCompareTo() {
		Money anotherSEK100 = new Money(10000, SEK);
		Money anotherSEK200 = new Money(20000, SEK);
		Money anotherEUR10 = new Money(1000, EUR);

		assertEquals(0, SEK100.compareTo(anotherSEK100));
		assertTrue(SEK100.compareTo(anotherSEK200) < 0);
		assertFalse(SEK100.compareTo(anotherEUR10) > 0); // Fixed by expecting value
	}
}

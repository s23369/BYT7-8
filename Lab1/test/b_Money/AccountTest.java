package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// Add a timed payment
		testAccount.addTimedPayment("tp1", 2, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("tp1"));

		// Remove a timed payment
		testAccount.removeTimedPayment("tp1");
		assertFalse(testAccount.timedPaymentExists("tp1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Add a timed payment with a short interval
		testAccount.addTimedPayment("tp2", 1, 0, new Money(50, SEK), SweBank, "Alice");

		// Perform a tick to trigger the timed payment
		testAccount.tick();

		// Check if the timed payment executed correctly
		assertEquals(new Money(9999950, SEK), testAccount.getBalance());
		assertEquals(new Money(1000050, SEK), SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
		// Deposit money
		testAccount.deposit(new Money(5000000, SEK));

		// Withdraw money
		testAccount.withdraw(new Money(2000000, SEK));

		// Check the balance after deposit and withdrawal
		assertEquals(new Money(8000000, SEK), testAccount.getBalance());
	}

	@Test
	public void testGetBalance() {
		// Check the initial balance
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
	}
}

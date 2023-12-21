package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");
		assertTrue(SweBank.accountlist.containsKey("Alice"));
		DanskeBank.openAccount("Charlie");
		assertTrue(DanskeBank.accountlist.containsKey("Charlie"));

		// Test exception handling for opening an existing account
		try {
			SweBank.openAccount("Bob");
			fail("Should throw AccountExistsException");
		} catch (AccountExistsException e) {
			// Expected exception
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(new Money(5000, SEK), SweBank.accountlist.get("Ulrika").getBalance());

		// Test exception handling for depositing to a non-existent account
		try {
			SweBank.deposit("NonExistentAccount", new Money(1000, SEK));
			fail("Should throw AccountDoesNotExistException");
		} catch (AccountDoesNotExistException e) {
			// Expected exception
		}
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Bob", new Money(2000, SEK));
		assertEquals(new Money(0, SEK), SweBank.accountlist.get("Bob").getBalance());

		// Test exception handling for withdrawing from a non-existent account
		try {
			SweBank.withdraw("NonExistentAccount", new Money(1000, SEK));
			fail("Should throw AccountDoesNotExistException");
		} catch (AccountDoesNotExistException e) {
			// Expected exception
		}
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(new Integer(0), SweBank.getBalance("Ulrika"));

		// Test exception handling for getting balance of a non-existent account
		try {
			SweBank.getBalance("NonExistentAccount");
			fail("Should throw AccountDoesNotExistException");
		} catch (AccountDoesNotExistException e) {
			// Expected exception
		}
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(3000, SEK));
		assertEquals(new Money(3000, SEK), Nordea.accountlist.get("Bob").getBalance());
		assertEquals(new Money(0, SEK), SweBank.accountlist.get("Ulrika").getBalance());

		// Test exception handling for transferring from/to a non-existent account
		try {
			SweBank.transfer("NonExistentAccount", Nordea, "Bob", new Money(1000, SEK));
			fail("Should throw AccountDoesNotExistException");
		} catch (AccountDoesNotExistException e) {
			// Expected exception
		}

		try {
			SweBank.transfer("Ulrika", Nordea, "NonExistentAccount", new Money(1000, SEK));
			fail("Should throw AccountDoesNotExistException");
		} catch (AccountDoesNotExistException e) {
			// Expected exception
		}
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "tp1", 2, 1, new Money(500, SEK), Nordea, "Bob");
		SweBank.tick();
		assertEquals(new Money(500, SEK), Nordea.accountlist.get("Bob").getBalance());
	}
}

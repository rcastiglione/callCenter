package ar.com.almundo.callcenter.model;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EmployeeTest extends TestCase {

	public EmployeeTest(final String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(EmployeeTest.class);
	}

	/**
	 * Checks that the employee is not available when picks up a call and that it's available when hangs up.
	 */
	public void testEmployeeAvailability() {
		final Employee e = new Operator(1L, "Operator1");

		Assert.assertTrue(e.isAvailable());

		final Call c = new Call(1L);

		// Starts the call
		c.start(e);

		Assert.assertFalse(e.isAvailable());

		// End the call
		c.end();

		Assert.assertTrue(e.isAvailable());
	}
}
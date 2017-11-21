package ar.com.almundo.callcenter.callhandlerchain;

import ar.com.almundo.callcenter.model.Director;
import ar.com.almundo.callcenter.model.Operator;
import ar.com.almundo.callcenter.model.Supervisor;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CallHandlerChainTest extends TestCase {

	public CallHandlerChainTest(final String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(CallHandlerChainTest.class);
	}

	public void testCreate() {
		final Long amountOperators = 2L;
		final Long amountSupervisors = 1L;
		final Long amountDirectors = 1L;

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(amountOperators, amountSupervisors, amountDirectors);
		Assert.assertEquals(2, operatorCallHandler.getEmployees().size());
		Assert.assertTrue(operatorCallHandler.getEmployees().contains(new Operator(1L, "Operator1")));
		Assert.assertTrue(operatorCallHandler.getEmployees().contains(new Operator(2L, "Operator2")));

		final SupervisorCallHandler supervisorCallHandler = operatorCallHandler.getNextChain();
		Assert.assertEquals(1, supervisorCallHandler.getEmployees().size());
		Assert.assertTrue(supervisorCallHandler.getEmployees().contains(new Supervisor(1L, "Supervisor1")));

		final DirectorCallHandler directorCallHandler = supervisorCallHandler.getNextChain();
		Assert.assertEquals(1, directorCallHandler.getEmployees().size());
		Assert.assertTrue(directorCallHandler.getEmployees().contains(new Director(1L, "Director1")));
	}

	public void testCreateNoneAvailable() {
		final Long amountOperators = 2L;
		final Long amountSupervisors = 1L;
		final Long amountDirectors = 1L;

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandlerNoneAvailable(amountOperators, amountSupervisors, amountDirectors);
		Assert.assertEquals(2, operatorCallHandler.getEmployees().size());
		Assert.assertTrue(operatorCallHandler.getEmployees().contains(new Operator(1L, "Operator1")));
		Assert.assertTrue(operatorCallHandler.getEmployees().contains(new Operator(2L, "Operator2")));
		for (final Operator o : operatorCallHandler.getEmployees()) {
			Assert.assertFalse(o.isAvailable());
		}

		final SupervisorCallHandler supervisorCallHandler = operatorCallHandler.getNextChain();
		Assert.assertEquals(1, supervisorCallHandler.getEmployees().size());
		Assert.assertTrue(supervisorCallHandler.getEmployees().contains(new Supervisor(1L, "Supervisor1")));
		for (final Supervisor s : supervisorCallHandler.getEmployees()) {
			Assert.assertFalse(s.isAvailable());
		}

		final DirectorCallHandler directorCallHandler = supervisorCallHandler.getNextChain();
		Assert.assertEquals(1, directorCallHandler.getEmployees().size());
		Assert.assertTrue(directorCallHandler.getEmployees().contains(new Director(1L, "Director1")));
		for (final Director d : directorCallHandler.getEmployees()) {
			Assert.assertFalse(d.isAvailable());
		}
	}
}
package ar.com.almundo.callcenter;

import org.apache.log4j.Logger;

import ar.com.almundo.callcenter.callhandlerchain.CallHandlerFactory;
import ar.com.almundo.callcenter.callhandlerchain.OperatorCallHandler;
import ar.com.almundo.callcenter.model.Call;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DispatcherTest extends TestCase {
	private final static Logger logger = Logger.getLogger(DispatcherTest.class);

	public DispatcherTest(final String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DispatcherTest.class);
	}

	public void testOneConcurrentCall() throws InterruptedException {
		logger.info("testOneConcurrentCall() start");

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(4L, 2L, 1L);
		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);

		final Call call = new Call(99L);

		dispatcher.dispatchCall(call);
		dispatcher.shutdown();

		logger.info("testOneConcurrentCall() end");
	}

	public void testTwoConcurrentCalls() throws InterruptedException {
		logger.info("testTwoConcurrentCalls() start");

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(4L, 2L, 1L);
		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);

		for (Long i = 1L; i <= 2; i++) {
			dispatcher.dispatchCall(new Call(i));
		}
		dispatcher.shutdown();

		logger.info("testTwoConcurrentCalls() end");
	}

	public void testTenConcurrentCalls() throws InterruptedException {
		logger.info("testTenConcurrentCalls() start");

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(7L, 2L, 1L);
		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);

		for (Long i = 1L; i <= 10; i++) {
			dispatcher.dispatchCall(new Call(i));
		}
		dispatcher.shutdown();

		logger.info("testTenConcurrentCalls() end");
	}

	/**
	 * This is a test case when there is no free employee to pick up a call. The exception {@code AllEmployeesBusyException} should be thrown but this exception is
	 * managed inside the thread {@code CallHandlerTask}.
	 */
	public void testNotAvailableEmployee() throws InterruptedException {
		logger.info("testNotAvailableEmployee() start");
		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandlerNoneAvailable(7L, 2L, 1L);
		Assert.assertFalse(operatorCallHandler.isAnyEmployeeAvailable());

		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);
		final Call call = new Call(32L);

		dispatcher.dispatchCall(call);
		Assert.assertFalse(operatorCallHandler.isAnyEmployeeAvailable());
		Assert.assertNull(call.getEmployee());

		dispatcher.shutdown();
		logger.info("testNotAvailableEmployee() end");
	}

	/**
	 * When there are more than 10 concurrent calls (maximum that can be attended concurrently), the other calls are put to wait to be attended.
	 */
	public void testTwentyConcurrentCalls() throws InterruptedException {
		logger.info("testTwentyConcurrentCalls() start");

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(20L, 2L, 1L);
		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);

		for (Long i = 1L; i <= 20; i++) {
			dispatcher.dispatchCall(new Call(i));
		}
		dispatcher.shutdown();

		logger.info("testTwentyConcurrentCalls() end");
	}
}
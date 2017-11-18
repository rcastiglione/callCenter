package ar.com.almundo.callcenter;

import org.apache.log4j.Logger;

import ar.com.almundo.callcenter.callhandlerchain.CallHandlerFactory;
import ar.com.almundo.callcenter.callhandlerchain.OperatorCallHandler;
import ar.com.almundo.callcenter.model.Call;
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

	public void testOneCall() throws InterruptedException {
		logger.info("testOneCall() start");

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(4L, 2L, 1L);
		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);

		final Call call = new Call(1L);

		dispatcher.dispatchCall(call);
		dispatcher.shutdown();

		logger.info("testOneCall() end");
	}

	public void testTwoCalls() throws InterruptedException {
		logger.info("testTwoCalls() end");

		final OperatorCallHandler operatorCallHandler = CallHandlerFactory.getOperatorCallHandler(4L, 2L, 1L);
		final Dispatcher dispatcher = new Dispatcher(operatorCallHandler);

		for (Long i = 1L; i <= 2; i++) {
			dispatcher.dispatchCall(new Call(i));
		}
		dispatcher.shutdown();

		logger.info("testTwoCalls() end");
	}
}
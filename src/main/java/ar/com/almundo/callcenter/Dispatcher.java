package ar.com.almundo.callcenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import ar.com.almundo.callcenter.callhandlerchain.OperatorCallHandler;
import ar.com.almundo.callcenter.model.Call;

public class Dispatcher {
	private final static Logger logger = Logger.getLogger(Dispatcher.class);

	private static final Integer MAX_CONCURRENT_CALLS = 10;

	private final OperatorCallHandler operatorCallHandler;
	private final ExecutorService executor;

	public Dispatcher(final OperatorCallHandler operatorCallHandler) {
		this.operatorCallHandler = operatorCallHandler;
		this.executor = Executors.newFixedThreadPool(MAX_CONCURRENT_CALLS);
	}

	public void dispatchCall(final Call call) {
		logger.info(String.format("The %s was just dispatched", call));

		this.executor.execute(new CallHandlerTask(this.operatorCallHandler, call));
	}

	public void shutdown() throws InterruptedException {
		this.executor.shutdown();
		this.executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		logger.info("Finished all calls");
	}
}
package ar.com.almundo.callcenter;

import java.util.Random;

import org.apache.log4j.Logger;

import ar.com.almundo.callcenter.callhandlerchain.OperatorCallHandler;
import ar.com.almundo.callcenter.model.Call;

public class CallHandlerTask implements Runnable {
	private final static Logger logger = Logger.getLogger(CallHandlerTask.class);

	private static final int MIN_CALL_DURATION_SECONDS = 5; // 5 seconds
	private static final int MAX_CALL_DURATION_SECONDS = 10; // 10 seconds

	private final Random random = new Random();

	private final OperatorCallHandler operatorCallHandler;
	private final Call call;

	public CallHandlerTask(final OperatorCallHandler operatorCallHandler, final Call call) {
		this.operatorCallHandler = operatorCallHandler;
		this.call = call;
	}

	@Override
	public void run() {
		logger.info(String.format("%s in progress...", this.call));
		this.operatorCallHandler.handleCall(this.call);
		try {
			Thread.sleep(this.getRandomCallDurationMillis());
		} catch (final InterruptedException e) {
			logger.error(e, e);
		}

		this.call.end();
	}

	private int getRandomCallDurationMillis() {
		final int randomNum = this.random.nextInt((MAX_CALL_DURATION_SECONDS - MIN_CALL_DURATION_SECONDS) + 1) + MIN_CALL_DURATION_SECONDS;
		return randomNum * 1000;
	}
}
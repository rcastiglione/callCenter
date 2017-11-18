package ar.com.almundo.callcenter.exception;

public abstract class CallCenterException extends RuntimeException {
	private static final long serialVersionUID = -1997754152378735655L;

	public CallCenterException(final String message) {
		super(message);
	}
}
package ar.com.almundo.callcenter.exception;

public class AllOperatorsBusyException extends CallCenterException {
	private static final long serialVersionUID = 8991258061576726461L;

	public AllOperatorsBusyException() {
		super("All ours operators are busy, please try again later.");
	}
}
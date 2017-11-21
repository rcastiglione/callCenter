package ar.com.almundo.callcenter.exception;

import ar.com.almundo.callcenter.model.Call;

/**
 * This {@code AllEmployeesBusyException} exception is thrown when there is nobody available to pick up a call.
 *
 * @author ricardo
 */
public class AllEmployeesBusyException extends CallCenterException {
	private static final long serialVersionUID = 8991258061576726461L;

	public AllEmployeesBusyException(final Call call) {
		super(String.format("All our employees are busy, the %s couldn't be picked up. Please, try again later.", call));
	}
}
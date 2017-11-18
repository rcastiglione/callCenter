package ar.com.almundo.callcenter.callhandlerchain;

import java.util.List;
import java.util.Optional;

import ar.com.almundo.callcenter.exception.AllOperatorsBusyException;
import ar.com.almundo.callcenter.model.Call;
import ar.com.almundo.callcenter.model.Employee;

/**
 *
 * @author ricardo
 *
 * @param <C>
 *            Chain Type
 * @param <E>
 *            Employee Type
 */
public abstract class CallHandlerChain<C extends CallHandlerChain<?, ?>, E extends Employee> {

	private final C nextChain;
	private final List<E> employees;

	public CallHandlerChain(final C nextChain, final List<E> employees) {
		super();
		this.nextChain = nextChain;
		this.employees = employees;
	}

	public void handleCall(final Call call) {
		final Optional<E> availableEmployeeOptional = this.employees.stream().filter(e -> e.isAvailable()).findFirst();

		if (availableEmployeeOptional.isPresent()) {
			call.start(availableEmployeeOptional.get());
			return;
		}

		if (this.nextChain == null) {
			throw new AllOperatorsBusyException();
		}

		this.nextChain.handleCall(call);

	}

	public C getNextChain() {
		return this.nextChain;
	}

	public List<E> getEmployees() {
		return this.employees;
	}

}
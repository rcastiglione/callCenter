package ar.com.almundo.callcenter.callhandlerchain;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import ar.com.almundo.callcenter.exception.AllEmployeesBusyException;
import ar.com.almundo.callcenter.model.Call;
import ar.com.almundo.callcenter.model.Employee;

/**
 *
 * The {@code CallHandlerChain} class manages the priority of employee types when picking up a call considering the following order: 1. Operators, 2.
 * Supervisors and 3. Directors. This also looks for the one available to pick up the call.
 *
 * @author ricardo
 *
 * @param <C>
 *            Chain Type
 * @param <E>
 *            Employee Type
 */
public abstract class CallHandlerChain<C extends CallHandlerChain<?, ?>, E extends Employee> {
	private final static Logger logger = Logger.getLogger(CallHandlerChain.class);

	private final C nextChain;
	private final List<E> employees;

	public CallHandlerChain(final C nextChain, final List<E> employees) {
		super();
		this.nextChain = nextChain;
		this.employees = employees;
	}

	// this is 'synchronized' to avoid the same employee is chosen for the call, so this operation can't be parallelized.
	public synchronized void handleCall(final Call call) {
		logger.info(String.format("[MONITOR] Availability %s %s", this.getClass().getSimpleName(), this.getAvailabilityDescription()));

		final Optional<E> availableEmployeeOptional = this.getNextEmployeeAvailable();
		if (availableEmployeeOptional.isPresent()) {
			call.start(availableEmployeeOptional.get());
			return;
		}

		if (this.nextChain == null) {
			throw new AllEmployeesBusyException(call);
		}

		this.nextChain.handleCall(call);
	}

	private Optional<E> getNextEmployeeAvailable() {
		// TODO this could be improved by including some mechanism to take the one how took less calls or
		// something like this to be more fair amongst the operators instead of taking always the first one available.
		return this.employees.stream().filter(e -> e.isAvailable()).findFirst();
	}

	public Boolean isAnyEmployeeAvailable() {
		final Optional<E> nextEmployeeAvailable = this.getNextEmployeeAvailable();
		if (nextEmployeeAvailable.isPresent()) {
			return true;
		}

		if (this.nextChain == null) {
			return false;
		}

		return this.nextChain.isAnyEmployeeAvailable();
	}

	private String getAvailabilityDescription() {
		final Integer availableEmployees = Long.valueOf(this.employees.stream().filter(e -> e.isAvailable()).count()).intValue();
		final Integer totalEmployees = this.employees.size();
		return String.format("[%d/%d]", availableEmployees, totalEmployees);
	}

	public C getNextChain() {
		return this.nextChain;
	}

	public List<E> getEmployees() {
		return this.employees;
	}

}
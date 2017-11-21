package ar.com.almundo.callcenter.callhandlerchain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.almundo.callcenter.model.Call;
import ar.com.almundo.callcenter.model.Director;
import ar.com.almundo.callcenter.model.Employee;
import ar.com.almundo.callcenter.model.Operator;
import ar.com.almundo.callcenter.model.Supervisor;

public class CallHandlerFactory {
	private final static Logger logger = Logger.getLogger(CallHandlerFactory.class);

	public static OperatorCallHandler getOperatorCallHandler(final Long amountOperators, final Long amountSupervisors, final Long amountDirectors) {
		final List<Director> directors = getEmployees(amountDirectors, Director.class, false);
		final DirectorCallHandler directorCallHandler = new DirectorCallHandler(directors);

		final List<Supervisor> supervisors = getEmployees(amountSupervisors, Supervisor.class, false);
		final SupervisorCallHandler supervisorCallHandler = new SupervisorCallHandler(directorCallHandler, supervisors);

		final List<Operator> operators = getEmployees(amountOperators, Operator.class, false);
		final OperatorCallHandler operatorCallHandler = new OperatorCallHandler(supervisorCallHandler, operators);

		return operatorCallHandler;
	}

	public static OperatorCallHandler getOperatorCallHandlerNoneAvailable(final Long amountOperators, final Long amountSupervisors, final Long amountDirectors) {
		final List<Director> directors = getEmployees(amountDirectors, Director.class, true);
		final DirectorCallHandler directorCallHandler = new DirectorCallHandler(directors);

		final List<Supervisor> supervisors = getEmployees(amountSupervisors, Supervisor.class, true);
		final SupervisorCallHandler supervisorCallHandler = new SupervisorCallHandler(directorCallHandler, supervisors);

		final List<Operator> operators = getEmployees(amountOperators, Operator.class, true);
		final OperatorCallHandler operatorCallHandler = new OperatorCallHandler(supervisorCallHandler, operators);

		return operatorCallHandler;
	}

	private static <E extends Employee> List<E> getEmployees(final Long amount, final Class<E> employeeClass, final boolean noneAvailable) {
		final List<E> employees = new ArrayList<E>();

		for (Long i = 1L; i < (amount + 1); i++) {
			try {
				final String employeeFullName = employeeClass.getSimpleName() + i;
				final E e = employeeClass.getConstructor(Long.class, String.class).newInstance(i, employeeFullName);
				if (noneAvailable) {
					final Call call = new Call(i);
					call.start(e);
				}
				employees.add(e);
			} catch (final Exception e) {
				logger.error(e, e);
			}
		}

		return employees;
	}
}
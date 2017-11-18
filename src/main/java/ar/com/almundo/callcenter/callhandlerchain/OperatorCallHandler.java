package ar.com.almundo.callcenter.callhandlerchain;

import java.util.List;

import ar.com.almundo.callcenter.model.Operator;

public class OperatorCallHandler extends CallHandlerChain<SupervisorCallHandler, Operator> {

	public OperatorCallHandler(final SupervisorCallHandler nextChain, final List<Operator> employees) {
		super(nextChain, employees);
	}

}
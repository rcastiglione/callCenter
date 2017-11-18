package ar.com.almundo.callcenter.callhandlerchain;

import java.util.List;

import ar.com.almundo.callcenter.model.Supervisor;

public class SupervisorCallHandler extends CallHandlerChain<DirectorCallHandler, Supervisor> {

	public SupervisorCallHandler(final DirectorCallHandler nextChain, final List<Supervisor> employees) {
		super(nextChain, employees);
	}

}
package ar.com.almundo.callcenter.callhandlerchain;

import java.util.List;

import ar.com.almundo.callcenter.model.Director;

public class DirectorCallHandler extends CallHandlerChain<CallHandlerChain<?, ?>, Director> {

	public DirectorCallHandler(final List<Director> employees) {
		super(null, employees);
	}

}
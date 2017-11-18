package ar.com.almundo.callcenter.model;

public abstract class CallCenterModel {

	private final Long id;

	public CallCenterModel(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}
}
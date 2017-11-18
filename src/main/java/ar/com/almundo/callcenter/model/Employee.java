package ar.com.almundo.callcenter.model;

import org.apache.log4j.Logger;

public abstract class Employee extends CallCenterModel {
	private final static Logger logger = Logger.getLogger(Employee.class);

	private final String fullName;
	private Call call;

	public Employee(final Long id, final String fullName) {
		super(id);
		this.fullName = fullName;
	}

	protected void handleCall(final Call call) {
		logger.info(String.format("The %s just picked up the %s", this, call));
		this.call = call;
	}

	public void onEndCall() {
		logger.info(String.format("The %s just finished the %s", this, this.call));
		this.call = null;
	}

	public boolean isAvailable() {
		return this.call == null;
	}

	public String getFullName() {
		return this.fullName;
	}

	@Override
	public boolean equals(final Object otherObj) {
		if (this == otherObj) {
			return true;
		}
		if ((otherObj == null) || !(otherObj instanceof Employee)) {
			return false;
		}

		final Employee other = (Employee) otherObj;

		boolean equals = true;

		equals &= this.getId().equals(other.getId());
		equals &= this.getFullName().equals(other.getFullName());

		return equals;
	}

	@Override
	public int hashCode() {
		int hash = 0;

		hash += this.getId().hashCode();
		hash += this.getFullName().hashCode();

		return hash;
	}

	@Override
	public String toString() {
		return String.format("%s(id=%s, fullName=%s)", this.getClass().getSimpleName(), this.getId(), this.getFullName());
	}
}
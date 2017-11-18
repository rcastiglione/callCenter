package ar.com.almundo.callcenter.model;

public class Call extends CallCenterModel {

	private Employee employee;
	private Long startTimeMillis;
	private Long endTimeMillis;

	public Call(final Long id) {
		super(id);
		this.startTimeMillis = 0L;
		this.endTimeMillis = 0L;
	}

	public void start(final Employee employee) {
		this.startTimeMillis = System.currentTimeMillis();
		this.employee = employee;
		employee.handleCall(this);
	}

	public void end() {
		this.endTimeMillis = System.currentTimeMillis();
		this.employee.onEndCall();
	}

	public Long getDurationSeconds() {
		if (this.endTimeMillis == 0) {
			return 0L;
		}
		return (this.endTimeMillis - this.startTimeMillis) / 1000;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	@Override
	public String toString() {
		return String.format("%s(id=%s, duration=%s)", this.getClass().getSimpleName(), this.getId(), this.getDurationSeconds());
	}
}
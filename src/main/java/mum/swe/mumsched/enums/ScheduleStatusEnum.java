package mum.swe.mumsched.enums;

public enum ScheduleStatusEnum {
	DRAFT(1), PENDING(2), APPROVED(3);
	
	@SuppressWarnings("unused")
	private final int code;
	private ScheduleStatusEnum(int code){
		this.code = code;
	}
	public int getCode() {
		return code;
	}
}

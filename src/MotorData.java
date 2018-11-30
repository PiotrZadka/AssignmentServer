public class MotorData {
	String tagId;
	String motorId;
	String attempt;
	
	MotorData(String tagId,String motorId, String attempt){
		this.tagId = tagId;
		this.attempt = attempt;
		this.motorId = motorId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getAttempt() {
		return attempt;
	}

	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}
	
	public String getMotorId() {
		return motorId;
	}

	public void setMotorId(String motorId) {
		this.motorId = motorId;
	}
	
}

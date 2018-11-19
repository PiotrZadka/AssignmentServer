

public class MotorData {
	String tagId;
	String attempt;
	
	MotorData(String tagId, String attempt){
		this.tagId = tagId;
		this.attempt = attempt;
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
	
}



public class cardReaderData {
	String tagId;
	String decideID;
	
	cardReaderData(String tagId, String decideID){
		this.tagId = tagId;
		this.decideID = decideID;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getReaderId() {
		return decideID;
	}

	public void setReaderId(String deviceID) {
		this.decideID = deviceID;
	}
}

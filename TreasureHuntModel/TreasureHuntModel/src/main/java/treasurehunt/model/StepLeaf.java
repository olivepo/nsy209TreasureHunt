package treasurehunt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StepLeaf extends Step {
	
	// constructeur public sans arguments nécéssaire à jackson
	public StepLeaf() {
		
	}
	
	public StepLeaf(String id, double latitude, double longitude) {
		this();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@JsonProperty
	public String courseEndMessage;


}

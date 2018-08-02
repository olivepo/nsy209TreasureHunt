package treasurehunt.model;

import com.fasterxml.jackson.annotation.*;

public class StepResolution {
	
	@JsonProperty
	public String stepId;
	@JsonProperty
	public int durationInMinutes;
	@JsonProperty
	public boolean jokerUsed;
	
	// constructeur public sans arguments nécéssaire à jackson
	public StepResolution() {
		
	}
	
	@JsonIgnore
	public int getScore() {
		return 0;
	}
	
}

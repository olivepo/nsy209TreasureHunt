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
	public int getScore(Step step) {
		if (step.maximumDurationInMinutes <= 0) {
			return step.scorePointsGivenIfSuccess;
		} else {
			float coefficient = jokerUsed ? 0 : (Float.max(0,
					(float)step.maximumDurationInMinutes - (float)durationInMinutes))/(float)step.maximumDurationInMinutes;
			return Math.round(step.scorePointsGivenIfSuccess * coefficient);
		}
	}
	
}

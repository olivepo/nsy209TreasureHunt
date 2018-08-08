package treasurehunt.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.*;

public class RunThrough {
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:nnnnnnnnn");
	
	// constructeur public sans arguments nécéssaire à jackson
	public RunThrough() {
		this.startedAt = LocalDateTime.now();
		this.endedAt = LocalDateTime.now();
		this.currentStepBegin = LocalDateTime.now();
		this.stepResolutions = new HashMap<String,StepResolution>();
	}
	
	@JsonProperty
	public String id;
	@JsonProperty
	public String accountEmail;
	@JsonProperty
	public String courseId;
	
	
	@JsonIgnore
	public LocalDateTime startedAt;
	@JsonProperty
	public String getStartedAt() {
		return startedAt.format(formatter);
	}
	@JsonProperty
	public void setStartedAt(String startedAt) {
		this.startedAt = LocalDateTime.parse(startedAt, formatter);
	}
	
	
	@JsonIgnore
	public LocalDateTime endedAt;
	@JsonProperty
	public String getEndedAt() {
		return endedAt.format(formatter);
	}
	@JsonProperty
	public void setEndedAt(String endedAt) {
		this.endedAt = LocalDateTime.parse(endedAt, formatter);
	}
	
	@JsonIgnore
	private LocalDateTime currentStepBegin;
	@JsonProperty
	public String getCurrentStepBegin() {
		return currentStepBegin.format(formatter);
	}
	@JsonProperty
	public void setCurrentStepBegin(String currentStepBegin) {
		this.currentStepBegin = LocalDateTime.parse(currentStepBegin, formatter);
	}
	
	@JsonIgnore
	private Step currentStep;
	@JsonIgnore
	private HashMap<String,StepResolution> stepResolutions;
	@JsonProperty
	public HashMap<String,StepResolution> getStepResolutions() {
		return stepResolutions;
	}
	@JsonProperty
	public void setStepResolutions(HashMap<String,StepResolution> stepResolutions) {
		this.stepResolutions = stepResolutions;
	}
	
	@JsonIgnore
	public boolean isCompleted() {
		return false;
	}
	
	@JsonIgnore
	public int getScore() {
		int result = 0;
		for (String key : stepResolutions.keySet()) {
			// à faire
		}
		return result;
	}
	
	@JsonIgnore
	public Step getCurrentStep() {
		return currentStep;
	}
	
	@JsonIgnore
	public void setCurrentStep(Step step) {
		currentStepBegin = LocalDateTime.now();
		currentStep = step;
	}
	
	@JsonIgnore
	public int getJokersUsed() {
		int result = 0;
		for (String key : stepResolutions.keySet()) {
			if (stepResolutions.get(key).jokerUsed) {
				result++;
			}
		}
		return result;
	}
	
	public void validateCurrentStepResolution(LocalDateTime time, boolean jokerUsed) {
		StepResolution stepResolution = new StepResolution();
		stepResolution.durationInMinutes = (int)Duration.between(currentStepBegin, time).toMinutes();
		stepResolution.jokerUsed = jokerUsed;
		stepResolution.stepId = currentStep.id;
		stepResolutions.put(currentStep.id,stepResolution);
	}
}

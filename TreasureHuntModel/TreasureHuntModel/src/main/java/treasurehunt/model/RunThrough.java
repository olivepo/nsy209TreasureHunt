package treasurehunt.model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

public class RunThrough {
	

	public int id;
	public Account account;
	public Course course;
	public LocalDateTime startedAt;
	public LocalDateTime endedAt;
	private LocalDateTime currentStepBegin;
	private Step currentStep;
	private List<StepResolution> stepResolutions;
	
	public boolean isCompleted() {
		return false;
	}
	
	public int getScore() {
		return 0;
	}
	
	public Step getCurrentStep() {
		return currentStep;
	}
	
	public void setCurrentStep(Step step) {
		currentStepBegin = LocalDateTime.now();
		currentStep = step;
	}
	
	public void validateCurrentStepResolution(LocalDateTime time, boolean jokerUsed) {
		StepResolution stepResolution = new StepResolution();
		stepResolution.durationInMinutes = (int)Duration.between(currentStepBegin, time).toMinutes();
		stepResolution.jokerUsed = jokerUsed;
		stepResolutions.add(stepResolution);
	}
}

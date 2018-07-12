package treasurehunt.model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="RunThrough")
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
	
	@XmlTransient
	public int getScore() {
		return 0;
	}
	
	@XmlTransient
	public Step getCurrentStep() {
		return currentStep;
	}
	
	@XmlTransient
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

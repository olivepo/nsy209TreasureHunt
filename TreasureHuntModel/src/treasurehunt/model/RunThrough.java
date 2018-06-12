package treasurehunt.model;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class RunThrough {
	
	@XmlTransient
	public Account account;
	@XmlTransient
	public Course course;
	
	@XmlElement
	public LocalDateTime startedAt;
	@XmlElement
	public LocalDateTime endedAt;

	private LocalDateTime currentStepBegin;
	private Step currentStep;
	

    @XmlElement(name="StepResolution")
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

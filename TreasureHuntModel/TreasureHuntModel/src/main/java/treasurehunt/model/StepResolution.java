package treasurehunt.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="StepResolution")
public class StepResolution {
	
	public Step step;
	public int durationInMinutes;
	public boolean jokerUsed;
	
	@XmlTransient
	public int getScore() {
		return 0;
	}
	
	@XmlTransient
	public String getStepId() {
		return step.id;
	}
	
}

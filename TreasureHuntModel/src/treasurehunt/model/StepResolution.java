package treasurehunt.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class StepResolution {
	
	@XmlTransient
	public Step step;
	public int durationInMinutes;
	public boolean jokerUsed;
	
	@XmlTransient
	public int getScore() {
		return 0;
	}
	
	public int getStepId() {
		return step.id;
	}
	
}

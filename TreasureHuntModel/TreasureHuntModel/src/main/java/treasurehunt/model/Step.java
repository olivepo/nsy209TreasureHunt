package treasurehunt.model;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlTransient
public abstract class Step {
	
	public String id;
	public float latitude;
	public float longitude;
	public int scorePointsGivenIfSuccess;
	public int maximumDurationInMinutes;
	public String description;
	public Riddle riddle;
	public List<Step> nextSteps;
	
	public abstract void addStep(Step step);
	public abstract void removeStep(Step step);
	
}

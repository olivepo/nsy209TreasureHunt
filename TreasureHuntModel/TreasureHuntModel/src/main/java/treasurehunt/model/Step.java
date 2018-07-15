package treasurehunt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
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

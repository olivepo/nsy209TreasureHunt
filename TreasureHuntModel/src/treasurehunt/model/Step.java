package treasurehunt.model;

import java.util.List;

public abstract class Step {

	public int id;
	public float latitude;
	public float longitude;
	public int scorePointsGivenIfSuccess;
	public int maximumDurationInMinutes;
	public String description;
	public Riddle riddle;
	protected List<Step> nextSteps;
	
	public abstract void addStep(Step step);
	public abstract void removeStep(Step step);
	
}

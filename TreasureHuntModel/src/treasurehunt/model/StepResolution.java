package treasurehunt.model;

public class StepResolution {
	
	public Step step;
	public int durationInMinutes;
	public boolean jokerUsed;
	
	public int getScore() {
		return 0;
	}
	
	public int getStepId() {
		return step.id;
	}
	
}

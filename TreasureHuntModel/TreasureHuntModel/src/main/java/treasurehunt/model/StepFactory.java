package treasurehunt.model;

public interface StepFactory {
	
	public Step createInstance(String id, double latitude, double longitude);
	
}

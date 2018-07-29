package treasurehunt.model;

public class StepCompositeFactory implements StepFactory {

	@Override
	public Step createInstance(String id, double latitude, double longitude) {
		// TODO Auto-generated method stub
		return new StepComposite(id, latitude, longitude);
	}

}

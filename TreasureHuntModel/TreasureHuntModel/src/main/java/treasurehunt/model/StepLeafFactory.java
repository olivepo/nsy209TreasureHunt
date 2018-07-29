package treasurehunt.model;

public class StepLeafFactory implements StepFactory {

	@Override
	public Step createInstance(String id, double latitude, double longitude) {
		// TODO Auto-generated method stub
		return new StepLeaf(id, latitude, longitude);
	}

}

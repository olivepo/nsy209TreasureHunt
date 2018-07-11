package treasurehunt.model;

public class StepLeafFactory implements StepFactory {

	@Override
	public Step createInstance() {
		// TODO Auto-generated method stub
		return new StepLeaf();
	}

}

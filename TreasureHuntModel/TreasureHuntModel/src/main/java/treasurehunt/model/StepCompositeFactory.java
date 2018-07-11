package treasurehunt.model;

public class StepCompositeFactory implements StepFactory {

	@Override
	public Step createInstance() {
		// TODO Auto-generated method stub
		return new StepComposite();
	}

}

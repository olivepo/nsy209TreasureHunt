package treasurehunt.model;

public class StepComposite extends Step {

	@Override
	public void addStep(Step step) {
		// TODO Auto-generated method stub
		nextSteps.add(step);
	}

	@Override
	public void removeStep(Step step) {
		// TODO Auto-generated method stub
		nextSteps.remove(step);
	}

}

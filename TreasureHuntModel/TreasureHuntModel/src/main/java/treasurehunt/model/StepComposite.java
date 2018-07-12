package treasurehunt.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="StepComposite")
public class StepComposite extends Step {
	
	public StepComposite() {
		
	}
	
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

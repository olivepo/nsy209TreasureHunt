package treasurehunt.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="StepLeaf")
public class StepLeaf extends Step {
	
	public StepLeaf() {
		
	}
	
	public String courseEndMessage;
	
	@Override
	public void addStep(Step step) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStep(Step step) {
		// TODO Auto-generated method stub
		
	}

}

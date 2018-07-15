package treasurehunt.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="StepComposite")
public class StepComposite extends Step {
	
	// constructeur public sans arguments nécéssaire à jackson
	public StepComposite() {
		
	}
	
	public StepComposite(String id, float latitude, float longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		nextSteps = new ArrayList<Step>();
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

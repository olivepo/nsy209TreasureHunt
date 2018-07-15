package treasurehunt.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="StepLeaf")
public class StepLeaf extends Step {
	
	// constructeur public sans arguments nécéssaire à jackson
	public StepLeaf() {
		
	}
	
	public StepLeaf(String id, float latitude, float longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
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

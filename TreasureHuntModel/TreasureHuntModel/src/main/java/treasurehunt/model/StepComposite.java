package treasurehunt.model;

import java.util.HashMap;
import java.util.HashSet;

import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.*;

public class StepComposite extends Step {
	
	@JsonIgnore
	private HashMap<String,Step> nextSteps;
	@JsonIgnore
	private HashSet<String> nextStepsIds;
	
	// constructeur public sans arguments nécéssaire à jackson
	public StepComposite() {
		nextSteps = new HashMap<String,Step>();
		nextStepsIds= new HashSet<String>();
	}
	
	public StepComposite(String id, double latitude, double longitude) {
		this();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
	
	@JsonProperty
	public HashSet<String> getNextStepsIds() {
		return nextStepsIds;
	}
	@JsonIgnore
	public Step getNextStep(String id) {
		return nextSteps.get(id);
	}
	
	public void addStep(Step step) {
		nextSteps.put(step.id,step);
		nextStepsIds.add(step.id);
	}

	public void removeStep(String id) {
		nextSteps.remove(id);
		nextStepsIds.remove(id);
	}

}

package treasurehunt.model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Step {

	public int id;
	public float latitude;
	public float longitude;
	public int scorePointsGivenIfSuccess;
	public int maximumDurationInMinutes;
	public String description;
	
	public Riddle riddle;
	@XmlElementWrapper(name="nextSteps")
    @XmlElements({
       @XmlElement(name="StepLeaf",     type=StepLeaf.class),
       @XmlElement(name="StepComposite", type=StepComposite.class)
    })
	protected List<Step> nextSteps;
	
	public abstract void addStep(Step step);
	public abstract void removeStep(Step step);
	
}

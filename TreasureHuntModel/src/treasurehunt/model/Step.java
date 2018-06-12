package treasurehunt.model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Step {

	@XmlElement
	public int id;
	@XmlElement
	public float latitude;
	@XmlElement
	public float longitude;
	@XmlElement
	public int scorePointsGivenIfSuccess;
	@XmlElement
	public int maximumDurationInMinutes;
	@XmlElement
	public String description;
	@XmlElement
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

package treasurehunt.model;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = StepComposite.class, name = "StepComposite"),
				@JsonSubTypes.Type(value = StepLeaf.class, name = "StepLeaf") })
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public abstract class Step {
	
	@JsonProperty
	public String id;
	@JsonProperty
	public double latitude;
	@JsonProperty
	public double longitude;
	@JsonProperty
	public int scorePointsGivenIfSuccess;
	@JsonProperty
	public int maximumDurationInMinutes;
	@JsonProperty
	public String description;
	@JsonProperty
	public Riddle riddle;
	
}

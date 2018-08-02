package treasurehunt.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunThroughs {
	
	@JsonProperty
	public List<RunThrough> list;
	
	// constructeur public sans arguments nécéssaire à jackson
	public RunThroughs() {
		list = new ArrayList<RunThrough>();
	}
	
}

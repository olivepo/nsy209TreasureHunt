package treasurehunt.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Courses {
	
	@JsonProperty
	public List<Course> list;
	
	// constructeur public sans arguments nécéssaire à jackson
	public Courses() {
		list = new ArrayList<Course>();
	}
	
}
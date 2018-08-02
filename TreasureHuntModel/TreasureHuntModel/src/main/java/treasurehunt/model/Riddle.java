package treasurehunt.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Riddle {
	
	@JsonProperty
	public String text;
	@JsonProperty
	public String jokerText;
	@JsonProperty
	public boolean isMCQ;
	@JsonProperty
	public List<AnswerChoice> answerChoices;
	
	// constructeur public sans arguments nécéssaire à jackson
	public Riddle() {
		answerChoices = new ArrayList<AnswerChoice>();
	}
	
}

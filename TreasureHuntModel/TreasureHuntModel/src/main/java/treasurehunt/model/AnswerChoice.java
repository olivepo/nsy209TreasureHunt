package treasurehunt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerChoice {
	
	@JsonProperty
	public String text;
	@JsonProperty
	public boolean isValid;
	
	// constructeur public sans arguments nécéssaire à jackson
	public AnswerChoice() {
		
	}
	
	public AnswerChoice(String text,boolean isValid) {
		this.text = text;
		this.isValid = isValid;
	}
	
}

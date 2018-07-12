package treasurehunt.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AnswerChoice")
public class AnswerChoice {
	
	public String text;
	public boolean isValid;
	
	public AnswerChoice() {
		
	}
	
}

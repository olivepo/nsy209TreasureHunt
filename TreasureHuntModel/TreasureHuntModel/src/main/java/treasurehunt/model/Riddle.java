package treasurehunt.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Riddle")
public class Riddle {
	
	public String text;
	public String jokerText;
	public List<AnswerChoice> answerChoices;
	
	public Riddle() {
		answerChoices = new ArrayList<AnswerChoice>();
	}
	
}

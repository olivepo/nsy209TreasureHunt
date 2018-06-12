package treasurehunt.model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Riddle {
	
	public String text;
	public String jokerText;
	@XmlElement(name="AnswerChoice")
	public List<AnswerChoice> answerChoices;
	
}

package treasurehunt.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Course {
	
	public String id;

	public String name;

	public LocalDateTime begin;

	public LocalDateTime end;

	public int jokersAllowed;

	public StepComposite start;
	
}

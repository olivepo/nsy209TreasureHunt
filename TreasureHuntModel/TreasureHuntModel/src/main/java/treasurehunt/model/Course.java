package treasurehunt.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="Course")
public class Course {

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:nnnnnnnnn");
	
	public Course() {
		this.begin = LocalDateTime.now();
		this.end = LocalDateTime.now();
	}
	
	public String id;
	public String accountEmail;
	public String name;
	
	@XmlTransient
	public LocalDateTime begin;
	@XmlElement
	public String getBegin() {
		return begin.format(formatter);
	}
	public void setBegin(String begin) {
		this.begin = LocalDateTime.parse(begin, formatter);
	}
	
	@XmlTransient
	public LocalDateTime end;
	@XmlElement
	public String getEnd() {
		return end.format(formatter);
	}
	public void setEnd(String end) {
		this.end = LocalDateTime.parse(end, formatter);
	}
	
	public int jokersAllowed;
	public StepComposite start;
	
}

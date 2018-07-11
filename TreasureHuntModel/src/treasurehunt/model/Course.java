package treasurehunt.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Course {

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public int id;
	
	public String accountEmail;
	
	public String name;
	

	public LocalDateTime begin;
	public String getBegin() {
		return begin.format(formatter);
	}
	public void setBegin(String begin) {
		this.begin = LocalDateTime.parse(begin, formatter);
	}
	

	public LocalDateTime end;
	public String getEnd() {
		return end.format(formatter);
	}
	public void setEnd(String end) {
		this.end = LocalDateTime.parse(end, formatter);
	}

	public int jokersAllowed;

	public StepComposite start;
	
}

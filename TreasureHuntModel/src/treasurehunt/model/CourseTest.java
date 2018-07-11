package treasurehunt.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class CourseTest {

	@Test
	public void testBeginGetSet() {
		String now = "2018-07-01 12:06:03";
		Course course = new Course();
		course.setBegin(now);
		assertTrue(course.begin.getYear() == 2018);
		assertTrue(course.begin.getMonth().getValue() == 7);
		assertTrue(course.begin.getDayOfMonth() == 1);
		assertTrue(course.begin.getHour() == 12);
		assertTrue(course.begin.getMinute() == 6);
		assertTrue(course.begin.getSecond() == 3);
		assertTrue(course.getBegin().equals(now));
	}
	
	@Test
	public void testEndGetSet() {
		String now = "2018-07-01 12:06:03";
		Course course = new Course();
		course.setEnd(now);
		assertTrue(course.end.getYear() == 2018);
		assertTrue(course.end.getMonth().getValue() == 7);
		assertTrue(course.end.getDayOfMonth() == 1);
		assertTrue(course.end.getHour() == 12);
		assertTrue(course.end.getMinute() == 6);
		assertTrue(course.end.getSecond() == 3);
		assertTrue(course.getEnd().equals(now));
	}
}

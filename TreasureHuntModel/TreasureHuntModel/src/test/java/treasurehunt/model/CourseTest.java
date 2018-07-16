package treasurehunt.model;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class CourseTest {

	@Test
	public void testBeginGetSet() {
		String now = "2018-07-01 12:06:03:000000000";
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
		String now = "2018-07-01 12:06:03:000000000";
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

	@Test
	public void testGetSteps() {
		Course c = new Course();
		c.start = (StepComposite) new StepCompositeFactory().createInstance("step1",0.0f,0.0f);
		c.start.id = "step1id";
		StepComposite step2 = (StepComposite) new StepCompositeFactory().createInstance("step2",0.0f,0.0f);
		StepComposite step3 = (StepComposite) new StepCompositeFactory().createInstance("step3",0.0f,0.0f);
		StepLeaf step4 = (StepLeaf) new StepLeafFactory().createInstance("step4",0.0f,0.0f);
		c.start.addStep(step2);
		c.start.addStep(step3);
		step2.addStep(step4);
		step3.addStep(step4);
		HashMap<String,Step> steps = c.getStepsAsHashMap();
		assertTrue(steps.size() == 3);
		assertTrue(steps.get("step2") instanceof StepComposite);
		assertTrue(steps.get("step4") instanceof StepLeaf);
	}
	
}

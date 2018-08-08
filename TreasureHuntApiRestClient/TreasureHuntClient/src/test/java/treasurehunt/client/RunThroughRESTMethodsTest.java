package treasurehunt.client;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import treasurehunt.model.RunThrough;
import treasurehunt.model.StepComposite;
import treasurehunt.model.StepLeaf;

public class RunThroughRESTMethodsTest {
	
	private final static String baseUrl = "http://35.234.90.191/TreasureHuntApiRestServer/api/";
	
	private final static String accountEmail = "test@montest.fr";
	private final static String courseId = "uniqueId";
	private final static LocalDateTime endedAt = LocalDateTime.now();
	
	@Test
	public void testAll() {
		
		//Configuration.baseUrl = baseUrl;
		
		testDelete();
		// base vide : Echec des operations GET,DELETE
		assertFalse(testGet());
		assertFalse(testDelete());

		// insertion de 1 element
		assertTrue(testPut());

		// base complete : réussite des opérations GET,DELETE
		assertTrue(testGet());
		assertTrue(testGetAll());
		/*assertTrue(testDelete());

		// base a nouveau vide
		assertFalse(testGet());*/
	}


	private boolean testPut() {
		
		RunThrough r = new RunThrough();
		r.setAccountEmail(accountEmail);
		r.setCourseId(courseId);
		r.endedAt = endedAt;
		StepComposite aStep = new StepComposite("aStep",0.0f,0.0f);
		StepLeaf finalStep = new StepLeaf("aFinalStep",0.0f,0.0f);
		aStep.addStep(finalStep);
		r.setCurrentStep(aStep);
		r.validateCurrentStepResolution(LocalDateTime.now(), true);
		r.setCurrentStep(finalStep);
		r.validateCurrentStepResolution(LocalDateTime.now(), false);
		
		try {
			return RunThroughRESTMethods.put(r);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean testGet() {

		RunThrough r;
		try {
			r = RunThroughRESTMethods.get(accountEmail,courseId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (r == null) return false;
		if (!r.getAccountEmail().equals(accountEmail)) return false;
		if (!r.getCourseId().equals(courseId)) return false;
		if (!r.endedAt.equals(endedAt)) return false;
		if (r.getStepResolutions().size() != 2) return false;
		
		return true;

	}

	private boolean testDelete() {

		try {
			return RunThroughRESTMethods.delete(accountEmail,courseId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean testGetAll() {

		List<RunThrough> list;
		try {
			list = RunThroughRESTMethods.getRunThroughs(accountEmail);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return !list.isEmpty();

	}
}

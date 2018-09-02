package treasurehunt.client;

import static org.junit.Assert.*;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.List;

import treasurehunt.model.AnswerChoice;
import treasurehunt.model.Course;
import treasurehunt.model.Riddle;
import treasurehunt.model.StepComposite;
import treasurehunt.model.StepCompositeFactory;
import treasurehunt.model.StepLeaf;
import treasurehunt.model.StepLeafFactory;

public class CourseRESTMethodsTest {
	
	private final static String baseUrl = "http://35.234.90.191/TreasureHuntApiRestServer/api/";
	
	private final static String id = "uniqueid";
	private final static String accountEmail = "test@montest.fr";
	private final static String name = "Chasse de test";
	private final static LocalDateTime end = LocalDateTime.now();
	private final static int jokersAllowed = 3;
	private final static double latitude = 47.796761; // guidel
	private final static double longitude = -3.487450; // guidel
	private final static int radiusInMetres = 7000;
	private final static double outOfBoundslatitude = 47.747544; // lorient
	private final static double outOfBoundslongitude = -3.366260; // lorient
	private final static double inBoundslatitude = 47.770951; // guidel-plages
	private final static double inBoundslongitude = -3.525428; // guidel-plages
	
	@Test
	public void testAll() {
		
		Configuration.baseUrl = baseUrl;
		
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
		try {
			CourseRESTMethods.getNearestCourses(inBoundslatitude, inBoundslongitude, 30000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private boolean testPut() {

		Course c = new Course();
		c.id = id;
		c.accountEmail = accountEmail;
		c.name = name;
		c.end = end;
		c.jokersAllowed = jokersAllowed;
		c.start = (StepComposite) new StepCompositeFactory().createInstance("step1",latitude,longitude);
		c.start.id = "step1id";
		c.start.description = "Rendez-vous devant la maison avec le porche bleu";
		c.start.riddle = new Riddle();
		c.start.riddle.isMCQ = true;	
		c.start.riddle.text = "Donnez la bonne réponse";
		c.start.riddle.jokerText = "Choisissez la bone réponse";
		c.start.riddle.answerChoices.add(new AnswerChoice("La bonne réponse",true));
		c.start.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		c.start.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		c.start.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		c.start.maximumDurationInMinutes = 0;
		c.start.scorePointsGivenIfSuccess = 500;
		StepComposite step2 = (StepComposite) new StepCompositeFactory().createInstance("step2",47.796095,-3.482712);	
		step2.description = "Faîtes 100 pas vers l'est puis 50 au sud pour atteindre l'étape suivante";
		step2.riddle = new Riddle();
		step2.riddle.isMCQ = true;
		step2.riddle.text = "Donnez la bonne réponse";
		step2.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		step2.riddle.answerChoices.add(new AnswerChoice("La bonne réponse",true));
		step2.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		step2.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		step2.maximumDurationInMinutes = 20;
		step2.scorePointsGivenIfSuccess = 1000;
		StepComposite step3 = (StepComposite) new StepCompositeFactory().createInstance("step3",47.792789,-3.491570);
		step3.description = "Rendez-vous à la troisième étape";
		step3.riddle = new Riddle();
		step3.riddle.isMCQ = true;
		step3.riddle.text = "Donnez la bonne réponse";
		step3.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		step3.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		step3.riddle.answerChoices.add(new AnswerChoice("La bonne réponse",true));
		step3.riddle.answerChoices.add(new AnswerChoice("une mauvaise réponse",false));
		step3.maximumDurationInMinutes = 15;
		step3.scorePointsGivenIfSuccess = 1000;
		StepLeaf step4 = (StepLeaf) new StepLeafFactory().createInstance("step4",47.790349,-3.488269);
		step4.description = "Pour finir le parcours, suivez le chemin de terre jusqu'à l'orée du bois.";
		step4.riddle = new Riddle();
		step4.riddle.isMCQ = true;
		step4.riddle.text = "Quelle est la couleur du cheval blanc d'Henri IV après la bataille ?";
		step4.riddle.answerChoices.add(new AnswerChoice("Blanc",false));
		step4.riddle.answerChoices.add(new AnswerChoice("Rouge",false));
		step4.riddle.answerChoices.add(new AnswerChoice("Gris",false));
		step4.riddle.answerChoices.add(new AnswerChoice("A la fois blanc, rouge et gris",true));
		step4.maximumDurationInMinutes = 20;
		step4.scorePointsGivenIfSuccess = 1000;
		c.start.addStep(step2);
		c.start.addStep(step3);
		step2.addStep(step4);
		step3.addStep(step4);
		
		try {
			return CourseRESTMethods.put(c);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean testGet() {

		Course c;
		try {
			c = CourseRESTMethods.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (c == null) return false;
		if (!c.id.equals(id)) return false;
		if (!c.name.equals(name)) return false;
		if (!c.end.equals(end)) return false;
		if (!c.start.id.equals("step1id")) return false;
		if (!c.start.getNextStepsIds().contains("step2")) return false;
		if (!c.start.getNextStepsIds().contains("step3")) return false;
		StepComposite step2 = (StepComposite) c.start.getNextStep("step2");
		StepComposite step3 = (StepComposite) c.start.getNextStep("step3");
		if (!step2.getNextStepsIds().contains("step4")) return false;
		if (!step3.getNextStepsIds().contains("step4")) return false;
		if (step2.getNextStep("step4") != step3.getNextStep("step4")) return false; // les deux doivent pointer vers le même objet step4
		if (!step3.riddle.answerChoices.get(0).text.equals("une mauvaise réponse")) return false;
		
		return true;

	}

	private boolean testDelete() {

		try {
			return CourseRESTMethods.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean testGetAll() {

		List<Course> list;
		try {
			list = CourseRESTMethods.getNearestCourses(outOfBoundslatitude,outOfBoundslongitude,radiusInMetres);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (!list.isEmpty()) return false;
		try {
			list = CourseRESTMethods.getNearestCourses(inBoundslatitude,inBoundslongitude,radiusInMetres);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return (list.size() >= 1);
	}

}
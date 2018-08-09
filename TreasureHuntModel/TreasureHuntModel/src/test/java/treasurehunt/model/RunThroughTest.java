package treasurehunt.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class RunThroughTest {
	
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
	public void testgetScore() {
		
		Course c = new Course();
		c.id = id;
		c.accountEmail = accountEmail;
		c.name = name;
		c.end = end;
		c.jokersAllowed = jokersAllowed;
		c.start = (StepComposite) new StepCompositeFactory().createInstance("step1",latitude,longitude);
		c.start.id = "step1id";
		c.start.description = "Rendez-vous à la première étape";
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
		step2.description = "Rendez-vous à la deuxième étape";
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
		step4.description = "Rendez-vous à l'étape finale";
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
		
		RunThrough r = new RunThrough();
		Step currentStep = c.start;
		r.setCourseId(c.id);
		r.setAccountEmail(accountEmail);
		r.setCurrentStep(currentStep);
		r.validateCurrentStepResolution(LocalDateTime.now().plusMinutes(5), false);
		currentStep = ((StepComposite) currentStep).getNextStep(step2.id);
		r.setCurrentStep(currentStep);
		r.validateCurrentStepResolution(LocalDateTime.now().plusMinutes(15), false);
		currentStep = ((StepComposite) currentStep).getNextStep(step4.id);
		r.setCurrentStep(currentStep);
		r.validateCurrentStepResolution(LocalDateTime.now().plusHours(1), true);
		
		assertTrue(r.getScore(c) == 750);
	}

}

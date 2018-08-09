package treasurehunt.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.*;

public class RunThrough {
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:nnnnnnnnn");
	
	// constructeur public sans arguments nécéssaire à jackson
	public RunThrough() {
		this.startedAt = LocalDateTime.now();
		this.endedAt = LocalDateTime.now();
		this.currentStepBegin = LocalDateTime.now();
		this.stepResolutions = new HashMap<String,StepResolution>();
	}
	
	@JsonProperty
	private String id;
	@JsonIgnore
	public String getId() {
		return id;
	}
	
	@JsonProperty
	private String accountEmail;
	@JsonIgnore
	public String getAccountEmail() {
		return accountEmail;
	}
	@JsonIgnore
	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
		buildId();
	}
	@JsonProperty
	private String courseId;
	@JsonIgnore
	public String getCourseId() {
		return courseId;
	}
	@JsonIgnore
	public void setCourseId(String courseId) {
		this.courseId = courseId;
		buildId();
	}
	
	@JsonIgnore
	public LocalDateTime startedAt;
	@JsonProperty
	public String getStartedAt() {
		return startedAt.format(formatter);
	}
	@JsonProperty
	public void setStartedAt(String startedAt) {
		this.startedAt = LocalDateTime.parse(startedAt, formatter);
	}
	
	
	@JsonIgnore
	public LocalDateTime endedAt;
	@JsonProperty
	public String getEndedAt() {
		return endedAt.format(formatter);
	}
	@JsonProperty
	public void setEndedAt(String endedAt) {
		this.endedAt = LocalDateTime.parse(endedAt, formatter);
	}
	
	@JsonIgnore
	private LocalDateTime currentStepBegin;
	@JsonProperty
	public String getCurrentStepBegin() {
		return currentStepBegin.format(formatter);
	}
	@JsonProperty
	public void setCurrentStepBegin(String currentStepBegin) {
		this.currentStepBegin = LocalDateTime.parse(currentStepBegin, formatter);
	}
	
	@JsonIgnore
	private Step currentStep;
	@JsonIgnore
	private HashMap<String,StepResolution> stepResolutions;
	@JsonProperty
	public HashMap<String,StepResolution> getStepResolutions() {
		return stepResolutions;
	}
	@JsonProperty
	public void setStepResolutions(HashMap<String,StepResolution> stepResolutions) {
		this.stepResolutions = stepResolutions;
	}
	
	@JsonIgnore
	public boolean isCompleted() {
		return false;
	}
	
	@JsonIgnore
	public int getScore(Course course) {
		int result = 0;
		StepResolution currentStepResolution;
		HashMap<String,Step> steps = course.getStepsAsHashMap();
		steps.put(course.start.id, course.start); // start ne fait pas partie des steps fournis
		for (String key : stepResolutions.keySet()) {
			currentStepResolution = stepResolutions.get(key);
			result += currentStepResolution.getScore(steps.get(currentStepResolution.stepId));
		}
		return result;
	}
	
	@JsonIgnore
	public Step getCurrentStep() {
		return currentStep;
	}
	
	@JsonIgnore
	public void setCurrentStep(Step step) {
		currentStepBegin = LocalDateTime.now();
		currentStep = step;
	}
	
	@JsonIgnore
	public int getJokersUsed() {
		int result = 0;
		for (String key : stepResolutions.keySet()) {
			if (stepResolutions.get(key).jokerUsed) {
				result++;
			}
		}
		return result;
	}
	
	private void buildId() {
		id = buildId(accountEmail,courseId);
	}
	
	public static String buildId(String accountEmail,String courseId) {
		return accountEmail+";"+courseId;
	}
	
	public void validateCurrentStepResolution(LocalDateTime time, boolean jokerUsed) {
		StepResolution stepResolution = new StepResolution();
		stepResolution.durationInMinutes = (int)Duration.between(currentStepBegin, time).toMinutes();
		stepResolution.jokerUsed = jokerUsed;
		stepResolution.stepId = currentStep.id;
		stepResolutions.put(currentStep.id,stepResolution);
	}
}

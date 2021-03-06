package treasurehunt.model;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CourseStepsIterator implements Iterator<Step>{

	Deque<Step> stack = new LinkedList<>();


    public CourseStepsIterator(Course course) {
        stack.add(course.start);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Step next() {
        if(stack.isEmpty()){
            throw new NoSuchElementException();
        }
        Step currentStep = stack.pop();
        if (currentStep != null) {
            if (currentStep instanceof StepComposite) {
                StepComposite s = (StepComposite) currentStep;
                for (String nextStepId : s.getNextStepsIds()) {
                	if (s.getNextStep(nextStepId) != null) {
                		stack.add(s.getNextStep(nextStepId));
                	}
                }
            }
        }
        return currentStep;
    }

}


package ct414;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
public class AssessmentImpl implements Assessment, Serializable {

    

    private ArrayList<Question> questions = new ArrayList<>();
    private String info;
    private Date closingDate;
    private String courseCode;
    private int[] selectedAnswers;
    private int ID;
    
    public AssessmentImpl(String inf, String cCode, int ID){
        
        this.info = inf;
        this.courseCode = cCode;
        this.ID = ID;
        
        // Set a closing date one week after the assessment object is created
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 7);
        this.closingDate = c.getTime();
        
        QuestionBank qBank = new QuestionBank();
        
        ArrayList<QuestionImpl> allQs = qBank.getQuestionBank();
        Collections.shuffle(allQs);
        int n = 0;
        while (n < 4){
            questions.add(allQs.get(n));
            n++;
        }
        
        
        
    }
    
    
    
    @Override
    public String getInformation() {
        return this.info;
    }

    @Override
    public Date getClosingDate() {
        return this.closingDate;
    }

    @Override
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }


    @Override
    public void selectAnswer(int questionNumber, int optionNumber) throws InvalidQuestionNumber, InvalidOptionNumber {
        if (questionNumber <= questions.size() && questionNumber > 0){
            if (optionNumber <= 4 & optionNumber > 0) {
                selectedAnswers[questionNumber-1] = optionNumber;
            }else throw new InvalidOptionNumber();
        }else throw new InvalidQuestionNumber();
        //String[] possibleAnswers = this.questions.get(questionNumber).getAnswerOptions();
        //selectedAns = possibleAnswers[optionNumber];
    }

    @Override
    public int getSelectedAnswer(int questionNumber) {
        return selectedAnswers[questionNumber-1];
    }

    @Override
    public int getAssociatedID() {
        return this.ID;
    }

    @Override
    public Question getQuestion(int questionNumber) throws InvalidQuestionNumber {
        if (questionNumber<(questions.size())){
            return questions.get(questionNumber-1);
        }else throw new InvalidQuestionNumber();
    }

    String getCourseCode() {
        return this.courseCode;
    }

}

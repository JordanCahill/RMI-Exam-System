
package ct414;

import ct414.exceptions.InvalidOptionNumber;
import ct414.exceptions.InvalidQuestionNumber;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of Assessment interface
 * Assigns a number of random questions upon creation of constructor
 * 
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
public class MCQExam implements Assessment, Serializable {

    private ArrayList<Question> questions = new ArrayList<>(); // Store MCQQuestion objects
    private String info; // Exam name
    private Date closingDate; // Closing date (Allows 1 week)
    private String courseCode; // Associated course code
    private int ID; // Associate student ID
    private int numQuestions; // Total number of questions
    private int[] selectedAnswers=new int[10]; // Store answers for a particular submission
    
    public MCQExam(String name, String cCode, int ID, int numQs) throws ParseException{

        this.info = name;
        this.courseCode = cCode;
        this.ID = ID;
        this.numQuestions = numQs;
        
        // Set a closing date one week after the assessment object is created
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 7);
        this.closingDate = c.getTime();

        // Create a QuestionBank object to retrieve a number of random questions
        QuestionBank qBank = new QuestionBank(numQuestions);
        
        // Create and store the questions
        ArrayList<MCQQuestion> allQs = qBank.getQuestionBank();
        int n = 0;
        while (n < numQuestions){
            questions.add(allQs.get(n));
            n++;
        }       
    }
    
    
    



    /**
     * Allows the user to select an answer for a specific questions
     * 
     * @param questionNumber Index of the question for this MCQExam
     * @param optionNumber Index of the selected answer
     * @throws InvalidQuestionNumber
     * @throws InvalidOptionNumber 
     */
    @Override
    public void selectAnswer(int questionNumber, int optionNumber) throws InvalidQuestionNumber, InvalidOptionNumber {
        try{
            if (questionNumber <= questions.size() && questionNumber > 0){ // If chosen question is valid
                if (optionNumber <= 4 & optionNumber > 0) { // If chosen answer is valid
                    selectedAnswers[questionNumber-1] = optionNumber; // Store the answer
                }else throw new InvalidOptionNumber("Error selecting answer: Option does not exist");
            }else throw new InvalidQuestionNumber("Error selecting answer: Question does not exist");
        }catch(InvalidOptionNumber | InvalidQuestionNumber e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public Question getQuestion(int questionNumber) throws InvalidQuestionNumber {
        try{
            if (questionNumber<(questions.size())){
                return questions.get(questionNumber);
            }else throw new InvalidQuestionNumber("Error retrieving question: Does not exist");
        }
        catch(InvalidQuestionNumber e){
            return null; 
        }
    }

    // ***** Class getters *****
    @Override
    public int getSelectedAnswer(int questionNumber) {
        return selectedAnswers[questionNumber];
    }
    @Override
    public int getAssociatedID() {
        return this.ID;
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
    String getCourseCode() {
        return this.courseCode;
    }
    String getName(){
        return this.info;
    }
    int getNumQuestions(){
        return this.numQuestions;
    }
    // ***** End of getters *****
}


package ct414;

import java.io.Serializable;

/**
 * Implementation of Question Interface
 * 
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
public class MCQQuestion implements Question, Serializable {

    private int questionNum; // Index of question located in the exam
    private String question; //.String containing the actual question
    private String[] questionAnswers; // Array of answers
    private int correctAnswer; // Correct answer
    
    public MCQQuestion(int num, String q, String[] answers, int correctAns){
        this.questionNum=num;
        this.question = q;
        this.questionAnswers = answers;
        this.correctAnswer = correctAns;
    }
    
    // ***** Getters *****
    @Override
    public int getQuestionNumber() {
        return questionNum-1;
    }
    @Override
    public String getQuestionDetail() {
        return this.question;
    }
    @Override
    public String[] getAnswerOptions() {
        return this.questionAnswers;
    }   
    @Override
    public String getAnswer(int i){
        return this.questionAnswers[i];
    }
    @Override
    public int getCorrectAnswer(){
        return this.correctAnswer;
    }
}

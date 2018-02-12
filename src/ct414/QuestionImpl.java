
package ct414;

import java.io.Serializable;

/**
 *
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
public class QuestionImpl implements Question, Serializable {

    private int questionNum;
    private String question;
    private String[] questionAnswers;
    private int correctAnswer;
    
    public QuestionImpl(int num, String q, String[] answers, int correctAns){
        this.questionNum=num;
        this.question = q;
        this.questionAnswers = answers;
        this.correctAnswer = correctAns;
    }
    
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

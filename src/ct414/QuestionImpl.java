
package ct414;

import java.io.Serializable;

/**
 *
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
public class QuestionImpl implements Question, Serializable {

    private int questionNum = 1;
    private String question;
    private String[] questionAnswers;
    private int correctAnswer;
    
    public QuestionImpl(String q, String[] answers, int correctAnswer){
        this.questionNum++;
        this.question = q;
        this.questionAnswers = answers;
    }
    
    @Override
    public int getQuestionNumber() {
        return this.questionNum;
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

}

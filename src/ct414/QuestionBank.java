
package ct414;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
class QuestionBank {
    
    ArrayList<MCQQuestion> questions = new ArrayList<>();
    ArrayList<MCQQuestion> questionsRand = new ArrayList<>();
    private int numQuestions = 10;
    
    public QuestionBank(){
        
        CreateQuestionBank();
        
    }
    
    public ArrayList<MCQQuestion> getQuestionBank(){
        return questions;
    }

    private void CreateQuestionBank() {
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<numQuestions+1; i++) { // 
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        
            
        
        String[] a1 = {"Dublin", "Cork", "Belfast", "Galway"}; 
        questionsRand.add(new MCQQuestion(list.get(0),"What is the capital of Ireland?", a1, 0));

        String[] a2 = {"82", "92", "102", "112"};
        questionsRand.add(new MCQQuestion(list.get(1),"How many floors does the Empire State Building have?", a2, 2));

        String[] a3 = {"Pearse Stadium", "Stamford Bridge", "Fahy's Field", "Old Trafford"};
        questionsRand.add(new MCQQuestion(list.get(2),"Where do Manchester United play their home games?", a3, 3));

        String[] a4 = {"Shannon", "Rhine", "Volga", "Tiber"};
        questionsRand.add(new MCQQuestion(list.get(3),"What is the longest river in Europe?", a4, 2));

        String[] a5 = {"10", "12", "20", "72"};
        questionsRand.add(new MCQQuestion(list.get(4),"What is the square root of 144?", a5, 1));

        String[] a6 = {"Europe", "Pacific ocean", "The mediterranean", "The moon"};
        questionsRand.add(new MCQQuestion(list.get(5),"Where is the sea of tranquility?", a6, 3));

        String[] a7 = {"Cretaceous", "Jurassic", "Triassic", "Paleozoic"};
        questionsRand.add(new MCQQuestion(list.get(6),"Which geological age did the T-Rex live in?", a7, 0));

        String[] a8 = {"Apollo 7", "Apollo 9", "Apollo 11", "Apollo 13"};
        questionsRand.add(new MCQQuestion(list.get(7),"Which Apollo mission landed the first humans on the Moon?", a8, 2));

        String[] a9 = {"LHR", "HRW", "HTR", "LHW"};
        questionsRand.add(new MCQQuestion(list.get(8),"Heathrow airport has which airport code?", a9, 0));

        String[] a10 = {"Sense", "Touch", "Sight", "Hearing"};
        questionsRand.add(new MCQQuestion(list.get(9),"If something is tactile, which sense does it relate to?", a10, 1));
        
        int i=1;
        while(i<6){
            for(MCQQuestion q:questionsRand){
                if(q.getQuestionNumber() == i){
                    questions.add(q);
                }
            }
            i++;
        }
        
    }
    
    public int questionBankSize(){
        return questions.size();
    }
}

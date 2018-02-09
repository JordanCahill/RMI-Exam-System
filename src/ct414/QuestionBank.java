
package ct414;

import java.util.ArrayList;

/**
 *
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
class QuestionBank {
    
    ArrayList<QuestionImpl> questions = new ArrayList<>();
    
    public QuestionBank(){
        
        CreateQuestionBank();
        
    }
    
    public ArrayList<QuestionImpl> getQuestionBank(){
        return questions;
    }

    private void CreateQuestionBank() {
        String[] a1 = {"Dublin", "Cork", "Belfast", "Galway"}; 
        questions.add(new QuestionImpl("What is the capital of Ireland?", a1, 0));
        
        String[] a2 = {"82", "92", "102", "112"};
        questions.add(new QuestionImpl("How many floors does the Empire State Building have?", a2, 2));
        
        String[] a3 = {"Pearse Stadium", "Stamford Bridge", "Fahy's Field", "Old Trafford"};
        questions.add(new QuestionImpl("Where do Manchester United play their home games?", a3, 3));
                
        String[] a4 = {"Shannon", "Rhine", "Volga", "Tiber"};
        questions.add(new QuestionImpl("What is the longest river in Europe?", a4, 2));
        
        String[] a5 = {"10", "12", "20", "72"};
        questions.add(new QuestionImpl("What is the square root of 144?", a5, 1));
        
        String[] a6 = {"Europe", "Pacific ocean", "The mediterranean", "The moon"};
        questions.add(new QuestionImpl("Where is the sea of tranquility?", a6, 3));
        
        String[] a7 = {"Cretaceous", "Jurassic", "Triassic", "Paleozoic"};
        questions.add(new QuestionImpl("Which geological age did the T-Rex live in?", a7, 0));
        
        String[] a8 = {"Apollo 7", "Apollo 9", "Apollo 11", "Apollo 13"};
        questions.add(new QuestionImpl("Which Apollo mission landed the first humans on the Moon?", a8, 2));
        
        String[] a9 = {"LHR", "HRW", "HTR", "LHW"};
        questions.add(new QuestionImpl("Heathrow airport has which airport code?", a9, 0));
        
        String[] a10 = {"Sense", "Touch", "Sight", "Hearing"};
        questions.add(new QuestionImpl("If something is tactile, which sense does it relate to?", a10, 1));
        
    }
    
    public int questionBankSize(){
        return questions.size();
    }
}

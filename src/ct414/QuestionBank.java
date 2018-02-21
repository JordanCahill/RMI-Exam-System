
package ct414;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used to create and store questions
 * Keeps overall project a little neater
 * Questions randomly added to MCQExam objects upon creation
 * 
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
public class QuestionBank {
    
    ArrayList<MCQQuestion> questions = new ArrayList<>(); // Final array to return to MCQExam class
    ArrayList<MCQQuestion> questionsRand = new ArrayList<>(); // Array of questions in random order
    private int totalNumQuestions = 10; // Total number of questions, needs to be update when one is added
    private int numQuestions; // Number of questions to add to array
    
    public QuestionBank(int numQs){
        numQuestions = numQs;
        CreateQuestionBank();
        
    }
    
    // Return the array to an MCQExam object
    public ArrayList<MCQQuestion> getQuestionBank(){
        return questions;
    }

    /**
     * Method used to create an ArrayList of random preset questions
     * NOTE: If question is added, global "totalNumQuestions" must be updated
     */
    private void CreateQuestionBank() {
        
        // Add each integer up to the value of "totalNumQuestions" to be used as an index
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=1; i<totalNumQuestions+1; i++) { 
            list.add(i);
        }
        Collections.shuffle(list); // Shuffle the index
        
        int numQ=0;
        
        /***********************************************************************

         For each question:
            - Define the answers as a String array
            - Use numQ as a random index
            - Add MCQQuestion object to questionsRand array
            - Update numQ index counter
        */
        String[] a1 = {"Dublin", "Cork", "Belfast", "Galway"}; 
        questionsRand.add(new MCQQuestion(list.get(numQ),"What is the capital of Ireland?", a1, 0)); numQ++;

        String[] a2 = {"82", "92", "102", "112"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"How many floors does the Empire State Building have?", a2, 2)); numQ++;

        String[] a3 = {"Pearse Stadium", "Stamford Bridge", "Fahy's Field", "Old Trafford"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"Where do Manchester United play their home games?", a3, 3)); numQ++;

        String[] a4 = {"Shannon", "Rhine", "Volga", "Tiber"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"What is the longest river in Europe?", a4, 2)); numQ++;

        String[] a5 = {"10", "12", "20", "72"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"What is the square root of 144?", a5, 1)); numQ++;

        String[] a6 = {"Europe", "Pacific ocean", "The mediterranean", "The moon"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"Where is the sea of tranquility?", a6, 3)); numQ++;

        String[] a7 = {"Cretaceous", "Jurassic", "Triassic", "Paleozoic"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"Which geological age did the T-Rex live in?", a7, 0)); numQ++;

        String[] a8 = {"Apollo 7", "Apollo 9", "Apollo 11", "Apollo 13"}; 
        questionsRand.add(new MCQQuestion(list.get(numQ),"Which Apollo mission landed the first humans on the Moon?", a8, 2)); numQ++;

        String[] a9 = {"LHR", "HRW", "HTR", "LHW"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"Heathrow airport has which airport code?", a9, 0)); numQ++;

        String[] a10 = {"Sense", "Touch", "Sight", "Hearing"};
        questionsRand.add(new MCQQuestion(list.get(numQ),"If something is tactile, which sense does it relate to?", a10, 1)); numQ++;
        
        // *********************************************************************
        
        int i=1;
        // Add the first few questions from the randomized array to a new array
        // Until numQuestions is reached
        while(i<numQuestions+1){  
            for(MCQQuestion q:questionsRand){
                if(q.getQuestionNumber() == i){
                    questions.add(q);
                    // Since questionsRand is already randomized, questions will be too
                }
            }
            i++; // Update counter
        }
        
    }
    
    public int questionBankSize(){
        return questions.size();
    }
}

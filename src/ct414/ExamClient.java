
package ct414;

import ct414.exceptions.NoMatchingAssessment;
import ct414.exceptions.InvalidOptionNumber;
import ct414.exceptions.InvalidQuestionNumber;
import ct414.exceptions.UnauthorizedAccess;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Jordan Cahill
 * @date 09-Feb-2018
 */
public class ExamClient {

    /**
     * Client class used to invoke methods using RMI from a server 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Set up a security manager
        if (System.getSecurityManager() == null) { 
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
                        
            // Set up the RMI registry and search for the server
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ExamServer exam = (ExamServer) registry.lookup(name);
            
            
            Scanner in = new Scanner(System.in); // Allow user inputs
            boolean completed = false; // Loop until submission is complete
            boolean loginSuccesful = false; // Loop until the user succesfully logs in
            int studentid = 0;
            int token = 0;

            // User logging in
            while(!loginSuccesful) { // Loop until user succesfully logs in
                // Enter credentials
                System.out.println("Enter Student ID: ");
                studentid = Integer.valueOf(in.nextLine());
                System.out.println("Enter Password: ");
                String password = in.nextLine();
                token = exam.login(studentid, password);
                
                if (token == 0) { // If student credentials don't match any contained on the server
                    System.out.println("\nInvalid ID or password, please try again.\n");
                }
                else if (token == studentid) {
                    System.out.println(studentid + " has logged in.");
                    loginSuccesful = true; // Break out of while loop
                }
            }
            
            while(!completed){ // Loop until the user decides to submit a final submission
           
                displayAssignments(exam, token, studentid); // Display assignments based on the students course code
                Assessment a = startAssignment(in, exam, token, studentid); // User selects an assignment and begins
                
                
                System.out.println("\nWould you like to make another submission? (y/n)");
                String resubmit = in.nextLine();
                if(resubmit.equals("n")) {
                    evaluate(exam, token, studentid, a); // Assignment is graded and printed
                    completed = true; // Break out of loop 
                }
                
            }

        // Finish program by logging the student out
        System.out.println("\nStudent "+ studentid + " has logged out");
        }
        catch (Exception e) {
            System.err.println("Client exception");
        }
    }

    /**
     * Method used to display assessments available to a specific student based on their course code
     * 
     * @param exam Allows RMI
     * @param token Pass into "getAvailableSummary()" to verify student login
     * @param studentid To display relevant assessments
     * @throws UnauthorizedAccess
     * @throws NoMatchingAssessment
     * @throws RemoteException 
     */
    private static void displayAssignments(ExamServer exam, int token, int studentid) 
            throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        
        System.out.println("Your available assessments are listed below:");
        List<String> examList = new ArrayList<>(); // Empty ArrayList
        if (examList.isEmpty()){ // Prevents duplicate exams being created on the same server
            examList = exam.getAvailableSummary(token, studentid);
            System.out.println(examList);
        }else{
            System.out.println(examList);
        }
                    
    }

    /**
     * User decides on an assessment to begin and is given the questions 
     * Allows multiple attempts
     * 
     * @param in Allows user command line inputs
     * @param exam Allows RMI
     * @param token Pass into "getAvailableSummary()" to verify student login
     * @param studentid To display relevant assessments
     * @return The completed assignment
     * @throws UnauthorizedAccess
     * @throws NoMatchingAssessment
     * @throws RemoteException
     * @throws InvalidQuestionNumber
     * @throws InvalidOptionNumber 
     */
    private static Assessment startAssignment(Scanner in, ExamServer exam, int token, int studentid) 
                            throws UnauthorizedAccess, NoMatchingAssessment, RemoteException, InvalidQuestionNumber, InvalidOptionNumber {
        
        // User chooses an assignment
        System.out.println("\nPlease enter the name of the assessment you wish to complete:");
        String examName = in.nextLine();
        Assessment test = exam.getAssessment(token, studentid, examName);
        
        // Display information and the assignment closing date
        System.out.println("\n" + test.getInformation());
        System.out.println("Closing date: " + test.getClosingDate());
        
        boolean submitted = false;
        while(!submitted){ // Allow for multiple submissions
            for(int i=0; i<test.getQuestions().size(); i++){ // Loop through each question
                
                // Print each question
                System.out.println("----------------------------------------------------");
                System.out.println("\nQuestion " + test.getQuestion(i).getQuestionNumber() + ":");
                System.out.println(test.getQuestion(i).getQuestionDetail());
                
                String[] answers = test.getQuestion(i).getAnswerOptions(); // Answers for the associated question
                for (int j=0; j<answers.length;j++) {
                    System.out.println((j+1) + ") " + answers[j]);
                }
                
                System.out.println("\nPlease enter the number of your answer for question " + test.getQuestion(i).getQuestionNumber());
                String answer = in.nextLine();
                
                // Add selected answer to an array
                if(Integer.parseInt(answer)<test.getQuestion(i).getAnswerOptions().length+1){
                    test.selectAnswer(test.getQuestion(i).getQuestionNumber(),Integer.parseInt(answer));
                }else{ // If the user selects an answer outside the range
                    System.out.println("Option does not exist..");
                }
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("Exam complete, would you like to submit?");
            System.out.println("Type 'y' to submit, or anything else to retry..");
            String query = in.nextLine();
            if(query.equals("y")){ // User has decided to submit
                System.out.println("Assessment submitted successfully.");
                submitted = true;
            }
        }
        return test;
    }

    /**
     * Submits and evaluates the last attempted assignment and prints the results
     * 
     * @param exam
     * @param token
     * @param studentid
     * @param test
     * @throws UnauthorizedAccess
     * @throws NoMatchingAssessment
     * @throws RemoteException 
     */
    private static void evaluate(ExamServer exam, int token, int studentid, Assessment test) 
            throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        
        exam.submitAssessment(token, studentid, test); // Submit to the server
        double count=0.0d; // Running counter for each correct answer
        
        // Arrays for the index of the question answers and number of correct results
        int[] answers = new int[(test.getQuestions().size())];
        boolean[] results = new boolean[(test.getQuestions().size())];
        
        for(int i=0;i<test.getQuestions().size();i++){ // Get the correct answer of each question
            answers[i] = (test.getQuestions().get(i).getCorrectAnswer()+1); // +1 as index starts at 0
        }
        
        for(int i=0; i<answers.length; i++) { // Loop through each question
            System.out.print("Correct answer for question " + (i+1) + ": " +  answers[i] + "       ");
            System.out.println("You chose answer: " + test.getSelectedAnswer(i));
            if(test.getSelectedAnswer(i) == answers[i]) { // Compare selected answers with correct answers
                results[i] = true;
                count=count+1.0;
            }
            else {results[i] = false;}          
        }
        
        // Calculate and print the users score as a percentage
        double score = (count/(double) results.length)*100;
        System.out.println("\nYou answered " + (int)count + " out of " + answers.length + " correctly.");
        System.out.println("Your final score: " + score + "%");
    }
}

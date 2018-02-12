
package ct414;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Jordan Cahill
 * @date 09-Feb-2018
 */
public class ExamClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ExamServer exam = (ExamServer) registry.lookup(name);
            Scanner in = new Scanner(System.in);
            boolean completed = false;
            boolean loginSuccesful = false;
            int studentid = 0;
            int token = 0;

            //Logging in
            while(!loginSuccesful) {
                System.out.println("Enter Student ID: ");
                studentid = Integer.valueOf(in.nextLine());
                System.out.println("Enter Password: ");
                String password = in.nextLine();
                token = exam.login(studentid, password);
                if (token == 0) {
                    loginSuccesful = false;
                    System.out.println("\nInvalid ID or password, please try again.\n");
                }
                else if (token == studentid) {
                    System.out.println(studentid + " has logged in.");
                    loginSuccesful = true;
                }
            }

            while(!completed) {
                
                displayAssignments(in, exam, token, studentid);
                Assessment a = startAssignment(in, exam, token, studentid);
                evaluate(exam, token, studentid, a);
                System.out.println("\nWould you like to make another submission? (y/n)");
                String resubmit = in.nextLine();
                if(resubmit.equals("n")) {
                    completed = true;
                }
            }
            System.out.println("\nStudent "+ studentid + " has logged out");
        }
        catch (Exception e) {
            System.err.println("Client exception");
            e.printStackTrace();
        }
    }

    private static void displayAssignments(Scanner in, ExamServer exam, int token, int studentid) 
            throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        System.out.println("here");
        List<String> examList = exam.getAvailableSummary(token, studentid);
        System.out.println(examList);            
    }

    private static Assessment startAssignment(Scanner in, ExamServer exam, int token, int studentid) 
                            throws UnauthorizedAccess, NoMatchingAssessment, RemoteException, InvalidQuestionNumber, InvalidOptionNumber {
        
        System.out.println("\nPlease enter the name of the assessment you wish to complete:");
        String courseCode = in.nextLine();
        Assessment test = exam.getAssessment(token, studentid, courseCode);
        System.out.println("\n" + test.getInformation());
        
        boolean submitted = false;
        while(!submitted){
            for(int i=0; i<test.getQuestions().size(); i++){
                System.out.println("----------------------------------------------------");
                System.out.println("\nQuestion " + test.getQuestion(i).getQuestionNumber() + ":");
                System.out.println(test.getQuestion(i).getQuestionDetail());
                String[] answers = test.getQuestion(i).getAnswerOptions();
                for (int j=0; j<answers.length;j++) {
                    System.out.println((j+1) + ") " + answers[j]);
                }

                System.out.println("\nPlease enter the number of your answer for question " + test.getQuestion(i).getQuestionNumber());
                String answer = in.nextLine();
                
                if(Integer.parseInt(answer)<test.getQuestion(i).getAnswerOptions().length+1){
                    test.selectAnswer(test.getQuestion(i).getQuestionNumber(),Integer.parseInt(answer));
                }else{
                    System.out.println("Option does not exist..");
                }
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("Exam complete, would you like to submit?");
            System.out.println("Type 'y' to submit, or anything else to retry..");
            String query = in.nextLine();
            if(query.equals("y")){
                submitted = true;
            }
        }
        return test;
    }

    private static void evaluate(ExamServer exam, int token, int studentid, Assessment test) 
            throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        
        exam.submitAssessment(token, studentid, test);
        double count=0.0d;
        int[] answers = new int[(test.getQuestions().size())];
        boolean[] results = new boolean[(test.getQuestions().size())];
        for(int i=0;i<test.getQuestions().size();i++){
            answers[i] = (test.getQuestions().get(i).getCorrectAnswer()+1);
        }
        
        for(int i=0; i<answers.length; i++) {
            System.out.print("Correct answer for question " + (i+1) + ": " +  answers[i] + "       ");
            System.out.println("You chose answer: " + test.getSelectedAnswer(i));
            if(test.getSelectedAnswer(i) == answers[i]) {
                results[i] = true;
                count=count+1.0;
            }
            else {results[i] = false;}          
        }

        double score = (count/(double) results.length)*100;
        System.out.println("\nYou answered " + (int)count + " out of " + answers.length + " correctly.");
        System.out.println("Your final score: " + score + "%");
        
        
    }

}

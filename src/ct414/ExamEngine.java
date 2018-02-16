
package ct414;

import ct414.exceptions.NoMatchingAssessment;
import ct414.exceptions.UnauthorizedAccess;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Server implementation to be bound to the registry and define the remote methods 
 * 
 * @author Jordan Cahill
 */
public class ExamEngine implements ExamServer {

    private ArrayList<Student> studentList = new ArrayList<>(); // Store each Student to the server
    private ArrayList<MCQExam> assessments = new ArrayList<>(); // Store each Assessment to the server
    private ArrayList<String> assessmentNames = new ArrayList<>(); // Names of each assessment
    private HashMap<Integer, Assessment> completedExams = new HashMap<>(); // Store completed assessments with the appopriate student ID
        
    /**
     * Constructor creates student objects
     * 
     * @throws RemoteException
     * @throws ParseException 
     */
    public ExamEngine() throws RemoteException, ParseException{
        super();
        
        studentList = new ArrayList<>();
    
        // Create and store Server Student objects
        Student s1 = new Student(1,"a","4BP1"); studentList.add(s1);
        Student s2 = new Student(14483208,"qwerty","4BP1"); studentList.add(s2);
        Student s3 = new Student(14424257,"password", "4BLE"); studentList.add(s3);
        Student s4 = new Student(13837257,"pword", "4BLE"); studentList.add(s4);
        
        // Create assessments for different course codes
        if (assessments.isEmpty()){ // Prevents duplicate exams being created on the same server
            System.out.println("Creating assessments..");
            MCQExam exam1 = new MCQExam("4BP1 Quiz 1", "4BP1", 4382, 5); assessments.add(exam1);
            MCQExam exam2 = new MCQExam("4BLE Quiz 1", "4BLE", 3208, 5); assessments.add(exam2);
            MCQExam exam3 = new MCQExam("4BP1 Quiz 2", "4BP1", 3208, 5); assessments.add(exam3);
        }
        
        
    }

    /**
     * Check entered student ID & password with the server entries and log student in
     * 
     * @param studentid Entered ID
     * @param password Entered password
     * @return
     * @throws RemoteException 
     */
    @Override
    public int login(int studentid, String password) throws 
                 RemoteException {
        try{
            for(Student s: studentList) { // Loop through all students on the server
                if(studentid == s.getStudentID()){  // Check ID first
                    if (password.equals(s.getPassword())){ // Check password
                        System.out.println("Student " + studentid + " logged on.");
                        return studentid; // Return the ID as a token to allow access
                    }else throw new UnauthorizedAccess("Incorrect Password");
                }
            }throw new UnauthorizedAccess("Student ID not found");
        }catch (UnauthorizedAccess e){
            System.out.println("Error logging in:" + e);
            return 0; // Return a blank token if credentials are incorrect
        }
    }

    /**
     * Return a summary list of assessments available to the student based on their course code
     * 
     * @param token Verify successful login
     * @param studentid Current ID of the students session
     * @return List of available assessments
     * @throws UnauthorizedAccess
     * @throws NoMatchingAssessment
     * @throws RemoteException 
     */
    @Override
    public List<String> getAvailableSummary(int token, int studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        try{
            for(Student s: studentList) { // Check token against student IDs to verify login
                if (token==s.getStudentID()){      
                    
                    for (MCQExam a: assessments){ // Loop through assessments checking course codes
                        if(s.getCourseCode().equals(a.getCourseCode())){
                            assessmentNames.add(a.getInformation());
                        }
                    }
                    if (assessmentNames.isEmpty()){ // Case where no available assessments
                        throw new NoMatchingAssessment("No exam found for student's ID");
                    }else{
                        return assessmentNames;
                    }
                }
            } throw new UnauthorizedAccess("Access not granted to student ID"); // If login returned an invalid token
        } catch (NoMatchingAssessment | UnauthorizedAccess e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Return an Assessment object associated with a particular course code
     * 
     * @param token Verify successful login
     * @param studentid Student associated with the assessment
     * @param name Name of the assessment being checked
     * @return 
     * @throws UnauthorizedAccess
     * @throws NoMatchingAssessment
     * @throws RemoteException 
     */
    @Override
    public Assessment getAssessment(int token, int studentid, String name) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        try{
            for(Student s: studentList) { // Loop through each student to verify login
                if (token==s.getStudentID()){
                    for (MCQExam a: assessments){ // Loop through assessments
                        if (a.getName().equals(name)){ // Compare entered name with each assessment
                            return a; // Return the matching assessment
                        }
                    // Case where no exam is found
                    }throw new NoMatchingAssessment("No exam found with that name");
                }
            // Case where login is invalid
            } throw new UnauthorizedAccess("Access not granted to student ID");
        } catch (NoMatchingAssessment | UnauthorizedAccess e){
            System.out.print(e.getMessage());
            return null;
        }
    }

    /**
     * Submits a completed assignment
     * 
     * @param token Verify login
     * @param studentid Associated student ID
     * @param completed Assessment to be submitted
     * @throws UnauthorizedAccess
     * @throws NoMatchingAssessment
     * @throws RemoteException 
     */
    @Override
    public void submitAssessment(int token, int studentid, Assessment completed) throws 
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        try{
            for(Student s: studentList) { // Verify login
                if (token==s.getStudentID()){ 
                    Date submitDate = new Date(); // Date of submission
                    if(submitDate.before(completed.getClosingDate())){ // Check if exam hasn't expired
                        completedExams.put(studentid, completed); // Submit the exam with a paired student ID 
                        // Verify submitted assignment
                        System.out.println("Student " + studentid + " has submitted assessment: " + completed.getInformation());
                    }throw new NoMatchingAssessment("Exam submission date has expired"); // Case where exam has reached expiry date                
                }
            } throw new UnauthorizedAccess("Access not granted to student ID"); // Case of invalid login
        } catch (NoMatchingAssessment | UnauthorizedAccess e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            // Set up the server
            String name = "ExamServer";
            ExamServer engine = new ExamEngine();
            ExamServer stub =
                (ExamServer) UnicastRemoteObject.exportObject(engine, 0);
            
            // Bind server to the RMI registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ExamEngine bound");
        } catch (RemoteException | ParseException e) {
            System.err.println("ExamEngine exception:" + e.getMessage());
        }
    }
}

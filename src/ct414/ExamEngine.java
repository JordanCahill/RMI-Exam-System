
package ct414;

import ct414.exceptions.NoMatchingAssessment;
import ct414.exceptions.UnauthorizedAccess;
import static java.lang.System.currentTimeMillis;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExamEngine implements ExamServer {

    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<MCQExam> assessments = new ArrayList<>();
    private ArrayList<String> assessmentNames = new ArrayList<>();
    private HashMap<Integer, Assessment> completedExams = new HashMap<>();
        
    // Constructor is required
    public ExamEngine() throws RemoteException{
        super();
        
        studentList = new ArrayList<>();
    
        Student s1 = new Student(1,"a","4BP1"); studentList.add(s1);
        Student s2 = new Student(14483208,"qwerty","4BP1"); studentList.add(s2);
        Student s3 = new Student(14424257,"password", "4BLE"); studentList.add(s3);
        Student s4 = new Student(13837257,"pword", "4BLE"); studentList.add(s4);
        
        
        
        System.out.println("Creating assessments..");
        MCQExam exam1 = new MCQExam("Quiz 1", "4BP1", 4382); assessments.add(exam1);
        MCQExam exam2 = new MCQExam("Quiz 2", "4BLE", 3208); assessments.add(exam2);
        
        
        
    }

    // Implement the methods defined in the ExamServer interface...
    // Return an access token that allows access to the server for some time period
    @Override
    public int login(int studentid, String password) throws 
                 RemoteException {

        try{
            for(Student s: studentList) {
                if(studentid == s.getStudentID()){ 
                    if (password.equals(s.getPassword())){
                        System.out.println("Student " + studentid + " logged on.");
                        return studentid;
                    }else throw new UnauthorizedAccess("Incorrect Password");
                }
            }throw new UnauthorizedAccess("Student ID not found");
        }catch (UnauthorizedAccess e){
            System.out.println("Error logging in:" + e);
            return 0;
        }
    }

    // Return a summary list of Assessments currently available for this studentid
    public List<String> getAvailableSummary(int token, int studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        try{
            for(Student s: studentList) {
                if (token==s.getStudentID()){      
                    
                    for (MCQExam a: assessments){
                            assessmentNames.add(a.getInformation());
                    }
                    if (assessmentNames.isEmpty()){
                        throw new NoMatchingAssessment("No exam found for student's ID");
                    }else{
                        return assessmentNames;
                    }
                }
            } throw new UnauthorizedAccess("Access not granted to student ID");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Return an Assessment object associated with a particular course code
    public Assessment getAssessment(int token, int studentid, String courseCode) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        try{
            for(Student s: studentList) {
                if (token==s.getStudentID()){
                    String cc = s.getCourseCode();
                    for (MCQExam a: assessments){
                        if (a.getCourseCode().equals(cc)){
                            return a;
                        }
                    }throw new NoMatchingAssessment("No exam found for student's course code");
                }
            } throw new UnauthorizedAccess("Access not granted to student ID");
        } catch (Exception e){
            throw new RemoteException();
        }
    }

    // Submit a completed assessment
    public void submitAssessment(int token, int studentid, Assessment completed) throws 
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        try{
            for(Student s: studentList) {
                if (token==s.getStudentID()){
                    Date submitDate = new Date();
                    if(submitDate.before(completed.getClosingDate())){
                        completedExams.put(studentid, completed);
                    }throw new NoMatchingAssessment("Exam submission date has expired");                    
                }
            } throw new UnauthorizedAccess("Access not granted to student ID");
        } catch (Exception e){
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
        try {
            String name = "ExamServer";
            ExamServer engine = new ExamEngine();
            ExamServer stub =
                (ExamServer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ExamEngine bound");
        } catch (Exception e) {
            System.err.println("ExamEngine exception:");
            e.printStackTrace();
        }
    }
}

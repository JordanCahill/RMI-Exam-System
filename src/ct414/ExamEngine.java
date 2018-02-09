
package ct414;

import static java.lang.System.currentTimeMillis;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExamEngine implements ExamServer {

    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<AssessmentImpl> assessments = new ArrayList<>();
        
    // Constructor is required
    public ExamEngine() throws RemoteException{
        super();
        
        studentList = new ArrayList<>();
    
        Student s1 = new Student(1,"a","4BP1"); studentList.add(s1);
        Student s2 = new Student(14483208,"qwerty","4BP1"); studentList.add(s2);
        Student s3 = new Student(14424257,"password", "4BLE"); studentList.add(s3);
        
        AssessmentImpl exam1 = new AssessmentImpl("Quiz 1", "CT104", 14483208); assessments.add(exam1);
        AssessmentImpl exam2 = new AssessmentImpl("Quiz 2", "CT304", 14483208); assessments.add(exam2);
        
        
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

        // TBD: You need to implement this method!
        // For the moment method just returns an empty or null value to allow it to compile

        return null;
    }

    // Return an Assessment object associated with a particular course code
    public Assessment getAssessment(int token, int studentid, String courseCode) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        /*
        Assessment object is going to need relevant course codes
        */
        
        // TBD: You need to implement this method!
        // For the moment method just returns an empty or null value to allow it to compile

        return null;
    }

    // Submit a completed assessment
    public void submitAssessment(int token, int studentid, Assessment completed) throws 
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        /*
        Print completed assessment? toString method?
        */
        // TBD: You need to implement this method!
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

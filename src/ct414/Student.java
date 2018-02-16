
package ct414;

/**
 * Object used to store student information on the server
 *
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
class Student {
    
    private final String password;
    private final int studentID;
    private final String courseCode;
    
    public Student(int id, String pass, String cc){
        
        this.password = pass;
        this.studentID = id;
        this.courseCode = cc;
        
    }

    // ***** Getters *****
    public int getStudentID() {
        return studentID;
    }
    public String getPassword() {
        return password;
    }
    public String getCourseCode(){
        return courseCode;
    }

}

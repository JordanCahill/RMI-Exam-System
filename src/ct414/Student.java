
package ct414;

/**
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

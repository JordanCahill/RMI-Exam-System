
package ct414;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Jordan Cahill
 * @date 05-Feb-2018
 */
public class CurrentSession extends TimerTask implements Serializable{

    private boolean sessionActive;
    private String studID;
    private Student student;
    private Timer timer;
    private Date closeDate;
    private Date currentDate;
    private long timeAlive;
    
    public CurrentSession(String userID, Student stud, Date close, Date curr){
        this.studID = userID;
        this.student = stud;
        this.closeDate = close;
        this.currentDate = curr;
        this.sessionActive = true;
        this.timeAlive = System.currentTimeMillis();
        this.timer = new Timer();
            
    }
    
   
    private void startTimer() {
        this.timer.scheduleAtFixedRate(this, new Date(System.currentTimeMillis()), 1); // Once a minute
    }

    @Override
    public void run() {
        this.timeAlive++;
        if(this.timeAlive == closeDate.getTime()) {
            this.sessionActive = false;
            this.timer.cancel();
            System.out.println("Session " + this.studID + " terminated.");
            System.out.println(this);
        }
    }    

}

package self.kin.test;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.timers.TimerManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kin
 * Date: 6/4/13
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestServlet extends HttpServlet implements Serializable{

    private static final long serialVersionUID = -3097867193032937121L;

    public TimerManager tm;
    public Timer timerBlocked;
    public Timer timerNormal;

    int notificationInterval = 10;
    int sleepTime=45;

    int countNormal=0;
    int countBlocked =0;

    boolean blockFlag = true;

    public String getJNDIName(){
        return "java:comp/env/timer/TestTimer";
    }


    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("act") != null && req.getParameter("act").equals("stop")){
            if (timerBlocked != null){
                timerBlocked.cancel();
                timerBlocked = null;
                timerNormal.cancel();
                timerNormal = null;
                countNormal = countBlocked = 0;
            }
            return;
        }

        if (tm == null){
            try {
                InitialContext ic = new InitialContext();
                tm = (TimerManager)ic.lookup(getJNDIName());
            } catch (NamingException ne) {
                ne.printStackTrace();
            }
        }
        if ( timerBlocked == null && timerNormal == null){

            System.out.println("===Thread ID: " + Thread.currentThread().getId() + "===Notification Interval: " + notificationInterval + " Seconds");
            //schedule
            timerBlocked = tm.schedule(new BlockedTimerListener(), new Date(), notificationInterval * 1000);
            timerNormal = tm.schedule(new NormalTimerListener(), new Date(), notificationInterval * 1000);
        }


    }

    public class BlockedTimerListener implements TimerListener, Serializable {

        public void timerExpired(Timer timer) {
            System.out.println("===Blocked Timer #: " + countBlocked + " started at " + timer.getScheduledExecutionTime());
            System.out.println("======Schedule interval: " + timer.getPeriod());
            if (blockFlag){
                System.out.println("======Timer Blocked");
                try {
                    int c=0;
                    while(c++<sleepTime){
                        System.out.println("=========#" + countBlocked + " execution time: " + (System.currentTimeMillis() - timer.getScheduledExecutionTime()));
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                blockFlag = false;
            }


            System.out.println("===Run Count: " + countBlocked++ + " ended at " + System.currentTimeMillis());
        }
    }

    public class NormalTimerListener implements TimerListener, Serializable {

        public void timerExpired(Timer timer) {
            System.out.println("===Normal timer #: " + countNormal++ + " execute at " + timer.getScheduledExecutionTime());
        }
    }


}

package self.kin.test;

import commonj.timers.Timer;
import commonj.timers.TimerManager;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kin
 * Date: 6/4/13
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestServlet extends HttpServlet implements Serializable{

    public TimerManager tm;
    public Timer timerBlocked;
    public Timer timerNormal;

    int notificationInterval = 10;
    int sleepTime=45;

    int countNormal=0;
    int countBlocked =0;

    boolean blockFlag = true;

    @Inject
    BlockedTimerListener blockedTimerListener;

    @Inject
    NormalTimerListener normalTimerListener;

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
            timerBlocked = tm.schedule(blockedTimerListener, 0, notificationInterval * 1000);
            timerNormal = tm.schedule(normalTimerListener, 0, notificationInterval * 1000);
        }


    }


}

package self.kin.test;

import commonj.timers.Timer;
import commonj.timers.TimerListener;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kin
 * Date: 6/5/13
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class NormalTimerListener implements TimerListener, Serializable {

    public void timerExpired(Timer timer) {
        System.out.println("===Normal timer #: " + " execute at " + timer.getScheduledExecutionTime());
    }
}
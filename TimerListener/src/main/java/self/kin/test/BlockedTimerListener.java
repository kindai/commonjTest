package self.kin.test;

import commonj.timers.Timer;
import commonj.timers.TimerListener;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kin
 * Date: 6/5/13
 * Time: 10:45 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateless
public class BlockedTimerListener implements TimerListener, Serializable {

    public void timerExpired(Timer timer) {
        System.out.println("===Blocked Timer #: " + " started at " + timer.getScheduledExecutionTime());
        System.out.println("======Schedule interval: " + timer.getPeriod());
        if (true){
            System.out.println("======Timer Blocked");
            try {
                int c=0;
                while(c++<45){
                    System.out.println("=========#" + " execution time: " + (System.currentTimeMillis() - timer.getScheduledExecutionTime()));
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


        System.out.println("===Run Count: " + " ended at " + System.currentTimeMillis());
    }
}

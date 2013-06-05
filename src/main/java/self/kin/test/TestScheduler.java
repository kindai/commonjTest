package self.kin.test;

import javax.servlet.http.HttpServlet;

/**
 * Created with IntelliJ IDEA.
 * User: kin
 * Date: 6/5/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestScheduler extends TestServlet {

    public String getJNDIName(){
        return "weblogic.JobScheduler";
    }
}

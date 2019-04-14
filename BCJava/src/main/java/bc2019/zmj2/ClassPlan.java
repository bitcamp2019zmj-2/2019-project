package bc2019.zmj2;

import java.util.ArrayList;
import java.util.List;

import bc2019.zmj2.client.Course;
import bc2019.zmj2.client.Group;
import bc2019.zmj2.client.Major;
import bc2019.zmj2.gui.MainAppx;
import bc2019.zmj2.util.Util;

public class ClassPlan {
    public boolean someLibraryMethod() {
        return true;
    }
    
    public static void main(String[] args) {
    	List<Course> creqs = new ArrayList<Course>();
    	List<Group> greqs = new ArrayList<Group>();
    	Course c = new Course("CMSC",132,"");
    	Course g1 = new Course("CMSC",351,"");
    	Course g2 = new Course("CMSC",350,"");
    	Group x = new Group("Basic");
    	x.addReq(g1);
    	x.addReq(g2);
    	creqs.add(c);
    	greqs.add(x);
    	
    	Major m = new Major("CMSC",creqs,greqs);
    	
    	MainAppx.begin(args);
    	
    	try {
//			Util.login("johndoe@email.com", "123456");
//			Util.retrieve("classes/cmsc351", null);
			Util.write("majors/cmsc", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}

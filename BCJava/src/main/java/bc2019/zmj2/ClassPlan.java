package bc2019.zmj2;

import java.util.ArrayList;
import java.util.List;

import bc2019.zmj2.client.Course;
import bc2019.zmj2.client.CourseRef;
import bc2019.zmj2.client.Database;
import bc2019.zmj2.client.Group;
import bc2019.zmj2.client.Major;
import bc2019.zmj2.gui.MainAppx;
import bc2019.zmj2.util.Util;
import bc2019.zmj2.util.WebUtil;

public class ClassPlan {
    public boolean someLibraryMethod() {
        return true;
    }
    
    public static void main(String[] args) {
    	List<CourseRef> creqs = new ArrayList<CourseRef>();
    	List<Group> greqs = new ArrayList<Group>();
    	Course c = new Course("CMSC",132,"");
    	Course g1 = new Course("CMSC",351,"");
    	Course g2 = new Course("CMSC",350,"");
    	Database.addCourse("cmsc132", c);
    	Database.addCourse("cmsc351", g1);
    	Database.addCourse("cmsc350", g2);
    	Group x = new Group("Basic");
    	x.addReq(new CourseRef("cmsc351"));
    	x.addReq(new CourseRef("cmsc350"));
    	creqs.add(new CourseRef("cmsc132"));
    	greqs.add(x);
    	
    	Major m = new Major("CMSC",creqs,greqs);
    	WebUtil.init();
    	Util.init();
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

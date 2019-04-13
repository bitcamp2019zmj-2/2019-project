package bc2019.zmj2;

import bc2019.zmj2.util.AuthException;
import bc2019.zmj2.util.Util;

public class ClassPlan {
    public boolean someLibraryMethod() {
        return true;
    }
    
    public static void main(String[] args) {
    	try {
			Util.login("johndoe@email.com", "123456");
		} catch (AuthException e) {
			e.printStackTrace();
		}
    }
}

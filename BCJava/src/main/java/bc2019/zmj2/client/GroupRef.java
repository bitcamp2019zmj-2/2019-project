package bc2019.zmj2.client;

public class GroupRef implements Requirable {

	private String groupKey = null;
	
	public GroupRef() {
		groupKey = null;
	}
	
	public GroupRef(String key) {
		groupKey = key;
	}
	
	public String getGroupKey() {
		return this.groupKey;
	}
	
	@Override
	public boolean reqMet(User u) {
		
		return false;
	}

	@Override
	public boolean reqMet(User u, Grade g) {
		// TODO Auto-generated method stub
		return false;
	}

}

package State;

public class clientState {
	
	private static clientState single_instance = null;
	
	public static clientState getInstance() {
		if (single_instance == null)
			single_instance = new clientState();
		return single_instance;
	}
}

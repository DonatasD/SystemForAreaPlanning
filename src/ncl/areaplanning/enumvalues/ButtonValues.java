package ncl.areaplanning.enumvalues;

public enum ButtonValues {
	NEW("New"), SHOW_GRID("Show Grid");
	
	private String displayName;
	
	ButtonValues(String displayName) {
		this.displayName = displayName;
	}
	
	public String displayName() {
		return displayName;
	}
	@Override 
	public String toString() {
		return displayName;
	}
}

package command;

public enum CourseCommand {
	PICK ("pick_course"),
	DROP ("drop_course"),
	GET_QUERY ("get_query");
	
	private String cmd;
	CourseCommand(String c) {
		this.cmd = c;
	}
	
	public String cmd() {
		return this.cmd;
	}
	
}

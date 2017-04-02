package command;

/**
 * 
 * @author Nikita Dizhur
 * @since 12-01-2017
 * 
 * Class that represents all types of commands that {@link #CourseListView} can send to {@link #CourseListController}.
 * 
 */
public class CourseCommand {
	public static final String PICK = "pick_course";
	public static final String DROP = "drop_course";
	public static final String GET_QUERY = "get_query";
	public static final String BAD_CMD = "bad_command";
	public static final String DETAILS = "details";
	public static final String GET_CHOSEN = "chosen";
	public static final String GET_FACULTIES = "chosen_faculty";
	public static final String SAVE_CHOSEN_COURSES = "save_chosen_courses";
}

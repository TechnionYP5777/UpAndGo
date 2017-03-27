package command;

/**
 * 
 * @author Nikita Dizhur
 * @since 27-12-2016
 * 
 * Class that represents all types of commands that {@link #ITimeTableView} can send to {@link #TimeTableController}.
 * 
 */
public class TimeTableCommand {
	public static final String GET_NEXT_GENERATED_SCHED = "get_next_generated_schedule";
	public static final String GET_PREV_GENERATED_SCHED = "get_prev_generated_schedule";
	public static final String GET_MANUAL_SCHED = "get_manual_schedule";
	public static final String RECALC_SCHED = "recalculate_schedule";
	public static final String SAVE_SCHED = "save_schedule";
}

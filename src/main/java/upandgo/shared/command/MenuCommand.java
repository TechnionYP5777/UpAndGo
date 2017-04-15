package upandgo.shared.command;

/**
 * 
 * @author Lida Piatigorski
 * @since 14-01-2017
 * 
 * Class that represents all types of commands that {@link #MenuView} can send to {@link #MenuController}.
 * 
 */
public class MenuCommand {
	public static final String LOAD_CATALOG = "load_catalog";
	public static final String LOAD_GILAYON = "load_gilayon";
	public static final String SAVE = "save";
	public static final String BAD_CMD = "bad_command";

}

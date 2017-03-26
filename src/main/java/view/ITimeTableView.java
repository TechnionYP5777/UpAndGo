package view;

import java.time.LocalTime;

/**
 * 
 * @author Nikita Dizhur
 * @since 12-01-2017
 * 
 * Interface that defines part of UI that is in charge of displaying schedule and schedule options.
 * 
 */
public interface ITimeTableView extends View {

	boolean isDaysoffCount();

	boolean isBlankSpaceCount();

	LocalTime getMinStartTime();

	LocalTime getMaxEndTime();

}

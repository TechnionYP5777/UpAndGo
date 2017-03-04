package view;

import java.time.LocalTime;

public interface ITimeTableView extends View {

	boolean isDaysoffCount();

	boolean isBlankSpaceCount();

	LocalTime getMinStartTime();

	LocalTime getMaxEndTime();

}

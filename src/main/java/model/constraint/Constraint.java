package model.constraint;

/**
 * @author kobybs
 * @since 20-12-16
 * 
 * Abstract class that represents general constraint for scheduler.
 */

/**
 * @author kobybs
 * @since 25-12-16
 */

import model.course.Course;
import model.course.Lesson;
import model.course.LessonGroup;

public interface Constraint<T> {
	boolean isClashWith(final Lesson ¢);

	boolean isClashWith(final T ¢);
}
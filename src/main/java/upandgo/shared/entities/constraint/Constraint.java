package upandgo.shared.entities.constraint;

import upandgo.shared.entities.LessonGroup;

public interface Constraint<T> {
	boolean isClashWith(final LessonGroup xxx);

	boolean isClashWith(final T xxx);
}
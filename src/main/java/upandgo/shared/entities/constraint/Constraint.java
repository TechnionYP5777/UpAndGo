package upandgo.shared.entities.constraint;

import upandgo.shared.entities.LessonGroup;

public interface Constraint<T> {
	boolean isClashWith(final LessonGroup ¢);

	boolean isClashWith(final T ¢);
}
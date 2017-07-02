package upandgo.shared.entities.constraint;

import upandgo.shared.entities.LessonGroup;

public interface Constraint<T> {
	boolean isClashWith(LessonGroup xxx);

	boolean isClashWith(T xxx);
}
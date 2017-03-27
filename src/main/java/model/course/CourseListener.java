package model.course;

/**
 * 
 * @author Nikita Dizhur
 * @since 07-01-17
 * 
 * Interface for subscribing (listening) to course changes.
 * 
 */
public interface CourseListener {

	void getUpdate(Course c);

}

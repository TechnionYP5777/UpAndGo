package upandgo.shared.entities.course;

import com.google.gwt.user.client.rpc.IsSerializable;

import upandgo.shared.entities.Exam;

//import upandgo.shared.entities.Exam;

/**
 * 
 * @author Nikita Dizhur
 * @since 11-01-17
 * 
 *        A small class for storing course name and number, without specifying
 *        all other details.
 * 
 */
public class CourseId implements Comparable<CourseId>, IsSerializable {

	public CourseId() {
		// here because GWT needs it
		name = number = "";
		aTerm = null;
		bTerm = null;
		
	}

	@Override
	public int hashCode() {
		return 31 * ((name == null ? 0 : name.hashCode()) + 31) + (number == null ? 0 : number.hashCode());
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final CourseId other = (CourseId) o;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	private String name;
	private String number;

	private Exam aTerm;
	private Exam bTerm;

	public CourseId(final String cNum, final String cName, Exam cATerm, Exam cBTerm) {
		name = cName;
		number = cNum;
		aTerm = cATerm;
		bTerm = cBTerm;
	}

	public String getTitle() {
		return name + " - " + number;
	}

	public String name() {
		return name;
	}

	public String number() {
		return number;
	}

	@Override
	public int compareTo(final CourseId xxx) {
		return number.compareTo(xxx.number);
	}

	public Exam aTerm() {
		return aTerm;
	}

	public Exam bTerm() {
		return bTerm;
	}

}

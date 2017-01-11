package model.course;

/*
 * a small class for storing course name and number, without specifying all other details
 */

public class CourseId {

	@Override
	public int hashCode() {
		return 31 * (((name == null) ? 0 : name.hashCode()) + 31) + ((number == null) ? 0 : number.hashCode());
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CourseId other = (CourseId) o;
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

	public final String name;
	public final String number;
	
	public CourseId(String cNum, String cName) {
		name = cName;
		number = cNum;
	}

}

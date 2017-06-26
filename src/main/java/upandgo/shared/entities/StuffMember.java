package upandgo.shared.entities;

import java.util.Objects;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-12-16
 * 
 * Class that holds information about specific staff member.
 * 
 */
public class StuffMember implements IsSerializable {
	
	public String fName;
	public String lName;
	public String title;

	public StuffMember(){
		fName = null;
		lName = null;
		title = null;

	}
	
	public StuffMember(final String fName1, final String lName1) {
		// THIS CONSTRCUTOR MADE FOR TESTS, DO NOT USE WITHOUT A GOOD REASON
		fName = fName1;
		lName = lName1;
		title = "";
	}

	public StuffMember(final String fName1, final String lName1, final String title) {
		// THIS CONSTRCUTOR MADE FOR TESTS, DO NOT USE WITHOUT A GOOD REASON
		fName = fName1;
		lName = lName1;
		this.title = title;
	}

	public String getFirstName() {
		return fName;
	}

	public String getLastName() {
		return lName;
	}

	public String getTitle() {
		return title;
	}
	
	public String getFullName() {
		return title+" "+fName+" "+lName;
	}

	@Override
	public boolean equals(final Object xxx) {
		return xxx != null && (xxx == this || xxx instanceof StuffMember && fName.equals(((StuffMember) xxx).getFirstName())
				&& lName.equals(((StuffMember) xxx).getLastName()) && title.equals(((StuffMember) xxx).getTitle()));
	}

	@Override
	public String toString() {
		return fName + "-" + lName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fName, lName, title);
	}

}

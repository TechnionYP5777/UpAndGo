package upandgo.shared.entities;

import java.util.ArrayList;
import java.util.List;
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
	//private static final long serialVersionUID = 7103892149028991099L;

	public String fName;
	public String lName;
	public String title;
	public String email;
	public String office;
	public List<LocalTime> officeHours;

	public StuffMember(){
		fName = null;
		lName = null;
		title = null;
		email = null;
		office = null;
		officeHours = null;
	}
	
	public StuffMember(final String fName1, final String lName1) {
		// THIS CONSTRCUTOR MADE FOR TESTS, DO NOT USE WITHOUT A GOOD REASON
		fName = fName1;
		lName = lName1;
		officeHours = null;
	}

	public StuffMember(final String fName1, final String lName1, final String title) {
		// THIS CONSTRCUTOR MADE FOR TESTS, DO NOT USE WITHOUT A GOOD REASON
		fName = fName1;
		lName = lName1;
		this.title = title;
		office = email = "";
		officeHours = null;
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

	public StuffMember(final String fName1, final String lName1, final String ttl, final String eml,
			final String office1, final List<LocalTime> ofHours) {
		fName = fName1;
		lName = lName1;
		title = ttl;
		email = eml;
		office = office1;
		officeHours = new ArrayList<>(ofHours);
	}

	@Override
	public boolean equals(final Object xxx) {
		return xxx != null && (xxx == this || xxx instanceof StuffMember && fName.equals(((StuffMember) xxx).getFirstName())
				&& lName.equals(((StuffMember) xxx).getLastName()) && title.equals(((StuffMember) xxx).getTitle()));
	}

	@Override
	public String toString() {
		return fName + " " + lName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fName, lName, title);
	}

}

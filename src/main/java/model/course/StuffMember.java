package model.course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StuffMember {
	public String fName;
	public String lName;
	public String title;
	public String email;
	public String office;
	public final List<LocalDateTime> officeHours;
	
	public StuffMember(String fName1, String lName1, String ttl, String eml, String office1,
																			List<LocalDateTime> ofHours) {
		this.fName = fName1;
		this.lName = lName1;
		this.title = ttl;
		this.email = eml;
		this.office = office1;
		this.officeHours = new ArrayList<>(ofHours);
	}
	
}

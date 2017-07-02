package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class StaffMemberTest {

	@Test
	@SuppressWarnings("static-method")
	public void emptyConstructorTest() {
		StuffMember staff = new StuffMember();
		assertNull(staff.getFirstName());
		assertNull(staff.getLastName());
		assertNull(staff.getTitle());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void Constructor1Test() {
		StuffMember staff = new StuffMember("אולמן", "טאוב");
		assertEquals("אולמן", staff.getFirstName());
		assertEquals("טאוב", staff.getLastName());
		assertEquals("", staff.getTitle());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void Constructor2Test() {
		StuffMember staff = new StuffMember("אולמן", "טאוב", "פרופסור");
		assertEquals("אולמן", staff.getFirstName());
		assertEquals("טאוב", staff.getLastName());
		assertEquals("פרופסור", staff.getTitle());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void getFullNameTest() {
		StuffMember staff = new StuffMember("אולמן", "טאוב", "פרופסור");
		assertEquals("פרופסור אולמן טאוב", staff.getFullName());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void equalsTest() {
		StuffMember staff = new StuffMember("אולמן", "טאוב", "פרופסור");
		StuffMember staff2 = new StuffMember("אולמן", "פישבך", "פרופסור");
		StuffMember staff3 = new StuffMember("אולמן", "פישבך", "מר");
		StuffMember staff4 = new StuffMember("אולמן", "פישבך", "מר");
		assert !staff3.equals(null);
		assert !staff3.equals(staff2);
		assert !staff2.equals(staff);
		assert staff3.equals(staff4);
		assert staff3.equals(staff3);
		assert !staff3.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void toStringTest() {
		assertEquals("אולמן-טאוב", (new StuffMember("אולמן", "טאוב", "פרופסור")).toString());
	}
	
	
}

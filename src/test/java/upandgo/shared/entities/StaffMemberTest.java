package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class StaffMemberTest {

	@Test
	public void emptyConstructorTest() {
		StuffMember staff = new StuffMember();
		
		assertNull(staff.getFirstName());
		assertNull(staff.getLastName());
		assertNull(staff.getTitle());
	}
	
	@Test
	public void Constructor1Test() {
		StuffMember staff = new StuffMember("אולמן", "טאוב");
		
		assertEquals("אולמן", staff.getFirstName());
		assertEquals("טאוב",staff.getLastName());
		assertEquals("", staff.getTitle());
	}
	
	@Test
	public void Constructor2Test() {
		StuffMember staff = new StuffMember("אולמן", "טאוב", "פרופסור");
		
		assertEquals("אולמן", staff.getFirstName());
		assertEquals("טאוב",staff.getLastName());
		assertEquals("פרופסור", staff.getTitle());
	}
	
	@Test
	public void equalsTest() {
		StuffMember staff = new StuffMember("אולמן", "טאוב", "פרופסור");
		StuffMember staff2 = new StuffMember("אולמן", "פישבך", "פרופסור");
		StuffMember staff3 = new StuffMember("אולמן", "פישבך", "מר");
		StuffMember staff4 = new StuffMember("אולמן", "פישבך", "מר");
		
		assertFalse(staff3.equals(staff2));
		assertFalse(staff2.equals(staff));
		assertTrue(staff3.equals(staff4));
	}
	
	@Test
	public void toStringTest() {
		StuffMember staff = new StuffMember("אולמן", "טאוב", "פרופסור");
				
		assertEquals("אולמן-טאוב", staff.toString());
	}
	
	
}

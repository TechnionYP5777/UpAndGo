package upandgo.shared.model.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.entities.Exam;
import upandgo.shared.model.scedule.Collision;

public class CollisionTest {

	@Test
	public void test() {
		Collision col = new Collision("a", "b");
		Collision col2 = new Collision("a", "b");
		Collision col3 = new Collision("b", "a");
		Collision col4 = new Collision("a", "a");
		
		assertFalse(col.equals(null));
		assertTrue(col.equals(col));
		assertFalse(col.equals(new Exam("13:00 08 08 2017")));
		assertTrue(col.equals(col2));
		assertTrue(col.equals(col3));
		assertFalse(col.equals(col4));
	}

}

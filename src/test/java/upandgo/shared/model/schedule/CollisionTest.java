package upandgo.shared.model.schedule;

import org.junit.Test;

import upandgo.shared.entities.Exam;
import upandgo.shared.model.scedule.Collision;

public class CollisionTest {

	@Test
	@SuppressWarnings("static-method")
	public void test() {
		Collision col = new Collision("a", "b"), col2 = new Collision("a", "b"), col3 = new Collision("b", "a"),
				col4 = new Collision("a", "a");
		assert !col.equals(null);
		assert col.equals(col);
		assert !col.equals(new Exam("13:00 08 08 2017"));
		assert col.equals(col2);
		assert col.equals(col3);
		assert !col.equals(col4);
	}

}

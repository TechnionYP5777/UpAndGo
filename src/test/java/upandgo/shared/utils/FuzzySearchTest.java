package upandgo.shared.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FuzzySearchTest {

	@Test
	public void test() {
		assert(50 <= FuzzySearch.similarity("הנדסת חשמל", "הנדסה כימית"));
		assertEquals(100, FuzzySearch.similarity("הנדסת חשמל", "הנדסת חשמל"));
		assertEquals(0, FuzzySearch.similarity("הנדסת חשמל", "234123"));
		assert(50 < FuzzySearch.similarity("236", "236924"));
	}

}

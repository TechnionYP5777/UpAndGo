package upandgo.shared.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FuzzySearchTest {

	@Test
	public void test() {
		assertEquals(50, FuzzySearch.similarity("הנדסת חשמל", "הנדסה כימית"));
		assertEquals(100, FuzzySearch.similarity("הנדסת חשמל", "הנדסת חשמל"));
		assertEquals(0, FuzzySearch.similarity("הנדסת חשמל", "234123"));
	}

}

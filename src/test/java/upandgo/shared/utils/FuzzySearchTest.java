package upandgo.shared.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FuzzySearchTest {

	@Test
	@SuppressWarnings("static-method")
	public void test() {
		assert (FuzzySearch.similarity("הנדסת חשמל", "הנדסה כימית") >= 50);
		assertEquals(100, FuzzySearch.similarity("הנדסת חשמל", "הנדסת חשמל"));
		assertEquals(0, FuzzySearch.similarity("הנדסת חשמל", "234123"));
		assert (FuzzySearch.similarity("236", "236924") > 50);
	}

}

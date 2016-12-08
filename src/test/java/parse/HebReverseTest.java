package parse;

import static org.junit.Assert.*;

import org.junit.Test;

public class HebReverseTest {

	@SuppressWarnings("static-method")
	@Test
	public void simpleTest() {
		assertEquals(HebReverse.reverseLine("test"),"tset");
		assertNotEquals(HebReverse.reverseLine("test"),"test");
		assertEquals(HebReverse.reverseLine("בדיקה"),"הקידב");
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void longTest() {
		assertEquals(HebReverse.reverseLine("|       תיתביבסו תיחרזא הסדנה - תועש תכרעמ |"),
				"| מערכת שעות - הנדסה אזרחית וסביבתית       |");
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void reverseOnlyLettersSimpleTest() {
		assertEquals(HebReverse.reverseTextNotNumbers("|       תיתביבסו תיחרזא הסדנה - תועש תכרעמ |"),
				"| מערכת שעות - הנדסה אזרחית וסביבתית       |");
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void reverseTextNotNumbersTest1() {
		assertEquals(HebReverse.reverseTextNotNumbers("|                        הקיטסיטטס  014003 |"),
				"| 014003  סטטיסטיקה                        |");
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void reverseTextNotNumbersTest2() {
		assertEquals(HebReverse.reverseTextNotNumbers("|3.0 :קנ          2-ת 2-ה:עובשב הארוה תועש |"),
				"| שעות הוראה בשבוע:ה-2 ת-2          נק: 3.0|");
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest3() {
		assertEquals(HebReverse.reverseTextNotNumbers("|   9.00  העש 29/06/16 'ד  םוי: ןושאר דעומ |"),
				"| מועד ראשון :יום  ד' 29/06/16 שעה  9.00   |");
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest4() {
		assertEquals(HebReverse.reverseTextNotNumbers("|      ןיבר 407   8.30-10.30'ג :ליגרת  11  |"),
				"|  11  תרגיל: ג'10.30-8.30   407 רבין      |");
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest5() {
		assertEquals(HebReverse.reverseTextNotNumbers("|          .דבלב םילשורי תינכתל 91 הצובק.1 |"),
				"| 1.קבוצה 91 לתכנית ירושלים בלבד.          |");
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest6() {
		assertEquals(HebReverse.reverseTextNotNumbers("|    .425 ץיבורוב :21 ,15 ,13 ,11 תוצובק   |"),
				"|   קבוצות 11, 13, 15, 21: בורוביץ 425.    |");
	}

}

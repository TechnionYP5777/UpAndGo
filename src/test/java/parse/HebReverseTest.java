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
		String toReverse = "|       תיתביבסו תיחרזא הסדנה - תועש תכרעמ |";
		String reveresed = "| מערכת שעות - הנדסה אזרחית וסביבתית       |";
		assertEquals(HebReverse.reverseLine(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void reverseOnlyLettersSimpleTest() {
		String toReverse = "|       תיתביבסו תיחרזא הסדנה - תועש תכרעמ |";
		String reveresed = "| מערכת שעות - הנדסה אזרחית וסביבתית       |";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void reverseTextNotNumbersTest1() {
		String toReverse = "|                        הקיטסיטטס  014003 |";
		String reveresed = "| 014003  סטטיסטיקה                        |";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void reverseTextNotNumbersTest2() {
		String toReverse = "|3.0 :קנ          2-ת 2-ה:עובשב הארוה תועש |";
		String reveresed = "| שעות הוראה בשבוע:ה-2 ת-2          נק: 3.0|";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest3() {
		String toReverse = "|   9.00  העש 29/06/16 'ד  םוי: ןושאר דעומ |";
		String reveresed = "| מועד ראשון :יום  ד' 29/06/16 שעה  9.00   |";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest4() {
		String toReverse = "|      ןיבר 407   8.30-10.30'ג :ליגרת  11  |";
		String reveresed = "|  11  תרגיל: ג'10.30-8.30   407 רבין      |";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest5() {
		String toReverse = "|          .דבלב םילשורי תינכתל 91 הצובק.1 |";
		String reveresed = "| 1.קבוצה 91 לתכנית ירושלים בלבד.          |";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}
	
	@SuppressWarnings("static-method")
	@Test	
	public void reverseTextNotNumbersTest6() {
		String toReverse = "|    .425 ץיבורוב :21 ,15 ,13 ,11 תוצובק   |";
		String reveresed = "|   קבוצות 11, 13, 15, 21: בורוביץ 425.    |";
		assertEquals(HebReverse.reverseTextNotNumbers(toReverse),reveresed);
	}

}

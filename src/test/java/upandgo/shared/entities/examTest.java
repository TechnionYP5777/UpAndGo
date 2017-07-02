package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class examTest {

	@Test
	@SuppressWarnings("static-method")
	public void examEmptyConstructorShouldBuildEmptyExam() {
		Exam exam = new Exam();
		assertEquals("", exam.getMonth());
		assertEquals("", exam.getDayOfMonth());
		assertEquals("", exam.getYear());
		assertEquals("", exam.getDay());
		assertEquals("", exam.getTime());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void examStringConstructorShouldBuildEmptyExamIfFormatIsNotOk() {
		String str = "13:00 08 08 2017";
		Exam exam = new Exam(str);
		assertEquals("", exam.getMonth());
		assertEquals("", exam.getDayOfMonth());
		assertEquals("", exam.getYear());
		assertEquals("", exam.getDay());
		assertEquals("", exam.getTime());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void examStringConstructorShouldBuildExamWithStringFormat() {
		String str = "13:00 08 08 2017 4";
		Exam exam = new Exam(str);
		assertEquals("08", exam.getMonth());
		assertEquals("08", exam.getDayOfMonth());
		assertEquals("2017", exam.getYear());
		assertEquals("ד'", exam.getDay());
		assertEquals("13:00", exam.getTime());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void hebDayConverterTest() {
		String[] res = new String[] { "א'", "ב'", "ג'", "ד'", "ה'", "ו'", "ש'" };
		for (int i = 0; i < res.length; ++i)
			assertEquals(res[i], (new Exam("13:00 08 08 2017 " + (i + 1))).getDay());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void getTimeToDisplayShouldReturnEmptyStringIfTimeInvalid() {
		assertEquals("", (new Exam("00:00 08 08 2017 4")).getTimeToDisplay());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void getTimeToDisplayShouldStringInCorrectFormat() {
		assertEquals("13:00 - ", (new Exam("13:00 08 08 2017 4")).getTimeToDisplay());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void getDateTest() {
		assertEquals("20.08", (new Exam("13:00 20 08 2017 4")).getDate());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void toStringTest() {
		assertEquals("20.08 יום ד'", (new Exam("13:00 20 08 2017 4")).toString());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void CompaerTest() {
		Exam exama = new Exam("13:00 20 08 2017 4");
		Exam examb = new Exam("13:00 19 08 2017 4");
		Exam examc = new Exam("13:00 19 03 2017 4");
		Exam examd = new Exam("13:00 19 08 2005 4");
		assertEquals(-1, examb.compare(exama));
		assertEquals(-1, examc.compare(exama));
		assertEquals(-1, examd.compare(exama));
		assertEquals(0, examb.compare(examb));
		assertEquals(1, exama.compare(examb));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void daysBetweenExamsTest() {
		Exam exama = new Exam("13:00 20 08 2017 4");
		Exam examb = new Exam("13:00 19 08 2017 4");
		Exam examc = new Exam("13:00 19 11 2017 4");
		assertEquals(1, examb.daysBetweenExams(exama));
		assertEquals(1, exama.daysBetweenExams(examb));
		assertEquals(92, examb.daysBetweenExams(examc));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void daysBetweenExamsLeapYearTest() {
		assertEquals(2, (new Exam("13:00 01 03 2004 4")).daysBetweenExams(new Exam("13:00 28 02 2004 4")));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void daysBetweenExamsNotLeapYearTest() {
		assertEquals(1, (new Exam("13:00 01 03 2017 4")).daysBetweenExams(new Exam("13:00 28 02 2017 4")));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void datesBetweenExamsTest() {
		Exam exama = new Exam("13:00 20 02 2017 4"), examb = new Exam("13:00 24 02 2017 4");
		String[] res = new String[] { "21.2", "22.2", "23.2" };
		for (int i = 0; i < res.length; ++i) {
			assertEquals(res[i], exama.datesBetweenExams(examb).get(i));
			assertEquals(res[i], examb.datesBetweenExams(exama).get(i));
		}
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void datesBetweenExamsLeapYearTest() {
		Exam exama = new Exam("13:00 28 02 2004 4"), examb = new Exam("13:00 02 03 2004 4");
		String[] res = new String[] { "29.2", "1.3" };
		for (int i = 0; i < res.length; ++i)
			assertEquals(res[i], exama.datesBetweenExams(examb).get(i));
	}


}

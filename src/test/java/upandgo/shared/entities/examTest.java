package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class examTest {

	@Test
	public void examEmptyConstructorShouldBuildEmptyExam() {
		Exam exam = new Exam();
		assertEquals("", exam.getMonth());
		assertEquals("", exam.getDayOfMonth());
		assertEquals("", exam.getYear());
		assertEquals("", exam.getDay());
		assertEquals("", exam.getTime());
	}
	
	@Test
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
	public void hebDayConverterTest() {
		String[] res = new String[] {"א'", "ב'", "ג'", "ד'", "ה'", "ו'", "ש'"};
		for (int i = 0; i < res.length; i++) {
			Exam exam = new Exam("13:00 08 08 2017" + " " + (i+1));
			assertEquals(res[i], exam.getDay());
		}
	}
	
	@Test
	public void getTimeToDisplayShouldReturnEmptyStringIfTimeInvalid() {
		Exam exam = new Exam("00:00 08 08 2017 4");
		assertEquals("", exam.getTimeToDisplay());
		
	}
	
	@Test
	public void getTimeToDisplayShouldStringInCorrectFormat() {
		Exam exam = new Exam("13:00 08 08 2017 4");
		assertEquals("13:00 - ", exam.getTimeToDisplay());	
	}
	
	@Test
	public void getDateTest() {
		Exam exam = new Exam("13:00 20 08 2017 4");
		assertEquals("20.08", exam.getDate());	
	}
	
	@Test
	public void toStringTest() {
		Exam exam = new Exam("13:00 20 08 2017 4");
		assertEquals("20.08 יום ד'", exam.toString());	
	}
	
	@Test
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
	public void daysBetweenExamsTest() {
		Exam exama = new Exam("13:00 20 08 2017 4");
		Exam examb = new Exam("13:00 19 08 2017 4");
				
		assertEquals(1, examb.daysBetweenExams(exama));
		assertEquals(1, exama.daysBetweenExams(examb));
	}
	
	@Test
	public void daysBetweenExamsLeapYearTest() {
		Exam exama = new Exam("13:00 28 02 2004 4");
		Exam examb = new Exam("13:00 01 03 2004 4");
				
		assertEquals(2, examb.daysBetweenExams(exama));
	}
	
	@Test
	public void daysBetweenExamsNotLeapYearTest() {
		Exam exama = new Exam("13:00 28 02 2017 4");
		Exam examb = new Exam("13:00 01 03 2017 4");
				
		assertEquals(1, examb.daysBetweenExams(exama));
	}
	
	@Test
	public void datesBetweenExamsTest() {
		Exam exama = new Exam("13:00 20 02 2017 4");
		Exam examb = new Exam("13:00 24 02 2017 4");
		String[] res = new String[] { "21.2", "22.2", "23.2" };
		for (int i = 0; i < res.length; i++) {
			assertEquals(res[i], exama.datesBetweenExams(examb).get(i));
		}
	}
	
	@Test
	public void datesBetweenExamsLeapYearTest() {
		Exam exama = new Exam("13:00 28 02 2004 4");
		Exam examb = new Exam("13:00 02 03 2004 4");
		String[] res = new String[] { "29.2", "1.3"};
		for (int i = 0; i < res.length; i++) {
			assertEquals(res[i], exama.datesBetweenExams(examb).get(i));
		}
	}
	
	
	

}

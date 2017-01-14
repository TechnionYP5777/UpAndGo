/**
 * 
 */
package catalog.loader;

import org.junit.Test;

import model.CourseModel;
import model.loader.XmlCourseLoader;

/**
 * @author sapir
 * @since 2017-01-day
 */
public class CatalogLoaderTest {
	@Test
	@SuppressWarnings({ "static-method", "unused" })
	public void Test0() {
		SECatalogLoader cl = new SECatalogLoader("SoftwareEngineering.XML", new CourseModel(new XmlCourseLoader("resources//testXML//REP2.XML")));
	}
	@Test
	@SuppressWarnings({ "static-method", "unused" })
	public void Test1() {
		SECatalogLoader cl = new SECatalogLoader("SoftwareEngineering.XML", new CourseModel(new XmlCourseLoader("REPFILE/REP.XML")));
	}
}

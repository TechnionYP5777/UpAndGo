/**
 * 
 */
package catalog.loader;

import org.junit.Test;

import model.ConcreteModel;
import model.loader.XmlCourseLoader;

/**
 * @author sapir
 * @since 2017-01-day
 */
public class CatalogLoaderTest {
	@Test
	@SuppressWarnings("static-method")
	public void Test0() {
		SECatalogLoader cl = new SECatalogLoader("resources\\testXML\\SoftwareEngineering.XML", new ConcreteModel(new XmlCourseLoader("resources\\testXML\\REP2.XML")));
		System.out.println("$$");
		System.out.println(cl.getCatalog());
	}
}

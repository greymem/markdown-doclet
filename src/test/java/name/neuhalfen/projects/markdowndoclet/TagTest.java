package name.neuhalfen.projects.markdowndoclet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import name.neuhalfen.projects.markdowndoclet.xmldoclet.xjc.Class;
import name.neuhalfen.projects.markdowndoclet.xmldoclet.xjc.Package;
import name.neuhalfen.projects.markdowndoclet.xmldoclet.xjc.Root;

/**
 * Unit test group for Tags
 */
public class TagTest extends AbstractTestParent {

	/**
	 * testing a simple tags
	 */
	@Test
	public void testTag1() {
		String[] sourceFiles = new String[] { "./src/test/java/name/neuhalfen/projects/markdowndoclet/simpledata/Tag1.java" };
		Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] { "-dryrun" });

		Package packageNode = rootNode.getPackage().get(0);
		Class classNode = packageNode.getClazz().get(0);

		assertEquals(rootNode.getPackage().size(), 1);
		assertNull(packageNode.getComment());
		assertEquals(packageNode.getName(), "name.neuhalfen.projects.markdowndoclet.simpledata");
		assertEquals(packageNode.getAnnotation().size(), 0);
		assertEquals(packageNode.getEnum().size(), 0);
		assertEquals(packageNode.getInterface().size(), 0);
		assertEquals(packageNode.getClazz().size(), 1);

		assertEquals(classNode.getTag().size(), 7);
		assertEquals(classNode.getMethod().get(0).getTag().size(), 3);
	}

}

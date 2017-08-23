package name.neuhalfen.projects.markdowndoclet;

import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main parser class. It scans the given Doclet document root and creates the markdown files.
 *
 * The work is done in Freemarker templates.
 *
 * @author Jens Neuhalfen
 */
public class Parser {

  private final static Logger log = LoggerFactory.getLogger(Parser.class);

  //protected Map<String, Package> packages = new TreeMap<String, Package>();

  private final Configuration configuration;

  private Parser(Configuration configuration) {
    this.configuration = configuration;
  }

  public static Parser buildParser() {
    Configuration configuration = new Configuration(new Version(2, 3, 25));
    // Where do we load the templates from:
    configuration.setClassForTemplateLoading(Parser.class, "/templates");

    // Some other recommended settings:
    configuration.setDefaultEncoding("UTF-8");
    configuration.setLocale(Locale.US);

    configuration.setBooleanFormat("yes,no");
    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    return new Parser(configuration);
  }

  public void renderClass(OutputStream out, ClassDoc classDoc)
      throws IOException, TemplateException {
    Writer w = new OutputStreamWriter(out);

    PackageDoc packageDoc = classDoc.containingPackage();

    /*
    Package packageNode = packages.get(packageDoc.name());
    if (packageNode == null) {
      packageNode = parsePackage(packageDoc);
      packages.put(packageDoc.name(), packageNode);
      //	(index) rootNode.getPackage().add(packageNode);
    }
*/
    if (classDoc instanceof AnnotationTypeDoc) {
      renderAnnotationTypeDoc(w, (AnnotationTypeDoc) classDoc);
    } else if (classDoc.isEnum()) {
      renderEnum(w, classDoc);
    } else if (classDoc.isInterface()) {
      renderInterface(w, classDoc);
    } else {
      renderClass(w, classDoc);
    }
  }


  /**
   * Parse an annotation.
   *
   * @param annotationTypeDoc A AnnotationTypeDoc instance
   * @return the annotation node
   */
  private void renderAnnotationTypeDoc(Writer w, AnnotationTypeDoc annotationTypeDoc)
      throws IOException, TemplateException {
    final String templateName = "class.ftl";
    render(w, annotationTypeDoc, templateName);
  }


  private void renderEnum(Writer w, ClassDoc classDoc) throws IOException, TemplateException {
    render(w, classDoc, "class.ftl");
  }


  private void renderInterface(Writer w, ClassDoc classDoc) throws IOException, TemplateException {
    render(w, classDoc, "class.ftl");
  }

  private void renderClass(Writer w, ClassDoc classDoc) throws IOException, TemplateException {
    render(w, classDoc, "class.ftl");
  }

  private void render(Writer w, ClassDoc doc, String templateName)
      throws IOException, TemplateException {
    Template template = configuration.getTemplate(templateName);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("subject", doc);
    input.put("util", new Util());
    template.process(input, w);
  }

}

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
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main parser class. It scans the given Doclet document root and creates the markdown files.
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
      parseAnnotationTypeDoc(w, (AnnotationTypeDoc) classDoc);
    } else if (classDoc.isEnum()) {
      parseEnum(w, classDoc);
    } else if (classDoc.isInterface()) {
      parseInterface(w, classDoc);
    } else {
      parseClass(w, classDoc);
    }
  }

/*
  protected Package parsePackage(PackageDoc packageDoc) {
    Package packageNode = objectFactory.createPackage();
    packageNode.setName(packageDoc.name());
    String comment = packageDoc.commentText();
    if (comment.length() > 0) {
      packageNode.setComment(comment);
    }

    for (Tag tag : packageDoc.tags()) {
      packageNode.getTag().add(parseTag(tag));
    }

    return packageNode;
  }
*/

  /**
   * Parse an annotation.
   *
   * @param annotationTypeDoc A AnnotationTypeDoc instance
   * @return the annotation node
   */
  protected void parseAnnotationTypeDoc(Writer w, AnnotationTypeDoc annotationTypeDoc)
      throws IOException, TemplateException {
    Template template = configuration.getTemplate("annotation.ftl");
    template.process(annotationTypeDoc, w);
  }

  protected void parseEnum(Writer w, ClassDoc classDoc) throws IOException, TemplateException {
    Template template = configuration.getTemplate("class.ftl");
    template.process(classDoc, w);
  }


  protected void parseInterface(Writer w, ClassDoc classDoc) throws IOException, TemplateException {
    Template template = configuration.getTemplate("class.ftl");
    template.process(classDoc, w);
  }

  protected void parseClass(Writer w, ClassDoc classDoc) throws IOException, TemplateException {
    Template template = configuration.getTemplate("class.ftl");
    template.process(classDoc, w);
  }
}

package name.neuhalfen.projects.markdowndoclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import freemarker.template.TemplateException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Doclet class.
 *
 * @author markus
 */
public class MarkdownDoclet {

  private final static Logger log = LoggerFactory.getLogger(Parser.class);

  /**
   * The Options instance to parse command line strings.
   */
  public final static Options options;

  static {
    options = new Options();

    OptionBuilder.withArgName("directory");
    OptionBuilder.isRequired(false);
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Destination directory for output file.\nDefault: .");
    options.addOption(OptionBuilder.create("d"));

    OptionBuilder.withArgName("encoding");
    OptionBuilder.isRequired(false);
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Encoding of the output file.\nDefault: UTF8");
    options.addOption(OptionBuilder.create("docencoding"));

    OptionBuilder.withArgName("dryrun");
    OptionBuilder.isRequired(false);
    OptionBuilder.hasArgs(0);
    OptionBuilder.withDescription("Parse javadoc, but don't write output file.\nDefault: false");
    options.addOption(OptionBuilder.create("dryrun"));

    OptionBuilder.withArgName("doctitle");
    OptionBuilder.isRequired(false);
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("IGNORED");
    options.addOption(OptionBuilder.create("doctitle"));

    OptionBuilder.withArgName("windowtitle");
    OptionBuilder.isRequired(false);
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("IGNORED");
    options.addOption(OptionBuilder.create("windowtitle"));

  }

  /**
   * Check for doclet-added options. Returns the number of arguments you must specify on the command
   * line for the given option. For example, "-d docs" would return 2. <P> This method is required
   * if the doclet contains any options. If this method is missing, Javadoc will print an invalid
   * flag error for every option.
   *
   * @param optionName The name of the option.
   * @return number of arguments on the command line for an option including the option name itself.
   * Zero return means option not known. Negative value means error occurred.
   * @see com.sun.javadoc.Doclet#optionLength(String)
   */
  public static int optionLength(String optionName) {
    Option option = options.getOption(optionName);
    if (option == null) {
      return 0;
    }
    return option.getArgs() + 1;
  }

  /**
   * Check that options have the correct arguments. <P> This method is not required, but is
   * recommended, as every option will be considered valid if this method is not present. It will
   * default gracefully (to true) if absent. <P> Printing option related error messages (using the
   * provided DocErrorReporter) is the responsibility of this method.
   *
   * @param optionsArrayArray The two dimensional array of options.
   * @param reporter The error reporter.
   * @return <code>true</code> if the options are valid.
   * @see com.sun.javadoc.Doclet#validOptions(String[][], DocErrorReporter)
   */
  public static boolean validOptions(String optionsArrayArray[][], DocErrorReporter reporter) {
    return null != parseCommandLine(optionsArrayArray);
  }

  /**
   * Processes the JavaDoc documentation. <p> This method is required for all doclets.
   *
   * @param rootDoc The root of the documentation tree.
   * @return <code>true</code> if processing was successful.
   * @see com.sun.javadoc.Doclet#start(RootDoc)
   */
  public static boolean start(RootDoc rootDoc) {
    CommandLine commandLine = parseCommandLine(rootDoc.options());
    Parser parser = Parser.buildParser();

    for (ClassDoc classDoc : rootDoc.classes()) {
      final String packageName = classDoc.containingPackage().name();
      final File containingDir = buildContainingDirPath(commandLine, packageName);
      if (!containingDir.exists()) {
        containingDir.mkdirs(); // ignore errors for now .. also parameters
      }

      File classFile = new File(containingDir, classDoc.name() + ".md");
      try (
          FileOutputStream fileOutputStream = new FileOutputStream(classFile);
          BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)
      ) {

        parser.renderClass(bufferedOutputStream, classDoc);

      } catch (IOException | TemplateException e) {
        haltAndCatchFire(e);
      }
    }

    // save(commandLine, root);
    return true;
  }

  private static char pathSeparator() {
    // fix for MacOSX bug
    return File.pathSeparatorChar == ':' ? '/' : File.pathSeparatorChar;
  }

  static void haltAndCatchFire(Exception e) {
    log.error(e.getMessage(), e);
    throw new RuntimeException(e);
  }

  /**
   * @param commandLine the parsed command line arguments
   * @param packageName the name of the packageName
   */
  public static File buildContainingDirPath(CommandLine commandLine, String packageName) {

    final File packageDir;
    final File rootDir;
    if (commandLine.hasOption("d")) {
      rootDir = new File(commandLine.getOptionValue("d"));
    } else {
      rootDir = new File(".");
    }

    final String dirName = packageName.replace('.', pathSeparator());
    final File containingDir = new File(rootDir,dirName);
    return containingDir;
  }


  /**
   * Return the version of the Java Programming Language supported by this doclet. <p> This method
   * is required by any doclet supporting a language version newer than 1.1. <p> This Doclet
   * supports Java 5.
   *
   * @return LanguageVersion#JAVA_1_5
   * @see com.sun.javadoc.Doclet#languageVersion()
   */
  public static LanguageVersion languageVersion() {
    return LanguageVersion.JAVA_1_5 ;
  }

  /**
   * Parse the given options.
   *
   * @param optionsArrayArray The two dimensional array of options.
   * @return the parsed command line arguments.
   */
  public static CommandLine parseCommandLine(String[][] optionsArrayArray) {
    try {
      List<String> argumentList = new ArrayList<String>();
      for (String[] optionsArray : optionsArrayArray) {
        argumentList.addAll(Arrays.asList(optionsArray));
      }

      CommandLineParser commandLineParser = new BasicParser() {
        @Override
        protected void processOption(final String arg,
            @SuppressWarnings("rawtypes") final ListIterator iter)
            throws ParseException {
          boolean hasOption = getOptions().hasOption(arg);
          if (hasOption) {
            super.processOption(arg, iter);
          }
        }
      };
      CommandLine commandLine = commandLineParser
          .parse(options, argumentList.toArray(new String[]{}));
      return commandLine;
    } catch (ParseException e) {
      LoggingOutputStream loggingOutputStream = new LoggingOutputStream(log, LoggingLevelEnum.INFO);
      PrintWriter printWriter = new PrintWriter(loggingOutputStream);

      HelpFormatter helpFormatter = new HelpFormatter();
      helpFormatter.printHelp(printWriter, 74,
          "javadoc -doclet " + MarkdownDoclet.class.getName() + " [options]",
          null, options, 1, 3, null, false);
      return null;
    }
  }
}

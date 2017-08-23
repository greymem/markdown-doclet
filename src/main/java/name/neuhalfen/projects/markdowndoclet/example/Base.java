package name.neuhalfen.projects.markdowndoclet.example;

import java.io.Serializable;

/**
 * This is the base doc.
 * Second sentence.
 *
 * @author Tester Testmann
 */
public class Base implements Serializable {

  /**
   * This is  a method with varargs.
   *
   * @param firstInt a single integer
   * @param va  a vararg list of Strings
   */
  public void withVarArgs(int firstInt, String... va){
    // NOOP
  }
}

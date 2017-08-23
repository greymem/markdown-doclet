package name.neuhalfen.projects.markdowndoclet.example;

import java.io.Serializable;
import java.util.List;

/**
 * bla bla
 *
 * laber sülz
 *
 * @param <DOCUMENTED> This is documented
 * @param <EXTENDED> This is extended
 */
public class BaseWithTypeParams<UNDOCUMENTED, DOCUMENTED, EXTENDED extends Base & Serializable> {

  /**
   * @param list the list
   */
  public void boundedWildcard(List<? extends Base> list) { /* ... */ }

  public void unboundedWildcard(List<?> list) {
  }

  /**
   * bla.
   *
   * @param <T> Type param
   * @return return value c´ass T
   */
  public <T> T methodWildcardReturnOnly() {
    return null;
  }

  /**
   * @param t param t
   * @param <T> Type param T
   * @return typed return
   */
  public <T> T methodWildcard(T t) {
    return null;
  }

  public class NestedNonStatic {

    public DOCUMENTED typeParamOfParent;
  }

  /**
   * Nested class
   */
  public static class NestedStatic {

    public boolean empty() {
      return true;
    }
  }
}

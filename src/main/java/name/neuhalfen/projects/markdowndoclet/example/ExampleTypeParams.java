package name.neuhalfen.projects.markdowndoclet.example;

import java.io.Serializable;

/**
 * ETP documentation, first sentence. Second sentence, also see the {@link Example} class!
 *
 * Paragraph number 2
 * @param <EXTENDED2>  The extended Type param. Seocnd sentence
 */
public class ExampleTypeParams<EXTENDED2 extends Base & Serializable> extends BaseWithTypeParams<String, Object, EXTENDED2> {
  public <T extends EXTENDED2> T veryMuchType() {return null;}
}

package name.neuhalfen.projects.markdowndoclet.example;

import java.io.IOException;

@SomeAnnotation
public class Example extends Base implements DerivedInterface, AnotherInterface   {

  public Example(String str){}
  public Example(){this("aaaah");}

  private void privateVoidMethod() {
  }

  /**
   * How to use: {@code test code!} - that was easy!
   */
  protected void protectedVoidMethod() {
  }


  void packageVoidMethod() {
  }

  /**
   * First sentence {@see MyInterface}.
   *
   * Second sentence
   *
   * @throws RuntimeException  RTE is bad
   * @throws IOException   IO is worse
   */
  public void publicVoidMethodThrows() throws RuntimeException, IOException {
  }

  public int publicIntMethod() {
    return 0;
  }

  @Override
  public void methodFromMyInterface() {

  }

  /**
   * First sentence
   *
   * Second.
   *
   * @param i  an integer
   * @param myInterface   bla bla
   */
  public final void publicFinalVoidWithTwoParams(int i, Object undocumented, MyInterface myInterface) {
  }

  @SomeAnnotation
  public Base baseWithMethodAnnotation() {
    return this;
  }



  public  @SomeAnnotation Base baseWithRetAndArgsAnnotation(@SomeAnnotation Object paramWithAnnotation) {
    return this;
  }


  @Override
  public void testExtended() {
    //
  }
}

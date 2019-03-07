public class StopWatch {
  private long start;

  public StopWatch(){
    start = System.nanoTime();
  }

  public long elapsedTime(){
    long end = System.nanoTime();
    return end - start;
  }
}

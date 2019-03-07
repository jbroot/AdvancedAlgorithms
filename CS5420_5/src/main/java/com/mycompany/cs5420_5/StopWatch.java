package com.mycompany.cs5420_5;

public class StopWatch {
  private long start;

  public StopWatch(){
    start = System.nanoTime();
  }

  public long elapsedTime(){
    long end = System.nanoTime();
    return (end - start); // converts time to milliseconds
  }
}

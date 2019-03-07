package com.mycompany.assign2cs5420;



public class StopWatch {
  private long start;

  public StopWatch(){
    start = System.nanoTime();
  }

  public long elapsedTime(){
    long end = System.nanoTime();
    return (end - start)/1_000_000; // converts time to milliseconds
  }
}

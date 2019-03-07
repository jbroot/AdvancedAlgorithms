/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cs5420_5;


import java.io.FileInputStream;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ccp
 */
public class RabinKarpTest {
    private final int[][] fullText;
    private int[] expResult;
    
    public RabinKarpTest() {
        fullText = new int[1000][1000];
        expResult = new int[2];
        try{
              FileInputStream in = new FileInputStream("octal.txt");
              Scanner scnr = new Scanner(in);
              for(int j=0; scnr.hasNext(); ++j){
                  String row = scnr.nextLine();
                  for(int i=0; i < row.length(); ++i){
                      fullText[j][i] = Character.getNumericValue(row.charAt(i));
                  }
              }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    private int[][] rndSubArray(int size, int[][] supArr){
        Random r = new Random();
        int[][] p = new int[size][size];
        
        //random coordinates for top left part of subarray
        int i = r.nextInt(supArr[0].length-size+1);
        int j = r.nextInt(supArr[0].length-size+1);
        
        for(int m=0;m<size;++m){
            for(int n=0; n<size;++n){
                p[m][n] = supArr[i+m][j+n];
            }
        }
        expResult = new int[]{i,j};
        return p;
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    /**
     * Test of search method, of class RabinKarp.
     */
    @Test
    public void testSearch() {
        int[] small = new int[]{2, 8, 16, 64};
        int[] large = new int[]{5,25,100, 1000};        
        int repeats = 100;
        double[][] times = new double[small.length][repeats];
        boolean printAvgTimes = true;
        
        for(int rpt =0; rpt<repeats; ++rpt){
            for(int i=0; i<small.length; ++i){        
                int[][] sup = rndSubArray(large[i], fullText);
                int[][] sub = rndSubArray(small[i], sup);
                
                RabinKarp instance = new RabinKarp(sub, sup);
                
                StopWatch time = new StopWatch();
                int[] result = instance.search();
                times[i][rpt] = (double) time.elapsedTime();
                
                assertArrayEquals(expResult, result);
            }
        }
        //prints average time taken per size
        double[] avg = new double[4];
        if(printAvgTimes){
            for(int i=0; i<small.length; ++i){
                double sum = 0;
                for(int j=0; j< repeats; ++j){
                    sum += times[i][j];
                }
                System.out.println(small[i]+": "+sum/(double)repeats);
                avg[i]=sum/(double)repeats;
                if(i>0){
                    System.out.println(avg[i]/avg[i-1]);
                }
            }
        }
    }
}

package com.mycompany.cs5420_5;
/* Adapted from the textbook's site at
 *   https://algs4.cs.princeton.edu/53substring/RabinKarp.java.html
 */
import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
  private int[][] pat;
  private double patHash;    // Pattern hash value
  private int m;           // Pattern length
  private long q;          // A large prime, small enough to avoid overflow
  private int R;           // Radix
  private long RM;         // R^(M-1) % Q
  private int[][] sup;

  public RabinKarp(int[][] pat, int[][]sup) {
    this.sup = sup;
    this.pat = pat;
    R = 8;
    m = pat[0].length;
    q = longRandomPrime();

    RM = 1;
    for (int i = 1; i <= m-1; i++)
      RM = (R * RM) % q;
    long[] patHashRows = hashRows(pat, 0, m);
    patHash = longHash(patHashRows, 0, m);
  }

  private static long longRandomPrime() {
    return BigInteger.probablePrime(31, new Random()).longValue();
  }

  //hash m rows
  private long[] hashRows(int[][] key, int start, int end) {
    long[] h = new long[m];
    for(int i = 0; i<m;++i){
        long rowH = 0;
        for (int j = start; j < end; j++){
          rowH = (R * rowH + key[i][j]) % q;
        }
        h[i]= rowH;
    }
    return h;
  }
  
  //hash rows to scalar
  private long intHash(int[] hashes, int start, int end){
    long h = 0;
    for(int i = start; i<end;++i){
        h = (R * h + hashes[i]) % q;
    }
    return h;
  }
  
  //hash rows to scalar
  private double longHash(long[] hashes, int start, int end){
    double h = 0;
    for(int i = start; i<end;++i){
        h = (R * h + (double)hashes[i]) % q;
    }
    return h;
  }

  //check if pattern is found in superArray starting at [x][y]
  private boolean check(int x, int y) {
    for(int iter = 0; iter<m; ++iter){
        for (int j = 0; j < m; j++)
          if (pat[iter][j] != sup[x+iter][y+j])
            return false;
    }
    return true;
  }
  
  //return coordinates of the lowest coordinate subarray has in superarray. (top-left corner)
  public int[] search() {
    int n = sup[0].length;
    if (n < m) return new int[]{-1};
    long[] rowHashes = new long[n];
    //initialize row hashes
    for(int i=0; i<n;++i){
        rowHashes[i] = intHash(sup[i], 0, m);
    }
    //for each column
    for(int ic = 0; ic<=n-m; ++ic){
        //initialize colSetHash for first m rows
        double colSetHash = longHash(rowHashes, 0, m);
        if(patHash == colSetHash && check(0,ic)) return new int[]{0,ic};
        
        for(int ir = 0; ir<n; ++ir){
            //remove oldest row
            colSetHash = (colSetHash + q - RM*(rowHashes[ir])%q)%q;
            
            if(ic<n-m){
                //update this old row for the next ic iter
                rowHashes[ir] = (rowHashes[ir] + q - RM*sup[ir][ic]%q)%q;
                rowHashes[ir] = (rowHashes[ir]*R + sup[ir][ic+m])%q;
            }
            
            if(ir<n-m){
                //add next row
                colSetHash = (colSetHash*R + rowHashes[ir+m])%q;
                if(patHash == colSetHash && check(ir+1,ic)){
                    return new int[]{ir+1,ic};
                }
            }
        }
    }
    return new int[]{-1}; // no match
  }
}

package com.mycompany.cs5420;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BFSTopoSortTest {
    private Graph graph;
    private BFSTopoSort bfs;
    private final String[] files;
    public BFSTopoSortTest() {
        files = new String[] {"tinyDG.txt", "tinyDG2.txt"};
    }
    
    private void initBFS(String file){
        try{
            FileInputStream in = new FileInputStream(file);
            
            Scanner scnr = new Scanner(in);
            //get number vertices
            int V = Integer.parseInt(scnr.nextLine());
            graph = new Graph(V, Graph.Kind.DIRECTED);

            while(scnr.hasNext()){
              String line = scnr.nextLine();
              String[] tokens = line.split("\\s+");
              int v = Integer.parseInt(tokens[0].trim().substring(0, tokens[0].length() - 1));
              for(int i = 1; i < tokens.length; i++){
                int w = Integer.parseInt(tokens[i]);
                graph.addEdge(v, w);
              }

            }

            scnr.close();
            graph = graph.fromAdjList(in);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        bfs = new BFSTopoSort(graph);
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
     * Test of sort method, of class BFSTopoSort.
     */
    @Test
    public void testSort() {
        System.out.println("sort");
        initBFS(files[0]);
        ArrayList<Integer> expResult = new ArrayList<>();
        expResult.addAll(Arrays.asList(2, 8, 3, 0, 7, 5, 1, 6, 4, 9, 11, 10, 12));
        ArrayList<Integer> result = bfs.sort();
        assertEquals(expResult, result);
        
        initBFS(files[1]);
        
        ArrayList<Integer> expResult2 = new ArrayList<>();
        expResult2.addAll(Arrays.asList(3, 6, 0, 5, 1, 2, 4));
        ArrayList<Integer> result2 = bfs.sort();
        assertEquals(expResult2, result2);
    }
    
}

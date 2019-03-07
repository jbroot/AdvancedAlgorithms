package com.mycompany.assign2cs5420;

import java.io.FileInputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepthFirstSearchTest {
    
    Graph graph;
    DepthFirstSearch dfs;
    public DepthFirstSearchTest() {
        try{
            FileInputStream in = new FileInputStream("tinyDG.txt");
            graph = Graph.fromAdjList(in);
        }
        catch(Exception e){
            System.out.println(e);
        }
        dfs = new DepthFirstSearch(graph);
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
    
    private void iterAll(int[] edges, DepthFirstSearch.EdgeType e){
        for(int i=0; i<edges.length;++i){
            assertEquals(dfs.findEdgeType(edges[i], edges[++i]), e);
        }
    }
    
    @Test
    public void testFindEdgeType(){
        //confirm all edges are correct
        //int[2n] points to int[2n+1]. first n = 0
        int[] greens = {
            0,1,0,6,0,5,5,4,6,9,9,11,11,12,9,10   
        };
        iterAll(greens, DepthFirstSearch.EdgeType.GREEN);
        
        int[] reds = {
        };
        iterAll(reds, DepthFirstSearch.EdgeType.RED);
        
        int[] yellows = {
            9,12
        };
        iterAll(yellows, DepthFirstSearch.EdgeType.YELLOW);
        
        int[] blues = {
            2,3,8,7,2,0,3,5,7,6,6,4
        };
        iterAll(blues, DepthFirstSearch.EdgeType.BLUE);
    }    
}
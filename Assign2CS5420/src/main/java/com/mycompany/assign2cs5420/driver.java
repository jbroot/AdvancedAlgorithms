/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.assign2cs5420;

import java.io.FileInputStream;

/**
 *
 * @author ccp
 */
public class driver {
    Graph graph;
    
    public static void main(String[] args) {
        //Graph graph = null;
        driver d = new driver();
        d.run();
        /*String[] fileNames = {
            "tinyUG.txt",
            "mediumUG.txt"
        };
        unionFind[] ufs = new unionFind[fileNames.length];
        
        int iter = 0;
        for(String file : fileNames){
            ufs[iter++] = new unionFind(file);
        }*/
    }
    private void run(){
        
        try{
            FileInputStream in = new FileInputStream("tinyDG.txt");
            graph = graph.fromAdjList(in);
        }
        catch(Exception e){
            System.out.println(e);
        }
        DepthFirstSearch dfs = new DepthFirstSearch(graph);
    }
    
}

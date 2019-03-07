/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cs5420;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 *
 * @author ccp
 */
public class driver {
    Graph graph;
    public static void main(String[] args) {
        driver d = new driver();
        d.run();
    }
    private void run(){
        try{
            FileInputStream in = new FileInputStream("tinyDG.txt");
            
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
        
        BFSTopoSort bfs = new BFSTopoSort(graph);
        System.out.println(bfs.sort());
    }
}

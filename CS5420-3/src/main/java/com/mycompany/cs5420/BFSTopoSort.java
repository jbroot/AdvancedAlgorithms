package com.mycompany.cs5420;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class BFSTopoSort {
    private final Graph graph;
    BFSTopoSort(Graph g){
        graph = g;
    }
    
    public ArrayList<Integer> sort(){
        int size = graph.V();
        ArrayList<Integer> sorted = new ArrayList<>();
        int[] indegrees = new int[size];
        
        Queue<Integer> q = new ArrayDeque<>();
        //find indegrees
        for(int i =0; i< size; ++i){
            indegrees[i] = graph.inDegree(i);
            if(indegrees[i]==0) q.add(i);
        }
        //while q isn't empty
        while(!q.isEmpty()){
            //values in q always have 0 indegress
            int popped = q.poll();
            sorted.add(popped);
            //minus one indegree from all nodes it points to
            //add these nodes on queue if counted indegrees equal 0
            //if indegree is negative then it has already been added
            for(Integer i : graph.adj(popped)){
                if(--indegrees[i]==0){
                    q.add(i);
                }
            }
        }
        return sorted;
    }
    
}

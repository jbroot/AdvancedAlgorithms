package com.mycompany.assign2cs5420;

import java.io.FileInputStream;
import java.util.LinkedList;

public class unionFind {
    //Develop and implement an algorithm that uses union-find with path compression to
    //count the connected component of a given undirected graph
    private int[] id;
    private int[] size;
    private int componentCount;
    private Graph graph;
    
    public unionFind(String filename) {
        graphInit(filename);
        shouldUnion();
        countComponents();
    }
    
    private void countComponents(){
        LinkedList<Integer> components = new LinkedList<Integer>();
        for(int i : id){
            if(!components.contains(i)){
                ++componentCount;
                components.add(i);
            }
        }
    }
    
    private void shouldUnion(){
        
        for(int i = 0; i< graph.V(); ++i){
            Iterable<Integer> iter = graph.adj(i);
            for(Integer j : iter){
                //connection equals parent
                union(i,j);
            }
        }
    }
    
    //initialize graph
    private void graphInit(String filename){
        try{
            FileInputStream in = new FileInputStream(filename);
            graph = graph.fromAdjList(in);
        }
        catch(Exception e){
            System.out.println(e);
        }
        id = new int[graph.V()];
        size = new int[graph.V()];
        //all initialized as roots with size 1
        for(int i=0; i< id.length; ++i){
            id[i] = i;
            size[i] = 1;
        }
    }
    
    //recognizes connection between sets
    private void union(int l, int r){
        int i = find(l);
        int j = find(r);
        if(i==j) return;
        if(size[l]<=size[r]){
            id[i] = j; 
            size[j] += size[i];
        }
        else{
            id[j] = i;
            size[i] += size[j];
        }
    }
    
    private int find(int l){
        int origL = l;
        while(l != id[l]){
            l = id[l];
        }
        //path compression
        while(origL != id[origL]){
            int temp = id[origL];
            //set to found root l
            id[origL] = l;
            origL = id[temp];
        }
        return l;
    }
    
    private boolean connected(int l, int r){
        return find(l) == find(r);
    }
    
    public int count(){
        return componentCount;
    }
    
}

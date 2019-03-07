package com.mycompany.assign2cs5420;

//import graphs.Graph;
import java.util.Stack;

public class DepthFirstSearch {
  //white = not done; grey = doing; black = done
  private enum Color {WHITE, GREY, BLACK}
  //Green = tree; red = back; yellow = forward; blue = cross
  public enum EdgeType {GREEN, RED, YELLOW, BLUE}

  private class VertexAttributes{
    int parent = -1;
    Color color = Color.WHITE;
    int[] time = new int[2];
    int component = 0;
  }
  
  private final Graph g;
  private VertexAttributes[] vAttributes;
  private int time;
  private int componentsCount = 0;
  boolean[] visited;

  public DepthFirstSearch(Graph g) {
    this.g = g;
    visited = new boolean[g.V()];
    vAttributes = new VertexAttributes[g.V()];
    
    for(int v = 0; v < g.V(); v++){
      vAttributes[v] = new VertexAttributes();
    }

    int time = 0;
    for(int v = 0; v < g.V(); v++){
      if(vAttributes[v].color == Color.WHITE){
        dfs(v);
      }
    }
  }

  private void dfs(int u) {
    time++;
    vAttributes[u].time[0] = time;
    vAttributes[u].color = Color.GREY;
    
    for(int v : g.adj(u)){
        if(vAttributes[v].color == Color.WHITE){
            vAttributes[v].parent = u;
            dfs(v);
      }
    }

    vAttributes[u].color = Color.BLACK;
    vAttributes[u].component = componentsCount;
    if(vAttributes[u].parent == -1){
      componentsCount++;
    }

    time++;
    vAttributes[u].time[1] = time;
  }

  public Iterable<Integer> pathTo(int v) {
    Stack<Integer> path = new Stack<>();
    int x = v;
    while (vAttributes[x].parent != -1) {
      path.push(x);
      x = vAttributes[x].parent;
    }
    path.push(x);

    return path;
  }
  
  //is u ancestor of v
  private boolean isAncestor(int u, int v){
      for(Integer i: pathTo(v)){
          if(i==u) return true;
      }
      return false;
  }
  
  public EdgeType findEdgeType(int u, int v){
        //Tree-green: Found in DFS         
        //end of dfs search is vAttributes[0].time[1]
        int endDfs = vAttributes[0].time[1];
        
        //if v done before endDfs and u is ancestor of v
        if(vAttributes[v].time[1] <= endDfs){
            int j=0;
            for(Integer i:pathTo(v)){
                //System.out.println(i+" "+ u+" "+v);
                if(i==u) return EdgeType.GREEN;
                if(j++ == 1) break;
            }
        }
        
        //red-back: if node child before node parent (if child in path from DFS)
        //if v is ancestor of u
        if(isAncestor(v,u)){
            return EdgeType.RED;
        }
        
        //else must be forward or cross
        //yellow-forward: if v is descendant in dfs tree and u found in dfs
        if(isAncestor(u,v) && vAttributes[u].time[0] <= endDfs){
            return EdgeType.YELLOW;
        }
        //else blue-cross
        return EdgeType.BLUE;
  }

  public int component(int v){
    return vAttributes[v].component;
  }

  public int componentsCount() {
    return componentsCount;
  }

}

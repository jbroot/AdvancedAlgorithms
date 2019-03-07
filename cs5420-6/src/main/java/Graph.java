import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
  public enum Kind { UNDIRECTED, DIRECTED }
  private final int V;
  private int E;
  private Kind kind = Kind.UNDIRECTED;
  private LinkedList<Integer>[] adj;
  private int[] indegree;

  @SuppressWarnings("unchecked")
  public Graph(int V) {
    this.V = V;
    this.E = 0;
    adj = (LinkedList<Integer>[]) new LinkedList[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new LinkedList<>();
    }
  }

  public Graph(int V, Kind k){
    this(V);
    this.kind = k;
    if(k == Kind.DIRECTED) {
      this.indegree = new int[V];
    }
  }

  public static Graph fromAdjList(InputStream in) {
    Scanner scnr = new Scanner(in);
    Graph g = new Graph(Integer.parseInt(scnr.nextLine()));

    while(scnr.hasNext()){
      String line = scnr.nextLine();
      String[] tokens = line.split("\\s+");
      int v = Integer.parseInt(tokens[0].trim().substring(0, tokens[0].length() - 1));
      for(int i = 1; i < tokens.length; i++){
        int w = Integer.parseInt(tokens[i]);
        g.addEdge(v, w);
      }

    }

    scnr.close();

    return g;
  }

  public static Graph fromEdgeList(InputStream in) {
    Scanner scnr = new Scanner(in);
    Graph g = new Graph(scnr.nextInt());

    while(scnr.hasNext()){
      int v = scnr.nextInt();
      int w = scnr.nextInt();

      g.addEdge(v, w);
      //g.addEdge(w, v);
    }

    scnr.close();

    return g;
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  public void addEdge(int v, int w) {
    E++;
    adj[v].add(w);

    if (this.kind == Kind.DIRECTED){
      indegree[w]++;
    }
  }

  public void removeEdge(int v, int w){
    adj[v].remove(adj[v].indexOf(w));
    E--;

    if (this.kind == Kind.DIRECTED){
      indegree[w]--;
    }
  }

  public Iterable<Integer> adj(int v) {
    return adj[v];
  }

  public int degree(int v) {
    if(this.kind == Kind.DIRECTED){
      return outDegree(v) + inDegree(v);
    }

    return adj[v].size();
  }

  public int outDegree(int v) {
    if(this.kind == Kind.UNDIRECTED) {
      throw new UnsupportedOperationException();
    }

    return adj[v].size();
  }

  public int inDegree(int v) {
    if(this.kind == Kind.UNDIRECTED) {
      throw new UnsupportedOperationException();
    }

    return this.indegree[v];
  }

  public boolean isDirected(){
    return this.kind == Kind.DIRECTED;
  }

  public void makeDirected(){
    this.kind = Kind.DIRECTED;
  }

  public Graph reverse() {
    if(this.kind == Kind.UNDIRECTED){
      return this;
    } else {
      Graph reverse = new Graph(V, Kind.DIRECTED);
      for (int v = 0; v < this.V; v++) {
        for (int w : adj(v)) {
          reverse.addEdge(w, v);
        }
      }
      return reverse;
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(V).append('\n')
        .append(E).append('\n');
    for (int v = 0; v < V; v++) {
      sb.append(v + ": ");
      for (int w : adj[v]) {
        sb.append(w + " ");
      }

      sb.append('\n');
    }

    return sb.toString();
  }
}
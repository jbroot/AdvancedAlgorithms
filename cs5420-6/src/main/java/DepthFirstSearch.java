import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearch {
  private enum Color {WHITE, GREY, BLACK}

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

  private LinkedList<Integer> pre, post;

  public DepthFirstSearch(Graph g) {
    this.g = g;
    vAttributes = new VertexAttributes[g.V()];
    for(int v = 0; v < g.V(); v++){
      vAttributes[v] = new VertexAttributes();
    }

    if(g.isDirected()){
      this.pre = new LinkedList<>();
      this.post = new LinkedList<>();
    }

    for(int v = 0; v < g.V(); v++){
      if(vAttributes[v].color == Color.WHITE){
        dfs(v);
      }
    }
  }

  public DepthFirstSearch(Graph g, int s) {
    this.g = g;
    vAttributes = new VertexAttributes[g.V()];
    for(int v = 0; v < g.V(); v++){
      vAttributes[v] = new VertexAttributes();
    }

    if(g.isDirected()){
      this.pre = new LinkedList<>();
      this.post = new LinkedList<>();
    }

    if (vAttributes[s].color == Color.WHITE) {
      dfs(s);
    }
  }

  public DepthFirstSearch(Graph g, LinkedList<Integer> sources) {
    this.g = g;
    vAttributes = new VertexAttributes[g.V()];
    for(int v = 0; v < g.V(); v++){
      vAttributes[v] = new VertexAttributes();
    }

    if(g.isDirected()){
      this.pre = new LinkedList<>();
      this.post = new LinkedList<>();
    }

    for(int s : sources) {
      if (vAttributes[s].color == Color.WHITE) {
        dfs(s);
      }
    }

  }

  private void dfs(int u) {
    time++;

    vAttributes[u].time[0] = time;
    vAttributes[u].color = Color.GREY;
    if(g.isDirected()) pre.add(u);
    for(int v : g.adj(u)){
      if(vAttributes[v].color == Color.WHITE){
        vAttributes[v].parent = u;
        dfs(v);
      }
    }
    if(g.isDirected()) post.add(u);
    vAttributes[u].color = Color.BLACK;
    vAttributes[u].component = componentsCount;
    if(vAttributes[u].parent == -1){
      componentsCount++;
    }

    time++;
    vAttributes[u].time[1] = time;
  }

  public boolean reachable(int v) {
    return vAttributes[v].color == Color.BLACK;
  }

  public Iterable<Integer> pathTo(int v) {
    Stack<Integer> path = new Stack<Integer>();
    int x = v;
    while (vAttributes[x].parent != -1) {
      path.push(x);
      x = vAttributes[x].parent;
    }
    path.push(x);

    return path;
  }

  public int component(int v){
    return vAttributes[v].component;
  }

  public int componentsCount() {
    return componentsCount;
  }

  public Iterable<Integer> inPreOrder() {
    return this.pre;
  }

  public Iterable<Integer> inPostOrder() {
    return this.post;
  }

  public void printInPreOrder() {
    while(!this.pre.isEmpty()){
      System.out.print(this.pre.removeFirst() + " ");
    }

    System.out.println();
  }

  public void printInPostOrder() {
    while(!this.post.isEmpty()){
      System.out.print(this.post.removeFirst() + " ");
    }

    System.out.println();
  }

  public void printInReversePostOrder() {
    Stack<Integer> reverse = (Stack<Integer>) inReversePostOrder();
    while(!reverse.isEmpty()){
      System.out.print(reverse.pop() + " ");
    }

    System.out.println();
  }

  public Iterable<Integer> inReversePostOrder() {
    Stack<Integer> reverse = new Stack<Integer>();
    for (int v : this.post) {
      reverse.push(v);
    }

    return reverse;
  }


  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int v = 0; v < g.V(); v++) {

      sb.append(v).append(": (").append(vAttributes[v].time[0])
          .append('/').append(vAttributes[v].time[1]).append(")")
          .append(" - ").append(vAttributes[v].color)
          .append(" - ").append(vAttributes[v].parent)
          .append(" - ").append(vAttributes[v].component).append('\n');
    }

    return sb.toString();
  }
}
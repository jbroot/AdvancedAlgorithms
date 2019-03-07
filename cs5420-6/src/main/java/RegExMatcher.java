/* Adapted from the textbook's site at
 *   https://algs4.cs.princeton.edu/54regexp/NFA.java.html
 */
import java.util.LinkedList;
import java.util.Stack;

public class RegExMatcher {
    public static Graph graph;
  private static Graph constructNFA(final String pattern, final int M) {
    Stack<Integer> ops = new Stack<Integer>();
    Graph g = new Graph(M + 1); // Using a directed graph
    for (int i = 0; i < M; i++) {
      char ichar = pattern.charAt(i);
      int lp = i;
      if (ichar == '(' || ichar == '|') {
        ops.push(i);
      } else if (ichar == ')') {
        //get place of '(' or '|'
        int or = ops.pop();
        // ORs
        if(pattern.charAt(or)=='|'){
            int[] arr = new int[ops.capacity()];
            int arri=0;            
            //arr = places of |s to first (
            for(arr[0] = or; pattern.charAt(arr[arri])=='|'; arr[++arri]=ops.pop()){}
            //lp = place of (
            lp = arr[arri];
            for(int ori=0; arr[ori]!=lp; ++ori){
                //add edges from ( to node after each |
                g.addEdge(lp, arr[ori]+1);
                //add edges from each | to )
                g.addEdge(arr[ori], i);
            }
        }
        else if (pattern.charAt(or) == '(') {
          lp = or;
        }
        else assert false;
      }

      if (i < M - 1){
        //if meta doesn't apply to () then lp = i
        if(ichar != ')') lp = i;
        // Closure *
        if(pattern.charAt(i + 1) == '*') {
            //can skip or go back
            g.addEdge(lp, i + 1);
            g.addEdge(i + 1, lp);
          }
        else if(pattern.charAt(i+1)=='+'){
            //possible to go back but not skip
            g.addEdge(i+1,lp);
        }
        else if(pattern.charAt(i+1)=='?'){
            //possible to skip
            
            g.addEdge(lp, i+1);
        }
      }
      
      //if metachar then add edge to next state
      if (ichar == '(' || ichar == '*' || ichar == '+' ||
              ichar == ')' || ichar == '?') {
        g.addEdge(i, i + 1);
      }
    }

    if (ops.size() != 0) {
      throw new IllegalArgumentException("Invalid regular expression");
    }
    graph = g;
    return g;
  }

  //if the char in text matches the correct position in pattern check if the node
  //that the matching char virtually points to is reachable given previous conditions
  public static boolean recognizes(String pattern, String text) {
    final int M = pattern.length();
    Graph g = constructNFA(pattern, M);
    DepthFirstSearch dfs = new DepthFirstSearch(g, 0);

    //pc = list of reachable vertices
    LinkedList<Integer> pc = new LinkedList<Integer>();
    for (int v = 0; v < g.V(); v++) {
      if (dfs.reachable(v)) {
        pc.add(v);
      }
    }

    for (int i = 0; i < text.length(); i++) {
      // Don't allow metacharacters (used in specifying patterns) in text
      if (text.charAt(i) == '*' || text.charAt(i) == '|' ||
          text.charAt(i) == '(' || text.charAt(i) == ')'||
          text.charAt(i) == '.' || text.charAt(i) == '?'||
          text.charAt(i) == '+') {
        throw new IllegalArgumentException("Metacharacters (, *, |, and ) not allowed.");
      }

      //match = all reachable vertex (pc) + 1 that == text[i]
      LinkedList<Integer> match = new LinkedList<Integer>();
      //for reachable vertex in list
      for (int v : pc) {
        if (v == M) continue;
        if ((pattern.charAt(v) == text.charAt(i)) || pattern.charAt(v) == '.')
          match.add(v + 1);
      }

      //do dfs for all in match (set of matching char plus one node)
      //all nodes reachable by any in match are black
      dfs = new DepthFirstSearch(g, match);
      pc = new LinkedList<Integer>();
      //pc = all in matches if reachable--if accept state in here then return true later
      for (int v = 0; v < g.V(); v++) {
        if (dfs.reachable(v)) pc.add(v);
      }

      // Return if no states reachable
      if (pc.size() == 0) {
        return false;
      }
    }

    // Check for accept state
    for (int v : pc) {
      if (v == M) {
        return true;
      }
    }

    return false;
  }
}

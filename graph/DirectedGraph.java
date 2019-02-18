package graph;

import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Tina Nguyen
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        Iteration<Integer> pred = predecessors(v);
        int size = 0;
        for (int c : pred) {
            size += 1;
        }
        return size;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        int i = 0;
        ArrayList<Integer> list = new ArrayList<>();
        while (helpPred(v, i) != 0) {
            list.add(helpPred(v, i));
            i += 1;
        }
        return Iteration.iteration(list);
    }

    /** Return V's predecessor of I.
     * Helper function for predecessors(int v). */
    private int helpPred(int v, int i) {
        Iteration<int[]> edges = this.edges();
        ArrayList<int[]> result = new ArrayList<>();
        while (edges.hasNext()) {
            int[] curr = edges.next();
            result.add(curr);
        }

        if (contains(v)) {
            ArrayList<int[]> pred = new ArrayList<>();
            for (int[] x: result) {
                if (x[1] == v) {
                    pred.add(x);
                }
            }
            if (pred.size() > i) {
                return pred.get(i)[0];
            }
        }
        return 0;
    }

}

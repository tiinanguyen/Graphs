package graph;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Tina Nguyen
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testRemoveEdge() {
        DirectedGraph g = new DirectedGraph();

        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(2, 3);
        g.add(4, 1);
        g.add(5, 1);

        g.remove(5, 1);

        assertFalse(g.contains(5, 1));

        assertTrue(g.contains(5) && g.contains(1));

    }

    @Test
    public void testMax() {
        DirectedGraph g = new DirectedGraph();

        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        assertEquals(10, g.maxVertex());
    }

    @Test
    public void testRemoveVertex() {
        DirectedGraph g = new DirectedGraph();

        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(2, 3);
        g.add(4, 1);
        g.add(5, 1);

        g.remove(1);

        assertFalse(g.contains(5, 1));
        assertFalse(g.contains(4, 1));
        assertFalse(g.contains(1, 2));

        assertFalse(g.contains(1));
    }

    @Test
    public void testContain() {
        DirectedGraph g = new DirectedGraph();
        assertFalse(g.contains(0));
        g.add();
        g.add();
        g.add();
        assertTrue(g.contains(1));
        assertFalse(g.contains(10));
        assertTrue(g.contains(2));
        g.remove(1);
        g.remove(2);
        assertFalse(g.contains(1));
        assertFalse(g.contains(2));
    }

    @Test
    public void testVertices() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();

        Iteration<Integer> test = g.vertices();
        ArrayList<Object> succ = new ArrayList<>();
        while (test.hasNext()) {
            Object curr = test.next();
            succ.add(curr);
        }
        assertTrue(succ.contains(1));
        assertTrue(succ.contains(2));
        assertFalse(succ.contains(10));
    }

    @Test
    public void nonEmptyGraph() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 4);

        assertEquals("Vertex mismatch", 4, g.vertexSize());
        assertEquals("Edges mismatch", 3, g.edgeSize());
    }

    @Test
    public void testSuccessor() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(2, 3);
        g.add(2, 4);
        g.add(2, 5);
        g.add(2, 6);
        g.add(2, 7);
        g.add(8, 8);


        Iteration<Integer> test = g.successors(2);
        ArrayList<Object> succ = new ArrayList<>();
        while (test.hasNext()) {
            Object curr = test.next();
            succ.add(curr);
        }

        assertTrue(succ.contains(3));
        assertTrue(succ.contains(4));
        assertTrue(succ.contains(5));
        assertFalse(succ.contains(8));


        Iteration<Integer> testSelf = g.successors(8);
        ArrayList<Object> succ2 = new ArrayList<>();
        while (testSelf.hasNext()) {
            Object curr = testSelf.next();
            succ2.add(curr);
        }
        assertTrue(succ2.contains(8));
    }

    @Test
    public void sanityCheckSuccessor() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 4);
        g.add(1, 3);


        Iteration<Integer> test = g.successors(1);
        ArrayList<Object> succ = new ArrayList<>();
        while (test.hasNext()) {
            Object curr = test.next();
            succ.add(curr);
        }

        ArrayList<Integer> result = new ArrayList<>();
        result.add(4);
        result.add(3);

        assertEquals(result, succ);
    }

    @Test
    public void testUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 1);
        g.remove(3, 2);

        assertEquals(2, g.inDegree(1));
        assertEquals(1, g.inDegree(2));
        assertTrue(g.contains(2));
        assertTrue(g.contains(3));

        assertTrue(g.contains(1, 2));
        assertTrue(g.contains(2, 1));

        Iteration<Integer> test = g.predecessors(1);
        ArrayList<Object> pred = new ArrayList<>();
        while (test.hasNext()) {
            Object curr = test.next();
            pred.add(curr);
        }

        assertTrue(pred.contains(2));
        assertTrue(pred.contains(3));

    }

    @Test
    public void testInDegree() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(3, 1);
        g.add(4, 1);

        assertEquals(4, g.vertexSize());
        assertEquals(3, g.edgeSize());

        g.remove(4, 1);

        assertEquals(1, g.inDegree(1));

        Iteration<Integer> test = g.predecessors(1);
        ArrayList<Object> pred = new ArrayList<>();
        while (test.hasNext()) {
            Object curr = test.next();
            pred.add(curr);
        }

        assertFalse(g.contains(4, 1));
        assertTrue(pred.contains(3));
    }

}

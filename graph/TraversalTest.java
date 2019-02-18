package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Traversal class.
 * @author Tina Nguyen
 */
public class TraversalTest {

    @Test
    public void testBFT() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 3);
        g.add(3, 5);
        g.add(5, 6);
        g.add(1, 2);
        g.add(2, 4);


        BreadthFirstTraversal bft = new BreadthFirstTraversal(g);
        bft.traverse(1);
        assertTrue(bft.marked(3));
        assertFalse(bft.marked(6));
        assertTrue(bft.marked(4));
    }

    @Test
    public void testDFT() {
        UndirectedGraph g = new UndirectedGraph();
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

        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 7);
        g.add(2, 5);
        g.add(2, 6);
        g.add(6, 3);
        g.add(3, 8);

        g.add(9, 10);

        DepthFirstTraversal dft = new DepthFirstTraversal(g);
        dft.traverse(1);
        assertTrue(dft.marked(2));
        assertTrue(dft.marked(4));
        assertFalse(dft.marked(9));

    }

}

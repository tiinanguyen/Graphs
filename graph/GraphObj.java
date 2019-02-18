package graph;

import java.util.ArrayList;
import java.util.Collections;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Tina Nguyen + cantor function
 *  for edgeID from Wikipedia + suggestions
 *  for code implementation from Piazza posts
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _edges = new ArrayList<>();
        _vertices = new ArrayList<>();
        _removeVert = new ArrayList<>();
        _neighborEdges = new ArrayList<ArrayList<Integer>>();
        _maxVert = 0;
        _numEdges = 0;
    }

    @Override
    public int vertexSize() {
        int size = 0;
        for (int i = 0; i < _vertices.size(); i++) {
            if (_vertices.get(i) != null) {
                size++;
            }
        }
        return size;
    }

    @Override
    public int maxVertex() {
        if (_vertices == null || _vertices.size() < 1) {
            return 0;
        }
        int currmax = 1;
        for (int i = 0; i < _vertices.size(); i++) {
            if (_vertices.get(i) != null && _vertices.get(i) > currmax) {
                currmax = _vertices.get(i);
            }
        }
        _maxVert = currmax;
        return _maxVert;
    }

    @Override
    public int edgeSize() {
        return _numEdges;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (_neighborEdges.get(v - 1) == null) {
            return 0;
        }
        return _neighborEdges.get(v - 1).size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            for (int i : _neighborEdges.get(u - 1)) {
                if (i == v) {
                    return true;
                }
            }
            if (!isDirected()) {
                for (int i : _neighborEdges.get(v - 1)) {
                    if (i == u) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        int node = 0;
        if (_removeVert.isEmpty()) {
            _neighborEdges.add(new ArrayList<>());
            _vertices.add(_neighborEdges.size());
            return _neighborEdges.size();
        } else {
            node = Collections.min(_removeVert);
            _vertices.add(node + 1);
            _neighborEdges.set(node, new ArrayList<>());
            _removeVert.remove((Object) node);
        }
        return node + 1;
    }

    @Override
    public int add(int u, int v) {
        int[] add = new int[2];
        add[0] = u;
        add[1] = v;
        if (_vertices.contains(u) && _vertices.contains(v)) {
            if (!isDirected()) {
                if (u == v) {
                    _neighborEdges.get(u - 1).add(v);
                    _edges.add(add);
                    _numEdges += 1;
                } else {
                    _neighborEdges.get(u - 1).add(v);
                    _neighborEdges.get(v - 1).add(u);
                    _edges.add(add);
                    _numEdges += 1;
                }
            } else {
                _neighborEdges.get(u - 1).add(v);
                _edges.add(add);
                _numEdges += 1;
            }
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        _removeVert.add(v - 1);
        if (_vertices.contains(v)) {
            _vertices.set(v - 1, null);
            if (isDirected()) {
                for (ArrayList<Integer> i : _neighborEdges) {
                    if (i != null && i.contains(v)) {
                        i.remove((Object) v);
                    }
                }
                for (int i = 0; i < _edges.size(); i++) {
                    int edge1 = _edges.get(i)[0];
                    int edge2 = _edges.get(i)[1];
                    if (edge1 == v || edge2 == v) {
                        _numEdges -= 1;
                        _edges.remove(i);
                        i -= 1;
                    }
                }
            } else {
                for (int i = 0; i < _edges.size(); i++) {
                    int edge1 = _edges.get(i)[0];
                    int edge2 = _edges.get(i)[1];
                    if (edge1 == v || edge2 == v) {
                        _numEdges -= 1;
                        _edges.remove(i);
                        i -= 1;
                    }
                }
                for (ArrayList<Integer> i : _neighborEdges) {
                    if (i != null && i.contains(v)) {
                        i.remove((Object) v);
                    }
                }
            }
        }
        _neighborEdges.set(v - 1, null);
    }

    @Override
    public void remove(int u, int v) {
        int[] add = new int[2];
        add[0] = u;
        add[1] = v;
        if (contains(u, v)) {
            if (isDirected()) {
                _neighborEdges.get(u - 1).remove((Object) v);
                _numEdges -= 1;
                for (int[] i : _edges) {
                    if (i[0] == add[0] && i[1] == add[1]) {
                        _edges.set(_edges.indexOf(i), null);
                    }
                }
            } else {
                _neighborEdges.get(u - 1).remove((Integer) v);
                _neighborEdges.get(v - 1).remove((Integer) u);
                _numEdges -= 1;
                int[] edge = {Math.min(u, v), Math.max(u, v)};
                for (int[] i : _edges) {
                    if (i == edge) {
                        _edges.set(_edges.indexOf(i), null);
                    }
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < _vertices.size(); i++) {
            if (_vertices.get(i) != null) {
                nodes.add(_vertices.get(i));
            }
        }
        return Iteration.iteration(nodes);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        if (!contains(v)) {
            return Iteration.iteration(new ArrayList<Integer>());
        }
        ArrayList<Integer> succ = new ArrayList<>();
        for (int i : _neighborEdges.get(v - 1)) {
            succ.add(i);
        }
        return Iteration.iteration(succ.iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> edges = new ArrayList<>();
        for (int[] edge : _edges) {
            if (edge != null) {
                edges.add(edge);
            }
        }
        return Iteration.iteration(edges);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new Error("Vertex not in graph.");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!isDirected()) {
            int max = Math.max(u, v);
            int min = Math.min(u, v);
            return (max + min) * (max + min + 1) / 2 + min;
        } else {
            u -= 1;
            v -= 1;
            return (u + v) * (u + v + 1) / 2 + v;
        }
    }


    /** Store edges. */
    private ArrayList<int[]> _edges;
    /** Edge counter. */
    private int _numEdges;
    /** Store vertices. */
    private ArrayList<Integer> _vertices;
    /** Removed vertices. */
    private ArrayList<Integer> _removeVert;
    /** The max vertex node. */
    private int _maxVert;
    /** Graph representation. */
    private ArrayList<ArrayList<Integer>> _neighborEdges;


}

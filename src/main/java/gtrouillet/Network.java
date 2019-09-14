package gtrouillet;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Represents a Network of nodes and its connections
 */
public class Network {
    private int size;
    private LinkedList<Integer>[] adjList;

    /**
     * Creates a new instance of {@link Network}
     *
     * @param size the size of the new @link Network}
     */
    public Network(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid size parameter, only a positive integer is allowed");
        }
        this.size = size;
        this.adjList = new LinkedList[size + 1];
        for (int i = 1; i <= size; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void connect(final int nodeA, final int nodeB) {
        validateParams(nodeA, nodeB);
        if (nodeA == nodeB) {
            return;
        }
        adjList[nodeA].add(nodeB);
        adjList[nodeB].add(nodeA);
    }

    public boolean query(final int nodeA, final int nodeB) {
        validateParams(nodeA, nodeB);
        final DFS dfs = new DFS(this);
        return dfs.neighborhood(nodeA).contains(nodeB);
    }

    private void validateParams(final int nodeA, final int nodeB) {
        if (nodeA <= 0 || nodeA > size) {
            throw new IllegalArgumentException(String.format("NodeA must be between 1 and Network size %d", size));
        }
        if (nodeB <= 0 || nodeB > size) {
            throw new IllegalArgumentException(String.format("NodeB must be between 1 and Network size %d", size));
        }
    }

    private static class DFS {
        private Set<Integer> visited;
        private Network network;

        public DFS(final Network network) {
            visited = new LinkedHashSet<>();
            this.network = network;
        }

        private void reachableNodes(final Integer n) {
            visited.add(n);
            network.adjList[n].forEach(v -> {
                if (!visited.contains(v)) {
                    reachableNodes(v);
                }
            });
        }

        public Set<Integer> neighborhood(final Integer n) {
            reachableNodes(n);
            return visited;
        }
    }
}

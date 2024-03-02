import java.util.*;

public class DjikstraAlgorithm {

    // Takes an adjacency matrix and source index as a parameter and  returns shortest distance to all the vertices
    public static double[] dijkstra(double[][] adjMatrix, int source) {
        int n = adjMatrix.length;
        double[] dist = new double[n];
        boolean[] visited = new boolean[n];

        // Initialize all distances to infinity and make all flags to false
        for (int i = 0; i < n; i++) {
            dist[i] = Double.MAX_VALUE;
            visited[i] = false;
        }

        // Set the distance of the source vertex to 0
        dist[source] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && adjMatrix[u][v] != 0 &&
                        dist[u] != Double.MAX_VALUE && dist[u] + adjMatrix[u][v] < dist[v]) {
                    dist[v] = dist[u] + adjMatrix[u][v];
                }
            }
        }

        return dist;
    }
    public static int minDistance(double[] dist, boolean[] visited) {
        double minDist = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] <= minDist) {
                minDist = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }


    // takes an adjacency matrix as paramater with source and destination index and returns the shortest route and and the
    // visited nodes from the source to destination
    public static DataRoute dijkstraNoLeft(double[][] graph, int source, int destination) {
        int size = graph.length;
        double[] dist = new double[size];
        int[] prev = new int[size];
        boolean[] visited = new boolean[size];
        Arrays.fill(dist, Double.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[source] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(size);
        queue.offer(source);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;
            for (int v = 0; v < size; v++) {
                if (graph[u][v] != Double.MAX_VALUE && !visited[v]) {
                    double alt = dist[u] + graph[u][v];
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        prev[v] = u;
                        queue.offer(v);
                    }
                }
            }
        }
        ArrayList<Integer> path = new ArrayList<>();
        int curr = destination;
        while (curr != -1) {
            path.add(curr);
            curr = prev[curr];
        }
        Collections.reverse(path);
        return new DataRoute(path, dist[destination]);
    }


}

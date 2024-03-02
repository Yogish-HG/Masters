import java.util.ArrayList;

public class DataRoute {

    private ArrayList<Integer> visitedVertices;
    private double totalDistance;

    public DataRoute(ArrayList<Integer> vertices, double distance){
        visitedVertices = vertices;
        totalDistance = distance;
    }

    public ArrayList<Integer> getVisitedVertices() {
        return visitedVertices;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
}

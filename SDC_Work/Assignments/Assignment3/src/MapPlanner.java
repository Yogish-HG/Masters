import java.util.*;

public class MapPlanner {

    private int degreeOfDeviation;
    private Location startPoint;
    private Set<Point> points;

    private ArrayList<streetObj> streets;
    private HashMap<String, Point[] > mapOfStreetsToPoints;


    /**
     * Create the Map Planner object.  The degrees provided tell us how much deviation from straight-forward
     * is needed to identify an actual turn in a route rather than a straight-on driving.
     * @param degrees
     */
    public MapPlanner( int degrees ) {
        if(Math.abs(degrees) > 360){
            return;
        }
        degreeOfDeviation = degrees;
        streets = new ArrayList<>();
        points = new LinkedHashSet<>();
        mapOfStreetsToPoints = new HashMap<>();
        startPoint = null;
    }

    /**
     * Identify the location of the depot.  That location is used as the starting point of any route request
     * to a destination
     * @param depot -- the street ID and side of the street (left or right) where we find the depot
     * @return -- true if the depot was set.  False if there was a problem in setting the depot location.
     */
    public Boolean depotLocation( Location depot ) {
        if(depot == null || depot.getStreetId() == null || depot.getStreetSide() == null){
            return false;
        }
        for(streetObj a : streets){
            if(Objects.equals(depot.getStreetId(), a.getID())){
                startPoint = new Location(depot.getStreetId(), depot.getStreetSide());
                return true;
            }
        }

        return false;
    }

    /**
     * Add a street to our map of the city.  The street is identified by the unique street id.
     * Although the parameters indicate a start and an end to the street, the street is bi-directional.
     * The start and end are just relevant when identifying the side of the street for some location.
     *
     * Street coordinates are in metres.
     *
     * Streets that share coordinates of endpoints meet at an intersection and you can drive from one street to the
     * other at that intersection.
     * @param streetId -- unique identifier for the street.
     * @param start -- coordinates of the starting intersection for the street
     * @param end -- coordinates of the ending intersection for the street
     * @return -- true if the street could be added.  False if the street isn't available in the map.
     */
    public Boolean addStreet( String streetId, Point start, Point end ) {
        try {
            if(start.getX() == end.getX() && start.getY() == end.getY() || Objects.equals(streetId, " ")){
                return false;
            }
            Point[] check = {start, end};
            for (Point[] p : mapOfStreetsToPoints.values()) {
                if(Helper.pointObjectsSame(p, check)){
                    return false;
                }
            }
            for(String s : mapOfStreetsToPoints.keySet()){
                if(Objects.equals(s, streetId)){
                    return false;
                }
            }

            if(Objects.equals(streetId, " ") || streetId == null){
                return false;
            }

            double dist = start.distanceTo(end);
            streetObj street = new streetObj(streetId, start, end, dist);
            streets.add(street);
            points.add(start);
            points.add(end);
            mapOfStreetsToPoints.put(streetId, new Point[] {start, end});
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     *  Given a depot location, return the street id of the street that is furthest away from the depot by distance,
     *  allowing for left turns to get to the street.
     */
    public String furthestStreet() {
        if(startPoint == null){
            return null;
        }
        // considering points as vertex to my graph
        int size = points.size();
        double[][] graph = new double[size][size];

        // creating a new arraylist to store the same points which is present in the set
        List<Point> list = new ArrayList<>(points);

        // traversing the arraylist of points(streets) to create the adjacency matrix
        for(int i=0; i< list.size(); i ++){
            for(int j=0; j< list.size(); j ++){
                if(i == j){
                    graph[i][j] = 0d;
                    continue;
                }
                Point[] tempArr = {list.get(i), list.get(j)};

                // Populating the graph

                for (Point[] value : mapOfStreetsToPoints.values()) {
                    if(Helper.pointObjectsSame(tempArr, value )){
                        graph[i][j] = list.get(i).distanceTo(list.get(j));
                    }
                }
            }
        }
        Point start = null;

        // Deciding the starting vertex(point) of a street by taking reference to street side.
        Point[] arr = mapOfStreetsToPoints.get(startPoint.getStreetId());
        if(startPoint.getStreetSide() == StreetSide.Left){
            start = Helper.ifGoToLeft(arr);
        }
        if(startPoint.getStreetSide() == StreetSide.Right){
            start = Helper.ifGoToRight(arr);
        }
        int index = list.indexOf(start);

        double[] res = DjikstraAlgorithm.dijkstra(graph, index);

        int maxIndex = 0;
        double maxValue = res[0];

        // getting the index of the furthest vertex from the source vertex.

        for (int i = 1; i < res.length; i++) {
            if (res[i] > maxValue) {
                maxValue = res[i];
                maxIndex = i;
            }
        }

        Point resPoint = list.get(maxIndex);

        int furthestPointCount = 0;

        // checking if the furthest vertex is connected to more than one street
        for(Point[] p : mapOfStreetsToPoints.values()){
            if(Helper.arePointsEqual(resPoint, p[0])){
                furthestPointCount += 1;
            }
            if(Helper.arePointsEqual(resPoint, p[1])){
                furthestPointCount += 1;
            }
        }

        // if furthest street is connected to single street, then that street ID is returned.
        if(furthestPointCount == 1){
            for (Map.Entry<String, Point[]> entry : mapOfStreetsToPoints.entrySet()) {
                String key = entry.getKey();
                Point[] last = {entry.getValue()[0], entry.getValue()[1]};
                for(Point p : last) {
                    if (Objects.equals(p, resPoint) && !Objects.equals(key, startPoint.getStreetId())) {
                        return key;
                    }
                }
            }
        }

        // if furthest street is connected to more than one street, then longest street's ID is returned.
        if(furthestPointCount > 1){
            ArrayList<String> streetName = new ArrayList<>();
            ArrayList<Double> dist = new ArrayList<>();

            for (Map.Entry<String, Point[]> entry : mapOfStreetsToPoints.entrySet()){
                if(Helper.arePointsEqual(entry.getValue()[0], resPoint) || Helper.arePointsEqual(entry.getValue()[1], resPoint)){
                    streetName.add(entry.getKey());
                    dist.add(entry.getValue()[0].distanceTo(entry.getValue()[1]));
                }
            }
            int indexOfMax = 0;

            for (int i = 1; i < dist.size(); i++) {
                if (dist.get(i) < dist.get(indexOfMax)) {
                    indexOfMax = i;
                }
            }
            return streetName.get(indexOfMax);
        }
        return null;
    }

    /**
     * Compute a route to the given destination from the depot, given the current map and not allowing
     * the route to make any left turns at intersections.
     * @param destination -- the destination for the route
     * @return -- the route to the destination, or null if no route exists.
     */
    public Route routeNoLeftTurn( Location destination ) {

        if(destination == null){
            return null;
        }
        // return null if start and end are the same.
        if(destination == startPoint){
            return null;
        }
        // Considering a street as the vertex of the graph
        List<Point[]> streetRepresentationInPoints = new ArrayList<>(mapOfStreetsToPoints.values());
        ArrayList<String> streetRepresentationInString = new ArrayList<>(mapOfStreetsToPoints.keySet());
        int size = streetRepresentationInPoints.size();
        double[][] noLeftGraph = new double[size][size];
        TurnDirection[][] directionGraph = new TurnDirection[size][size];

        // Populating the graph
        for(int i = 0; i< size; i++){
            for(int k=0; k< size; k++){
                if(i == k){
                    noLeftGraph[i][k] = 0d;
                    directionGraph[i][k] = null;
                    continue;
                }
                /* if one point is common for a street ,then that point is considered as an intersection for the street
                   The common point as considered as turnAt point, other two points are considered as turnOn and the
                   the instance.
                 */

                if(Helper.onePointSame(streetRepresentationInPoints.get(i), streetRepresentationInPoints.get(k))!= null){
                    Point turnAtPoint = Helper.onePointSame(streetRepresentationInPoints.get(i), streetRepresentationInPoints.get(k));
                    Point instance = null;
                    Point turnOnTowards = null;
                    for(int j =0; j < 2; j++){
                        if(!Helper.arePointsEqual(streetRepresentationInPoints.get(i)[j],turnAtPoint)){
                            instance = streetRepresentationInPoints.get(i)[j];
                        }
                    }
                    for(int j =0; j < 2; j++){
                        if(!Helper.arePointsEqual(streetRepresentationInPoints.get(k)[j],turnAtPoint)){
                            turnOnTowards = streetRepresentationInPoints.get(k)[j];
                        }
                    }

                    /* if the turn taken is left, and there are only two streets intersecting at a point
                    the turn is valid and populated in the graph, else not considered.
                     */
                    TurnDirection turn = instance.turnType(turnAtPoint, turnOnTowards, degreeOfDeviation);

                    if(turn == TurnDirection.Left){
                        int count = 0;
                        for(Point[] p : mapOfStreetsToPoints.values()){
                            if(Helper.arePointsEqual(p[0], turnAtPoint) || Helper.arePointsEqual(p[1], turnAtPoint)){
                                count += 1;
                            }
                        }
                        if(count == 2){
                            double dist = 0;
                            dist += instance.distanceTo(turnAtPoint);
                            dist += turnAtPoint.distanceTo(turnOnTowards);
                            noLeftGraph[i][k] = dist;
                            directionGraph[i][k] = turn;
                        }
                    }
                    if(turn != TurnDirection.Left){
                        double dist = 0;
                        dist += instance.distanceTo(turnAtPoint);
                        dist += turnAtPoint.distanceTo(turnOnTowards);
                        noLeftGraph[i][k] = dist;
                        directionGraph[i][k] = turn;

                    }

                }
            }
        }
        // getting the starting and ending street and using the Dijkstra's algorithm to get the route data
        int start, dest;
        start = streetRepresentationInString.indexOf(startPoint.getStreetId());
        dest = streetRepresentationInString.indexOf(destination.getStreetId());
        DataRoute dataRoute = DjikstraAlgorithm.dijkstraNoLeft(noLeftGraph, start, dest);
        ArrayList<TurnDirection> visitedNodes = new ArrayList<>();
        ArrayList<String> streetIDs = new ArrayList<>();
        for(int j = 0; j < dataRoute.getVisitedVertices().size(); j++){
            streetIDs.add(streetRepresentationInString.get(dataRoute.getVisitedVertices().get(j)));
        }
        for(int i=0; i<dataRoute.getVisitedVertices().size()-1; i++){
            visitedNodes.add(directionGraph[dataRoute.getVisitedVertices().get(i)][dataRoute.getVisitedVertices().get(i+1)]);
        }
        return new Route(dataRoute.getVisitedVertices(),visitedNodes,streetIDs, dataRoute.getTotalDistance());
    }
}

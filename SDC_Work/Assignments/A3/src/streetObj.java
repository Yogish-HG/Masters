public class streetObj {

    private String ID;
    private Point start;
    private Point end;
    private double distBwThem;

    public streetObj(String id, Point startOfStreet, Point endOfStreet, double dist){
        ID = id;
        start = startOfStreet;
        end = endOfStreet;
        distBwThem = dist;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public double getDistBwThem() {
        return distBwThem;
    }

    public void setDistBwThem(double distBwThem) {
        this.distBwThem = distBwThem;
    }
}

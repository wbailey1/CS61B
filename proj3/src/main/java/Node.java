public class Node implements Comparable<Node>{

    private double x;
    private double y;
    private double w;
    private double h;
    private String filename;
    private Node q1;
    private Node q2;
    private Node q3;
    private Node q4;
    private int depth;

    public Node(double x, double y, double w, double h, String filename, int depth) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.filename = filename;
        this.depth = depth;
    }

    @Override
    public int compareTo(Node other) {
        if (other.getY() < y) {
            return 1;
        } else if (other.getY() > y) {
            return -1;
        } else {
            if (other.getX() > x) {
                return -1;
            } else if (other.getX() < x) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void setFilename(String file) {
        this.filename = file;
    }

    public int getDepth() {
        return depth;
    }

    public String getFilename() {
        return filename;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setq1(Node q1) {
        this.q1 = q1;
    }

    public void setq2(Node q2) {
        this.q2 = q2;
    }

    public void setq3(Node q3) {
        this.q3 = q3;
    }

    public void setq4(Node q4) {
        this.q4 = q4;
    }

    public Node getq1() {
        return this.q1;
    }

    public Node getq2() {
        return this.q2;
    }

    public Node getq3() {
        return this.q3;
    }

    public Node getq4() {
        return this.q4;
    }
}
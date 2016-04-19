import java.io.File;
import java.util.List;

public class QuadTree {
    private Node root;
    private double w;
    private double h;
    private double rasterUllon;
    private double rasterUllat;
    private double rasterLrlon;
    private double rasterLrlat;

    public QuadTree(double minX, double minY, double maxX, double maxY) {
        this.w = maxX - minX;
        this.h = maxY - minY;
        this.root = new Node(minX, maxY, w, h, "root.png", 0);
        File folder = new File(MapServer.IMG_ROOT);
        String[] listOfFiles = folder.list();
        for (String file : listOfFiles) {
            insert(root, minX, maxY, w, h, file, file, 0);
        }
    }

    public Node getRootNode() {
        return this.root;
    }

    public void clear() {
        this.root.setq1(null);
        this.root.setq2(null);
        this.root.setq3(null);
        this.root.setq4(null);
    }

    private int insert(Node n, double x, double y, double width, double height,
                       String file, String protectedFile, int depth) {
        String first = file.substring(0, 1);
        if (depth == 0) {
            if (!first.equals("1") && !first.equals("2")
                    && !first.equals("3") && !first.equals("4")) {
                return 0;
            }
        }
        double nextWidth = width / 2;
        double nextHeight = height / 2;
        if (first.equals(".")) {
            n.setFilename(protectedFile);
        } else if (first.equals("1")) {
            if (n.getq1() == null) {
                n.setq1(new Node(x, y, nextWidth, nextHeight, null, depth + 1));
            }
            insert(n.getq1(), x, y, nextWidth, nextHeight,
                    file.substring(1), protectedFile, depth + 1);
        } else if (first.equals("2")) {
            if (n.getq2() == null) {
                n.setq2(new Node(x + nextWidth, y, nextWidth, nextHeight, null, depth + 1));
            }
            insert(n.getq2(), x + nextWidth, y, nextWidth,
                    nextHeight, file.substring(1), protectedFile, depth + 1);
        } else if (first.equals("3")) {
            if (n.getq3() == null) {
                n.setq3(new Node(x, y - nextHeight, nextWidth, nextHeight, null, depth + 1));
            }
            insert(n.getq3(), x, y - nextHeight, nextWidth,
                    nextHeight, file.substring(1), protectedFile, depth + 1);
        } else if (first.equals("4")) {
            if (n.getq4() == null) {
                n.setq4(new Node(x + nextWidth, y - nextHeight,
                        nextWidth, nextHeight, null, depth + 1));
            }
            insert(n.getq4(), x + nextWidth, y - nextHeight, nextWidth,
                    nextHeight, file.substring(1), protectedFile, depth + 1);
        }
        return 0;
    }

    public void setParams(double ullon, double ullat, double lrlon, double lrlat) {
        this.rasterUllon = ullon;
        this.rasterUllat = ullat;
        this.rasterLrlon = lrlon;
        this.rasterLrlat = lrlat;
    }

    private boolean contains(Node n) {
        return (!(rasterLrlon < n.getX()) && !(rasterUllon > n.getX() + n.getW()))
                && (!(rasterLrlat > n.getY()) && !(rasterUllat < n.getY() - n.getH()));
    }

    public List<Node> intersects(Node n, int depth, List<Node> list) {
        if (depth == n.getDepth() || n.getq1() == null) {
            if (contains(n)) {
                if(!list.contains(n)) {
                    list.add(n);
                }
            }
            return list;
        } else {
            intersects(n.getq1(), depth, list);
            intersects(n.getq2(), depth, list);
            intersects(n.getq3(), depth, list);
            intersects(n.getq4(), depth, list);
        }
        return list;
    }
}

public class QuadTree {
    public String val;
    public QuadTree TopLeft, TopRight, LowLeft, LowRight;
    public double tl, tr, ll, lr;
    public QuadTree(String value, double ROOT_ULLON, double ROOT_ULLAT, double ROOT_LRLON, double ROOT_LRLAT) {
        this.val = value;
        this.TopLeft = this.TopRight = this.LowLeft = this.LowRight = null;
        this.tl = ROOT_ULLON;
        this.tr = ROOT_ULLAT;
        this.ll = ROOT_LRLON;
        this.lr = ROOT_LRLAT;
    }

}

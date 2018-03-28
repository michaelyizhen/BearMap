import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class QueryQuadTree {

    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    public static final int TILE_SIZE = 256;

    List<QuadTree> node;
    int indexI = 0;
    int indexJ = 0;
    int maxWidth;
    int maxHeight;
    String[][] render_grid;

    public QueryQuadTree() {

    }

    public boolean isValid(double ullon, double ullat, double lrlon, double lrlat) {
        if (ullon >= ROOT_ULLON && ullat <= ROOT_ULLAT && lrlon <= ROOT_LRLON && lrlat >= ROOT_LRLAT && ullon < lrlon && ullat > lrlat) {
            return true;
        }
        return false;
    }

    public int findDepth(double ullon, double ullat, double lrlon, double lrlat, double width, double height) {
        int depth = 0;
        double standardWidth = Math.abs((ROOT_ULLON - ROOT_LRLON)) / TILE_SIZE;
        double LDD = Math.abs(ullon - lrlon) / width;

        while (LDD < standardWidth && depth < 7) {
            LDD *= 2;
            depth += 1;
        }
        return depth;
    }


    public Map<String, Object> getCoordinate(QuadTree root, double ullon, double ullat, double lrlon,
                                             double lrlat, double width, double height) {
        // each node of Quadtree, be aware of corner
        // @param
        node = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        int depth = findDepth(ullon, ullat, lrlon, lrlat, width, height);
        double area = Math.pow(2, depth);
        double standardWidth = Math.abs(ROOT_ULLON - ROOT_LRLON)  / area;
        double standardHeight = Math.abs(ROOT_ULLAT - ROOT_LRLAT) / area;


        //System.out.println((Math.abs(ullon - lrlon) / standardWidth));
        findCoodinate(root, ullon, ullat, lrlon, lrlat, standardWidth, standardHeight, 0, depth);
        maxWidth = (int)((Math.abs(node.get(0).tl - node.get(node.size() - 1).ll) + (standardWidth / 4)) / standardWidth);
        maxHeight = node.size() / maxWidth;
        render_grid = new String[maxHeight][maxWidth];
        double w = node.get(0).tl;
        double h = node.get(0).tr;

        for (int i = 0; i < node.size(); i++) {
            render_grid[adjustInt((h - node.get(i).tr) / standardHeight)][adjustInt((node.get(i).tl - w) / standardWidth)] = node.get(i).val;
        }

        map.put("raster_ul_lon", w);
        map.put("raster_ul_lat", h);
        map.put("raster_lr_lon", node.get(node.size() - 1).ll);
        map.put("raster_lr_lat", node.get(node.size() - 1).lr);
        //System.out.println(index);
        map.put("render_grid", render_grid);
        boolean success = isValid(node.get(0).tl, node.get(0).tr, node.get(node.size() - 1).ll, node.get(node.size() - 1).lr);
        map.put("query_success", success);
        map.put("depth", depth);
        return map;
    }

    private void findCoodinate(QuadTree root, double ullon, double ullat, double lrlon,
                               double lrlat, double sWidth, double sHeight, int start, int depth) {
        /**
         * @param ullon,ullat,lrlon,lrlat
         @param QuadTree node--> tl tr ll lr
         */
        if (start == depth) {
            // check valid value and add it into Node List
            if (root.tl > ullon - sWidth && root.tr < ullat + sHeight && root.ll < lrlon + sWidth && root.lr > lrlat - sHeight) {
                node.add(root);
            }
            // backup : || (ullon <= root.tl && lrlat <= root.tr)|| (lrlon >= root.ll && ullat >= root.tr)
            // check corner, and add it into Corner List
            return;
        }
        findCoodinate(root.TopLeft, ullon, ullat, lrlon, lrlat, sWidth, sHeight, start + 1, depth);
        findCoodinate(root.TopRight, ullon, ullat, lrlon, lrlat, sWidth, sHeight, start + 1, depth);
        findCoodinate(root.LowLeft, ullon, ullat, lrlon, lrlat, sWidth, sHeight, start + 1, depth);
        findCoodinate(root.LowRight, ullon, ullat, lrlon, lrlat, sWidth, sHeight, start + 1, depth);
    }

    private int adjustInt(double d) {
        int remainder = (int)((d - (int)d) * 10);
        int tail = remainder >= 5 ? 1 : 0;
        return (int)d + tail;
    }
}


/*
        int i = 0, j = 0, k = 0, index = 0;
        if (maxHeight % 2 > 0) {
            if (isFirst(node.get(0).val)) {
                for (int l = 0; l < maxWidth; l++) {
                    render_grid[0][l] = node.get(index++).val;
                }
                j = 0;
                i = 1;
            }
        }
        for (; i < maxHeight - 1; i+= 2) {
            // i and i + 1
            while (j < maxWidth || k < maxWidth) {
                //System.out.println(index);
                while ((int)node.get(index).val.charAt(node.get(index).val.lastIndexOf('.') - 1) <= 50 && j <maxWidth) {
                    render_grid[i][j++] =  node.get(index++).val;
                }
                while (index < node.size() && (int)node.get(index).val.charAt(node.get(index).val.lastIndexOf('.') - 1) > 50 && k <maxWidth) {
                    render_grid[i + 1][k++] =  node.get(index++).val;
                }
            }
            j = 0;
            k = 0;
        }
        if (index < node.size()) {
            for (int l = 0; l < maxWidth; l++) {
                render_grid[i][l] = node.get(index++).val;
                index++;
            }
        }
        */
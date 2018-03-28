public class BuildQuadTree {
        public void solution(QuadTree root, int index, int maxlevel, String imgRoot) {
            index *= 10;
            if (index / maxlevel >= 1 && index != 0) {
                return;
            }
            else {
                double width = ( root.ll - root.tl) / 2;
                double height = (root.lr - root.tr) / 2;

                root.TopLeft = new QuadTree((imgRoot + (index + 1) +".png"), root.tl, root.tr, root.tl + width, root.tr + height);
                root.TopRight = new QuadTree(imgRoot + (index + 2) +".png", root.tl + width, root.tr, root.ll, root.tr + height);
                root.LowLeft = new QuadTree(imgRoot + (index + 3) +".png", root.tl, root.tr + height, root.tl + width, root.lr);
                root.LowRight = new QuadTree(imgRoot + (index + 4) +".png", root.tl + width, root.tr + height, root.ll, root.lr);
                solution(root.TopLeft, index + 1, maxlevel, imgRoot);
                solution(root.TopRight, index + 2, maxlevel, imgRoot);
                solution(root.LowLeft, index + 3, maxlevel, imgRoot);
                solution(root.LowRight, index + 4, maxlevel, imgRoot);
            }
        }
}

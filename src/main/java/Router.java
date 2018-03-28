
import java.util.*;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a LinkedList of <code>Long</code>s representing the shortest path from st to dest, 
     * where the longs are node IDs.
     */

    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                                double destlon, double destlat) {
        long sc = g.closest(stlon, stlat);
        long dt = g.closest(destlon, destlat);

        Map<Long, Double> dtMap = new HashMap<>();
        Map<Long, Long> edgeMap = new HashMap<>();

        PriorityQueue<FringeObj> priorityQueue = new PriorityQueue<>(new FringeObj(sc, 0));
        priorityQueue.add(new FringeObj(sc, g.distance(sc, dt)));
        double newDistance;
        dtMap.put(sc, 0.0d);
        boolean flag = false;
        while (!flag) {
            FringeObj fringeObj = priorityQueue.peek();
            for (long vecId : g.adjacent(fringeObj.id)) {
                if (vecId == fringeObj.id) {
                    continue;
                }
                newDistance = dtMap.get(fringeObj.id) + g.distance(vecId, fringeObj.id);
                if (dtMap.containsKey(vecId)) {
                    if (newDistance >= dtMap.get(vecId)) {
                        continue;
                    }
                }
                dtMap.put(vecId, newDistance);
                edgeMap.put(vecId, fringeObj.id);
                priorityQueue.add(new FringeObj(vecId, newDistance + g.distance(vecId, dt)));
                if (vecId == dt) {
                    flag = true;
                }
            }
            priorityQueue.remove();
        }
        LinkedList<Long> result = new LinkedList<>();
        result.addFirst(dt);
        long num = edgeMap.get(dt);
        while (num != sc) {
            result.addFirst(num);
            num = edgeMap.get(num);
        }
        result.addFirst(sc);
        return result;
    }

    private static class FringeObj implements Comparator<FringeObj> {
        long id;
        double distance;

        FringeObj(long id, double distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compare(FringeObj o1, FringeObj o2) {
            return o1.distance > o2.distance ? 1 : -1;
        }
    }
}

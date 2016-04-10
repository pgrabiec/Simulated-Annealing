package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman;

import java.awt.*;
import java.util.Random;

public class PointsGenerator {
    public static Point[] getConstDensityPoints(int size, int limit) {
        Random random = new Random();

        Point[] result = new Point[size];

        for (int i=0; i<size; i++) {
            result[i] = new Point(Math.abs(random.nextInt()) % limit, Math.abs(random.nextInt()) % limit);
        }

        return result;
    }

    public static Point[] getGroupedPoints(int sizePerGroup, int groupsCount, int distance, int width, int height) {
        Point[] result = new Point[sizePerGroup * groupsCount];

        Random random = new Random();

        int id = 0;

        for (int i=0; i<sizePerGroup; i++) {
            for (int group=0; group < groupsCount; group++) {
                int xMin = (group % 3) * (width + distance);
                int yMin = (group / 3) * (height + distance);

                int x, y;
                x = (Math.abs(random.nextInt()) % width) + xMin;
                y = (Math.abs(random.nextInt()) % height) + yMin;

                result[id] = new Point(x, y);
                id++;
            }
        }

        return result;
    }
}

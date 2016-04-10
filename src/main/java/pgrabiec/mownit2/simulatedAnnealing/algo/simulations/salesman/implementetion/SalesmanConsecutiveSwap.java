package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.implementetion;

import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.SalesmanProblemSimulation;

import java.awt.*;

public class SalesmanConsecutiveSwap extends SalesmanProblemSimulation {
    public SalesmanConsecutiveSwap(long iterationsLimit, String title, double randomCoefficient, TemperatureFunction energyFunction, Point[] points_set) {
        super(iterationsLimit, title, randomCoefficient, energyFunction, points_set);
    }

    @Override
    protected Point[] getNeighbour(Point[] pts) {
        int swapIndex1 = (int) (Math.random() * (pts.length - 1));
        int swapIndex2 = (findClosest(swapIndex1, pts) + 1) % pts.length;

        Point tmp = pts[swapIndex1];
        pts[swapIndex1] = pts[swapIndex2];
        pts[swapIndex2] = tmp;

        return pts;
    }

    /**
     * Finds the closest neighbour to pts[id]
     * */
    private int findClosest(int id, Point[] pts) {
        double minDist = Double.MAX_VALUE;
        int indexMin = -1;

        Point basePoint = pts[id];
        Point currentPoint;

        double currDist;
        for (int i=0; i<pts.length; i++) {
            if (i != id) {
                currentPoint = pts[i];

                currDist = basePoint.distance(currentPoint);

                if (currDist < minDist) {
                    minDist = currDist;
                    indexMin = i;
                }
            }
        }

        return indexMin;
    }
}

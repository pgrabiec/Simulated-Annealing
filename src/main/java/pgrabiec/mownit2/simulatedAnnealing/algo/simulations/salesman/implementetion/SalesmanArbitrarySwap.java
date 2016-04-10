package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.implementetion;

import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.SalesmanProblemSimulation;

import java.awt.*;

public class SalesmanArbitrarySwap extends SalesmanProblemSimulation {
    public SalesmanArbitrarySwap(long iterationsLimit, String title, double randomCoefficient, TemperatureFunction energyFunction, Point[] points_set) {
        super(iterationsLimit, title, randomCoefficient, energyFunction, points_set);
    }

    @Override
    protected Point[] getNeighbour(Point[] currentData) {
        int index1 = (int) (Math.random() * (currentData.length - 1));
        int index2 = (int) (Math.random() * (currentData.length - 1));

        Point tmp;

        tmp = currentData[index1];
        currentData[index1] = currentData[index2];
        currentData[index2] = tmp;

        return currentData;
    }
}

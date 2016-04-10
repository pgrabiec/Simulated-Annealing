package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman;

import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;
import pgrabiec.mownit2.simulatedAnnealing.algo.SimulatedAnnealing;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.visualization.GraphPointVisualization;

import java.awt.*;

public abstract class SalesmanProblemSimulation extends SimulatedAnnealing<Point[]> {
    protected final Point[] POINTS_SET;

    protected final GraphPointVisualization visualization;

    public SalesmanProblemSimulation(long iterationsLimit, String title, double randomCoefficient, TemperatureFunction energyFunction, Point[] points_set) {
        super(iterationsLimit, randomCoefficient, energyFunction, title);
        POINTS_SET = points_set;

        visualization = new GraphPointVisualization(title);
        visualization.addNodes(POINTS_SET);
    }

    @Override
    protected void optimalChanged(Point[] current, double currentValue, long iterations) {
        visualization.setEdges(current);
    }

    @Override
    protected Point[] getValueCopyOf(Point[] data) {
        Point[] copy = new Point[data.length];

        System.arraycopy(data, 0, copy, 0, data.length);

        return copy;
    }

    @Override
    protected Point[] getInitialPoint() {
        return getValueCopyOf(POINTS_SET);
    }

    @Override
    protected double getEnergy(Point[] argument) {
        double result = 0.0;

        Point p1, p2;

        for (int i=0; i<argument.length; i++) {
            p1 = argument[i];
            p2 = argument[(i+1)%argument.length];

            result += getDistance(p1, p2);
        }

        return result;
    }

    protected double getDistance(Point p1, Point p2) {
        int distanceX = Math.abs(p2.x-p1.x);
        int distanceY = Math.abs(p2.y-p1.y);
        return Math.sqrt(distanceX*distanceX + distanceY*distanceY);
    }

    @Override
    protected void accepted(Point[] point, double energy) {}
}

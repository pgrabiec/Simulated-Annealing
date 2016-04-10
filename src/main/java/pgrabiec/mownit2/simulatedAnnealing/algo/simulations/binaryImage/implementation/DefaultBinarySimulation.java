package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.implementation;

import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.BinaryImage;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.BinaryImageSimulation;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.visualization.BinaryImageVisualization;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultBinarySimulation extends BinaryImageSimulation{
    private final int NEIGHBOUR_MODE;
    public static final int NEIGHBOUR_MODE_4 = 4;
    public static final int NEIGHBOUR_MODE_8 = 8;

    private final int SWAP_MODE;
    public static final int SWAP_MODE_RANDOM = 1;
    public static final int SWAP_MODE_CONSECUTIVE = 2;

    private final BinaryImageVisualization bestVisualization;


    public DefaultBinarySimulation(long iterationsLimit, double randomCoefficient, TemperatureFunction energyFunction, BinaryImage initialImage, int neighbour_mode, int swap_mode, String title) {
        super(title, iterationsLimit, randomCoefficient, energyFunction, initialImage);
        NEIGHBOUR_MODE = neighbour_mode;
        SWAP_MODE = swap_mode;



        bestVisualization = new BinaryImageVisualization("Best result " + title);
    }


    @Override
    protected BinaryImage getNeighbour(BinaryImage currentImage) {
        if (NEIGHBOUR_MODE == NEIGHBOUR_MODE_4 || NEIGHBOUR_MODE == NEIGHBOUR_MODE_8) {
            int randX = (int) (Math.random() * currentImage.getWidth()) % (currentImage.getWidth());
            int randY = (int) (Math.random() * currentImage.getHeight()) % (currentImage.getHeight());

            Point swappedPoint = new Point(randX, randY);

            List<Point> neighbours = getValidNeighbours(currentImage, swappedPoint);

            executeSwap(currentImage, swappedPoint, neighbours);

            return currentImage;
        } else {
            throw new IllegalStateException("Neighbour mode not specified");
        }
    }

    private void executeSwap(BinaryImage image, Point swapped, List<Point> neighbours) {
        if (SWAP_MODE == SWAP_MODE_RANDOM) {
            int randIndex = (int) (Math.random() * neighbours.size()) % (neighbours.size());

            Point neighbour = neighbours.get(randIndex);

            image.swapPixels(swapped, neighbour);

        } else if (SWAP_MODE == SWAP_MODE_CONSECUTIVE) {
            double minEnergy = Double.MAX_VALUE;
            Point minPoint = null;

            double energy;
            for (Point neighbour : neighbours) {
                image.swapPixels(neighbour, swapped);
                energy = image.getEnergy();

                if (energy < minEnergy) {
                    minEnergy = energy;
                    minPoint = neighbour;
                }

                image.swapPixels(neighbour, swapped);
            }

            image.swapPixels(swapped, minPoint);
        } else {
            throw new IllegalStateException("Swap mode not specified");
        }
    }

    private List<Point> getValidNeighbours(BinaryImage image, Point source) {
        List<Point> result = new ArrayList<Point>(8);

        int x = source.x;
        int y = source.y;

        int currX, currY;

        if (NEIGHBOUR_MODE == NEIGHBOUR_MODE_4 || NEIGHBOUR_MODE == NEIGHBOUR_MODE_8) {
            int[][] neighbours = new int[][] {
                    {x-1, y},
                    {x+1, y},
                    {x, y-1},
                    {x, y+1},
            };

            for (int[] neighbour : neighbours) {
                currX = neighbour[0];
                currY = neighbour[1];
                if (currX >= 0 && currX < image.getWidth() && currY >= 0 && currY < image.getHeight()) {
                    result.add(new Point(currX, currY));
                }
            }
        } else {
            throw new IllegalStateException("Neighbour mode not specified");
        }

        if (NEIGHBOUR_MODE == NEIGHBOUR_MODE_8) {
            int[][] neighbours = new int[][] {
                    {x-1, y-1},
                    {x-1, y+1},
                    {x+1, y-1},
                    {x+1, y+1},
            };

            for (int[] neighbour : neighbours) {
                currX = neighbour[0];
                currY = neighbour[1];
                if (currX >= 0 && currX < image.getWidth() && currY >= 0 && currY < image.getHeight()) {
                    result.add(new Point(currX, currY));
                }
            }
        }

        return result;
    }

    @Override
    protected void accepted(BinaryImage point, double energy) {}

    @Override
    protected void optimalChanged(BinaryImage current, double currentEnergy, long iterations) {
        bestVisualization.updateImage(current);
    }
}

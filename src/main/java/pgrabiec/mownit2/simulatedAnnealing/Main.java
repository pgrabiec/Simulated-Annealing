package pgrabiec.mownit2.simulatedAnnealing;

import pgrabiec.mownit2.simulatedAnnealing.algo.SimulatedAnnealing;
import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.BinaryImage;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.implementation.DefaultBinarySimulation;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.PointsGenerator;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.implementetion.SalesmanArbitrarySwap;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.implementetion.SalesmanConsecutiveSwap;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku.Sudoku;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku.SudokuIO;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku.implementation.DefaultSudokuSimulation;
import pgrabiec.mownit2.simulatedAnnealing.algo.temperatureFunctions.LinearTemperatureDrop;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        testSalesman();

        testBinaryImageSolver();

        testSudoku();
    }

    private static void testSudoku() {
        double coefficient = 1e1;

        Sudoku sudoku;

        try {
            sudoku = SudokuIO.readSudoku("sudoku.txt", " ");

            new DefaultSudokuSimulation(
                    coefficient,
                    TemperatureFunction.getDefaultLinear(),
                    sudoku
            ).solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testSalesman() {
        long iterationsLimit = 200000000;
        double exponentCoefficient = 1e1;
        int coordinatesLimit = 1000;
        int pointsGroupWidth = 100;
        int pointsGroupHeight = 100;
        int pointsGroupsDistance = 120;

        int[] N = new int[] {
                30,
                90,
                180
        };

        for (int n=0; n<N.length; n++) {
            Point[] constPoints = PointsGenerator.getConstDensityPoints(N[n], coordinatesLimit);
            Point[] groupedPoints = PointsGenerator.getGroupedPoints(
                    N[n]/9,
                    9,
                    pointsGroupsDistance,
                    pointsGroupWidth,
                    pointsGroupHeight
            );

            new SalesmanArbitrarySwap(
                    iterationsLimit,
                    "Arbitrary | " + " Const dens | n=" + N[n] + " | Iterations=" + iterationsLimit,
                    exponentCoefficient,
                    TemperatureFunction.getDefaultLinear(),
                    constPoints
            ).solve();

            new SalesmanConsecutiveSwap(
                    iterationsLimit,
                    "Consecutive | " + " Const dens | n=" + N[n] + " | Iterations=" + iterationsLimit,
                    exponentCoefficient,
                    TemperatureFunction.getDefaultLinear(),
                    constPoints
            ).solve();

            new SalesmanArbitrarySwap(
                    iterationsLimit,
                    "Arbitrary | " + " Grouped | n=" + N[n] + " | Iterations=" + iterationsLimit,
                    exponentCoefficient,
                    TemperatureFunction.getDefaultLinear(),
                    groupedPoints
            ).solve();

            new SalesmanConsecutiveSwap(
                    iterationsLimit,
                    "Consecutive | " + " Grouped | n=" + N[n] + " | Iterations=" + iterationsLimit,
                    exponentCoefficient,
                    TemperatureFunction.getDefaultLinear(),
                    groupedPoints
            ).solve();


        }
    }

    private static void testBinaryImageSolver() {
        double exponentCoefficient = 1e-1;
        long iterationsLimit = 50000;

        double[] densities = new double[] {
                0.1, 0.2, 0.4
        };

        int[] neighbourModes = new int[] {
                DefaultBinarySimulation.NEIGHBOUR_MODE_4,
                DefaultBinarySimulation.NEIGHBOUR_MODE_8
        };

        String[] neighbourModesDescription = new String[] {
                "4 neighbours",
                "8 neighbours"
        };

        TemperatureFunction[] temperatureFunctions = new TemperatureFunction[] {
                new LinearTemperatureDrop(1e5, 1e10, 1e-2, 1e-1),
                new LinearTemperatureDrop(1e5, 1e10, 1e-1, 1e-1)
        };

        String[] temperatureFunctionsDescription = new String[] {
                "Quick T drop",
                "Slow T drop"
        };


        for (double density : densities) {
            BinaryImage image = new BinaryImage(100, 100, density);

            for (int neighbourMode=0; neighbourMode < neighbourModes.length; neighbourMode++) {
                for (int tempFunction=0; tempFunction < temperatureFunctions.length; tempFunction++) {
                    SimulatedAnnealing<BinaryImage> simulation = new DefaultBinarySimulation(
                            iterationsLimit,
                            exponentCoefficient,
                            temperatureFunctions[tempFunction].clone(),
                            image.getCopy(),
                            neighbourModes[neighbourMode],
                            DefaultBinarySimulation.SWAP_MODE_CONSECUTIVE,
                            "Dens=" + density + " | " + neighbourModesDescription[neighbourMode] + " | " + temperatureFunctionsDescription[tempFunction]
                    );

                    simulation.solve();
                }
            }
        }

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

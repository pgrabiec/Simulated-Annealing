package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku.implementation;

import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku.Sudoku;
import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku.SudokuSimulation;

public class DefaultSudokuSimulation extends SudokuSimulation {
    private static final double STOP_ENERGY = 0.0;

    public DefaultSudokuSimulation(double randomCoefficient, TemperatureFunction energyFunction, Sudoku initialSudoku) {
        super(randomCoefficient, energyFunction, initialSudoku);
    }

    @Override
    protected Sudoku getNeighbour(Sudoku currentSudoku) {
        return currentSudoku.getNeighbour();
    }

    @Override
    protected void optimalChanged(Sudoku current, double currentEnergy, long iterations) {
        if (currentEnergy == STOP_ENERGY) {
            int emptyCount = 0;
            for (boolean[] notEmpty : current.getInitialFillMask()) {
                for (boolean b : notEmpty) {
                    if (!b) {
                        emptyCount++;
                    }
                }
            }

            System.out.println("Solved!\tIn " + iterations + " iterations" + " empty: " + emptyCount);
            current.print();
            System.exit(0);
        }
        System.out.println("Optimal:\t" + currentEnergy + "\tIterations: " + iterations);
        current.print();
    }

    @Override
    protected void accepted(Sudoku point, double energy) {}
}

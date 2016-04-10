package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku;

import pgrabiec.mownit2.simulatedAnnealing.algo.SimulatedAnnealing;
import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;

public abstract class SudokuSimulation extends SimulatedAnnealing<Sudoku> {
    private final Sudoku initialSudoku;

    public SudokuSimulation(double randomCoefficient, TemperatureFunction energyFunction, Sudoku initialSudoku) {
        super(Long.MAX_VALUE, randomCoefficient, energyFunction, "");
        this.initialSudoku = getValueCopyOf(initialSudoku);
    }

    @Override
    protected Sudoku getValueCopyOf(Sudoku data) {
        return data.getValueCopy();
    }

    @Override
    protected Sudoku getInitialPoint() {
        return initialSudoku;
    }

    @Override
    protected double getEnergy(Sudoku argument) {
        return argument.getEnergy();
    }
}

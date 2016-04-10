package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage;

import pgrabiec.mownit2.simulatedAnnealing.algo.SimulatedAnnealing;
import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;

public abstract class BinaryImageSimulation extends SimulatedAnnealing<BinaryImage> {
    private final BinaryImage initialImage;

    public BinaryImageSimulation(String title, long iterationsLimit, double randomCoefficient, TemperatureFunction energyFunction, BinaryImage initialImage) {
        super(iterationsLimit, randomCoefficient, energyFunction, title);
        this.initialImage = initialImage;
    }

    @Override
    protected BinaryImage getValueCopyOf(BinaryImage data) {
        return data.getCopy();
    }

    @Override
    protected BinaryImage getInitialPoint() {
        return getValueCopyOf(initialImage);
    }

    @Override
    protected double getEnergy(BinaryImage argument) {
        return argument.getEnergy();
    }
}

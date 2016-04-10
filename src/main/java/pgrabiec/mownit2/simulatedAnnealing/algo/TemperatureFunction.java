package pgrabiec.mownit2.simulatedAnnealing.algo;

import pgrabiec.mownit2.simulatedAnnealing.algo.temperatureFunctions.LinearTemperatureDrop;

public abstract class TemperatureFunction {
    protected final double ENERGY_MIN;
    protected final double ENERGY_MAX;
    protected final double ARGUMENT_STEP;

    protected double argument;
    protected double energy;

    public TemperatureFunction(double ENERGY_MIN, double ENERGY_MAX, double argumentStep) {
        this.ENERGY_MIN = ENERGY_MIN;
        this.ENERGY_MAX = ENERGY_MAX;

        ARGUMENT_STEP = argumentStep;

        init();
    }

    public void init() {
        energy = ENERGY_MAX;
        argument = 0.0;
    }

    public boolean reachedLimit() {
        return energy <= ENERGY_MIN;
    }

    public double nextEnergy() {
        double result = getValue(argument, ENERGY_MAX, ENERGY_MIN);

        argument += ARGUMENT_STEP;
        energy = result;

        return result;
    }

    protected abstract double getValue(double argument, double ENERGY_MAX, double ENERGY_MIN);

    public static TemperatureFunction getDefaultLinear() {
        return new LinearTemperatureDrop(1e5, 1e10, 1e-2, 1e-1);
    }

    public abstract TemperatureFunction clone();
}

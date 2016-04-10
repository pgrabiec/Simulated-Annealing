package pgrabiec.mownit2.simulatedAnnealing.algo.temperatureFunctions;

import pgrabiec.mownit2.simulatedAnnealing.algo.TemperatureFunction;

public class LinearTemperatureDrop extends TemperatureFunction {
    private final double LINEAR_COEFFICIENT;

    public LinearTemperatureDrop(double ENERGY_MIN, double ENERGY_MAX, double argumentStep, double linearCoefficient) {
        super(ENERGY_MIN, ENERGY_MAX, argumentStep);

        LINEAR_COEFFICIENT = -1.0 * Math.abs(linearCoefficient);
    }

    @Override
    protected double getValue(double argument, double ENERGY_MAX, double ENERGY_MIN) {
        return ENERGY_MAX + LINEAR_COEFFICIENT * argument;
    }

    @Override
    public TemperatureFunction clone() {
        return new LinearTemperatureDrop(
                ENERGY_MIN,
                ENERGY_MAX,
                ARGUMENT_STEP,
                LINEAR_COEFFICIENT
        );
    }
}

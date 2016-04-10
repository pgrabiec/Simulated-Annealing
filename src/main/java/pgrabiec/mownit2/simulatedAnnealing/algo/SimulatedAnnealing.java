package pgrabiec.mownit2.simulatedAnnealing.algo;

/**
 * type T - type of domain for the function to be minimized
 * */
public abstract class SimulatedAnnealing<T> {
    protected final long ITERATIONS_LIMIT;
    protected final double RANDOM_COEFFICIENT;
    protected final TemperatureFunction energyFunction;
    protected final String TITLE;

    public SimulatedAnnealing(long iterationsLimit, double randomCoefficient, TemperatureFunction energyFunction, String title) {
        ITERATIONS_LIMIT = iterationsLimit;
        TITLE = title;
        if (randomCoefficient <= 0.0) {
            throw new IllegalArgumentException("random coefficient must be positive");
        }

        this.RANDOM_COEFFICIENT = randomCoefficient;
        this.energyFunction = energyFunction;
    }

    public T solve() {
        T initialPoint = getInitialPoint();

        T result = executeSolver(initialPoint);

        System.out.println("Solved " + TITLE + "\tEnergy=" + getEnergy(result));

        return result;
    }

    private T executeSolver(T initialPoint) {
        T currentPoint = initialPoint;
        T consideredPoint;

        double currentEnergy = getEnergy(currentPoint);
        double consideredEnergy;

        double minimalEnergy = currentEnergy;
        T minimalEnergyPoint = currentPoint;

        optimalChanged(minimalEnergyPoint, minimalEnergy, 0);

        double currentTemperature;

        long iterations = 0;
        while (!energyFunction.reachedLimit()) {
            iterations++;
            if (iterations >= ITERATIONS_LIMIT) {
                return currentPoint;
            }

            if (!energyFunction.reachedLimit()) {
                currentTemperature = energyFunction.nextEnergy();
            } else {
                currentTemperature = 1e-3;
            }

            consideredPoint = getNeighbour(currentPoint);
            consideredEnergy = getEnergy(consideredPoint);

            if (consideredEnergy < minimalEnergy) {
                minimalEnergy = consideredEnergy;
                minimalEnergyPoint = getValueCopyOf(consideredPoint);

                optimalChanged(minimalEnergyPoint, minimalEnergy, iterations);
            }

            if (accept(currentEnergy, consideredEnergy, currentTemperature)) {
                currentPoint = consideredPoint;
                currentEnergy = consideredEnergy;

                accepted(currentPoint, currentEnergy);
            }
        }

        return minimalEnergyPoint;
    }

    private boolean accept(double currentValue, double consideredValue, double currentEnergy) {
        if (consideredValue < currentValue) {
            return true;
        }

        return randomAccept(currentValue, consideredValue, currentEnergy);
    }

    private boolean randomAccept(double currentValue, double consideredValue, double currentTemperature) {
        double probability = Math.exp(-1.0 * (Math.abs(consideredValue - currentValue) / (currentTemperature * RANDOM_COEFFICIENT)));
        double rand = Math.random();

        return probability >= rand;
    }

    /**
     * Additional loop condition
     *
     * @return  true when the search must be continued regardless of the energy state
     *          false otherwise
     *
    protected boolean proceed(double currentEnergy, T currentPoint) {
        return false;
    }
    */

    protected abstract void optimalChanged(T current, double currentValue, long iterations);
    protected abstract void accepted(T point, double energy);

    protected abstract T getValueCopyOf(T data);
    protected abstract T getInitialPoint();
    protected abstract T getNeighbour(T currentPoint);
    protected abstract double getEnergy(T argument);
}

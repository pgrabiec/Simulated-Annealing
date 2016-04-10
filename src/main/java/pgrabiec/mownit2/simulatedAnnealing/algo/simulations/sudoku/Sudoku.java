package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Sudoku {
    // Main array of Sudoku values
    private final int [][] S;

    // Mask indicating whether the position on the Sudoku was initially specified or not
    private final boolean[][] initialFillMask;

    // Variable for remembering whether a value has occurred in row, column or square
    private final boolean[] used = new boolean[10];

    // List of points identifying squares inside which values can be swapped
    private final List<Point> squaresToFill;

    /*  Key:     point identifying the square in which swaps can be performed
     *  Value:   array of points in that square that can be swapped
     * */
    private final Map<Point, Point[]> squarePointsToSwap;


    /** @param initialValues provides information about input Sudoku.
     *                       value of 0 indicates an empty field
     *                       value between 1 and 9 indicates specified value
     * */
    public Sudoku(int[][] initialValues) {
        squarePointsToSwap = new HashMap<Point, Point[]>(9);
        squaresToFill = new ArrayList<Point>(9);
        initialFillMask = new boolean[9][9];
        S = new int[9][9];

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                int value = initialValues[i][j];
                if (value < 0 || value > 9) {
                    throw new IllegalArgumentException("Sudoku cannot contain value: " + value);
                }

                S[i][j] = value;
                if (value != 0) {
                    initialFillMask[i][j] = true;
                } else {
                    initialFillMask[i][j] = false;
                }
            }
        }

        fillSquaresInitially();

        initToFillList();
    }

    private Sudoku(int[][] S, boolean[][] initialFillMask, List<Point> squaresToFill, Map<Point, Point[]> squarePointsToSwap) {
        this.S = S;
        this.initialFillMask = initialFillMask;
        this.squaresToFill = squaresToFill;
        this.squarePointsToSwap = squarePointsToSwap;
    }

    private void fillSquaresInitially() {
        List<Integer> remainingValues;
        for (Point square : getAllSquares()) {
            remainingValues = getSquareRemainingValues(square);

            for (int i=square.x; i<square.x+3; i++) {
                for (int j=square.y; j<square.y+3; j++) {
                    if (!initialFillMask[i][j]) {
                        S[i][j] = remainingValues.remove(0);
                    }
                }
            }
        }
    }

    /** Calculates and sets squaresToFill and squarePointsToSwap
     * */
    private void initToFillList() {
        List<Point> swapPoints;
        for (Point square : getAllSquares()) {
            swapPoints = getSquareSwappingPoints(square);

            if (swapPoints.size() > 1) {
                squaresToFill.add(square);

                Point[] points = new Point[swapPoints.size()];

                for (int i=0; i<swapPoints.size(); i++) {
                    points[i] = swapPoints.get(i);
                }

                squarePointsToSwap.put(square, points);
            }
        }
    }

    public double getEnergy() {
        int energy = 0;

        int[] used = new int[10];

        // Check by row
        for (int row=0; row<9; row++) {
            Arrays.fill(used, 0);
            for (int column=0; column<9; column++) {
                int num = S[row][column];
                used[num]++;
            }

            for (int i=1; i<=9; i++) {
                if (used[i] > 1) {
                    energy += used[i] - 1;
                }
            }
        }

        // Check by column
        for (int column=0; column<9; column++) {
            Arrays.fill(used, 0);

            for (int row=0; row<9; row++) {
                int num = S[row][column];

                used[num]++;
            }

            for (int i=1; i<=9; i++) {
                if (used[i] > 1) {
                    energy += used[i] - 1;
                }
            }
        }

        return energy;
    }

    public void setValue(int row, int column, int value) {
        if (initialFillMask[row][column]) {
            throw new IllegalArgumentException("Attempt to swap initial values");
        }

        S[row][column] = value;
    }

    public int getValue(int row, int column) {
        return S[row][column];
    }

    public void print() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("| ");
                }
                System.out.print(S[row][column] + " ");
            }
            if ((row+1) % 3 == 0 && row != 0) {
                System.out.println("\n----------------------");
            } else {
                System.out.println();
            }
        }
    }

    private void initUsed() {
        for (int i=1; i<used.length; i++) {
            used[i] = false;
        }
    }

    public Sudoku getValueCopy() {
        int[][] sCopy = new int[9][9];
        for (int i=0; i<9; i++) {
            System.arraycopy(S[i], 0, sCopy[i], 0, 9);
        }

        boolean[][] initialFillMaskCopy = initialFillMask;
        List<Point> squaresToFillCopy = squaresToFill;
        Map<Point, Point[]> squarePointsToSwapCopy = squarePointsToSwap;

        return new Sudoku(
                sCopy,
                initialFillMaskCopy,
                squaresToFillCopy,
                squarePointsToSwapCopy
        );
    }

    public Sudoku getNeighbour() {
        Point[] toSwap = getTwoFieldsToSwap();

        Point p1 = toSwap[0];
        Point p2 = toSwap[1];

        Sudoku neighbour = this.getValueCopy();

        int tmp = neighbour.getValue(p1.x, p1.y);

        neighbour.setValue(
                p1.x,
                p1.y,
                neighbour.getValue(p2.x, p2.y)
        );

        neighbour.setValue(p2.x, p2.y, tmp);

        return neighbour;
    }

    private List<Integer> getSquareRemainingValues(Point squarePoint) {
        int squareRowStart = squarePoint.x;
        int squareColumnStart = squarePoint.y;

        List<Integer> result = new ArrayList<Integer>(9);

        initUsed();
        for (int row = squareRowStart; row < squareRowStart+3; row++) {
            for (int column = squareColumnStart; column < squareColumnStart + 3; column++) {
                int value = S[row][column];
                used[value] = value != 0;
            }
        }

        for (int i = 1; i <= 9; i++) {
            if (!used[i]) {
                result.add(i);
            }
        }

        return result;
    }

    private List<Point> getSquareSwappingPoints(Point squarePoint) {
        int squareRowStart = squarePoint.x;
        int squareColumnStart = squarePoint.y;

        List<Point> result = new ArrayList<Point>(9);

        for (int row = squareRowStart; row < squareRowStart+3; row++) {
            for (int column = squareColumnStart; column < squareColumnStart + 3; column++) {
                if (!initialFillMask[row][column]) {
                    result.add(new Point(row, column));
                }
            }
        }

        return result;
    }

    private Point[] getTwoFieldsToSwap() {
        if (squaresToFill.size() < 1) {
            if (getEnergy() == 0) {
                System.out.println("Solved in 1 iteration");
                print();
                System.exit(0);
            }
            throw new IllegalStateException("No squares to fill");
        }

        int randSquareIndex = (int) (Math.random() * squaresToFill.size()) % squaresToFill.size();
        Point randSquare = squaresToFill.get(randSquareIndex);

        Point[] swappingPoints = squarePointsToSwap.get(randSquare);

        return getPointsToSwapInSquare(swappingPoints);
    }

    private Point[] getPointsToSwapInSquare(Point[] swappingPoints) {
        if (swappingPoints.length == 2) {
            return swappingPoints;
        }

        double maxEnergy1, maxEnergy2;
        Point maxPoint1, maxPoint2;

        maxEnergy1 = maxEnergy2 = -1e10;
        maxPoint1 = maxPoint2 = null;

        double currPointEnergy;
        for (Point p : swappingPoints) {
            currPointEnergy = getFieldEnergy(p);

            if (currPointEnergy > maxEnergy1) {
                maxEnergy2 = maxEnergy1;
                maxEnergy1 = currPointEnergy;
                maxPoint2 = maxPoint1;
                maxPoint1 = p;
            } else if (currPointEnergy > maxEnergy2) {
                maxEnergy2 = currPointEnergy;
                maxPoint2 = p;
            }
        }

        return new Point[] {
                maxPoint1,
                maxPoint2
        };
    }

    private double getFieldEnergy(Point p) {
        double energy = 0.0;

        int pVal = S[p.x][p.y];

        for (int row = 0; row < 9; row++) {
            int column = p.y;
            if (row != p.x) {
                int val = S[row][column];
                if (val == pVal) {
                    energy += 1.0;
                }
            }
        }


        for (int column = 0; column < 9; column++) {
            int row = p.x;
            if (column != p.y) {
                int val = S[row][column];
                if (val == pVal) {
                    energy += 1.0;
                }
            }
        }

        return energy;
    }

    private List<Point> getAllSquares() {
        List<Point> result = new ArrayList<Point>(9);

        for (int i=0; i<9; i+=3) {
            for (int j=0; j<9; j+=3) {
                result.add(new Point(i, j));
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Sudoku)) {
            return false;
        }

        int[][] s2 =  ((Sudoku) obj).S;

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                if (s2[i][j] != S[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean[][] getInitialFillMask() {
        return initialFillMask;
    }
}

package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage;

import java.awt.*;

public class BinaryImage {
    private final int width;
    private final int height;
    private final double density;

    /**
     * pixels[x][y] == true -> black | == false -> white
     * */
    private final boolean[][] pixels;

    private BinaryImage(double density, boolean[][] pixels) {
        this.width = pixels.length;
        this.height = pixels[0].length;
        this.density = density;

        this.pixels = pixels;
    }

    public BinaryImage(int width, int height, double density) {
        this.width = width;
        this.height = height;
        this.density = density;

        pixels = new boolean[width][height];

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                pixels[i][j] = false;
            }
        }

        density = Math.abs(density);
        if (density > 1.0) {
            density = 1.0;
        }

        int size = (int) ((width * height) * density);

        initRandom(size);
    }

    private void initRandom(int size) {
        int x, y;
        for (int i=0; i<size; i++) {
            x = (int) (Math.random() * width) % width;
            y = (int) (Math.random() * height) % height;
            while (pixels[x][y]) {
                x = (int) (Math.random() * width) % width;
                y = (int) (Math.random() * height) % height;
            }

            pixels[x][y] = true;
        }
    }

    public double getEnergy() {
        /*
        double result = 0.0;

        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                if (pixels[x][y]) {
                    result += x + y;
                }
            }
        }
        */

        double energy = 0.0;

        int range = 1;

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                for (int x1 = i-range; x1 <= i+range; x1++) {
                    for (int y1 = j-range; y1 <= j+range; y1++) {
                        if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height) {
                            if (x1 != i || y1 != j) {
                                boolean p1 = pixels[i][j];
                                boolean p2 = pixels[x1][y1];

                                if (p1 == p2) {
                                    int dx = Math.abs(i-x1);
                                    int dy = Math.abs(j-y1);
                                    energy += Math.sqrt(dx*dx + dy*dy);
                                }
                            }
                        }
                    }
                }
            }
        }

        return energy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void swapPixels(Point p1, Point p2) {
        boolean tmp = pixels[p1.x][p1.y];
        pixels[p1.x][p1.y] = pixels[p2.x][p2.y];
        pixels[p2.x][p2.y] = tmp;
    }

    public BinaryImage getCopy() {
        boolean[][] copy = new boolean[width][height];

        for (int i=0; i<width; i++) {
            System.arraycopy(pixels[i], 0, copy[i], 0, height);
        }

        return new BinaryImage(this.density, copy);
    }

    public boolean[][] getPixels() {
        return pixels;
    }
}

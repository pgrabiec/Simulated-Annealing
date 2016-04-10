package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.visualization;

import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.BinaryImage;

import javax.swing.*;

public class BinaryImageVisualization extends JFrame {
    private final PixelDrawPanel pixelPanel = new PixelDrawPanel(this, null);

    public BinaryImageVisualization(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setContentPane(pixelPanel);

        setSize(pixelPanel.getSize());

        setVisible(true);
    }

    public void updateImage(BinaryImage image) {
        pixelPanel.updateImage(image);
    }
}

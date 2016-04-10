package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.visualization;


import pgrabiec.mownit2.simulatedAnnealing.algo.simulations.binaryImage.BinaryImage;

import javax.swing.*;
import java.awt.*;

public class PixelDrawPanel extends JPanel {
    private final JFrame frame;
    private final int enlargement = 6;

    private int width = 0;
    private int height = 0;
    private BinaryImage image;

    public PixelDrawPanel(JFrame frame, BinaryImage initialImage) {
        this.frame = frame;
        image = initialImage;
        adjustSize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.setColor(Color.black);

            for (int x=0; x<image.getWidth(); x++) {
                for (int y=0; y<image.getHeight(); y++) {
                    if (image.getPixels()[x][y]) {
                        g.fillRect(x * enlargement, y * enlargement, enlargement - 1, enlargement - 1);
                    }
                }
            }

            g.setColor(Color.YELLOW);

            for (int x=0; x<image.getWidth(); x++) {
                for (int y=0; y<image.getHeight(); y++) {
                    if (!image.getPixels()[x][y]) {
                        g.fillRect(x * enlargement, y * enlargement, enlargement - 1, enlargement - 1);
                    }
                }
            }
        }
    }

    public void updateImage(BinaryImage image) {
        this.image = image;
        adjustSize();
        repaint();
    }

    private void adjustSize() {
        if (image == null) {
            setSize(width, height);
            return;
        }

        boolean changed = false;
        if (image.getWidth() * enlargement > width) {
            changed = true;
            width = image.getWidth() * enlargement;
        }
        if (image.getHeight() * enlargement > height) {
            changed = true;
            height = image.getHeight() * enlargement;
        }

        if (changed) {
            setSize(new Dimension(width, height));
            updateParent();
        }
    }

    private void updateParent() {
        frame.setSize(new Dimension(width + 18, height + 45));
    }
}

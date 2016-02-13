package hr.fer.zemris.optjava.anttrail.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.chart.Chart;
import hr.fer.zemris.optjava.anttrail.chart.Tile;

@SuppressWarnings("serial")
public class TileComponent extends JComponent {

    private static Image tileImage;
    private static Image antImage;
    private static Image foodImage;

    private Chart        chart;
    private Ant          ant;
    private int          width;
    private int          height;

    static {
        try {
            tileImage = ImageIO.read(new File("res/ground.png"));
            antImage = ImageIO.read(new File("res/ant.png"));
            foodImage = ImageIO.read(new File("res/crumb.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public TileComponent(Chart chart, Ant ant) {
        this.chart = chart;
        this.ant = ant;
        this.width = chart.getColsCount();
        this.height = chart.getRowsCount();
        setPreferredSize(new Dimension(width * 16, height * 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int antX = ant.getPos().x;
        int antY = ant.getPos().y;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                g.drawImage(tileImage, i * 16, j * 16, this);
                if (chart.get(i, j) == Tile.FOOD) {
                    g.drawImage(foodImage, i * 16, j * 16, this);
                }
            }
        }
        Graphics2D g2d = (Graphics2D) g.create();
        int dir = ant.getDirection();
        int tx = antX * 16 + 8;
        int ty = antY * 16 + 8;
        g2d.rotate(Math.PI / 2 * dir, tx, ty);
        g2d.drawImage(antImage, antX * 16, antY * 16, this);
    }

}

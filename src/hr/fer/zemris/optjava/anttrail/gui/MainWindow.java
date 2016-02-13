package hr.fer.zemris.optjava.anttrail.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import hr.fer.zemris.optjava.anttrail.ant.Ant;
import hr.fer.zemris.optjava.anttrail.ant.AntChangeObserver;
import hr.fer.zemris.optjava.anttrail.chart.Chart;
import hr.fer.zemris.optjava.anttrail.chart.ChartObserver;
import hr.fer.zemris.optjava.anttrail.chart.Tile;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements AntChangeObserver, ChartObserver {

    public MainWindow(Chart chart, Ant ant) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        initTileComponent(chart, ant);
        initButtons();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Ant Trail Genetic Algorithm");
        pack();
        setVisible(true);
    }

    private void initButtons() {
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(200, 40));
        buttonWrapper.add(okButton);
        buttonWrapper.add(Box.createRigidArea(new Dimension(4, 0)));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(200, 40));
        buttonWrapper.add(cancelButton);
        add(buttonWrapper, BorderLayout.SOUTH);
    }

    private void initTileComponent(Chart chart, Ant ant) {
        TileComponent tile = new TileComponent(chart, ant);
        JPanel tileWrapper = new JPanel(new GridBagLayout());
        tileWrapper.add(tile);
        JScrollPane scrollPane = new JScrollPane(tileWrapper);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void onAntChanged(Ant ant) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    @Override
    public void onChartChanged(Chart chart, int i, int j, Tile oldTile, Tile newTile) {
//        repaint();
    }

}

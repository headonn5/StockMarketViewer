package stock_ui;

import stock_model.StockModel;
import stock_utils.StockWatchType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/** A Swing Panel to display a list of labelled choices of stock type at one side and
 *  a graph plot on the other side of split pane.
 *
 *  @author Nishant Paul
 */

public class SplitPanel extends JPanel implements ListSelectionListener {

    private JSplitPane splitPane;
    private PlotPanel plotPanel;
    private JList<String> list;
    private String[] plotNames;
    private static final StockWatchType DEFAULT_STOCK_WATCH_TYPE = StockWatchType.CLOSING_PRICE;

    public SplitPanel(ArrayList<StockModel> stocks) {

        int itr = 0;
        plotNames = new String[StockWatchType.values().length];
        for (StockWatchType value: StockWatchType.values()) {
            plotNames[itr++] = value.toString();
        }

        // Left pane list
        list = new JList<>(plotNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listPane = new JScrollPane(list);

        // Right pane panel
        plotPanel = new PlotPanel(stocks);
        plotPanel.setVariablesForStock(DEFAULT_STOCK_WATCH_TYPE);

        // Set the split pane with a horizontal split
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPane, plotPanel);
        splitPane.setDividerLocation(150);
    }

    // Getter for splitPane
    public JSplitPane getSplitPane() {
        return splitPane;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // Redraw the plot panel with the current selection of stock type
            JList list = (JList)e.getSource();
            plotPanel.setVariablesForStock(StockWatchType.valueOf(plotNames[list.getSelectedIndex()]));
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** This frame contains a split panel to show options of the type of graph on one side,
 *  and the graph on the other side of the split pane.
 *
 *  @author Nishant Paul
 */

public class PlotFrame extends JFrame {

    public PlotFrame(ArrayList<StockModel> stocks) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Set size of the frame
        setSize(2*(int)width/3, 2*(int)height/3);

        // Add the plot panel to the frame
        SplitPanel splitPanel = new SplitPanel(stocks);
        getContentPane().add(splitPanel.getSplitPane(), BorderLayout.CENTER);

        // Set title of the frame
        setTitle("Market Watcher Graph");
    }
}

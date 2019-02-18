package stock_ui;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

/** The class that represents the first frame being shown to the user when this program runs.
 *
 *  @author Nishant Paul
 */

public class MarketWatcherFrame extends JFrame {

    public MarketWatcherFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setBounds((int) width / 4, (int) height / 4, (int) width / 2, (int) height / 2);

        // Add the basic panel to the main frame
        BasicPanel basicPanel = new BasicPanel();
        this.getContentPane().add(basicPanel, BorderLayout.CENTER);

        setTitle("Market Watcher");
    }
}

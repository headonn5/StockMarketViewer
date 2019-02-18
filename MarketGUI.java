/** The main class that loads the start-up UI for the user.
 *
 *  @author Nishant Paul
 */

public class MarketGUI {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MarketWatcherFrame marketWatcherFrame = new MarketWatcherFrame();
                Utility.displayGUI(marketWatcherFrame);
            }
        });
    }
}

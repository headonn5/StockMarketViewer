package stock_ui;

import javax.swing.*;
import java.awt.*;

/** A Swing Panel that contains a combo box for the specified list of string values.
 *
 *  @author Nishant Paul
 */

public class TickerPanel extends JPanel {

    // Ticker symbols for the stocks
    private String[] tickerSymbols = new String[]{ "AAPL", "GOOG", "FB", "IBM", "INFY", "MSFT", "TWTR" };
    private JComboBox<String> tickerBox;

    public TickerPanel() {
        // Ticker Combo Box
        tickerBox = new JComboBox<>(tickerSymbols);
        tickerBox.setSelectedIndex(0);

        GridLayout gridLayout = new GridLayout(1, 6);
        setLayout(gridLayout);

        // Add 6 columns of data to fill one row (6 columns) of grid layout
        JLabel tickerLabel = new JLabel("Select Ticker");
        add(new JLabel(" "));
        add(tickerLabel);
        add(tickerBox);
        add(new JLabel(" "));
        add(new JLabel(" "));
        add(new JLabel(" "));
    }

    // Getter for tickerBox
    public JComboBox<String> getTickerBox() {
        return tickerBox;
    }

}

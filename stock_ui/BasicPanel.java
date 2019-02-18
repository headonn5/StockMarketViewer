package stock_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import stock_model.StockModel;
import stock_network.NetworkRequest;
import stock_utils.*;


/** Represents the foundation panel class which shows the basic UI to the user.
 *  In this panel, a ticker sub-panel, a start-date sub-panel, and an end-date sub-panel is added
 *  along with a button to forward the user's request and some labels for the UX purpose.
 *  This class also sends the user's response to the stock_network.NetworkRequest class for further processing.
 *
 *  @author Nishant Paul
 */

public class BasicPanel extends JPanel {

    private TickerPanel tickerPanel;
    private StartDatePanel startDatePanel;
    private EndDatePanel endDatePanel;
    private SubmitPanel submitPanel;
    private PlotFrame plotFrame;

    public BasicPanel() {
        // Initialize and set Grid Layout to the panel
        GridLayout gridLayout = new GridLayout(7, 1, 5,5);
        setLayout(gridLayout);

        // 1st row
        tickerPanel = new TickerPanel();
        add(tickerPanel);

        // 2nd row
        add(new JSeparator());

        // 3rd row
        startDatePanel = new StartDatePanel();
        add(startDatePanel);

        // 4th row
        add(new JSeparator());

        // 5th row
        endDatePanel = new EndDatePanel();
        add(endDatePanel);

        // 6th row
        add(new JSeparator());

        // 7th row
        submitPanel = new SubmitPanel();
        add(submitPanel);
    }

    /**
     * Create a network request based on the user-selected parameters and plot the graph based on the API response
     */
    private void submitButtonClicked() {
        String selectedStockName = (String) tickerPanel.getTickerBox().getSelectedItem();
        String selectedStartDate = (startDatePanel.getStartMonthBox().getSelectedItem() + "/"
                + startDatePanel.getStartDayBox().getSelectedItem() + "/"
                + startDatePanel.getStartYearBox().getSelectedItem());
        String selectedEndDate = (endDatePanel.getEndMonthBox().getSelectedItem() + "/"
                + endDatePanel.getEndDayBox().getSelectedItem() + "/"
                + endDatePanel.getEndYearBox().getSelectedItem());

        InputStreamReader responseStream = null;
        try {
            // Create a network request
            responseStream = NetworkRequest.getRequest(selectedStockName, queryBuilder(selectedStartDate,
                    selectedEndDate));

            // Fetch the parsed response from the API
            ArrayList<StockModel> stockList = ResponseParser.parse(responseStream);

            // Show Graph plot for stocks
            showPlots(stockList);
        }
        catch (Exception e) {
            e.printStackTrace();
            // Show an Error Alert Dialog with an appropriate message
            showAlertDialog(e.getMessage());
        }
    }

    /**
     * Plot the graph in the new Frame window
     * @param stocks The list of stocks parsed from the API response
     */
    private void showPlots(ArrayList<StockModel> stocks) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (plotFrame != null) {
                    plotFrame.dispatchEvent(new WindowEvent(plotFrame, WindowEvent.WINDOW_CLOSING));
                }
                plotFrame = new PlotFrame(stocks);
                Utility.displayGUI(plotFrame);
            }
        });
    }

    private void showAlertDialog(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Builds a dictionary type object containing information to be sent in the url
     * @param startDate Pass the start date parameter input by the user
     * @param endDate Pass the end date parameter input by the user
     * @return A Dictionary/Mapped object containing necessary information to be sent to the API
     */
    private HashMap<String, Object> queryBuilder(String startDate, String endDate) {
        HashMap<String,Object> query = new HashMap<String, Object>();
        query.put("MOD_VIEW", "page");
        query.put("num_rows", 300);
        query.put("startDate", startDate);
        query.put("endDate", endDate);

        return query;
    }


    /**
     * A private class for submit panel consisting of a submit button which passes the information to the container
     * class when the submit button is clicked
     */
    private class SubmitPanel extends JPanel implements ActionListener {
        private JButton submitButton;

        public SubmitPanel() {

            GridLayout gridLayout = new GridLayout(3, 5);
            setLayout(gridLayout);

            submitButton = new JButton("Submit");
            submitButton.addActionListener(this);

            // Add 14 empty labels to fill 14 out of 15 cells of grid layout and
            // place submit button at the centre of the layout
            addEmptyLabels(2);
            add(submitButton);
            addEmptyLabels(10);
        }

        private void addEmptyLabels(int count) {
            for (int i=0; i<count; i++) {
                add(new JLabel(" "));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == "Submit") {
                submitButtonClicked();
            }
        }
    }
}

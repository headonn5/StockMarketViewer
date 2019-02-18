package stock_ui;

import stock_utils.Utility;

import javax.swing.*;
import java.awt.*;

/** A panel class that includes elements such as labels and combo box from the parent "stock_ui.DatesPanel" class
 *
 *  @author Nishant Paul
 */

public class StartDatePanel extends DatesPanel {

    private JComboBox<String> startDayBox;
    private JComboBox<String> startMonthBox;
    private JComboBox<String> startYearBox;

    public StartDatePanel() {

        GridLayout gridLayout = new GridLayout(2, 6);
        setLayout(gridLayout);

        startDayBox = getDayBox();
        startMonthBox = getMonthBox();
        startYearBox = getYearBox();
        JLabel startDateLabel = new JLabel("Select Start Date");

        // Add labels for boxes in the first row
        // Add startDateLabel and date selection boxes in the second row
        Utility.addEmptyLabels(this, 2);
        add(new JLabel("Day", SwingConstants.CENTER));
        add(new JLabel("Month", SwingConstants.CENTER));
        add(new JLabel("Year", SwingConstants.CENTER));
        Utility.addEmptyLabels(this, 2);
        add(startDateLabel);
        add(startDayBox);
        add(startMonthBox);
        add(startYearBox);
        Utility.addEmptyLabels(this, 1);
    }

    // Getter for startDayBox
    public JComboBox<String> getStartDayBox() {
        return startDayBox;
    }

    // Getter for startMonthBox
    public JComboBox<String> getStartMonthBox() {
        return startMonthBox;
    }

    // Getter for startYearBox
    public JComboBox<String> getStartYearBox() {
        return startYearBox;
    }
}

import javax.swing.*;
import java.awt.*;

/** A panel class that includes elements such as labels and combo box from the parent "DatesPanel" class
 *
 *  @author Nishant Paul
 */

public class EndDatePanel extends DatesPanel {

    private JComboBox<String> endDayBox;
    private JComboBox<String> endMonthBox;
    private JComboBox<String> endYearBox;

    public EndDatePanel() {

        GridLayout gridLayout = new GridLayout(2, 6);
        setLayout(gridLayout);

        endDayBox = getDayBox();
        endMonthBox = getMonthBox();
        endYearBox = getYearBox();
        JLabel endDateLabel = new JLabel("Select End Date");

        // Add labels for boxes in the first row
        // Add endDateLabel and date selection boxes in the second row
        Utility.addEmptyLabels(this, 2);
        add(new JLabel("Day", SwingConstants.CENTER));
        add(new JLabel("Month", SwingConstants.CENTER));
        add(new JLabel("Year", SwingConstants.CENTER));
        Utility.addEmptyLabels(this, 2);
        add(endDateLabel);
        add(endDayBox);
        add(endMonthBox);
        add(endYearBox);
        Utility.addEmptyLabels(this, 1);
    }

    // Getter for endDayBox
    public JComboBox<String> getEndDayBox() {
        return endDayBox;
    }

    // Getter for endMonthBox
    public JComboBox<String> getEndMonthBox() {
        return endMonthBox;
    }

    // Getter for endYearBox
    public JComboBox<String> getEndYearBox() {
        return endYearBox;
    }

}

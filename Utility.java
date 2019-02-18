import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/** A general utility container containing common methods to be used interchangeably for many classes.
 *
 *  @author Nishant Paul
 */

public enum Utility {;

    /**
     * @param panel Panel object
     * @param count number of empty labels to be added to the panel
     */
    public static void addEmptyLabels(JPanel panel, int count) {
        for (int i=0; i<count; i++) {
            panel.add(new JLabel(" "));
        }
    }

    /**
     * @param frame Frame to be presented at the top of the navigation stack
     */
    public static void displayGUI(JFrame frame) {
        if (frame != null && !frame.isVisible()) {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    /**
     * @param yValue Decimal value label
     * @param increment Increment value applied to yValue
     * @return String representation of the appropriate decimal number
     */
    public static String getDecimalString(double yValue, double increment) {
        // If increment is too small, ensure that the string label is displayed in 3 digit decimal
        // instead of 2 digit decimal to show the difference between continuous yLabels
        if (increment < 0.01) {
            return String.format("%.3f", yValue);
        }
        else if (increment > 1000) {
            return String.format("%.0f", yValue);
        }
        return String.format("%.2f", yValue);
    }

    /**
     * @param date Date object
     * @return String representation of date object split into 2 string objects
     */
    public static String[] getDateLabel(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d yyyy");
        String label = simpleDateFormat.format(date);
        // Split date label into month-day and year to draw label in 2 lines
        String label1 = label.substring(0, label.length() - 5);
        String label2 = label.substring(label.length() - 4);
        String[] labels = {label1, label2};
        return labels;
    }

}

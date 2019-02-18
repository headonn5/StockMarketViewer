package stock_ui;

import javax.swing.*;
import java.util.ArrayList;


/** The parent class for stock_ui.StartDatePanel and stock_ui.EndDatePanel classes that contains the basic layout structure
 *  for the start date and end dates panel.
 *
 *  @author Nishant Paul
 */

public class DatesPanel extends JPanel {

    private JComboBox<String> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<String> yearBox;

    public DatesPanel() {

        // Day Combo Box
        ArrayList<String> days = new ArrayList<>();
        for (int i=1; i<=31; i++) {
            days.add(Integer.toString(i));
        }
        // convert back days arraylist to array of String
        dayBox = new JComboBox<>(days.toArray(new String[days.size()]));
        dayBox.setSelectedIndex(0);

        // Month Combo Box
        ArrayList<String> months = new ArrayList<>();
        for (int i=1; i<=12; i++) {
            months.add(Integer.toString(i));
        }
        // convert back days arraylist to array of String
        monthBox = new JComboBox<>(months.toArray(new String[months.size()]));
        monthBox.setSelectedIndex(0);

        // Year Combo Box
        ArrayList<String> years = new ArrayList<>();
        for (int i=2015; i<=2018; i++) {
            years.add(Integer.toString(i));
        }
        // convert back days arraylist to array of String
        yearBox = new JComboBox<>(years.toArray(new String[years.size()]));
        yearBox.setSelectedIndex(0);

    }

    // Getter for dayBox
    public JComboBox<String> getDayBox() { return dayBox; }

    // Getter for monthBox
    public JComboBox<String> getMonthBox() {
        return monthBox;
    }

    // Getter for yearBox
    public JComboBox<String> getYearBox() {
        return yearBox;
    }
}

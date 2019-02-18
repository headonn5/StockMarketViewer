import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/** This class represents a graph plotting swing panel where all the intricacies
 *  of setting up the xy plane of the graph is handled.
 *  In this class, the graph area is defined, the X and Y axis are drawn, and
 *  the coordinates are scaled accordingly before being plotted based on the user-selection of yLabels.
 *
 *  @author Nishant Paul
 */

public class PlotPanel extends JPanel {

    private ArrayList<StockModel> stocks;
    private int canvasWidth = 0;
    private int canvasHeight = 0;
    private double xScale = 0.0;
    private double yScale = 0.0;
    private float xIncrement = 0;
    private double yIncrement = 0.0;
    private double maxYMark = Double.MAX_VALUE;
    private double minYMark = Double.MIN_VALUE;
    private static final int PADDING = 30;
    private static final int BAR_WIDTH = 5;
    private ArrayList<Double> yLabels;
    private boolean isPanelSet = false;

    /**
     * @param stocks List of stocks that needs to be plotted
     */
    public PlotPanel(ArrayList<StockModel> stocks) {
        this.stocks = stocks;
    }

    /**
     * Fetches the list of yLabels based on the user-selected input
     * @param stockWatchType Type of stock values to be plotted such as "Opening", "Closing", etc.
     * @return List of yLabels that needs to be scaled before plotting
     */
    private ArrayList<Double> getYLabels(StockWatchType stockWatchType) {
        ArrayList<Double> yLabels = new ArrayList<>();

        switch (stockWatchType) {
            case CLOSING_PRICE:
                for (StockModel stock: stocks) {
                    yLabels.add(stock.getClose());
                }
                break;
            case OPENING_PRICE:
                for (StockModel stock: stocks) {
                    yLabels.add(stock.getOpen());
                }
                break;
            case HIGHEST_PRICE:
                for (StockModel stock: stocks) {
                    yLabels.add(stock.getHigh());
                }
                break;
            case LOWEST_PRICE:
                for (StockModel stock: stocks) {
                    yLabels.add(stock.getLow());
                }
                break;
            case STOCK_VOLUME:
                for (StockModel stock: stocks) {
                    yLabels.add(stock.getVolume());
                }
                break;
        }

        return yLabels;
    }

    /**
     * It assigns the necessary variables the required values such as scaled increment of the graph,
     * minimum and maximum value of the X and Y axis, etc. before calling the draw/paint method
     * @param stockWatchType Type of stock values to be plotted such as "Opening", "Closing", etc.
     */
    public void setVariablesForStock(StockWatchType stockWatchType) {

        // Set the boolean to true when the method is called
        isPanelSet = true;

        // Sort the stock list according to date before processing the stock values
        stocks.sort(Comparator.comparing((StockModel o) -> o.getDate()));

        // Set the min y-mark and the max y-mark
        yLabels = getYLabels(stockWatchType);
        maxYMark = Collections.max(yLabels);
        minYMark = Collections.min(yLabels);
        validateMinMaxYValue();

        // Set the y increment level for the bars on y-axis
        yIncrement = (maxYMark - minYMark)/10.0;

        // Set the x increment level for the bars on x-axis
        xIncrement = (float)(yLabels.size()/10.0);

        // Redraw the panel by calling paintComponent method
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the pen color for filling the background of the graph area
        g2.setColor(Color.WHITE);

        // Calculate the width and height of the canvas where the graph would be plotted
        canvasWidth = getWidth() - 4*PADDING;
        canvasHeight = getHeight() - 4*PADDING;

        // Calculate the scale for x and y axis that would help convert stock values to graph coordinates
        xScale = ((double) getWidth() - 4*PADDING) / (stocks.size() + xIncrement);
        yScale = ((double) getHeight() - 4*PADDING) / 11;

        // Color the canvas' background
        g2.fillRect(3*PADDING, 2*PADDING, canvasWidth, canvasHeight);

        // Set the pen color for plotting the graph
        g2.setColor(Color.BLACK);

        // Draw y-axis
        g2.drawLine(3*PADDING, 2*PADDING, 3*PADDING, 2*PADDING + canvasHeight);

        // Draw x-axis
        g2.drawLine(3*PADDING, 2*PADDING + canvasHeight,
                3*PADDING + canvasWidth, 2*PADDING + canvasHeight);

        if (isPanelSet) {
            // Draw labels on x and y axis respectively
            drawXLabels(g2);
            drawYLabels(g2);

            // Finally, plot the graph
            plotCoordinatePoints(g2);
        }
    }

    /**
     * This method converts the values from the stock-list object to the scaled values of X and Y coordinates
     * @param g 2D Graphics object
     */
    private void plotCoordinatePoints(Graphics2D g) {

        double j = xIncrement;

        // If xIncrement is more than 1, this means x axis is labelled by a multiple of more than 1
        // Therefore, increment plot values by 1 aprox.
        double minIncrement = xIncrement/Math.ceil(xIncrement);

        ArrayList<Float> xCoordinates = new ArrayList<>();
        ArrayList<Float> yCoordinates = new ArrayList<>();

        // Store the points to be plotted in the array list
        for (int i=0; i<stocks.size(); i++) {
            double y1 = (2 * PADDING + canvasHeight) - yScale;
            double yValue = y1 - ((yLabels.get(i) - minYMark)  * (yScale / yIncrement));
            double xValue = j * xScale + 3 * PADDING;

            j = j + minIncrement;

            xCoordinates.add((float)xValue);
            yCoordinates.add((float)yValue);
        }

        // Draw the graph lines using the coordinate points' array lists
        drawGraph(g, xCoordinates, yCoordinates);
    }

    /**
     * This method takes the scaled coordinates and calls the draw method of the Graphics2D object
     * @param g 2D Graphics object
     * @param xCoordinates Scaled X coordinates
     * @param yCoordinates Scaled Y coordinates
     */
    private void drawGraph(Graphics2D g, ArrayList<Float> xCoordinates, ArrayList<Float> yCoordinates) {

        // Draw a dot-point if only one coordinate point is available for plotting, otherwise draw the lines
        if (xCoordinates.size() == 1) {
            Shape dot = new Rectangle2D.Float(xCoordinates.get(0), yCoordinates.get(0), 2.0f, 2.0f);
            g.draw(dot);
        }
        else {
            for (int i=0; i<xCoordinates.size()-1; i++) {
                Shape line = new Line2D.Float(xCoordinates.get(i), yCoordinates.get(i),
                        xCoordinates.get(i+1), yCoordinates.get(i+1));
                g.draw(line);
            }
        }
    }

    /**
     * It draws the line for the X-axis and finally type(draw) the equidistant labels on the X-axis
     */
    private void drawXLabels(Graphics2D g) {
        int minIncrement = (int)Math.ceil(stocks.size()/10.0);
        float j = 0;

        // X-axis is plotted differently than Y-axis. It is divided into equidistant labels which are
        // whole numbers (dates), therefore, x-axis is to be incremented in float increments (j) whereas y-axis is
        // incremented in integer increments (due to their float-valued labels) when being multiplied to their
        // respective scales of float values.
        for (int i=0; i<stocks.size(); i=i+minIncrement) {
            j += xIncrement;
            // Start from the left-most position (origin) of the graph
            float x1 = j * (float)xScale + 3 * PADDING;
            float x2 = x1;
            float y1 = 2 * PADDING + canvasHeight;
            float y2 = y1 - BAR_WIDTH;

            // Draw line bars
            Shape line = new Line2D.Float(x1, y1, x2, y2);
            g.draw(line);

            // Split date label into month-day and year to draw label in 2 lines
            String[] labels = Utility.getDateLabel(stocks.get(i).getDate());
            String label1 = labels[0];
            String label2 = labels[1];
            // Finally, draw string labels
            g.drawString(label1, x1 - g.getFontMetrics().stringWidth(label1)/2, y1 + PADDING);
            g.drawString(label2, x1 - g.getFontMetrics().stringWidth(label2)/2, y1 + PADDING + g.getFontMetrics().getHeight());
        }
    }

    /**
     * It draws the line for the Y-axis and finally type(draw) the equidistant labels on the Y-axis
     */
    private void drawYLabels(Graphics2D g) {
        // Divide the y-axis into 11 equal spaces.
        for (int i=0; i<11; i++) {
            float x1 = 3 * PADDING;
            float x2 = x1 + BAR_WIDTH;
            // Start from the lower-most position (origin) of the graph
            float y1 = (float)((2 * PADDING + canvasHeight) - ((i+1) * yScale));
            float y2 = y1;

            // Draw line bars
            double yMark = minYMark + i * yIncrement;
            Shape line = new Line2D.Float(x1, y1, x2, y2);
            g.draw(line);

            // Draw string labels
            String label = Utility.getDecimalString(yMark, yIncrement);
            int labelWidth = g.getFontMetrics().stringWidth(label);
            g.drawString(label, x1 - labelWidth - BAR_WIDTH, y1 + BAR_WIDTH);
        }
    }

    private void validateMinMaxYValue() {
        // If minimum and maximum y values are equal, this indicates only 1 stock object is received by the class
        // or all the stock objects has same y value for corresponding x values
        // Increase the maxYMark by a marginal value from minYMark to have continuous Y Labels on y-axis
        if (minYMark == maxYMark) {
            maxYMark = minYMark + 1;
        }
    }

}

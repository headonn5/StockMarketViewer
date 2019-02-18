package stock_utils;

import stock_model.StockModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/** The sole purpose of this class is to parse the response received from the API into the stock_model.StockModel object.
 *
 *  @author Nishant Paul
 */

public class ResponseParser {

    private static final String SPLITTER = ",";
    private static final String DATE_PATTERN = "MM/dd/yy";
    private static BufferedReader input = null;

    /**
     * @param stream InputStreamReader object received from Network Request
     * @return List of stock_model.StockModel objects
     * @throws Exception Error in parsing data
     */
    public static ArrayList<StockModel> parse(InputStreamReader stream) throws Exception {
        try {
            input = new BufferedReader(stream);
            String inputLine;
            ArrayList<StockModel> stockDataList = new ArrayList<>();

            // Skip the first line of the CSV as it is just a header line
            if (input.readLine() != null) {
                while ((inputLine = input.readLine()) != null) {

                    String[] data = inputLine.split(SPLITTER);
                    Date date;

                    // Format string to date object
                    // Throw parse exception if string is not formatted to date object
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
                        date = dateFormat.parse(data[0]);
                    }
                    catch (ParseException e) {
                        throw new Exception("Unable to parse the date object");
                    }

                    // Initiate a stock_model.StockModel object to store the response data
                    StockModel stockModel = new StockModel(
                            date, Double.parseDouble(data[1]), Double.parseDouble(data[2]),
                            Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));

                    // Add the stock model object to the list of stock data
                    stockDataList.add(stockModel);
                }
            }

            // Throw an error if the stock list is empty
            if (stockDataList.isEmpty()) {
                throw new Exception("Empty Response Received");
            }
            return stockDataList;
        }
        catch (IOException e) {
            throw new Exception("No Response Received");
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

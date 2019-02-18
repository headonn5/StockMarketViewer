package stock_network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/** This class is used for the web request-response operations. Its primary role is to format the BASE_URL
 *  with the information received from the user and build the url for an API request.
 *  It passes the response as a stream of bytes to the calling class.
 *  This also handles the network issues and request failed response and throw an appropriate error to the user.
 *
 *  @author Nishant Paul
 */

public class NetworkRequest {

    private static String BASE_URL = "https://quotes.wsj.com/%s/historical-prices/download?";

    /**
     * Build an API query, send a UTF-8 encoded GET request and receive the corresponding response
     * @param stock Name of the stock for which information is required
     * @param map Dictionary object containing input values of the user such as startDate, num_rows, etc.
     * @return An InputStreamReader object containing bytes of information received from the API request
     * @throws Exception Invalid response received or the network connection failed
     */
    public static InputStreamReader getRequest(String stock, HashMap<String,Object> map) throws Exception {

        URL url = new URL(addQueryParams(stock, map));
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();
            if (responseCode != 200) {
                throw new Exception(String.format("Connection to source was refused with Respnse Code: %d and Response Message: %s", responseCode, responseMessage));
            }
            return new InputStreamReader(urlConnection.getInputStream());
        }
        catch (ConnectException e) {
            throw new Exception(e);
        }
        catch (IOException e) {
            throw new Exception("Failed to connect to the host. Please check your Internet Connection.");
        }

    }

    private static String encodedUrl(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static String addQueryParams(String stockName, Map<String, Object> map) {

        // Add stock name to the base url
        String urlString = String.format(BASE_URL, stockName);

        // Add the query parameters to the base url string
        StringBuilder queryBuilder = new StringBuilder(urlString);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append("&");
            }
            queryBuilder.append(String.format("%s=%s",
                    encodedUrl(entry.getKey()),
                    encodedUrl(entry.getValue().toString())
            ));
        }

        return queryBuilder.toString();
    }
}

import java.util.Date;

/** A Model class to store properties of the Stock.
 *
 *  @author Nishant Paul
 */

public class StockModel {

    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;

    public StockModel(Date date, Double open, Double high, Double low, Double close, Double volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public Date getDate() { return date; }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getClose() { return close; }

    public Double getVolume() {
        return volume;
    }
}

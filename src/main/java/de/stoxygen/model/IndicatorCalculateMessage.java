package de.stoxygen.model;

import java.io.Serializable;
import java.util.Date;

public class IndicatorCalculateMessage implements Serializable {

    private String exchange;
    private String bond;
    private String aggregate;
    private Date timestamp;

    public IndicatorCalculateMessage() {

    }

    public IndicatorCalculateMessage(String exchange, String bond, String aggregate, Date timestamp) {
        this.exchange = exchange;
        this.bond = bond;
        this.aggregate = aggregate;
        this.timestamp = timestamp;
    }

    public String getExchange() {
        return exchange;
    }

    public String getBond() {
        return bond;
    }

    public String getAggregate() {
        return aggregate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "IndicatorCalculateMessage[exchange='" + exchange + "', bond='" + bond + "', aggregate='" + aggregate +
                "', timestamp='" + timestamp + "']";
    }
}

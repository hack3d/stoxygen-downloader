package de.stoxygen.model.aggregate;

import java.io.Serializable;
import java.util.Date;

public class AggregateMessage implements Serializable {

    private String exchange;
    private String bond;
    private AggregateType aggregateType;
    private Date insertTimestamp;

    public AggregateMessage() {}

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public AggregateType getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(AggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    public Date getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(Date insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public String toString() {
        return "AggregateMessage{" +
                "exchange='" + exchange + '\'' +
                ", bond='" + bond + '\'' +
                ", aggregateType=" + aggregateType +
                ", insertTimestamp=" + insertTimestamp +
                '}';
    }
}

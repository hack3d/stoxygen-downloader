package de.stoxygen.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tickdata1Minute extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long tickdata1MinuteId;

    @Column(nullable = false)
    private Float open;

    @Column(nullable = false)
    private Float close;

    @Column(nullable = false)
    private Float bid;

    @Column(nullable = false)
    private Float ask;

    @Column(nullable = false)
    private Float high;

    @Column(nullable = false)
    private Float low;

    @Column(nullable = false)
    private Float volume;

    @Column(nullable = false)
    private Boolean aggregated;

    @ManyToOne
    private Bond bonds;

    @ManyToOne
    private Exchange exchanges;

    public Long getTickdata1MinuteId() {
        return tickdata1MinuteId;
    }

    public Float getOpen() {
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public Float getClose() {
        return close;
    }

    public void setClose(Float close) {
        this.close = close;
    }

    public Float getBid() {
        return bid;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }

    public Float getAsk() {
        return ask;
    }

    public void setAsk(Float ask) {
        this.ask = ask;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Boolean getAggregated() {
        return aggregated;
    }

    public void setAggregated(Boolean aggregated) {
        this.aggregated = aggregated;
    }

    public void addBond(Bond bond) {
        this.bonds = bond;
    }

    public void addExchange(Exchange exchange) {
        this.exchanges = exchange;
    }

    public Tickdata1Minute() {

    }

    // For bitfinex exchange
    public Tickdata1Minute(Float bid, Float ask, Float high, Float low, Float open, Float close, Float volume) {
        this.bid = bid;
        this.ask = ask;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.aggregated = false;
    }

    @Override
    public String toString() {
        String info = String.format("TickdataCurrent[Id: {}, Open: {}, Close: {} Bid: {}, Ask: {}, High: {}, Low: {}, " +
                "Volume: {}, Aggregated: {}, Bond: {}, Exchange: {}", tickdata1MinuteId, open, close, bid, ask, high, low,
                volume, aggregated, bonds, exchanges);
        return info;
    }
}

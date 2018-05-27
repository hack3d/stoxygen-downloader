package de.stoxygen.model;

import javax.persistence.*;

@Entity
public class TickdataCurrent extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long tickdataCurrentsId;

    @Column(nullable = false)
    private Float last;

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

    public Long getTickdataCurrentsId() {
        return tickdataCurrentsId;
    }

    public Float getLast() {
        return last;
    }

    public void setLast(Float last) {
        this.last = last;
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

    // For bitfinex exchange
    public TickdataCurrent(Float bid, Float ask, Float high, Float low, Float last, Long volume) {
        this.bid = bid;
        this.ask = ask;
        this.high = high;
        this.low = low;
        this.last = last;
        this.volume = volume.floatValue();
        this.aggregated = false;
    }

    // For bitstamp exchange
    public TickdataCurrent(Float last, Float volume) {
        this.bid = Float.valueOf(0);
        this.ask = Float.valueOf(0);
        this.high = Float.valueOf(0);
        this.low = Float.valueOf(0);
        this.last = last;
        this.volume = volume;
        this.aggregated = false;
    }

    @Override
    public String toString() {
        String info = String.format("TickdataCurrent[Id: {}, Last: {}, Bid: {}, Ask: {}, High: {}, Low: {}, " +
                "Volume: {}, Aggregated: {}, Bond: {}, Exchange: {}", tickdataCurrentsId, last, bid, ask, high, low,
                volume, aggregated, bonds, exchanges);
        return info;
    }
}

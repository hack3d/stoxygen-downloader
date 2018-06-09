package de.stoxygen.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Exchange extends Auditable<String>{

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer exchangesId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false, length = 4)
    private String countryCode;

    @Column(columnDefinition = "int(2) default 0")
    private Integer intervalDelay;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Bond> bonds = new HashSet<>();

    public Exchange() {
    }

    public Set<Bond> getBonds() {
        return bonds;
    }

    public void addBond(Bond bond) {
        bonds.add(bond);
        bond.getExchanges().add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setIntervalDelay(Integer intervalDelay) {
        this.intervalDelay = intervalDelay;
    }

    public Integer getIntervalDelay() {
        return intervalDelay;
    }

    public Integer getExchangesId() {
        return exchangesId;
    }

    @Override
    public String toString() {
        String info = String.format("Exchange: id = %d, name = %s, symbol = %s, country_code = %s, interval = %s",
                exchangesId, name, symbol, countryCode, intervalDelay);
        return info;
    }

}

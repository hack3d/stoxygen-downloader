package de.stoxygen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Bond extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer bonds_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String isin;

    @Column(nullable = false)
    private Integer state;

    @Column(nullable = false)
    private String cryptoPair;

    @Column(nullable = false)
    private String cryptoBase;

    @Column(nullable = false)
    private String cryptoQuote;

    @ManyToMany(mappedBy = "bonds", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Exchange> exchanges = new HashSet<>();

    public Bond() {
    }

    public Bond(String name, String isin, Integer state, String cryptoBase, String cryptoQuote) {
        this.name = name;
        this.isin = isin;
        this.state = state;
        this.cryptoBase = cryptoBase;
        this.cryptoQuote = cryptoQuote;
        this.cryptoPair = cryptoBase + "" + cryptoQuote;
    }

    public Set<Exchange> getExchanges() {
        return exchanges;
    }

    public Integer getBonds_id() {
        return bonds_id;
    }

    public void setBonds_id(Integer bonds_id) {
        this.bonds_id = bonds_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCryptoPair() {
        return cryptoPair;
    }

    public void setCryptoPair(String cryptoPair) {
        this.cryptoPair = cryptoPair;
    }

    public String getCryptoBase() {
        return cryptoBase;
    }

    public void setCryptoBase(String cryptoBase) {
        this.cryptoBase = cryptoBase;
    }

    public String getCryptoQuote() {
        return cryptoQuote;
    }

    public void setCryptoQuote(String cryptoQuote) {
        this.cryptoQuote = cryptoQuote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bond bond = (Bond) o;
        return Objects.equals(name, bond.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isin, state, cryptoPair, cryptoBase, cryptoQuote);
    }

    @Override
    public String toString() {
        String info = String.format("Bond: id = %d, isin = %s, crypto_pair = %s", bonds_id, isin, cryptoPair);
        return info;
    }
}

package de.stoxygen.model.form;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;

import javax.validation.constraints.NotNull;

public class AddBondItemForm {

    @NotNull
    private int bonds_id;

    @NotNull
    private int exchangesId;

    private Iterable<Bond> bonds;

    private Exchange exchange;

    public AddBondItemForm() { }

    public AddBondItemForm(Iterable<Bond> bonds, Exchange exchange) {
        this.bonds = bonds;
        this.exchange = exchange;
    }

    public int getBonds_id() {
        return bonds_id;
    }

    public void setBonds_id(int bonds_id) {
        this.bonds_id = bonds_id;
    }

    public int getExchangesId() {
        return exchangesId;
    }

    public void setExchangesId(int exchangesId) {
        this.exchangesId = exchangesId;
    }

    public Iterable<Bond> getBonds() {
        return bonds;
    }

    public Exchange getExchange() {
        return exchange;
    }

}

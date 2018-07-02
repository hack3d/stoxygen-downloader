package de.stoxygen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitstampSymbol {

    private Integer base_decimals;
    private String minimum_order;
    private String name;
    private Integer counter_decimals;
    private String trading;
    private String url_symbol;
    private String description;

    public Integer getBase_decimals() {
        return base_decimals;
    }

    public String getMinimum_order() {
        return minimum_order;
    }

    public String getName() {
        return name;
    }

    public Integer getCounter_decimals() {
        return counter_decimals;
    }

    public String getTrading() {
        return trading;
    }

    public String getUrl_symbol() {
        return url_symbol;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("BitstampSymbol[name: %s, minimum_order: %s, base_decimals: %d, counter_decimals: %d, " +
                "trading: %s, url_symbol: %s, description: %s", name, minimum_order, base_decimals, counter_decimals,
                trading, url_symbol, description);
    }
}

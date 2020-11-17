package de.stoxygen.model.alphavantage;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.stoxygen.deserializer.StockUnitDeserializer;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonAlias("Time Series (Daily)")
    @JsonDeserialize(using = StockUnitDeserializer.class)
    private List<StockUnit> stockUnits;
    private String errorMessage;
    

    public String getErrorMessage() {
        return errorMessage;
    }

    public MetaData getMetadata() {
        return metaData;
    }

    public List<StockUnit> getStockUnits() {
        return stockUnits;
    }

    @Override
    public String toString() {
        return String.format("TimeSeriesResponse[metaData: %s, stockUnits: %s]", metaData, stockUnits);
    }

}

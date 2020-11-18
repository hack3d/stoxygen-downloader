package de.stoxygen;

import de.stoxygen.model.alphavantage.MetaData;
import de.stoxygen.model.alphavantage.TimeSeriesResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AlphavantageTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestfulClient restfulClient;

    @Test
    public void getMetaDataDaily() throws JsonMappingException, JsonProcessingException {
        String json = "{\"Meta Data\": {\"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\"2. Symbol\": \"IBM\",\"3. Last Refreshed\": \"2020-11-13\",\"4. Output Size\": \"Compact\",\"5. Time Zone\": \"US/Eastern\"}}";
        TimeSeriesResponse timeSeriesResponse = objectMapper.readValue(json, TimeSeriesResponse.class);
        
        assertEquals("Daily Prices (open, high, low, close) and Volumes", timeSeriesResponse.getMetadata().getInformation());
        assertEquals("IBM", timeSeriesResponse.getMetadata().getSymbol());
        assertEquals("2020-11-13", timeSeriesResponse.getMetadata().getLastRefreshed());
        assertEquals("US/Eastern", timeSeriesResponse.getMetadata().getTimeZone());
    }

    @Test
    public void getStockUnitsDaily() throws JsonMappingException, JsonProcessingException {
        String json = "{\"Meta Data\": {\"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\"2. Symbol\": \"IBM\",\"3. Last Refreshed\": \"2020-11-13\",\"4. Output Size\": \"Compact\",\"5. Time Zone\": \"US/Eastern\"}, \"Time Series (Daily)\": {\"2020-11-13\": {\"1. open\": \"115.1900\",\"2. high\": \"117.3700\",\"3. low\": \"115.0100\",\"4. close\": \"116.8500\",\"5. volume\": \"4683512\"},\"2020-11-12\": {\"1. open\": \"115.6300\",\"2. high\": \"116.3700\",\"3. low\": \"113.4800\",\"4. close\": \"114.5000\",\"5. volume\": \"6500799\"}}}";
        TimeSeriesResponse timeSeriesResponse = objectMapper.readValue(json, TimeSeriesResponse.class);


        assertEquals(115.19, timeSeriesResponse.getStockUnits().get(0).getOpen());
        assertEquals(117.37, timeSeriesResponse.getStockUnits().get(0).getHigh());
        assertEquals(115.01, timeSeriesResponse.getStockUnits().get(0).getLow());
        assertEquals(116.85, timeSeriesResponse.getStockUnits().get(0).getClose());
        assertEquals(4683512, timeSeriesResponse.getStockUnits().get(0).getVolume());
        assertEquals("2020-11-13", timeSeriesResponse.getStockUnits().get(0).getDateTime());
    }

    @Test
    public void testTimeSeriesResponse() {
        TimeSeriesResponse timeSeriesResponse = restfulClient.getAlphavantageDailyHistoricalData("IBM");
        
        assertEquals("Daily Prices (open, high, low, close) and Volumes", timeSeriesResponse.getMetadata().getInformation());
        assertEquals("IBM", timeSeriesResponse.getMetadata().getSymbol());
        assertNotNull(timeSeriesResponse.getStockUnits());
    }
}

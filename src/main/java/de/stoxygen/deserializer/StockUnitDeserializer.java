package de.stoxygen.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.stoxygen.model.alphavantage.StockUnit;

public class StockUnitDeserializer extends JsonDeserializer<List<StockUnit>> {
    private static final Logger logger = LoggerFactory.getLogger(StockUnitDeserializer.class);

    @Override
    public List<StockUnit> deserialize(JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        final ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        if(p.getCurrentToken().equals(JsonToken.START_OBJECT)) {
            
            HashMap<String, StockUnit> test = objectMapper.readValue(p, new TypeReference<HashMap<String, StockUnit>>() {
            });

            for(String key : test.keySet()) {
                test.get(key).setDateTime(key);
            }
            List<StockUnit> stockUnit = new ArrayList<StockUnit>(test.values());
            return stockUnit;
        } else {
            objectMapper.readTree(p);
            //return new List<StockUnit>();
            return null;
        }
    }


    
}
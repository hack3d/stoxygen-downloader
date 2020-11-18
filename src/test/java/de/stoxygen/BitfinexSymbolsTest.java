package de.stoxygen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BitfinexSymbolsTest {
    
    @Autowired
    private RestfulClient restfulClient;

    @Test
    public void getBitfinexSymbols() {
        List<String> symbols = restfulClient.getBitfinexSymbols("https://api.bitfinex.com");
        for (String symbol : symbols) {
            if(symbol.equals("BTCUSD")) {
                assertEquals("BTCUSD", symbol);
            }
        }
    }
}
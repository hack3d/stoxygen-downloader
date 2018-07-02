package de.stoxygen;

import de.stoxygen.model.BitstampSymbol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitstampSymbolsTest {

    @Autowired
    private RestfulClient restfulClient;

    @Test
    public void getBitstampSymbols() throws Exception {
        List<BitstampSymbol> symbols = restfulClient.getBitstampSymbols("https://www.bitstamp.net/api");
        for(BitstampSymbol symbol : symbols) {
            if(symbol.getName().equals("BTC/USD")) {
                assertThat(symbol.getName()).isEqualTo("BTC/USD");
            }
        }

    }
}

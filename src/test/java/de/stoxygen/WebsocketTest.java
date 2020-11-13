package de.stoxygen;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebsocketTest {
    
    @Autowired
    private WebsocketClient wss;

    @Test
    public void getBitstampWssTickdata() throws InterruptedException {
        wss.connect();
        // Check if websocket connection is open.
        while (!wss.isOpen()) {
            TimeUnit.SECONDS.sleep(1);

        }
        wss.send("{\"event\":\"bts:subscribe\",\"data\": {\"channel\":\"live_trades_btcusd\"}}");
    }
}
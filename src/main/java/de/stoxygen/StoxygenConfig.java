package de.stoxygen;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class StoxygenConfig {
    private static final Logger logger = LoggerFactory.getLogger(StoxygenConfig.class);


    @Value("${downloader.exchange.wss-url:}")
    private String exchange_wssurl;

    @Value("${downloader.exchange.http-url:}")
    private String exchange_httpurl;

    @Value("${downloader.exchange}")
    private String exchange;

    public String getExchange_wssurl() {
        return exchange_wssurl;
    }

    public String getExchange() {
        return exchange;
    }

    public String getExchange_httpurl() {
        return exchange_httpurl;
    }

    @Bean(name = "websocketClient")
    public WebsocketClient websocketClient() {
        try {
            return new WebsocketClient(new URI(getExchange_wssurl()), getExchange());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "pusher")
    public Pusher pusher() {
        try {
            PusherOptions opt = new PusherOptions();
            opt.setActivityTimeout(10000L);
            opt.setPongTimeout(5000L);
            return new Pusher("de504dc5763aeef9ff52", opt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "StoxygenConfig{" +
                "exchange=" + exchange +
                "exchange_wssurl=" + exchange_wssurl +
                "exchange_httpurl=" + exchange_httpurl +
                "}";
    }
}

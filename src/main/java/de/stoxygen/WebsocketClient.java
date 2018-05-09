package de.stoxygen;

import de.stoxygen.model.Exchange;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.services.WebsocketService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

public class WebsocketClient extends WebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketClient.class);


    private Integer exchangesId;
    private String name;

    @Autowired
    private WebsocketService websocketService;

    @Autowired
    private ExchangeRepository exchangeRepository;

    public WebsocketClient(URI serverUri) {
        super(serverUri);
        websocketService = new WebsocketService();
    }

    public WebsocketClient(URI serverUri, Integer exchangesId) {
        super(serverUri);
        this.exchangesId = exchangesId;

    }

    public WebsocketClient(URI serverUri, String name) {
        super(serverUri);
        this.name = name;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.debug("New connection opened!");
    }

    @Override
    public void onMessage(String s) {
        logger.debug("received message: {}", s);
        if(exchangesId != null) {
            Exchange exchange = exchangeRepository.findOne(exchangesId);
            websocketService.handleData(s, exchangesId);
        } else if(name != null) {
            Exchange exchange = exchangeRepository.findBySymbol(name);
            websocketService.handleData(s, exchange.getExchangesId());
        } else {
            websocketService.handleData(s);
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.debug("closed with exit code {} additional info: {}", i, s);

    }

    @Override
    public void onError(Exception e) {
        logger.error("An error occurred: {}", e);
    }

}

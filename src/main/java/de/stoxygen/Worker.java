package de.stoxygen;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;
import de.stoxygen.model.Exchange;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.services.MailService;
import de.stoxygen.services.WebsocketService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class Worker {
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    @Autowired
    private StoxygenConfig stoxygenConfig;

    @Autowired
    private RestfulClient restfulClient;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private WebsocketClient wss;

    @Autowired
    private WebsocketService websocketService;

    private ArrayList<String> cryptoPairs;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void checkBitfinexSymbols() {
        logger.info("Start - checkBitfinexSymbols");
        logger.debug("Exchange {}", stoxygenConfig.getExchange());
        if(stoxygenConfig.getExchange().equals("btfx")) {
            /*
            List<String> symbols = restfulClient.getBitfinexSymbols(stoxygenConfig.getExchange_httpurl());
            for(String symbol : symbols) {
                symbol = symbol.replaceAll("\"", "");
                logger.debug(symbol);
                List<Bond> bond = bondRepository.findByCryptoPair(symbol);
                if(bond.isEmpty()) {
                    String subject = symbol + " not found!";
                    String message = "The symbol " + symbol + " could not found in the database. We found it on the exchange BITFINEX.";
                    mailService.sendMail(subject, message);
                    logger.debug("Crypto pair could not found in database. Send a mail.");
                }
            }
            */
        }

        logger.info("End - checkBitfinexSymbols");
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void checkUnusedCryptoPairs() {
        logger.info("Start - checkUnusedCryptoPairs");
        if(stoxygenConfig.getExchange().equals("btfx")) {

        }
        logger.info("End - checkUnusedCryptoPairs");
    }

    @Scheduled(initialDelay=5000, fixedRate=5000)
    public void addCryptoPairs() {
        // Only handle if exchange is 'btfx'
        if(stoxygenConfig.getExchange().equals("btfx")) {
            Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());

            logger.debug("Size of bonds: {}", exchange.getBonds().size());
            exchange.getBonds().forEach( bond -> {
                logger.debug("Check crypto pair: {}", bond.getCryptoPair());

                if (!cryptoPairs.contains(bond.getCryptoPair())) {
                    logger.info("Crypto pair {} not found in list. Subscribe it!", bond.getCryptoPair());
                    cryptoPairs.add(bond.getCryptoPair());
                    String sym = "t" + bond.getCryptoPair().toUpperCase();
                    JSONObject obj = new JSONObject();
                    obj.put("event", "subscribe");
                    obj.put("channel", "ticker");
                    obj.put("symbol", sym);
                    String message = obj.toString();
                    logger.debug("Websocket send message: {}", message);
                    wss.send(message);
                }
            });
        }
    }

    public void setupWebsocket() {
        // Only handle if exchange is 'btfx'
        if(stoxygenConfig.getExchange().equals("btfx")) {
            Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
            logger.debug("Exchange[Id: {}, Name: {}, Symbol: {}]", exchange.getExchangesId(), exchange.getName(), exchange.getSymbol());
            cryptoPairs = new ArrayList<String>();

            try {
                wss.connect();
                logger.debug("Websocket state: {}", wss.getReadyState());


                // Check if websocket connection is open.
                while (!wss.isOpen()) {
                    logger.debug("Connection to websocket-server is not open yet!");
                    TimeUnit.SECONDS.sleep(1);

                }

                logger.debug("Websocket state: {}", wss.getReadyState());
                exchange.getBonds().forEach(bond -> {
                    cryptoPairs.add(bond.getCryptoPair());
                    String sym = "t" + bond.getCryptoPair().toUpperCase();
                    logger.debug("Symbol {}", sym);
                    JSONObject obj = new JSONObject();
                    obj.put("event", "subscribe");
                    obj.put("channel", "ticker");
                    obj.put("symbol", sym);
                    String message = obj.toString();
                    logger.debug("Websocket send message: {}", message);
                    wss.send(message);
                    logger.debug("{}", bond.toString());
                });


            } catch (InterruptedException e) {
                logger.error("Exception: {}", e);
                e.printStackTrace();
            }
        }

        // Only handle if exchange is 'btsp'
        if(stoxygenConfig.getExchange().equals("btsp")) {
            //PusherOptions options = new PusherOptions().setCluster("stoxygen-downloader");
            Pusher pusher = new Pusher("de504dc5763aeef9ff52");
            pusher.connect(new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                    logger.debug("State changed to {} from {}", connectionStateChange.getCurrentState(), connectionStateChange.getPreviousState());
                }

                @Override
                public void onError(String s, String s1, Exception e) {
                    logger.error("There was a problem connection! Message: {}; Exception: {}", s, e.getMessage());
                }
            });

            Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
            final Channel[] channel = new Channel[1];
            exchange.getBonds().forEach(bond -> {
                String channel_str = "live_trades_";
                channel_str = channel_str + bond.getCryptoPair().toLowerCase();
                logger.info("Channel: {}", channel_str);
                channel[0] = pusher.subscribe(channel_str);
            });

            //Channel channel = pusher.subscribe("live_trades_etheur");
            channel[0].bind("trade", new ChannelEventListener() {
                @Override
                public void onSubscriptionSucceeded(String s) {
                    logger.info("Subscribed to channel: {}", s);
                }

                @Override
                public void onEvent(String s, String s1, String s2) {
                    logger.info("Channel: {}; Data received {}", s, s2);
                    websocketService.handleBtspData(s2, exchange.getExchangesId(), s);
                }
            });
        }

    }

}

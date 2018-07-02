package de.stoxygen;

import de.stoxygen.model.BitstampSymbol;
import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.services.MailService;
import de.stoxygen.services.PusherService;
import de.stoxygen.services.WebsocketService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

    @Autowired
    private PusherService pusherService;

    private ArrayList<String> cryptoPairs;
    private ArrayList<String> checkedCryptoPairsBitstamp;

    @PostConstruct
    public void initCheckedCryptoPairs() {
        checkedCryptoPairsBitstamp = new ArrayList<String>();
        bondRepository.findAll().forEach(bond -> {
            logger.debug("Add crypto pair '{}' to list checkedCryptoPairsBitstamp", bond.getCryptoPair());
            checkedCryptoPairsBitstamp.add(bond.getCryptoPair());
        });
    }

    @Scheduled(initialDelay = 50000, fixedDelay = 60000)
    private void checkBitfinexSymbols() {
        logger.info("Start - checkBitfinexSymbols");
        if(stoxygenConfig.getExchange().equals("btfx")) {
            logger.debug("Exchange {}", stoxygenConfig.getExchange());
            List<String> symbols = restfulClient.getBitfinexSymbols(stoxygenConfig.getExchange_httpurl());
            for(String symbol : symbols) {
                symbol = symbol.replaceAll("\"", "");
                logger.debug(symbol);
                try {
                    Bond bond = bondRepository.findByCryptoPair(symbol);
                    logger.info("Found bond name: ", bond.getName());
                } catch (NullPointerException e) {
                    String subject = symbol + " not found!";
                    String message = "The symbol " + symbol + " could not found in the database. We found it on the exchange BITFINEX.";
                    mailService.sendMail(subject, message);
                    logger.debug("Crypto pair could not found in database. Send a mail.");
                }
            }

        }

        logger.info("End - checkBitfinexSymbols");
    }

    @Scheduled(initialDelay = 50000, fixedDelay = 5000)
    private void checkBitstampSymbols() {
        logger.info("Start - checkBitstampSymbols");
        if(stoxygenConfig.getExchange().equals("btsp")) {
            logger.debug("Exchange {}", stoxygenConfig.getExchange());
            List<BitstampSymbol> symbols = restfulClient.getBitstampSymbols(stoxygenConfig.getExchange_httpurl());
            for (BitstampSymbol symbol : symbols) {
                logger.debug("Search for crypto pair {} on exchange BITSTAMP", symbol.getUrl_symbol());
                if(!checkedCryptoPairsBitstamp.contains(symbol.getUrl_symbol())) {
                    logger.warn("Crypto pair '{}' not found in bond table", symbol.getUrl_symbol());
                    checkedCryptoPairsBitstamp.add(symbol.getUrl_symbol());
                    String subject = symbol.getName() + " not found!";
                    String message = "The symbol " + symbol.getName() + " could not found in the database. We found it on the exchange BITSTAMP.";
                    mailService.sendMail(subject, message);
                    logger.info("Crypto pair could not found in database. Send a mail.");

                }
            }
        }
        logger.info("End - checkBistampSymbols");
    }


    //@Scheduled(cron = "0 0 */1 * * ?")
    /*
    public void checkUnusedCryptoPairs() {
        logger.info("Start - checkUnusedCryptoPairs");
        if(stoxygenConfig.getExchange().equals("btfx")) {

        }
        logger.info("End - checkUnusedCryptoPairs");
    }
    */

    @Scheduled(initialDelay=5000, fixedDelay=5000)
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

        // Only handle if exchange is 'btsp'
        if(stoxygenConfig.getExchange().equals("btsp")) {
            Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());

            logger.debug("Size of bonds: {}", exchange.getBonds().size());
            exchange.getBonds().forEach( bond -> {
                logger.debug("Check crypto pair: {}", bond.getCryptoPair());

                if(!cryptoPairs.contains(bond.getCryptoPair())) {
                    logger.info("Crypto pair {} not found in list. Subcribe it!", bond.getCryptoPair());
                    cryptoPairs.add(bond.getCryptoPair());
                    String channel_str;
                    if (bond.getCryptoPair().equals("btcusd")) {
                        channel_str = "live_trades";
                    } else {
                        channel_str = "live_trades_";
                        channel_str = channel_str + bond.getCryptoPair().toLowerCase();
                    }
                    logger.info("Channel: {}", channel_str);
                    pusherService.addSubscription(channel_str, "trade");
                }
            });
        }
    }

    public void setupWebsocket() {
        // Init ArrayList
        cryptoPairs = new ArrayList<String>();

        // Only handle if exchange is 'btfx'
        if(stoxygenConfig.getExchange().equals("btfx")) {
            Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
            logger.debug("Exchange[Id: {}, Name: {}, Symbol: {}]", exchange.getExchangesId(), exchange.getName(), exchange.getSymbol());

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
            Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
            pusherService.setExchange(exchange);
            exchange.getBonds().forEach(bond -> {
                logger.info("Bond: {}", bond.getCryptoPair());
                cryptoPairs.add(bond.getCryptoPair());
                String channel_str;
                if (bond.getCryptoPair().equals("btcusd")) {
                    channel_str = "live_trades";
                } else {
                    channel_str = "live_trades_";
                    channel_str = channel_str + bond.getCryptoPair().toLowerCase();
                }
                logger.info("Channel: {}", channel_str);
                pusherService.addSubscription(channel_str, "trade");
            });
        }

    }

}

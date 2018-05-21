package de.stoxygen;

import de.stoxygen.model.Exchange;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.services.MailService;
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
        AtomicBoolean found = new AtomicBoolean(false);
        Iterator<String> iter = cryptoPairs.iterator();
        final String[] curItem = new String[1];

        Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());

        exchange.getBonds().forEach( bond -> {
            while (iter.hasNext() == true) {
                curItem[0] = (String) iter.next();
                if(curItem[0].equals(bond.getCryptoPair())) {
                    found.set(true);
                    break;
                }
            }

            if (found.get()) {
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

    public void setupWebsocket() {
        Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
        logger.debug("Exchange[Id: {}, Name: {}, Symbol: {}]", exchange.getExchangesId(), exchange.getName(), exchange.getSymbol());
        cryptoPairs = new ArrayList<String>();

        try {
            //wss = new WebsocketClient(new URI(stoxygenConfig.getExchange_wssurl()), exchange.getExchangesId());
            wss.connect();
            logger.debug("Websocket state: {}", wss.getReadyState());


            // Check if websocket connection is open.
            while (!wss.isOpen()) {
                logger.debug("Connection to websocket-server is not open yet!");
                TimeUnit.SECONDS.sleep(1);

            }
            //WebsocketClient finalWss = wss;
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

}

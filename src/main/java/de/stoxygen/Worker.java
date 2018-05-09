package de.stoxygen;

import de.stoxygen.model.Exchange;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.services.MailService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
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

    public void setupWebsocket() {
        Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
        logger.debug("Exchange[Id: {}, Name: {}, Symbol: {}]", exchange.getExchangesId(), exchange.getName(), exchange.getSymbol());

        try {
            //wss = new WebsocketClient(new URI(stoxygenConfig.getExchange_wssurl()), exchange.getExchangesId());
            wss.connect();
            logger.debug("Websocket state: {}", wss.getReadyState());


            // Check if websocket connection is open.
            while (!wss.isOpen()) {
                logger.debug("Connection to websocket-server is not open yet!");
                TimeUnit.SECONDS.sleep(1);

            }
            WebsocketClient finalWss = wss;
            exchange.getBonds().forEach(bond -> {
                String sym = "t" + bond.getCryptoPair().toUpperCase();
                JSONObject obj = new JSONObject();
                obj.put("event", "subscribe");
                obj.put("channel", "ticker");
                obj.put("symbol", sym);
                String message = obj.toString();
                logger.debug("Websocket send message: {}", message);
                finalWss.send(message);
                logger.debug("{}", bond.toString());
            });



        /*
        } catch (URISyntaxException e) {
            logger.error("Exception: {}", e);
            e.printStackTrace();
        */
        } catch (InterruptedException e) {
            logger.error("Exception: {}", e);
            e.printStackTrace();
        }

    }

}

package de.stoxygen.services;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.TickdataCurrent;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.repository.TickdataCurrentRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WebsocketService implements WebsocketListener {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketService.class);

    private HashMap<Integer,String> channelIDs = null;
    private Integer exchangesId;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private TickdataCurrentRepository tickdataCurrentRepository;

    @Override
    public void handleData(String msg) {
        logger.info("handle Data!");
    }

    @Override
    public void handleData(String msg, Integer exchangesId) {
        logger.info("ExchangeId: {}", exchangesId);
        this.exchangesId = exchangesId;
        Exchange exchange = exchangeRepository.findByExchangesId(exchangesId);

        if("btfx".equals(exchange.getSymbol())) {
            handleBtfxData(msg);
        }

    }

    /**
     * Handle data from bitstamp exchange via pusher websocket.
     * @param msg
     * @param exchangesId
     * @param channel
     */
    public void handleBtspData(String msg, Integer exchangesId, String channel) {
        this.exchangesId = exchangesId;
        Exchange exchange = exchangeRepository.findByExchangesId(exchangesId);
        String pair = channel.replaceAll("live_trades_", "");
        logger.debug("We receive data from crypto pair {}", pair);
        if(isJSONValid(msg)) {
            logger.debug("It's a JSONObject we parse it!");
            JSONObject obj = new JSONObject(msg);
            logger.debug("Channel: {}; Amount: {}, Price: {}", pair, obj.getFloat("amount"), obj.getFloat("price"));
            Bond bond = bondRepository.findByCryptoPair(pair.toLowerCase());
            TickdataCurrent tickdataCurrent = new TickdataCurrent(obj.getFloat("price"), obj.getFloat("amount"));
            tickdataCurrent.addBond(bond);
            tickdataCurrent.addExchange(exchange);
            tickdataCurrentRepository.save(tickdataCurrent);
        }
    }

    /**
     * Handle data from bitfinex exchange.
     * @param s
     */
    private void handleBtfxData(String s) {
        // At first check if we get JSON or not.
        if(isJSONValid(s)) {
            logger.debug("It's a JSONObject we parse it!");
            JSONObject obj = new JSONObject(s);
            logger.debug("Websocket JSON-Key: {}", obj.getString("event"));
            if(obj.getString("event").equals("subscribed")) {
                logger.info("Successfully subcribed to {} with channelId {}", obj.getString("pair"),  obj.getInt("chanId"));

                if(channelIDs == null) {
                    channelIDs = new HashMap<Integer, String>();
                }

                channelIDs.put(obj.getInt("chanId"), obj.getString("pair"));

            }
        } else {
            // We need to parse it separatly because it's not json.
            logger.debug("It's a JSONArray here.");
            JSONArray jarr = new JSONArray(s);
            try {
                //new JSONArray(jarr);
                logger.debug("ChannelId {}, [Bid: {}, BidSize: {}, Ask: {}, AskSize: {}, DailyChange: {}, " +
                                "DailyChangePerc: {}, LastPrice: {}, Volume: {}, High: {}, Low: {}]", jarr.getInt(0),
                        jarr.getJSONArray(1).getDouble(0), jarr.getJSONArray(1).getDouble(1),
                        jarr.getJSONArray(1).getInt(2), jarr.getJSONArray(1).getDouble(3),
                        jarr.getJSONArray(1).getDouble(4), jarr.getJSONArray(1).getDouble(5),
                        jarr.getJSONArray(1).getDouble(6), jarr.getJSONArray(1).getDouble(7),
                        jarr.getJSONArray(1).getDouble(8), jarr.getJSONArray(1).getInt(9));
                String crypto_pair = getChannelIDs().get(jarr.getInt(0));
                Bond bond = bondRepository.findByCryptoPair(crypto_pair.toLowerCase());
                Exchange exchange = exchangeRepository.findOne(exchangesId);
                TickdataCurrent tickdataCurrent = new TickdataCurrent((float)jarr.getJSONArray(1).getDouble(0),
                        (float)jarr.getJSONArray(1).getDouble(2), (float)jarr.getJSONArray(1).getDouble(8),
                        (float)jarr.getJSONArray(1).getDouble(9), (float)jarr.getJSONArray(1).getDouble(6),
                        (long)jarr.getJSONArray(1).getInt(7));
                tickdataCurrent.addBond(bond);
                tickdataCurrent.addExchange(exchange);
                tickdataCurrentRepository.save(tickdataCurrent);
            } catch (JSONException ex) {
                logger.info("Received a heartbeat or something else; {}", jarr.getString(1));

            }
        }
    }

    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public HashMap<Integer, String> getChannelIDs() {
        return channelIDs;
    }
}

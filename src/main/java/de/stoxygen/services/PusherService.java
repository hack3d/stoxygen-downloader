package de.stoxygen.services;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.connection.Connection;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import de.stoxygen.model.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PusherService implements ChannelEventListener {
    private static final Logger logger = LoggerFactory.getLogger(PusherService.class);

    @Autowired
    private Pusher pusher;

    @Autowired
    private WebsocketService websocketService;

    private Exchange exchange;

    private Channel channel;

    private ConnectionEventListener connectionEventListener;

    public PusherService() {
    }

    public void addSubscription(String str_channel, String event) {
        channel = pusher.subscribe(str_channel, this, event);
        pusher.connect();
    }

    public void removeSubscription(String str_channel) {
        pusher.unsubscribe(str_channel);

    }

    public void reconnect() {
        pusher.connect();
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public ConnectionState getConnectionState() {
        return pusher.getConnection().getState();
    }

    public Boolean checkSubscription(String channel) {
        return pusher.getChannel(channel).isSubscribed();
    }

    @Override
    public void onSubscriptionSucceeded(String s) {
        logger.info("Subscribed to channel: {}", s);
    }

    @Override
    public void onEvent(String s, String s1, String s2) {
        logger.info("Channel: {}; Data received {}", s, s2);
        logger.debug("Connection state {}", pusher.getConnection().getState());
        websocketService.handleBtspData(s2, exchange.getExchangesId(), s);
    }

}

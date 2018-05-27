package de.stoxygen.services;

import de.stoxygen.StoxygenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

@Profile("aggregate")
public class Aggregator {

    @Autowired
    private StoxygenConfig stoxygenConfig;

    public void generate1MinuteData() {
        if(stoxygenConfig.getExchange().equals("btsp")) {
            generate1MinuteBtspData();
        }
    }

    private void generate1MinuteBtspData() {

    }
}

package de.stoxygen.services;

import de.stoxygen.StoxygenConfig;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.Tickdata1Minute;
import de.stoxygen.model.TickdataCurrent;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.repository.Tickdata1minuteRepository;
import de.stoxygen.repository.TickdataCurrentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Profile("aggregate")
@Component
public class Aggregator {
    private static final Logger logger = LoggerFactory.getLogger(Aggregator.class);

    @Autowired
    private StoxygenConfig stoxygenConfig;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private TickdataCurrentRepository tickdataCurrentRepository;

    @Autowired
    private Tickdata1minuteRepository tickdata1minuteRepository;

    @Autowired
    private IndicatorService indicatorService;

    /**
     * Generate 1 Minute aggregated data. This job will run with a delay of 5 seconds.
     */
    //@Scheduled(initialDelay=5000, fixedDelay=5000)
    public void generate1MinuteData() {
        logger.debug("Start generate1MinuteData()!");

        Exchange exchange = exchangeRepository.findBySymbol(stoxygenConfig.getExchange());
        exchange.getBonds().forEach(bond -> {
            try {
                TickdataCurrent tickdataCurrent1 = tickdataCurrentRepository.findTop1ByExchangeAndBondAndAggregatedOrderByInsertTimestampAsc(exchange, bond, false);

                logger.debug("Timestamp of first item that not aggregated: {}", tickdataCurrent1.getInsertTimestamp());

                // Add to first insert_timestamp one minute
                List<TickdataCurrent> tickdataCurrentList = tickdataCurrentRepository.findByExchangeAndBondAndAggregatedAndInsertTimestampGreaterThanEqual(exchange, bond, false, addMinutes(tickdataCurrent1.getInsertTimestamp(), 1));
                logger.debug("We searched with timestamp {}", addMinutes(tickdataCurrent1.getInsertTimestamp(), 1));

                if (tickdataCurrentList.size() == 0) {
                    logger.info("We can not aggregate data for bond {}, because not enough data available.", bond.getName());
                } else {
                    List<TickdataCurrent> tickdataCurrentList1 = tickdataCurrentRepository.findByExchangeAndBondAndAggregatedAndInsertTimestampBetween(exchange, bond, false, tickdataCurrent1.getInsertTimestamp(), addMinutes(tickdataCurrent1.getInsertTimestamp(), 1));

                    // Variables for aggregated data
                    Float close;
                    Float open;
                    Float high;
                    Float low;
                    Float bid = Float.valueOf(0);
                    Float ask = Float.valueOf(0);
                    AtomicReference<Float> volume = new AtomicReference<>(Float.valueOf(0));

                    Iterator<TickdataCurrent> iterator = tickdataCurrentList1.iterator();
                    while(iterator.hasNext()) {
                        logger.debug("Current data: Data: {}", iterator.next());
                    }
                    tickdataCurrentList1.sort(Comparator.comparing(TickdataCurrent::getLast));
                    logger.debug("Sorted by Last");
                    logger.debug("Low price: ID: {}, Last: {}", tickdataCurrentList1.get(0).getTickdataCurrentsId(), tickdataCurrentList1.get(0).getLast());
                    low = tickdataCurrentList1.get(0).getLast();

                    tickdataCurrentList1.sort(Comparator.comparing(TickdataCurrent::getLast).reversed());
                    logger.debug("Sorted by Last reversed");
                    logger.debug("High price: ID: {}, Last: {}", tickdataCurrentList1.get(0).getTickdataCurrentsId(), tickdataCurrentList1.get(0).getLast());
                    high = tickdataCurrentList1.get(0).getLast();

                    tickdataCurrentList1.sort(Comparator.comparing(TickdataCurrent::getInsertTimestamp));
                    logger.debug("Sorted by insertTimestamp");
                    logger.debug("Open price: ID: {}, Last: {}", tickdataCurrentList1.get(0).getTickdataCurrentsId(), tickdataCurrentList1.get(0).getLast());
                    open = tickdataCurrentList1.get(0).getLast();

                    tickdataCurrentList1.sort(Comparator.comparing(TickdataCurrent::getInsertTimestamp).reversed());
                    logger.debug("Sorted by insertTimestamp reversed");
                    logger.debug("Close price: ID: {}, Last: {}", tickdataCurrentList1.get(0).getTickdataCurrentsId(), tickdataCurrentList1.get(0).getLast());
                    close = tickdataCurrentList1.get(0).getLast();

                    tickdataCurrentList1.forEach(tick -> {
                        //volume.updateAndGet(v -> v + tick.getVolume());
                        volume.getAndUpdate(v -> v + tick.getVolume());
                        logger.debug("Volume: '{}'; Added: '{}'", volume, tick.getVolume());
                    });

                    logger.info("Bond: {}, Open: {}, Close: {}, High: {}, Low: {}, Volume: {}", bond.getCryptoPair(), open, close, high, low, volume);

                    try {
                        // Save all aggregated data
                        Tickdata1Minute tickdata1Minute = new Tickdata1Minute(bid, ask, high, low, open, close, volume.get(), addMinutes(tickdataCurrent1.getInsertTimestamp(), 1));
                        tickdata1Minute.addBond(bond);
                        tickdata1Minute.addExchange(exchange);
                        tickdata1minuteRepository.save(tickdata1Minute);

                        // Set aggregated to true for data
                        tickdataCurrentList1.forEach(tick -> {
                            tick.setAggregated(true);
                            tickdataCurrentRepository.save(tick);
                        });

                        // Create message
                        indicatorService.createCalculate(exchange.getSymbol(), bond.getIsin(), "1min", tickdataCurrentList1.get(0).getInsertTimestamp());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace().toString());
                    }

                    logger.info("We aggregated data for bond {}", bond.getName());
                }

            } catch (NullPointerException e) {
                logger.warn("Maybe no data in table tickdata_current for bond {}", bond.getCryptoPair());
            }
        });

        logger.debug("End generate1MinuteData()!");
    }


    /**
     * Add minutes to Date object.
     * @param date
     * @param i
     * @return
     */
    private Date addMinutes(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, i);

        // Clear second and millisecond
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }
}

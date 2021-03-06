package de.stoxygen.controller;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.Tickdata1Minute;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.repository.Tickdata1minuteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/api/v1")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private Tickdata1minuteRepository tickdata1minuteRepository;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @RequestMapping(value = "/aggregatedData/1min/exchange/{exchange}/isin/{isin}/latestTimestamp/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public List<Tickdata1Minute> get1minAggregatedData(@PathVariable(value = "isin") String isin, @PathVariable(value = "exchange") String exchange, @PathVariable(value = "timestamp") String timestamp) {
        logger.info("Data[isin: {}, exchange: {}, timestamp: {}]", isin, exchange, timestamp);
        Date date = new Date(Long.valueOf(timestamp)*1000L);
        logger.debug("API /api/v1/aggregatedData/1min: {}", date);
        List<Bond> bonds = bondRepository.findByIsin(isin);
        Exchange exchange1 = exchangeRepository.findBySymbol(exchange);
        List<Tickdata1Minute> tickdata1Minute = new ArrayList<Tickdata1Minute>();
        for(Bond bond : bonds) {
            tickdata1Minute = tickdata1minuteRepository.findByBondsAndExchangeAndInsertTimestampLessThanEqual(bond, exchange1, date);
        }
        return tickdata1Minute;
    }

    @RequestMapping(value = "/bonds", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Bond> getAllBonds() {
        return bondRepository.findAll();
    }
}

package de.stoxygen.controller.api.v1;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.TickdataCurrent;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import de.stoxygen.repository.TickdataCurrentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/bonddata")
public class RestBondDataController {
    private static final Logger logger = LoggerFactory.getLogger(RestBondDataController.class);

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private TickdataCurrentRepository tickdataCurrentRepository;

    @RequestMapping(value = "/exchange/{exchange}/isin/{isin}/latestTimestamp/{timestamp}/1min", method = RequestMethod.GET)
    @ResponseBody
    public List<TickdataCurrent> getTickdataCurrent(@PathVariable(value = "isin") String isin, @PathVariable(value = "exchange") String exchange, @PathVariable(value = "timestamp") String timestamp) {
        logger.info("Data[isin: {}, exchange: {}, timestamp: {}]", isin, exchange, new Date(Long.valueOf(timestamp)));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(timestamp));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        logger.info("Calender: {}", calendar.getTime());

        List<Bond> bonds = bondRepository.findByIsin(isin);
        Exchange exchange1 = exchangeRepository.findBySymbol(exchange);
        List<TickdataCurrent> tickdataCurrentList = new ArrayList<TickdataCurrent>();
        for(Bond bond : bonds) {
            logger.debug("Bond: {}", bond.getIsin());
            tickdataCurrentList = tickdataCurrentRepository.findByExchangeAndBondAndInsertTimestampBetween(exchange1, bond, calendar.getTime(), new Date(Long.valueOf(timestamp)));
        }
        return tickdataCurrentList;
    }
}

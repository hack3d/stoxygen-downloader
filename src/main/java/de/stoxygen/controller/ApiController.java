package de.stoxygen.controller;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Tickdata1Minute;
import de.stoxygen.repository.BondRepository;
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
import java.util.List;


@Controller
@RequestMapping("/api")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private Tickdata1minuteRepository tickdata1minuteRepository;

    @Autowired
    private BondRepository bondRepository;

    @RequestMapping(value = "/aggregatedData/1min/{isin}", method = RequestMethod.GET)
    @ResponseBody
    public List<Tickdata1Minute> get1minAggregatedData(@PathVariable(value = "isin") String isin) {
        List<Bond> bonds = bondRepository.findByIsin(isin);
        List<Tickdata1Minute> tickdata1Minute = new ArrayList<Tickdata1Minute>();
        for(Bond bond : bonds) {
            tickdata1Minute = tickdata1minuteRepository.findByBonds(bond);
        }
        return tickdata1Minute;
    }
}

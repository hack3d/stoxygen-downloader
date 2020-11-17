package de.stoxygen;

import de.stoxygen.model.BitstampSymbol;
import de.stoxygen.model.Bond;
import de.stoxygen.model.alphavantage.TimeSeriesResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class RestfulClient {
    private static final Logger logger = LoggerFactory.getLogger(RestfulClient.class);
    
    @Autowired
    private StoxygenConfig stoxygenConfig;

    RestTemplate restTemplate;

    public RestfulClient() {
        restTemplate = new RestTemplate();
    }
    
    public List<String> getBitfinexSymbols(String url) {
        List<String> list = new ArrayList<>();
        logger.info("Begin /GET request to {}", url);
        url = url + "/v1/symbols";

        String symbols = restTemplate.getForObject(url, String.class);
        symbols = symbols.replace("[", "");
        symbols = symbols.replace("]", "");

        if(symbols != null) {
            logger.info("Response for GET request: {}", symbols);
            list = Arrays.asList(symbols.split(","));
        } else {
            logger.info("Response for GET request: NULL");
        }


        return list;
    }

    public List<BitstampSymbol> getBitstampSymbols(String url) {
        List<BitstampSymbol> list = new ArrayList<>();
        logger.info("Begin /GET request to {}", url);
        url = url + "/v2/trading-pairs-info/";

        BitstampSymbol[] symbols = restTemplate.getForObject(url, BitstampSymbol[].class);

        for(int i = 0; i >= symbols.length; i++) {
            logger.info("Add symbol '{}' to list", symbols[i].getName());
        }

        list = Arrays.asList(symbols);

        return list;
    }

    public TimeSeriesResponse getAlphavantageDailyHistoricalData(String symbol) {
        
        String url = stoxygenConfig.getAlphavantageUrl() + "/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + stoxygenConfig.getAlphavantageApiKey();
        logger.info("Begin /GET request to {}", url);

        TimeSeriesResponse stockData = restTemplate.getForObject(url,TimeSeriesResponse.class);
        logger.info("Alphavantage result: {}", stockData.toString());

        return stockData;
    }

    Job getJob(String url) {
        logger.info("Begin /GET request to get a job.");
        url = url + "/job/downloader_jobs";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("Hello World!", headers);
        ResponseEntity<Job> jobEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Job.class, 100);

        if(jobEntity.getBody() != null) {
            logger.info("Response for Get Request: {}", jobEntity.getBody().toString());
        } else {
            logger.info("Response for Get Request: NULL");
        }
        return jobEntity.getBody();
    }

    Bond getBondInformation(String url, String isin) {
        logger.info("Begin /GET request to get information about the bond {}", isin);
        url = url + "/currencies/" + isin;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("Hello World!", headers);
        ResponseEntity<Bond> bondEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Bond.class, 100);

        if(bondEntity.getBody() != null) {
            logger.info("Response for Get-Request: {}", bondEntity.getBody().toString());
        } else {
            logger.info("Response for Get-Request: NULL. Status: {}", bondEntity.getStatusCodeValue());
        }

        return bondEntity.getBody();
    }
}

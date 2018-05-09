package de.stoxygen;

import de.stoxygen.model.Bond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class RestfulClient {
    private static final Logger logger = LoggerFactory.getLogger(RestfulClient.class);

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

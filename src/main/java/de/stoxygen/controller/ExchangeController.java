package de.stoxygen.controller;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.form.AddBondItemForm;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private BondRepository bondRepository;

    @RequestMapping(value = "/exchanges", method = RequestMethod.GET)
    public String getAllExchanges(Model model) {
        model.addAttribute("exchanges", exchangeRepository.findAll());
        return "exchanges";
    }

    @RequestMapping(value = "/exchanges/{id}", method = RequestMethod.GET)
    public String getBondsByExchange(@PathVariable(value = "id") Integer exchangesId, Model model) {
        Exchange exchange = exchangeRepository.findOne(exchangesId);

        model.addAttribute("title", exchange.getName());
        model.addAttribute("bonds", exchange.getBonds());

        return "bonds";
    }

    @RequestMapping(value = "/exchanges/add", method = RequestMethod.GET)
    public String addNewExchange(Exchange exchange) {
        return "exchanges_add";
    }

    @RequestMapping(value = "/exchanges/add", method = RequestMethod.POST)
    public String addNewExchange(@Valid Exchange exchange, BindingResult bindingResult, Model model) {
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");
        if(bindingResult.hasErrors()) {
            return "exchanges_add";
        }
        logger.info("Exchange[Name: {}, Symbol: {}, Interval: {}, Country Code: {}", exchange.getName(),
                exchange.getSymbol(), exchange.getIntervalDelay(), exchange.getCountryCode());
        model.addAttribute("successMessage", "Successfull create Exchange");
        model.mergeAttributes(map);
        exchangeRepository.save(exchange);
        return "redirect:/exchanges";
    }

    @RequestMapping(value = "/exchanges/delete/{id}", method = RequestMethod.GET)
    public String deleteExchange(@PathVariable(value = "id") Integer exchangesId, Model model) {
        Exchange exchange = exchangeRepository.findByExchangesId(exchangesId);
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");

        if(exchange == null) {
            model.addAttribute("errorMessage", "Exchange with the ID " + exchangesId + " could not be found.");
            model.mergeAttributes(map);
            return "redirect:/exchanges";
        }

        model.addAttribute("successMessage", "Successfull delete with ID " + exchangesId);
        model.mergeAttributes(map);
        exchangeRepository.delete(exchange);

        return "redirect:/exchanges";
    }

    @RequestMapping(value = "/exchanges/add-bond/{id}", method = RequestMethod.GET)
    public String addNewBondExchange(@PathVariable(value = "id") int exchangesId, Model model) {
        Exchange exchange = exchangeRepository.findOne(exchangesId);


        AddBondItemForm form = new AddBondItemForm(
          bondRepository.findAll(), exchange
        );

        logger.debug("Exchange[Id: {}]", exchange.getExchangesId());
        model.addAttribute("title", exchange.getName());
        model.addAttribute("form", form);
        //model.addAttribute("bonds", initializeBonds());

        return "exchanges_add-bond";
    }

    @RequestMapping(value = "/exchanges/add-bond", method = RequestMethod.POST)
    public String addNewBondExchange(@ModelAttribute @Valid AddBondItemForm form, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "exchanges_add-bond";
        }

        logger.debug("Exchange[Id: {}], Bond[Id: {}]", form.getExchangesId(), form.getBonds_id());
        Bond theBond = bondRepository.findOne(form.getBonds_id());
        Exchange theExchange = exchangeRepository.findOne(form.getExchangesId());
        theExchange.addBond(theBond);
        exchangeRepository.save(theExchange);


        //Bond theBond = bondRepository.findOne(bond.get);
        //Exchange theExchange = exchangeRepository.findOne(form.getExchangesId());

        return "redirect:/exchanges";
    }

    @ModelAttribute("bonds")
    public Iterable<Bond> bonds() {
        return bondRepository.findAll();
    }
}

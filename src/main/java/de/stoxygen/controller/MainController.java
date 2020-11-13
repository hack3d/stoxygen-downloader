package de.stoxygen.controller;

import de.stoxygen.model.Bond;
import de.stoxygen.repository.BondRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BondRepository bondRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/bonds", method = RequestMethod.GET)
    public String getAllBonds(Model model) {

        model.addAttribute("bonds", bondRepository.findAll());
        return "bonds";
    }

    @RequestMapping(value = "/bonds/add", method = RequestMethod.GET)
    public String showNewBond(Bond bond) {
        return "bonds_add";
    }

    @RequestMapping(value = "/bonds/add", method = RequestMethod.POST)
    public String addNewBond(@Validated Bond bond, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "bonds_add";
        }

        bond.setCryptoPair(bond.getCryptoBase() + "" + bond.getCryptoQuote());
        if(bond.getState() == null) {
            bond.setState(0);
        }
        logger.info("Bond[Name: {}, ISIN: {}, State: {}, Crypto Base: {}, Crypto Quote: {}, Crypto Pair: {}", bond.getName(),
                bond.getIsin(), bond.getState(), bond.getCryptoBase(), bond.getCryptoQuote(), bond.getCryptoPair());
        bondRepository.save(bond);
        return "redirect:/bonds";
    }

}

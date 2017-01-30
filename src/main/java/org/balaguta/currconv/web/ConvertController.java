package org.balaguta.currconv.web;

import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.data.Converter;
import org.balaguta.currconv.data.entity.MoneyAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"fromCurrency", "toCurrency"})
public class ConvertController {

    private final Converter converter;
    private final CurrconvProperties properties;

    @Autowired
    public ConvertController(Converter converter, CurrconvProperties properties) {
        this.converter = converter;
        this.properties = properties;
    }

    @ModelAttribute
    public void populateCurrencies(ModelMap model) {
        List<String> supportedCurrencies = new ArrayList<>(properties.getSupportedCurrencies());
        Collections.sort(supportedCurrencies);
        model.put("supportedCurrencies", supportedCurrencies);
        if (!model.containsAttribute("fromCurrency")) {
            String fromCurrency = supportedCurrencies.get(0);
            String toCurrency = supportedCurrencies.get(1);
            model.put("fromCurrency", fromCurrency);
            model.put("toCurrency", toCurrency);
        }
    }

    @GetMapping
    public String convertForm(ConversionDto conversion,
                              @ModelAttribute("fromCurrency") String fromCurrency,
                              @ModelAttribute("toCurrency") String toCurrency) {
        conversion.setFromCurrency(fromCurrency);
        conversion.setToCurrency(toCurrency);
        return "index";
    }

    @PostMapping
    public String convert(@Valid ConversionDto conversion, BindingResult bindingResult, ModelMap model) {
        if (!bindingResult.hasErrors()) {
            MoneyAmount sourceAmount = new MoneyAmount(conversion.getAmount(), conversion.getFromCurrency());
            MoneyAmount targetAmount = converter.convert(sourceAmount, conversion.getToCurrency());
            model.put("fromCurrency", conversion.getFromCurrency());
            model.put("toCurrency", conversion.getToCurrency());
            model.put("sourceAmount", sourceAmount);
            model.put("targetAmount", targetAmount);
        }
        return "index";
    }

}

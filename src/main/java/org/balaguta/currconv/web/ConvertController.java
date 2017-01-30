package org.balaguta.currconv.web;

import org.balaguta.currconv.data.Converter;
import org.balaguta.currconv.data.entity.MoneyAmount;
import org.balaguta.currconv.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"fromCurrency", "toCurrency"})
public class ConvertController {

    private final Converter converter;
    private final ConversionService conversionService;

    @Autowired
    public ConvertController(Converter converter, ConversionService conversionService) {
        this.converter = converter;
        this.conversionService = conversionService;
    }

    @ModelAttribute("supportedCurrencies")
    public List<String> supportedCurrencies() {
        return conversionService.getSupportedCurrencies();
    }

    @ModelAttribute("fromCurrency")
    public String fromCurrency() {
        return conversionService.getSupportedCurrencies().get(0);
    }

    @ModelAttribute("toCurrency")
    public String toCurrency() {
        return conversionService.getSupportedCurrencies().get(1);
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

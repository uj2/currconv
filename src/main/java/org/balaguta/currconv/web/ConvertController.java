package org.balaguta.currconv.web;

import org.balaguta.currconv.data.entity.Conversion;
import org.balaguta.currconv.data.entity.MoneyAmount;
import org.balaguta.currconv.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/")
@SessionAttributes({"fromCurrency", "toCurrency"})
public class ConvertController {

    private final ConversionService conversionService;

    @Autowired
    public ConvertController(ConversionService conversionService) {
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
    public Callable<String> convert(@Valid ConversionDto conversionDto, BindingResult bindingResult, ModelMap model) {
        return () -> {
            if (!bindingResult.hasErrors()) {
                MoneyAmount sourceAmount = new MoneyAmount(conversionDto.getAmount(), conversionDto.getFromCurrency());
                Conversion conversion = conversionService.convert(sourceAmount, conversionDto.getToCurrency());

                model.put("fromCurrency", conversionDto.getFromCurrency());
                model.put("toCurrency", conversionDto.getToCurrency());
                model.put("conversion", conversion);
            }
            return "index";
        };
    }

}

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
import java.security.Principal;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/")
@SessionAttributes({ConvertController.ATTR_FROM_CURRENCY, ConvertController.ATTR_TO_CURRENCY})
public class ConvertController {

    public static final String ATTR_CONVERSION_HISTORY = "conversionHistory";
    public static final String ATTR_CONVERSION = "conversion";
    public static final String ATTR_FROM_CURRENCY = "fromCurrency";
    public static final String ATTR_TO_CURRENCY = "toCurrency";
    public static final String ATTR_SUPPORTED_CURRENCIES = "supportedCurrencies";

    private final ConversionService conversionService;

    @Autowired
    public ConvertController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @ModelAttribute
    public void setupModel(@RequestParam(required = false) String results,
                           ConversionDto conversion,
                           Principal principal,
                           ModelMap model) {
        if (principal != null) {
            setupAuthenticatedModel(results, conversion, model);
        }
    }

    private void setupAuthenticatedModel(String results, ConversionDto conversion, ModelMap model) {
        List<Conversion> history = conversionService.getHistory();
        if (history.isEmpty() || results == null) {
            model.put(ATTR_CONVERSION_HISTORY, history);
        } else {
            model.put(ATTR_CONVERSION_HISTORY, history.subList(1, history.size()));
            model.put(ATTR_CONVERSION, history.get(0));
        }
    }

    @ModelAttribute("supportedCurrencies")
    public List<String> supportedCurrencies() {
        return conversionService.getSupportedCurrencies();
    }

    @ModelAttribute("fromCurrency")
    public String fromCurrency(@ModelAttribute(ATTR_SUPPORTED_CURRENCIES) List<String> supportedCurrencies) {
        return supportedCurrencies.get(0);
    }

    @ModelAttribute("toCurrency")
    public String toCurrency(@ModelAttribute(ATTR_SUPPORTED_CURRENCIES) List<String> supportedCurrencies) {
        return supportedCurrencies.get(1);
    }

    @GetMapping
    public String convertForm(ConversionDto conversion,
                              @ModelAttribute(ATTR_FROM_CURRENCY) String fromCurrency,
                              @ModelAttribute(ATTR_TO_CURRENCY) String toCurrency) {
        conversion.setFromCurrency(fromCurrency);
        conversion.setToCurrency(toCurrency);
        return "index";
    }

    @PostMapping
    public Callable<String> convert(@Valid ConversionDto conversionDto, BindingResult bindingResult, ModelMap model) {
        return () -> {
            if (bindingResult.hasErrors()) {
                return "index";
            }

            MoneyAmount sourceAmount = new MoneyAmount(conversionDto.getAmount(), conversionDto.getFromCurrency());
            conversionService.convert(sourceAmount, conversionDto.getToCurrency());

            model.put(ATTR_FROM_CURRENCY, conversionDto.getFromCurrency());
            model.put(ATTR_TO_CURRENCY, conversionDto.getToCurrency());
            return "redirect:/?results";
        };
    }

}

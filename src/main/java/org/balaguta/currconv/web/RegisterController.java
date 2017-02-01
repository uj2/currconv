package org.balaguta.currconv.web;

import org.balaguta.currconv.data.entity.Country;
import org.balaguta.currconv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    public static final String VIEW_NAME = "register";

    @Autowired
    private UserService userService;

    @ModelAttribute("countries")
    public List<Country> getCountries() {
        return userService.getCountries();
    }

    @GetMapping
    public String registerForm(UserDto user) {
        return VIEW_NAME;
    }

    @PostMapping
    public String register(@Valid UserDto userInfo, BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        if (bindingResult.hasErrors()) {
            return VIEW_NAME;
        }
        if (userService.userExists(userInfo.getEmail())) {
            bindingResult.addError(new ObjectError("user",
                    new String[] { "org.balaguta.currconv.User.exists.message" },
                    new Object[0], "e-mail already used"));
            return VIEW_NAME;
        }

        userService.registerUser(userInfo);

        // preauthenticate newly registered user
        if (request.getUserPrincipal() != null) {
            // for some reason, request.logout() doesn't work correctly
            SecurityContextHolder.clearContext();
        }
        request.login(userInfo.getEmail(), userInfo.getPassword());

        return "redirect:/";
    }

}

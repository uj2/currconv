package org.balaguta.currconv.web;

import org.balaguta.currconv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    public static final String VIEW_NAME = "register";

    @Autowired
    private UserService userService;

    @GetMapping
    public String registerForm(UserDto user) throws ServletException {
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
        request.login(userInfo.getEmail(), userInfo.getPassword());
        return "redirect:/";
    }

}

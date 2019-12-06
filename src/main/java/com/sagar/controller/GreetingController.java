package com.sagar.controller;

import com.sagar.entity.User;
import com.sagar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class GreetingController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Locale locale, @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String lang, RedirectAttributes redirectAttributes) {
        System.out.println("Lang ::" + lang);
        redirectAttributes.addFlashAttribute("name", name);
        User user = new User();
        user.setEmail("abc@abc.com");
        user.setAddress(messageSource.getMessage("sunflower", null, locale));
        user.setName(name);
        userRepository.save(user);
        return "redirect:/greeting";
    }

    @GetMapping("/greeting")
    public String geet(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "greeting";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}

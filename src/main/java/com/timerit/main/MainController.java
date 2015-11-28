package com.timerit.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by trpgm on 2015-11-28.
 */

@Controller
public class MainController {
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name","withclick");

        return "hello";
    }
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name","withclick");

        return "hello";
    }
}

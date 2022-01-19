package example.plugin.main.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChangeFrontController {
    @GetMapping("/change/frontend")
    public String page1(Model model)   {
        model.addAttribute("bodyTemplate", "/change/frontend.ftl");
        return "/base/layout";
    }
}

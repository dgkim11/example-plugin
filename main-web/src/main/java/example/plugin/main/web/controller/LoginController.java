package example.plugin.main.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(@RequestParam(defaultValue = "") String status, Model model)   {
        if(! status.isEmpty())  {
            model.addAttribute("errorMessage", "로그인 실패");
        }
        return "/loginPage";
    }

}

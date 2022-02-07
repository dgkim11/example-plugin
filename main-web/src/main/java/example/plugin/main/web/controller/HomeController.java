package example.plugin.main.web.controller;

import example.plugin.main.domain.menu.Menu;
import example.plugin.main.domain.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private MenuService menuService;

    public HomeController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model)    {
        // 단순히 username, password로 로그인하는 경우에 principal은 username만 존재한다.
        String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findAny().get();
        List<Menu> menuListByRole = menuService.findMenuListByRole(role);
        model.addAttribute("menuList", menuListByRole);
        return "/home";
    }
}

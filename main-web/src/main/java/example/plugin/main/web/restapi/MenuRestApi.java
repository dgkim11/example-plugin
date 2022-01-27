package example.plugin.main.web.restapi;

import example.plugin.main.application.menu.MenuFinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuRestApi {
    private MenuFinder menuFinder;

    public MenuRestApi(MenuFinder menuFinder) {
        this.menuFinder = menuFinder;
    }

    @GetMapping("/api/menu/list/top")
    @ResponseBody
    public String getTopMenuList()  {
//        menuFinder.findMenuListByRole()
        return null;
    }
}

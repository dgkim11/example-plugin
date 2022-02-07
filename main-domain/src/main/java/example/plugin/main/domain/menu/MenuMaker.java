package example.plugin.main.domain.menu;

import example.plugin.main.domain.role.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Role 별로 메뉴의 구성이 달라지기에 role별 menu 구성을 만들어 놓는다.
 */
public class MenuMaker {
    public static Map<String, List<Menu>> makeMenuListByRole(List<Role> roles, List<Menu> menuList) {
        Map<String, List<Menu>> menuListByRole = new HashMap<>();
        for(Role role : roles)   {
            menuListByRole.put(role.getName(), getMenuListByRole(role, menuList));
        }
        return menuListByRole;
    }

    private static List<Menu> getMenuListByRole(Role role, List<Menu> menuList) {
        List<Menu> menuListByRole = new ArrayList<>();
        for(Menu menu : menuList)   {
            // Menu 객체 원본을 수정하면 role별로 메뉴를 구성할 때 데이터가 꼬인다. 따라서, 원본 객체를 deep clone하여 사용한다.
            Menu clonedMenu = menu.deepClone();
            if(menu.isAccessable(role)) menuListByRole.add(clonedMenu);
            if(! clonedMenu.getSubMenuList().isEmpty())  {
                clonedMenu.setSubMenuList(getMenuListByRole(role, clonedMenu.getSubMenuList()));
            }
        }
        return menuListByRole;
    }
}

package example.plugin.main.domain.menu;

import lombok.*;

import java.util.*;

import example.plugin.main.domain.role.Role;

@Getter
@Builder
public class Menu {
    private String id;
    /**
     * 메뉴를 클릭하였을 때 이동할 page url.
     */
    private String pageUrl;
    /**
     * 해당 메뉴를 사용할 수 있는 권한을 가진 role 집합
     */
    private Set<Role> roles;
    /**
     * 하위 menu
     */
    @Setter
    private List<Menu> subMenuList;
    /**
     *  메뉴 리스트에서 보여질 메뉴명
     */
    private String menuName;
    /**
     * i18n 지원을 위해서 resource bundle에 있는 메세지 key.
     */
    private String menuBundleKey;

    public Menu deepClone() {
        List<Menu> clonedMenuList = new ArrayList<>();
        subMenuList.forEach(m -> clonedMenuList.add(m.deepClone()));
        return Menu.builder()
                .id(id)
                .pageUrl(pageUrl)
                .roles(new HashSet<>(roles))
                .subMenuList(clonedMenuList)
                .menuName(menuName)
                .menuBundleKey(menuBundleKey)
                .build();
    }

    public boolean isAccessable(Role role)  {
        if(roles.isEmpty()) return true;
        if(roles.contains(role)) return true;
        return false;
    }
}

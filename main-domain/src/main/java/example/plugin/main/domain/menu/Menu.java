package example.plugin.main.domain.menu;

import lombok.*;

import java.util.*;

import example.plugin.main.domain.role.Role;

@Builder
public class Menu {
    @Getter
    @NonNull
    private String id;
    /**
     * 메뉴를 클릭하였을 때 이동할 page url.
     */
    @Getter
    @NonNull
    private String pageUrl;
    /**
     * 해당 메뉴를 사용할 수 있는 권한을 가진 role 집합
     */
    @Getter
    @NonNull
    private Set<Role> roles;
    /**
     * 하위 menu
     */
    @Getter
    @Setter
    @NonNull
    private List<Menu> menuList;

    @NonNull
    private String bundleBaseName;
    /**
     *  메뉴 리스트에서 보여질 메뉴명
     */
    private Optional<String> menuName;
    /**
     * i18n 지원을 위해서 resource bundle에 있는 메세지 key.
     */
    private Optional<String> menuBundleKey;

    public Menu deepClone() {
        List<Menu> clonedMenuList = new ArrayList<>();
        menuList.forEach(m -> clonedMenuList.add(m.deepClone()));
        return Menu.builder()
                .id(id)
                .pageUrl(pageUrl)
                .roles(new HashSet<>(roles))
                .menuList(clonedMenuList)
                .bundleBaseName(bundleBaseName)
                .menuName(menuName)
                .menuBundleKey(menuBundleKey)
                .build();
    }

    public String getMenuName()    {
        return getMenuName(Locale.getDefault());
    }

    public String getMenuName(Locale locale)    {
        if(menuName.isPresent()) return menuName.get();
        return menuBundleKey.map(s -> ResourceBundle.getBundle(bundleBaseName, locale).getString(s)).orElseGet(() -> id);
    }

    public boolean isAccessable(Role role)  {
        if(roles.isEmpty()) return true;
        if(roles.contains(role)) return true;
        return false;
    }
}

package example.plugin.main.domain.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.plugin.main.domain.page.AuthorizedPageService;
import example.plugin.main.domain.role.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import example.plugin.main.domain.menu.config.MenuConfig;
import example.plugin.main.domain.menu.config.MenuInfo;
import example.plugin.main.domain.role.Role;

@Service
public class MenuService {
    private List<Menu> allMenuList;
    private Map<String, List<Menu>> menuListByRolId = new HashMap<>();
    private ObjectMapper objectMapper;
    private RoleService roleService;
    private AuthorizedPageService authPageService;

    private static final String MENU_FILE = "/menu.yaml";

    public MenuService(RoleService roleService, AuthorizedPageService authPageService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
        this.authPageService = authPageService;
    }

    @PostConstruct
    public void makeMenus() throws IOException, URISyntaxException {
        MenuConfig menuConfig;

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(MENU_FILE);
        if(in == null)  {
            File file = new File(getClass().getResource(MENU_FILE).toURI());
            menuConfig = objectMapper.readValue(file, MenuConfig.class);
        } else  {
            menuConfig = objectMapper.readValue(in, MenuConfig.class);
        }
        allMenuList = convertToMenuList(menuConfig);
        menuListByRolId = MenuMaker.makeMenuListByRole(roleService.getAllRoles(), allMenuList);

        registerAuthorizedPages(allMenuList);
    }

    private void registerAuthorizedPages(List<Menu> allMenuList) {
        allMenuList.forEach(menu -> {
            if (! menu.getRoles().isEmpty()) {
                authPageService.addPage(menu.getPageUrl(), menu.getRoles());
            }
        });
    }

    private List<Menu> convertToMenuList(MenuConfig menuConfig) {
        List<Menu> menuList = new ArrayList<>();
        List<MenuInfo> menuInfos = menuConfig.getMenus();
        menuInfos.forEach(m -> menuList.add(convertToMenu(m)));
        return menuList;
    }

    private Menu convertToMenu(MenuInfo menuInfo) {
        Set<Role> roleSet = makeRoleSet(menuInfo.getRoles());
        List<Menu> menuList = menuInfo.getSubMenu().stream().map(m -> convertToMenu(m)).collect(Collectors.toList());

        return Menu.builder()
                .id(menuInfo.getId())
                .pageUrl(menuInfo.getPageUrl())
                .roles(roleSet)
                .subMenuList(menuList)
                .menuName(menuInfo.getMenuName())
                .menuBundleKey(menuInfo.getMenuBundleKey())
                .build();
    }

    private Set<Role> makeRoleSet(String roles) {
        Set<Role> roleSet = new HashSet<>();
        // 특정 메뉴가 role 설정이 되어 있지 않은 경우, 빈값 리턴
        if(roles == null) return roleSet;

        String[] rolesArr = roles.split(",");
        for(String role : rolesArr) {
            roleSet.add(roleService.getRole(role.trim()));
        }
        return roleSet;
    }

    public List<Menu> getAllMenuList() {
        return allMenuList;
    }

    public List<Menu> findMenuListByRole(String roleId) {
        List<Menu> list = menuListByRolId.get(roleId);
        if(list == null) throw new RuntimeException("해당 role의 메뉴가 존재하지 않습니다. roleName:" + roleId);
        return list;
    }
}

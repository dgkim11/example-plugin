package example.plugin.main.application.menu;


import example.plugin.main.domain.menu.Menu;
import example.plugin.main.domain.menu.MenuService;
import example.plugin.main.domain.role.Role;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import example.plugin.main.domain.user.User;
import example.plugin.main.domain.user.UserRepository;

/**
 * 사용자별로 메뉴가 달라지기 때문에 사용자별 메뉴 리스트를 찾아준다.
 */
@Service
public class MenuFinder {
    private MenuService menuService;
    private UserRepository userRepository;
    /**
     * 각각의 메뉴별로 권한을 가지는 role들을 저장한다.
     */
    private Map<String, Set<String>> roleSetByMenu = new HashMap<>();
    private Map<String, Menu> menuMap = new HashMap<>();

    public MenuFinder(MenuService menuService, UserRepository userRepository) {
        this.menuService = menuService;
        this.userRepository = userRepository;
        makeRoleSetByMenu(Collections.emptySet(), menuService.getAllMenuList());

    }

    public Menu getMenu(String userId, String menuId) throws NotAccessableMenuException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new RuntimeException("해당 사용자를 찾을 수 없습니다. userId:" + userId);

        Set<String> roleNames = roleSetByMenu.get(menuId);
        if(! isAccessable(roleNames, user.get().getRoleId()))
            throw new NotAccessableMenuException("can't access the menu for the user. menuId:" + menuId + "userId:" + userId);

        return menuMap.get(menuId);
    }


    public List<Menu> findMenuListByUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) return Collections.emptyList();
        return menuService.findMenuListByRole(user.get().getRoleId());
    }

    public List<Menu> findMenuListByRole(String roleName) {
        return menuService.findMenuListByRole(roleName);
    }

    private boolean isAccessable(Set<String> roleNames, String roleName) {
        if(roleNames == null) return false;     // 해당 메뉴는 아무도 볼 수 없다.
        if(roleNames.isEmpty()) return true;    // 해당 페이지는 누구나 볼 수 있다.
        return roleNames.contains(roleName);
    }


    private void makeRoleSetByMenu(Set<Role> parentRoles, List<Menu> allMenuList) {

        for(Menu menu : allMenuList)    {
            // 기본 설정은 모든 roles에서 볼 수 있음. role set이 비어있으면 모두에게 권한이 있는 것임.
            Set<String> newRoles = new HashSet<>();
            // 부모의 role 집합과 자식의 role 집합이 다른 경우, 부모와 자식의 role의 교집합만 권한이 있다.
            if(parentRoles.size() > 0 && menu.getRoles().size() > 0)    {
                Set<String> leftRoles = convertToRoleNames(parentRoles);
                Set<String> rightRoles = convertToRoleNames(menu.getRoles());
                leftRoles.retainAll(rightRoles);
                if(leftRoles.isEmpty())  continue;      // 해당 메뉴는 아무도 볼 수 없다.
            // 부모의 role 집합만 있고, 자식의 role 집합이 없는 경우 부모의 role 상속
            } else if(!parentRoles.isEmpty() && menu.getRoles().isEmpty())  {
                newRoles = convertToRoleNames(parentRoles);
            // 부모의 role 집합은 없고, 자식의 role 집합만 있는 경우 자식의 role로 설정
            } else if(parentRoles.isEmpty() && ! menu.getRoles().isEmpty()) {
                newRoles = convertToRoleNames(menu.getRoles());
            }
            roleSetByMenu.put(menu.getId(), newRoles);
            menuMap.put(menu.getId(), menu);

            // 하위 sub menu들에 대해서 recursive하게 메뉴별 role 구성
            if(! menu.getSubMenuList().isEmpty())  {
                makeRoleSetByMenu(menu.getRoles(), menu.getSubMenuList());
            }
        }
    }

    private Set<String> convertToRoleNames(Set<Role> parentRoles) {
        return parentRoles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}

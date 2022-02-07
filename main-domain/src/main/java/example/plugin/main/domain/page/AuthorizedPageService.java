package example.plugin.main.domain.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.plugin.main.domain.role.Role;
import example.plugin.main.domain.role.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthorizedPageService {
    private ObjectMapper objectMapper;
    private RoleService roleService;
    private Map<String, Set<Role>> rolesByPage = new HashMap<>();

    public AuthorizedPageService(ObjectMapper objectMapper, RoleService roleService) {
        this.objectMapper = objectMapper;
        this.roleService = roleService;
    }

    public boolean canAccessTo(String roleName, String pageUrl) {
        Set<Role> roles = rolesByPage.get(pageUrl);
        if(roles != null) return roles.contains(roleName);
        return true;
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

    public void addPage(String pageUrl, Set<Role> roles) {
        rolesByPage.put(pageUrl, roles);
    }
}

package example.plugin.main.domain.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.plugin.main.domain.page.config.AuthorizedPageConfig;
import example.plugin.main.domain.role.Role;
import example.plugin.main.domain.role.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorizedPageService {
    private static final String PAGE_FILE = "/authorized_pages.yaml";

    private ObjectMapper objectMapper;
    private RoleService roleService;
    private Map<String, Set<String>> rolesByPage = new HashMap<>();

    public AuthorizedPageService(ObjectMapper objectMapper, RoleService roleService) {
        this.objectMapper = objectMapper;
        this.roleService = roleService;
    }

    @PostConstruct
    public void readAuthorizedPages() throws URISyntaxException, IOException {
        AuthorizedPageConfig pageConfig;

        // 배포가 jar 파일로 된 경우
        InputStream in = getClass().getClassLoader().getResourceAsStream(PAGE_FILE);
        // 배포가 개별 파일로 풀려져서 배포된 경우
        if(in == null)  {
            File file = new File(getClass().getResource(PAGE_FILE).toURI());
            pageConfig = objectMapper.readValue(file, AuthorizedPageConfig.class);
        } else  {
            pageConfig = objectMapper.readValue(in, AuthorizedPageConfig.class);
        }

        pageConfig.getPages().forEach(pageInfo -> rolesByPage.put(pageInfo.getUrl(), makeRoleSet(pageInfo.getRoles())));
    }

    public boolean canAccessTo(String roleName, String pageUrl) {
        Set<String> roles = rolesByPage.get(pageUrl);
        if(roles != null) return roles.contains(roleName);
        return true;
    }

    private Set<String> makeRoleSet(String roles) {
        Set<String> roleSet = new HashSet<>();
        // 특정 메뉴가 role 설정이 되어 있지 않은 경우, 빈값 리턴
        if(roles == null) return roleSet;

        String[] rolesArr = roles.split(",");
        for(String role : rolesArr) {
            roleSet.add(roleService.getRole(role.trim()).getId());
        }
        return roleSet;
    }

    public void addPage(String pageUrl, Set<Role> roles) {
        rolesByPage.put(pageUrl, convertToRoleIdSet(roles));
    }

    private Set<String> convertToRoleIdSet(Set<Role> roles) {
        return roles.stream().map(r -> r.getId()).collect(Collectors.toSet());
    }
}

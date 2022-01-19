package example.plugin.main.domain.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.plugin.main.domain.role.config.RoleConfig;
import example.plugin.main.domain.role.config.RoleInfo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private ObjectMapper objectMapper;
    private Map<String, Role> roleByNameMap = new HashMap<>();
    private static final String ROLE_FILE = "/roles.yaml";

    public RoleService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void makeRoles() throws IOException, URISyntaxException {
        RoleConfig roleConfig;

        // jar 형태로 배포되어 jar 파일내에 있는 resource 파일을 읽는 경우
        InputStream in = getClass().getResourceAsStream(ROLE_FILE);

        // 그러나, 배포가 jar 형태가 아니라 풀어서 배포된 경우에는 input stream이 null이다.
        if(in == null)  {   // 풀어서 배포된 경우
            File file = new File(getClass().getResource(ROLE_FILE).toURI());
            roleConfig = objectMapper.readValue(file, RoleConfig.class);
        } else  {           // jar로 배포된 경우
            roleConfig = objectMapper.readValue(in, RoleConfig.class);
        }
        convertToRolesMap(roleConfig);
    }

    private void convertToRolesMap(RoleConfig roleConfig) {
        for(RoleInfo roleInfo : roleConfig.getRoles())  {
            roleByNameMap.put(roleInfo.getName(), convertToRole(roleInfo));
        }
    }

    private Role convertToRole(RoleInfo roleInfo) {
        return new Role(roleInfo.getName(), roleInfo.getDescription());
    }

    public Role getRole(String roleName) {
        Role role = roleByNameMap.get(roleName);
        if(role == null) throw new RuntimeException("찾을 수 없는 role. roleName:" + roleName);
        return role;
    }

    public List<Role> getAllRoles() {
        return roleByNameMap.values().stream().collect(Collectors.toList());
    }
}

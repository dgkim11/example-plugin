package example.plugin.main.domain.page.config;

import lombok.Getter;

@Getter
public class AuthorizedPageInfo {
    private String url;
    private String name;
    private String description;
    private String roles;
}

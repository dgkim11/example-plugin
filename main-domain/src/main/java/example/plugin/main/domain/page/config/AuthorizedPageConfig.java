package example.plugin.main.domain.page.config;

import lombok.Getter;

import java.util.List;

@Getter
public class AuthorizedPageConfig {
    private List<AuthorizedPageInfo> pages;
}

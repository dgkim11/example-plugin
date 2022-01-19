package example.plugin.main.domain.menu.config;

import lombok.Getter;

import java.util.List;

/**
 * 파일에 저장된 menu의 설정 정보를 가진다.
 */
@Getter
public class MenuConfig {
    private List<MenuInfo> menus;
}

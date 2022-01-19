package example.plugin.main.domain.menu.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 파일에 저장된 menu의 설정 정보를 가진다.
 */
@Getter
public class MenuInfo {
    private String id;
    /**
     * 메뉴를 클릭하였을 때 이동할 page url.
     */
    private String pageUrl;
    private String title;
    /**
     * i8n을 지원하기 위해서 titleKey가 있는 경우 title 대신에 message bundle에 있는 key값을 넣는다.
     */
    private String titleKey;
    private String roles;
    /**
     * 하위 menu
     */
    private List<MenuInfo> menus = new ArrayList<>();
}

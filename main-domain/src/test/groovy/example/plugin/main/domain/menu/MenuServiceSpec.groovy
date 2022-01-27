package example.plugin.main.domain.menu

import example.plugin.main.domainconfig.DomainConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * menu.yaml 파일에 있는 내용을 바탕으로 menu에 대한 테스트가 수행된다.
 */
@ContextConfiguration(classes = [DomainConfig.class])
class MenuServiceSpec extends Specification {
    @Autowired private MenuService sut

    def "yaml 파일에서 모든 메뉴를 읽어들인다."()   {
        when:
        List<Menu> menuList = sut.getAllMenuList()

        then:
        menuList.size() == 3
        menuList.get(0).getMenuList().size() == 3
        menuList.get(1).getMenuList().size() == 3
        menuList.get(2).getMenuList().size() == 3
    }

    def "메뉴에 title 속성이 없고 locale이 한국인 경우 한국 메세지를 보여준다."()    {
        when:
        List<Menu> menuList = sut.getAllMenuList()

        then:
        menuList.get(0).getMenuList().get(0).getMenuName(Locale.KOREA) == "페이지11.한글"
        menuList.get(1).getMenuName(Locale.KOREA) == "페이지2.한글"
        menuList.get(1).getMenuList().get(0).getMenuName(Locale.KOREA) == "페이지22.한글"
        menuList.get(2).getMenuName(Locale.KOREA) == "페이지3.한글"
    }

    def "메뉴에 title 속성이 없고 locale이 영어인 경우 영어 메세지를 보여준다."()    {
        when:
        List<Menu> menuList = sut.getAllMenuList()

        then:
        menuList.get(0).getMenuList().get(0).getMenuName(Locale.KOREA) == "페이지11.한글"
        menuList.get(1).getMenuName(Locale.KOREA) == "페이지2.한글"
        menuList.get(1).getMenuList().get(0).getMenuName(Locale.KOREA) == "페이지22.한글"
        menuList.get(2).getMenuName(Locale.KOREA) == "페이지3.한글"
    }

    def "메뉴에 title 속성이 있는 경우 locale에 상관없이 title 메세지를 보여준다."()   {
        when:
        List<Menu> menuList = sut.getAllMenuList()

        then:
        menuList.get(0).getMenuName(Locale.KOREA) == "페이지1"
        menuList.get(2).getMenuList().get(0).getMenuName(Locale.KOREA) == "페이지31"
    }

    def "title, titleKey 모두 없으면 title은 menuId가 기본 값이 된다."() {
        when:
        List<Menu> menuList = sut.getAllMenuList()

        then:
        menuList.get(1).getMenuList().get(2).getMenuName(Locale.KOREA) == "menu23"
    }
}

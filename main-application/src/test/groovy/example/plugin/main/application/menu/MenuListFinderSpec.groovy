package example.plugin.main.application.menu

import example.plugin.main.TestMainApp
import example.plugin.main.domain.menu.Menu
import example.plugin.main.domain.user.User
import example.plugin.main.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * 해당 테스트는 테스트용 menu.yaml, roles.yaml에 설정된 내용을 바탕으로 결과값을 확인한다.
 */
@SpringBootTest(classes = [TestMainApp.class])
class MenuListFinderSpec extends Specification  {
    private User role1User, role2User, role3User
    @Autowired private UserRepository userRepository
    @Autowired private MenuFinder sut

    def setup() {
        role1User = new User("role1User", "홍길동", "role1", "")
        role2User = new User("role2User", "홍길동", "role2", "")
        role3User = new User("role3User", "홍길동", "role3", "")
        userRepository.save(role1User)
        userRepository.save(role2User)
        userRepository.save(role3User)
    }

    def cleanup()   {
        userRepository.deleteById(role1User.getUserId())
        userRepository.deleteById(role2User.getUserId())
        userRepository.deleteById(role3User.getUserId())
    }

    def "role1의 사용자 menu만 가져온다."() {
        when:
        List<Menu> menuList = sut.findMenuListByUser(role1User.getUserId())

        then: "명시적인 role이 없거나, 자신이 role이 포함된 메뉴만 갖는다."
        menuList.size() == 2
        menuList.get(0).getSubMenuList().size() == 3
        menuList.get(1).getSubMenuList().size() == 2
        // role1은 아래와 같은 메뉴 구성을 갖는다.
        //        - id: menu1
        //          pageUrl: /page/page1
        //          menus:
        //            - id: menu11
        //              roles: role1, role2
        //              pageUrl: /page/page11
        //            - id: menu12
        //              pageUrl: /page/page12
        //            - id: menu13
        //              pageUrl: /page/page13
        //        - id: menu2
        //          pageUrl: /page/page2
        //          menus:
        //            - id: menu21
        //              pageUrl: /page/page21
        //            - id: menu23
        //              pageUrl: /page/page23
    }
    def "role2의 사용자 menu만 가져온다."() {
        when:
        List<Menu> menuList = sut.findMenuListByUser(role2User.getUserId())

        then: "명시적인 role이 없거나, 자신이 role이 포함된 메뉴만 갖는다."
        menuList.size() == 2
        menuList.get(0).getSubMenuList().size() == 3
        menuList.get(1).getSubMenuList().size() == 3
        // role2은 아래와 같은 메뉴 구성을 갖는다.
        //        - id: menu1
        //          pageUrl: /page/page1
        //          menus:
        //            - id: menu11
        //              roles: role1, role2
        //              pageUrl: /page/page11
        //            - id: menu12
        //              pageUrl: /page/page12
        //            - id: menu13
        //              pageUrl: /page/page13
        //        - id: menu2
        //          pageUrl: /page/page2
        //          menus:
        //            - id: menu21
        //              pageUrl: /page/page21
        //            - id: menu22
        //              pageUrl: /page/page22
        //            - id: menu23
        //              pageUrl: /page/page23
    }
    def "role3의 사용자 menu만 가져온다."() {
        when:
        List<Menu> menuList = sut.findMenuListByUser(role3User.getUserId())

        then: "명시적인 role이 없거나, 자신이 role이 포함된 메뉴만 갖는다."
        menuList.size() == 3
        menuList.get(0).getSubMenuList().size() == 2
        menuList.get(1).getSubMenuList().size() == 3
        menuList.get(2).getSubMenuList().size() == 3
//  role3은 아래와 같은 메뉴 구성을 갖는다.
//        menus:
//            - id: menu1
//              pageUrl: /page/page1
//              menus:
//              - id: menu12
//                pageUrl: /page/page12
//              - id: menu13
//                pageUrl: /page/page13
//            - id: menu2
//              pageUrl: /page/page2
//                menus:
//                - id: menu21
//                pageUrl: /page/page21
//                - id: menu22
//                pageUrl: /page/page22
//                - id: menu23
//                pageUrl: /page/page23
//            - id: menu3
//              pageUrl: /page/page3
//                menus:
//                - id: menu31
//                pageUrl: /page/page31
//                - id: menu32
//                pageUrl: /page/page32
//                - id: menu33
//                pageUrl: /page/page33
    }

    def "사용자는 자신에게 권한이 없는 페이지를 요청할 수 없다."()  {
        expect:
        sut.getMenu("role1User", "menu31")
        ! sut.canDisplayMenuForUser("role2User", "menu33")
        ! sut.canDisplayMenuForUser("role3User", "menu11")
        sut.canDisplayMenuForUser("role1User", "menu11")
        sut.canDisplayMenuForUser("role2User", "menu21")
        sut.canDisplayMenuForUser("role3User", "menu31")
    }
}

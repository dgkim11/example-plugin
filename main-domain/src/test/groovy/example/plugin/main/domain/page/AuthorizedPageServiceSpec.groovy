package example.plugin.main.domain.page

import example.plugin.main.domain.TestDomainMainApp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * 테스트를 위한 yaml 파일은 resources/authorized_pages.yaml에 존재한다.
 */
@SpringBootTest(classes = [TestDomainMainApp.class])
class AuthorizedPageServiceSpec extends Specification  {
    @Autowired private AuthorizedPageService sut

    def "admin 권한이 있으면 admin 권한이 있는 페이지에 접근할 수 있다."()   {
        when:
        boolean accessable = sut.canAccessTo("admin", "/admin/page")

        then:
        accessable
    }
    def "admin 권한이 없으면 admin 권한이 있는 페이지에 접근할 수 없다."()   {
        when:
        boolean accessable = sut.canAccessTo("operator", "/admin/page")

        then:
        ! accessable
    }
    def "operator 권한이 있으면 operator 권한이 있는 페이지에 접근할 수 있다."()   {
        when:
        boolean accessable = sut.canAccessTo("operator", "/operator/page")

        then:
        accessable
    }
    def "operator 권한이 없으면 operator 권한이 있는 페이지에 접근할 수 없다."()   {
        when:
        boolean accessable = sut.canAccessTo("admin", "/operator/page")

        then:
        ! accessable
    }
}

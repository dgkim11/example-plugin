package example.plugin.main.application.security.auth;

import example.plugin.main.domain.user.User;

/**
 * 애플리케이션의 기본 인증 방식이 아닌 커스텀 형태로 인증을 하는 경우 해당 인터페이스를 구현하고, application.properties 파일에
 * authentication provider로 등록한다.
 */
public interface CustomAuthenticationProvider {
    public User authenticate(String loginId, String password);
}

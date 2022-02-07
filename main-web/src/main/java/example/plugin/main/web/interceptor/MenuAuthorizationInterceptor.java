package example.plugin.main.web.interceptor;

import example.plugin.main.domain.page.AuthorizedPageService;
import example.plugin.main.web.security.auth.AuthorizationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 권한이 없는 사용자의 페이지 접근을 막는다.
 */
public class MenuAuthorizationInterceptor implements HandlerInterceptor {
    private AuthorizedPageService authPageService;

    public MenuAuthorizationInterceptor(AuthorizedPageService authPageService) {
        this.authPageService = authPageService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = authorities.iterator().next().getAuthority();
        if(! authPageService.canAccessTo(role, uri)) throw new AuthorizationException("no access right to this page");

        return true;
    }
}

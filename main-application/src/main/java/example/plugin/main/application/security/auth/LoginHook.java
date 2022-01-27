package example.plugin.main.application.security.auth;

import example.plugin.main.application.hook.AbstractHookEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginHook extends AbstractHookEvent {
    public static final String ID = "auth.login";

    private final String loginId;
    private final String password;

    @Override
    public String id() {
        return ID;
    }
}

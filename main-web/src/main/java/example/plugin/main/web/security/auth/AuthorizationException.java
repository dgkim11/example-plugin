package example.plugin.main.web.security.auth;

public class AuthorizationException extends RuntimeException  {
    public AuthorizationException(String message) {
        super(message);
    }
}

package example.plugin.main.application.hook;

public interface HookEvent {
    String id();
    void addAttribute(Object key, Object value);
    Object getAttribute(Object key);
    void setResponseBody(Object responseBody);
    Object getResponseBody();
}

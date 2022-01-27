package example.plugin.main.application.hook;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHookEvent implements HookEvent {
    private Map<Object, Object> attrs = new HashMap<>();
    @Getter @Setter
    private Object responseBody;

    @Override
    public void addAttribute(Object key, Object value) {
        attrs.put(key, value);
    }

    @Override
    public Object getAttribute(Object key) {
        return attrs.get(key);
    }
}

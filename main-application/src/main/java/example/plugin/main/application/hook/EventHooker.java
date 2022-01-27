package example.plugin.main.application.hook;


public interface EventHooker {
    boolean beforeEventHook(HookEvent event);
    void afterEventHook(HookEvent event);
}

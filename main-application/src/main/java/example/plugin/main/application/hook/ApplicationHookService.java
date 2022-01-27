package example.plugin.main.application.hook;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationHookService {
    private Map<String, EventHooker> eventHookerMap = new HashMap<>();

    /**
     * 커스터마이징이 필요한 쪽에서 hooker를 등록한다.
     * @param hookId
     * @param eventHooker
     */
    public void addHook(String hookId, EventHooker eventHooker)   {
        eventHookerMap.put(hookId, eventHooker);
    }

    /**
     * 이벤트가 발생하기 전에 후킹을 한다. 즉, 애플리케이션 내부에서 특정 작업이 수행되기 전에 먼저 호출된다.
     * @param event
     * @return true - 이벤트 발생시킴,  false - 이벤트를 발생시키지 않는다. 즉, 해당 이벤트를 스킵한다
     */
    public boolean beforeEventHooker(HookEvent event) {
        EventHooker hooker = eventHookerMap.get(event.id());
        if(hooker == null) return true;
        return hooker.beforeEventHook(event);
    }

    /**
     * 후킹 이벤트 발생 후에 후킹을 한다. 즉, 애플리케이션 내부에서 특정 작업이 완료된 후에 호출된다.
     * @param event
     */
    public void afterEventHooker(HookEvent event)     {
        EventHooker hooker = eventHookerMap.get(event.id());
        if(hooker == null) return;
        hooker.afterEventHook(event);
    }
}

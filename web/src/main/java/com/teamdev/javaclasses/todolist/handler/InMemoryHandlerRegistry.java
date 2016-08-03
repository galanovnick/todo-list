package com.teamdev.javaclasses.todolist.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryHandlerRegistry implements HandlerRegistry {

    private final Logger log = LoggerFactory.getLogger(HandlerRegistry.class);

    private static HandlerRegistry instance;

    private final Map<UrlMethodPair, Handler> handlers = new HashMap<>();

    @Override
    public Optional<Handler> getHandler(UrlMethodPair key) {
        return Optional.ofNullable(handlers.get(key));
    }

    @Override
    public void register(UrlMethodPair key, Handler handler) {
        if (log.isDebugEnabled()) {
            log.debug(String.format(("Trying to register handler for url = '%s' and method = '%s'..."),
                    key.getUrl(), key.getMethod()));
        }
        handlers.put(key, handler);
        if (log.isDebugEnabled()) {
            log.debug(String.format(("Handler for url = '%s' and method = '%s' " +
                            "has been successfully registered."),
                    key.getUrl(), key.getMethod()));
        }
    }

    private InMemoryHandlerRegistry(){
    }

    public static HandlerRegistry getInstance() {
        if (instance == null) {
            return instance = new InMemoryHandlerRegistry();
        }
        return instance;
    }
}
package com.keep.sso.ticket.manager;

import com.keep.sso.ticket.service.KeepTokenService;
import com.keep.sso.ticket.service.TokenRegistryService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TicketRegistryServiceManager {

    private static final Map<Class, KeepTokenService> MAPS = new HashMap<>();

    public KeepTokenService getService(Class clazz) {
        return MAPS.get(clazz);
    }

    public void setService(Class clazz, KeepTokenService keepTokenService) {
        MAPS.put(clazz, keepTokenService);
    }
}

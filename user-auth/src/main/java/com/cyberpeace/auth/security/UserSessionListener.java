package com.cyberpeace.auth.security;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSessionListener implements ApplicationListener<AuthenticationSuccessEvent> {




    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        log.info("event ====== >{}",JSON.toJSONString(event));
    }
}

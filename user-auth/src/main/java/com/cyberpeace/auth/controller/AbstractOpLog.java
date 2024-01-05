package com.cyberpeace.auth.controller;

import com.cyberpeace.auth.service.OpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractOpLog {

    @Autowired
    private OpLogService opLogService;

    public void log(Integer method,String target,String detail) {
        opLogService.save(getModule(),method,target,detail);
    }

    protected abstract Integer getModule();

}
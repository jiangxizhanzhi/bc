package com.cyberpeace.oj.person.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.oj.person.controller.req.OpLogReq;
import com.cyberpeace.oj.person.dao.entity.OpLog;
import com.cyberpeace.oj.person.dao.mapper.OpLogMapper;
import com.cyberpeace.oj.person.service.OpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpLogServiceImpl extends ServiceImpl<OpLogMapper, OpLog> implements OpLogService {

    @Autowired
    private OpLogMapper evalOpLogMapper;

    @Override
    public IPage<OpLog> getPage(OpLogReq logReq) {

        return evalOpLogMapper.getPage(logReq.toPageParam(), logReq);
    }

    @Override
    public void save(Integer module, Integer method, String target, String detail) {
        OpLog log = new OpLog();
        log.setModule(module);
        log.setMethod(method);
        log.setTarget(target);
        log.setDetail(detail);
        log.setName(UserInfoContext.getUserContext().getName());
        log.setUsername(UserInfoContext.getUserContext().getUserName());
        log.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        this.save(log);
    }
}

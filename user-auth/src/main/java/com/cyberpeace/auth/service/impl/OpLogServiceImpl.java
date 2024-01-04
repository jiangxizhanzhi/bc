package com.cyberpeace.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.auth.controller.req.OpLogReq;
import com.cyberpeace.auth.service.bo.OpLog;
import com.cyberpeace.auth.mapper.OpLogMapper;
import com.cyberpeace.auth.service.OpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpLogServiceImpl extends ServiceImpl<OpLogMapper, OpLog> implements OpLogService {

    @Autowired
    private OpLogMapper evalOpLogMapper;
    @Autowired
    private SysUserService sysUserService;

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

    @Override
    public void save(Integer module, Integer method, String target, String detail, TbUser user) {
        OpLog log = new OpLog();
        log.setModule(module);
        log.setMethod(method);
        log.setTarget(target);
        log.setDetail(detail);
        SysUser sysUser = sysUserService.getById(user.getUserId());
        log.setName(sysUser.getName());
        log.setUsername(sysUser.getUsername());
        log.setCreateUserId(user.getUserId());
        this.save(log);
    }
}

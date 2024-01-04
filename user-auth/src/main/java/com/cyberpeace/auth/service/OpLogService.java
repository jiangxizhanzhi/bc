package com.cyberpeace.auth.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.auth.controller.req.OpLogReq;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.service.bo.OpLog;


public interface OpLogService extends IService<OpLog> {

    IPage<OpLog> getPage(OpLogReq logReq);

    void save(Integer module, Integer method, String target, String detail);
    void save(Integer module, Integer method, String target, String detail, TbUser user);
}

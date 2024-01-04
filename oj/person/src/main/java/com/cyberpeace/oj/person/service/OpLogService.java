package com.cyberpeace.oj.person.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.person.controller.req.OpLogReq;
import com.cyberpeace.oj.person.dao.entity.OpLog;


public interface OpLogService extends IService<OpLog> {

    IPage<OpLog> getPage(OpLogReq logReq);

    void save(Integer module, Integer method, String target, String detail);
}

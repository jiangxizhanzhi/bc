package com.cyberpeace.oj.person.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.person.controller.req.OpLogReq;
import com.cyberpeace.oj.person.dao.entity.OpLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface OpLogMapper extends BaseMapper<OpLog> {

    IPage<OpLog> getPage(@Param("page")Page toPageParam, @Param("logReq") OpLogReq logReq);
}

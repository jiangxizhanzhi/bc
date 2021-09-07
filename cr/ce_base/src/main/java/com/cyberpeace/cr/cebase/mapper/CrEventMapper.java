package com.cyberpeace.cr.cebase.mapper;

import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
@Repository
public interface CrEventMapper extends BaseMapper<CrEvent> {}

package com.cyberpeace.oj.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.rtks.dao.entity.Frame;

@Mapper
@Repository
public interface FrameMapper extends BaseMapper<Frame> {

}

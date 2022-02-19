package com.cyberpeace.cr.hld.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.cyberpeace.cr.hld.dao.entity.HldRoom;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HldRoomMapper extends BaseMapper<HldRoom> {

}

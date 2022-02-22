package com.cyberpeace.cr.hld.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.cyberpeace.cr.hld.dao.entity.HldRoom;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HldRoomMapper extends BaseMapper<HldRoom> {

    HldRoom selectByBusinessTemplateId(String businessTemplateId);

    List<String> listBusinessTemplateIds(List<String> hldRoomIds);

}

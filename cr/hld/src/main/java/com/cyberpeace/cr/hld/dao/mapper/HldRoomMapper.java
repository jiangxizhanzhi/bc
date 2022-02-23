package com.cyberpeace.cr.hld.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;

@Mapper
@Repository
public interface HldRoomMapper extends BaseMapper<HldRoom> {

	HldRoom selectByBusinessTemplateId(String businessTemplateId);

	List<String> listBusinessTemplateIds(List<String> hldRoomIds);

}

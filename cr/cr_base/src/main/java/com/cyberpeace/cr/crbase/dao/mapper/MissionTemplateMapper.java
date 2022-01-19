package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

@Mapper
@Repository
public interface MissionTemplateMapper extends BaseMapper<MissionTemplate> {

	void batchInsert(@Param("missionTemplates") List<MissionTemplate> missionTemplates);

}

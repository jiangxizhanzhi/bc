package com.cyberpeace.cr.crbase.dao.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

@Mapper
@Repository
public interface MissionTemplateMapper extends BaseMapper<MissionTemplate> {

}

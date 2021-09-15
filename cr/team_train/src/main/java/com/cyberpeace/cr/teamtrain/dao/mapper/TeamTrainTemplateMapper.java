package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplate;

@Mapper
@Repository
public interface TeamTrainTemplateMapper extends BaseMapper<TeamTrainTemplate> {

}

package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.MissionSituation;

/**
 * 任务态势Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface MissionSituationMapper extends BaseMapper<MissionSituation> {

}

package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationTopo;
import org.apache.ibatis.annotations.Param;

/**
 * 拓扑配置映射器
 *
 * @Author xuxz
 * @Date 27/2/2023 下午2:21
 */
@Mapper
public interface SituationTopoMapper extends BaseMapper<SituationTopo> {

    String getEnvIdBySubjectIdAndTaskId(@Param("subjectId") String subjectId, @Param("taskId") String taskId);

}

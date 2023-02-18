package com.cyberpeace.cr.scene_manage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.scene_manage.dao.entity.SceneTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SceneTemplateMapper extends BaseMapper<SceneTemplate> {

    SceneTemplate getByBusinessId(@Param("resource_id") String trainBusinessId);

    List<SceneTemplate> batchGetByBusinessId(List<String> businessTemplateIds);

}

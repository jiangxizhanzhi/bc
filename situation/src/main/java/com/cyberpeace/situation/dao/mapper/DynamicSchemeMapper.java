package com.cyberpeace.situation.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicSchemeMapper extends BaseMapper<DynamicScheme> {

    List<DynamicSchemeNameRes> getAllNamesBySceneId(String sceneId);

    void deleteBySceneId(String sceneId);

    DynamicScheme selectByName(@Param("name") String name,@Param("sceneId") String sceneId);

    DynamicScheme selectByResourceId(String resourceId);

    void updateScheduleById(@Param("schedule") String schedule,@Param("resourceId")String resourceId);

    String getScheduleBySceneId(String sceneId);
}

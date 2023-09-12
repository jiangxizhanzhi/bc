package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SituationGuideSchemeMapper extends BaseMapper<SituationGuideScheme> {

    void deleteBySceneId(String sceneId);
}

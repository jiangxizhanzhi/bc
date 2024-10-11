package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.entity.RbAttackPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RbAttackPathMapper extends BaseMapper<RbAttackPath> {

    List<RbAttackPath> queryAttackPath(@Param("sceneId") String sceneId);

    List<RbAttackPath> queryAttackPathByTemplateId(@Param("templateId") String templateId);

    void addAttackPath(@Param("attackPath") RbAttackPath attackPath);

    void deleteAttackPath(@Param("resourceId") String resourceId, @Param("sceneId") String sceneId,  @Param("templateId") String templateId);

    void deleteAttackPathByTemplateId(@Param("templateId") String templateId);
}

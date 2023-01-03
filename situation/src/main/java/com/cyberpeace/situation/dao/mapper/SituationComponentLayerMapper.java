package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.ComponentLayer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午9:36
 */

@Mapper
public interface SituationComponentLayerMapper extends BaseMapper<ComponentLayer> {

    void batchInsert(@Param("componentLayerList") List<ComponentLayer> componentLayerList);

    List<ComponentLayer> selectByTemplateId(@Param("templateId") String templateId);
}

package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午4:42
 */
@Mapper
public interface SituationDynamicEffectMappingMapper extends BaseMapper<DynamicEffectsMapping> {
    IPage<DynamicEffectsMapping> selectPage(@Param("page") Page page,
                                            @Param("dynamicEffectMappingPageReq") DynamicEffectMappingPageReq dynamicEffectMappingPageReq);

    List<DynamicEffectsMapping> selectBatchByType(@Param("types") Collection<String> types);
}

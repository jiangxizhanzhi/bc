package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffectMapping;
import com.cyberpeace.cr.crbase.service.context.effect.SituationEffectBo;

@Mapper
@Repository
public interface SituationEffectMappingMapper extends BaseMapper<SituationEffectMapping> {

	IPage<SituationEffectBo> page(@Param("page") Page page, @Param("pattern") String pattern);

	boolean ifNameDuplicate(@Param("name") String name, @Param("resourceId") String resourceId);
}

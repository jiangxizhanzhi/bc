package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffect;
import com.cyberpeace.cr.crbase.service.context.effect.SituationEffectBo;

@Mapper
@Repository
public interface SituationEffectMapper extends BaseMapper<SituationEffect> {

	IPage<SituationEffectBo> pageEffect(Page page, String category, String pattern);

	boolean ifNameDuplicate(String name, String resourceId);
}

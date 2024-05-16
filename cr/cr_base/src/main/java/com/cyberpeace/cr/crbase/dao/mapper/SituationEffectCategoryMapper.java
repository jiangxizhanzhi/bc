package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffectCategory;
import com.cyberpeace.cr.crbase.service.context.effect.SituationEffectBo;
import com.cyberpeace.cr.crbase.service.context.effect.SituationEffectCategoryName;

@Mapper
@Repository
public interface SituationEffectCategoryMapper extends BaseMapper<SituationEffectCategory> {

	List<SituationEffectCategoryName> listAllName();

	IPage<SituationEffectBo> page(Page page, String pattern);

	boolean ifNameDuplicate(String name, String resourceId);
}

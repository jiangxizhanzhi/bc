package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffect;
import com.cyberpeace.cr.crbase.service.context.effect.SituationEffectBo;

/**
 * 态势动效Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface SituationEffectMapper extends BaseMapper<SituationEffect> {

	/**
	 * 分页动效
	 *
	 * @param page     分页
	 * @param category 类别
	 * @param pattern  模式
	 * @return {@code IPage<SituationEffectBo> }
	 */
	IPage<SituationEffectBo> pageEffect(Page page, String category, String pattern);

	/**
	 * 如果名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean ifNameDuplicate(String name, String resourceId);
}

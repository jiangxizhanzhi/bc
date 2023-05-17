package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DynamicEffectsCategory;

/**
 * 动效类型映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface DynamicEffectsCategoryMapper extends BaseMapper<DynamicEffectsCategory> {

	/**
	 * 校验动效分组是否已存在
	 *
	 * @param resourceId 资源id
	 * @param name       名称
	 * @return {@code Boolean}
	 */
	Boolean checkCategoryName(@Param("resourceId") String resourceId, @Param("name") String name);

}

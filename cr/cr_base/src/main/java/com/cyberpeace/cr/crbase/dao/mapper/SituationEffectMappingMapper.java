package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffectMapping;
import com.cyberpeace.cr.crbase.service.context.effect.SituationEffectBo;

/**
 * 态势动效映射Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
public interface SituationEffectMappingMapper extends BaseMapper<SituationEffectMapping> {

	/**
	 * 分页
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @return {@code IPage<SituationEffectBo> }
	 */
	IPage<SituationEffectBo> page(@Param("page") Page page, @Param("pattern") String pattern);

	/**
	 * 如果名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean ifNameDuplicate(@Param("name") String name, @Param("resourceId") String resourceId);
}

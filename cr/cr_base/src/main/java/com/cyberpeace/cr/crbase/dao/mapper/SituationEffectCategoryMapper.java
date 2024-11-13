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

/**
 * 态势动效类别Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface SituationEffectCategoryMapper extends BaseMapper<SituationEffectCategory> {

	/**
	 * 列表全部名称
	 *
	 * @return {@code List<SituationEffectCategoryName> }
	 */
	List<SituationEffectCategoryName> listAllName();

	/**
	 * 分页
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @return {@code IPage<SituationEffectBo> }
	 */
	IPage<SituationEffectBo> page(Page page, String pattern);

	/**
	 * 如果名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean ifNameDuplicate(String name, String resourceId);
}

package com.cyberpeace.situation.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;

/**
 * 动效映射记录映射器
 *
 * @Author xuxz
 * @Date 26/11/2022 下午4:42
 */
@Mapper
public interface SituationDynamicEffectMappingMapper extends BaseMapper<DynamicEffectsMapping> {

	/**
	 * 分页查询
	 *
	 * @param page                        分页条件
	 * @param dynamicEffectMappingPageReq 查询条件
	 * @return {@code IPage<DynamicEffectsMapping>}
	 */
	IPage<DynamicEffectsMapping> selectPage(@Param("page") Page page,
			@Param("dynamicEffectMappingPageReq") DynamicEffectMappingPageReq dynamicEffectMappingPageReq);

	/**
	 * 通过类型获取映射记录列表
	 *
	 * @param types      类型
	 * @param subRangeId 分靶场ID
	 * @return {@code List<DynamicEffectsMapping>}
	 */
	List<DynamicEffectsMapping> selectBatchByType(@Param("types") Collection<String> types,
			@Param("subRangeId") String subRangeId);

	/**
	 * 校验映射记录是否存在
	 *
	 * @param attackType 攻击类型
	 * @param subRangeId 分靶场ID
	 * @param resourceId 资源id
	 * @return {@code Boolean}
	 */
	Boolean checkEffectsMapExist(@Param("attackType") String attackType,
			@Param("subRangeId") String subRangeId, @Param("resourceId") String resourceId);
}

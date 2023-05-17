package com.cyberpeace.situation.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.controller.res.DynamicEffectMappingRes;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;

/**
 * 动效映射记录映射器
 *
 * @Author xuxz
 * @Date 26/11/2022 下午4:42
 */
@Mapper
public interface DynamicEffectMappingMapper extends BaseMapper<DynamicEffectsMapping> {

	/**
	 * 分页查询
	 *
	 * @param page                        分页条件
	 * @param dynamicEffectMappingPageReq 查询条件
	 * @return {@code IPage<DynamicEffectsMapping>}
	 */
	IPage<DynamicEffectMappingRes> selectPage(@Param("page") Page page,
			@Param("dynamicEffectMappingPageReq") DynamicEffectMappingPageReq dynamicEffectMappingPageReq);

	/**
	 * 通过类型获取映射记录列表
	 *
	 * @param types      类型
	 * @return {@code List<DynamicEffectsMapping>}
	 */
	List<DynamicEffectsMapping> selectBatchByType(@Param("types") Collection<String> types);

	/**
	 * 校验映射记录是否存在
	 *
	 * @param attackType 攻击类型
	 * @param type 		 映射类型
	 * @param resourceId 资源id
	 * @return {@code Boolean}
	 */
	Boolean checkEffectsMapExist(@Param("attackType") String attackType,
			@Param("type") Integer type, @Param("resourceId") String resourceId);

	Boolean checkMapNameExist(@Param("name") String name, @Param("resourceId") String resourceId);

	/**
	 * 校验动效是否被使用
	 *
	 * @param effectIds 动效ID列表
	 * @return {@code Boolean}
	 */
	Boolean checkEffectsStatus(@Param("effectIds") List<String> effectIds);
}

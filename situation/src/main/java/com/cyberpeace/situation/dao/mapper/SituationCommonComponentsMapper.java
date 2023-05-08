package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;

/**
 * 通用组件映射器
 *
 * @Author xuxz
 * @Date 16/11/2022 下午5:12
 */
@Mapper
public interface SituationCommonComponentsMapper extends BaseMapper<SituationCommonComponents> {

	/**
	 * 分页查询
	 *
	 * @param page                             分页条件
	 * @param situationCommonComponentsPageReq 通用组件查询请求
	 * @return {@code IPage<SituationCommonComponentsPageRes>}
	 */
	IPage<SituationCommonComponentsPageRes> selectPage(@Param("page") Page page,
			@Param("situationCommonComponentsPageReq") SituationCommonComponentsPageReq situationCommonComponentsPageReq);

	/**
	 * 列表查询
	 *
	 * @param situationCommonComponentsPageReq 通用组件查询请求
	 * @return {@code List<SituationCommonComponentsPageRes>}
	 */
	List<SituationCommonComponentsPageRes> selectPages(
			@Param("situationCommonComponentsPageReq") SituationCommonComponentsPageReq situationCommonComponentsPageReq);

	/**
	 * 通过名称选择
	 *
	 * @param name 名称
	 * @return {@code List<SituationCommonComponents>}
	 */
	List<SituationCommonComponents> selectByName(@Param("name") String name);
}

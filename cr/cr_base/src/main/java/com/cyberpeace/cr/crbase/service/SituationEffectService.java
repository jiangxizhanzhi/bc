package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.service.context.IdListReq;
import com.cyberpeace.cr.crbase.service.context.effect.*;

/**
 * 态势动效Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface SituationEffectService {

	/**
	 * 列表类别
	 *
	 * @return {@code List<SituationEffectCategoryName> }
	 */
	List<SituationEffectCategoryName> listCategory();

	/**
	 * 创建类别
	 *
	 * @param req 请求体
	 */
	void createCategory(SituationEffectCategoryAddReq req);

	/**
	 * 修改类别
	 *
	 * @param req 请求体
	 */
	void modifyCategory(SituationEffectCategoryModifyReq req);

	/**
	 * 删除类别
	 *
	 * @param req 请求体
	 */
	void deleteCategory(IdListReq req);

	/**
	 * 创造动效
	 *
	 * @param req 请求体
	 */
	void createEffect(SituationEffectAddReq req);

	/**
	 * 修改动效
	 *
	 * @param req 请求体
	 */
	void modifyEffect(SituationEffectModifyReq req);

	/**
	 * 删除动效
	 *
	 * @param id id
	 */
	void deleteEffect(String id);

	/**
	 * 分页动效
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<SituationEffectBo> }
	 */
	ListRsp<SituationEffectBo> pageEffect(SituationEffectPageReq req);

	/**
	 * 分页类别
	 *
	 * @param reqBase 请求体基础
	 * @return {@code ListRsp<SituationEffectCategoryName> }
	 */
	ListRsp<SituationEffectCategoryName> pageCategory(PageReqBase reqBase);

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code SituationEffectBo }
	 */
	SituationEffectBo getDetail(String resourceId);

	/**
	 * 创造映射
	 *
	 * @param req 请求体
	 */
	void createMapping(SituationEffectMappingAddReq req);

	/**
	 * 获取映射详情
	 *
	 * @param resourceId 资源id
	 * @return {@code SituationEffectMappingBo }
	 */
	SituationEffectMappingBo getMappingDetail(String resourceId);

	/**
	 * 修改映射
	 *
	 * @param req 请求体
	 */
	void modifyMapping(SituationEffectMappingModifyReq req);

	/**
	 * 删除映射
	 *
	 * @param ids id列表
	 */
	void deleteMapping(List<String> ids);

	/**
	 * 分页映射
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<SituationEffectMappingBo> }
	 */
	ListRsp<SituationEffectMappingBo> pageMapping(PageReqBase req);
}

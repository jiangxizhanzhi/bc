package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffect;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffectCategory;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffectMapping;
import com.cyberpeace.cr.crbase.dao.mapper.SituationEffectCategoryMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationEffectMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationEffectMappingMapper;
import com.cyberpeace.cr.crbase.service.SituationEffectService;
import com.cyberpeace.cr.crbase.service.context.IdListReq;
import com.cyberpeace.cr.crbase.service.context.effect.*;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * 态势动效Service实现
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
@Slf4j
public class SituationEffectServiceImpl implements SituationEffectService {

	/**
	 * 态势动效Mapper
	 */
	@Autowired
	private SituationEffectMapper situationEffectMapper;

	/**
	 * 态势动效类别Mapper
	 */
	@Autowired
	private SituationEffectCategoryMapper situationEffectCategoryMapper;

	/**
	 * 态势动效映射Mapper
	 */
	@Autowired
	private SituationEffectMappingMapper situationEffectMappingMapper;

	/**
	 * 列表类别
	 *
	 * @return {@code List<SituationEffectCategoryName> }
	 */
	@Override
	public List<SituationEffectCategoryName> listCategory() {
		return situationEffectCategoryMapper.listAllName();
	}

	/**
	 * 创建类别
	 *
	 * @param req 请求体
	 */
	@Override
	public void createCategory(SituationEffectCategoryAddReq req) {
		if (ifCategoryNameDuplicate(req.getName(), "")) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		SituationEffectCategory category = new SituationEffectCategory();
		category.setName(req.getName());
		category.setResourceId(IDGeneratorUtil.getID());
		situationEffectCategoryMapper.insert(category);
	}

	/**
	 * 修改类别
	 *
	 * @param req 请求体
	 */
	@Override
	public void modifyCategory(SituationEffectCategoryModifyReq req) {
		if (ifCategoryNameDuplicate(req.getName(), req.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		SituationEffectCategory category = situationEffectCategoryMapper
				.selectById(req.getResourceId());
		category.setName(req.getName());
		situationEffectCategoryMapper.updateById(category);
	}

	/**
	 * 删除类别
	 *
	 * @param req 请求体
	 */
	@Override
	public void deleteCategory(IdListReq req) {
		situationEffectCategoryMapper.deleteBatchIds(req.getIds());
	}

	/**
	 * 创造动效
	 *
	 * @param req 请求体
	 */
	@Override
	public void createEffect(SituationEffectAddReq req) {
		if (ifEffectNameDuplicate(req.getName(), "")) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		SituationEffect effect = req2Entity(req);
		situationEffectMapper.insert(effect);
	}

	/**
	 * 修改动效
	 *
	 * @param req 请求体
	 */
	@Override
	public void modifyEffect(SituationEffectModifyReq req) {
		if (ifEffectNameDuplicate(req.getName(), req.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		SituationEffect effect = req2Entity(req);
		effect.setResourceId(req.getResourceId());
		situationEffectMapper.updateById(effect);
	}

	/**
	 * 删除动效
	 *
	 * @param id id
	 */
	@Override
	public void deleteEffect(String id) {
		situationEffectMapper.deleteById(id);
	}

	/**
	 * 分页动效
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<SituationEffectBo> }
	 */
	@Override
	public ListRsp<SituationEffectBo> pageEffect(SituationEffectPageReq req) {
		Page page = req.toPageParam();

		IPage<SituationEffectBo> result = situationEffectMapper.pageEffect(page,
				req.getCategoryId(), req.getPattern());

		return PageUtil.getListResult(result);
	}

	/**
	 * 分页类别
	 *
	 * @param reqBase 请求体基础
	 * @return {@code ListRsp<SituationEffectCategoryName> }
	 */
	@Override
	public ListRsp<SituationEffectCategoryName> pageCategory(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();

		IPage<SituationEffectBo> result = situationEffectCategoryMapper.page(page,
				reqBase.getPattern());

		return PageUtil.getListResult(result);
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code SituationEffectBo }
	 */
	@Override
	public SituationEffectBo getDetail(String resourceId) {
		SituationEffect effect = situationEffectMapper.selectById(resourceId);
		SituationEffectBo bo = BeanUtils.copy(effect, SituationEffectBo.class);
		return bo;
	}

	/**
	 * 创造映射
	 *
	 * @param req 请求体
	 */
	@Override
	public void createMapping(SituationEffectMappingAddReq req) {
		if (ifMappingNameDuplicate(req.getName(), "")) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		SituationEffectMapping mapping = mappingReq2Entity(req);
		mapping.setResourceId(IDGeneratorUtil.getID());
		situationEffectMappingMapper.insert(mapping);
	}

	/**
	 * 获取映射详情
	 *
	 * @param resourceId 资源id
	 * @return {@code SituationEffectMappingBo }
	 */
	@Override
	public SituationEffectMappingBo getMappingDetail(String resourceId) {
		SituationEffectMapping mapping = situationEffectMappingMapper.selectById(resourceId);
		SituationEffectMappingBo bo = BeanUtils.copy(mapping, SituationEffectMappingBo.class);
		return bo;
	}

	/**
	 * 修改映射
	 *
	 * @param req 请求体
	 */
	@Override
	public void modifyMapping(SituationEffectMappingModifyReq req) {
		if (ifMappingNameDuplicate(req.getName(), req.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		SituationEffectMapping mapping = mappingReq2Entity(req);
		mapping.setResourceId(req.getResourceId());
		situationEffectMappingMapper.updateById(mapping);
	}

	/**
	 * 删除映射
	 *
	 * @param ids id列表
	 */
	@Override
	public void deleteMapping(List<String> ids) {
		situationEffectMappingMapper.deleteBatchIds(ids);
	}

	/**
	 * 分页映射
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<SituationEffectMappingBo> }
	 */
	@Override
	public ListRsp<SituationEffectMappingBo> pageMapping(PageReqBase req) {
		Page page = req.toPageParam();

		IPage<SituationEffectBo> result = situationEffectMappingMapper.page(page, req.getPattern());

		return PageUtil.getListResult(result);
	}

	/**
	 * 映射要求2实体
	 *
	 * @param req 请求体
	 * @return {@code SituationEffectMapping }
	 */
	private SituationEffectMapping mappingReq2Entity(SituationEffectMappingAddReq req) {
		SituationEffectMapping mapping = new SituationEffectMapping();
		mapping.setName(req.getName());
		mapping.setRule(req.getRule());
		mapping.setSourceEffect(req.getSourceEffect());
		mapping.setTargetEffect(req.getTargetEffect());
		mapping.setFocus(req.getFocus());
		mapping.setColor(req.getColor());
		mapping.setLineType(req.getLineType());
		mapping.setLineWidth(req.getLineWidth());
		return mapping;
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return {@code SituationEffect }
	 */
	private SituationEffect req2Entity(SituationEffectAddReq req) {
		SituationEffect effect = new SituationEffect();
		effect.setName(req.getName());
		effect.setLogo(req.getLogo());
		effect.setCategoryId(req.getCategoryId());
		effect.setFile(req.getFile());
		return effect;
	}

	/**
	 * 如果类别名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	private boolean ifCategoryNameDuplicate(String name, String resourceId) {
		return situationEffectCategoryMapper.ifNameDuplicate(name, resourceId);
	}

	/**
	 * 如果动效名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	private boolean ifEffectNameDuplicate(String name, String resourceId) {
		return situationEffectMapper.ifNameDuplicate(name, resourceId);
	}

	/**
	 * 如果映射名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	private boolean ifMappingNameDuplicate(String name, String resourceId) {
		return situationEffectMappingMapper.ifNameDuplicate(name, resourceId);
	}
}

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
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffect;
import com.cyberpeace.cr.crbase.dao.entity.SituationEffectCategory;
import com.cyberpeace.cr.crbase.dao.mapper.SituationEffectCategoryMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationEffectMapper;
import com.cyberpeace.cr.crbase.service.SituationEffectService;
import com.cyberpeace.cr.crbase.service.context.IdListReq;
import com.cyberpeace.cr.crbase.service.context.effect.*;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SituationEffectServiceImpl implements SituationEffectService {

	@Autowired
	private SituationEffectMapper situationEffectMapper;

	@Autowired
	private SituationEffectCategoryMapper situationEffectCategoryMapper;

	@Override
	public List<SituationEffectCategoryName> listCategory() {
		return situationEffectCategoryMapper.listAllName();
	}

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

	@Override
	public void deleteCategory(IdListReq req) {
		situationEffectCategoryMapper.deleteBatchIds(req.getIds());
	}

	@Override
	public void createEffect(SituationEffectAddReq req) {
		if (ifEffectNameDuplicate(req.getName(), "")) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		SituationEffect effect = req2Entity(req);
		situationEffectMapper.insert(effect);
	}

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

	@Override
	public void deleteEffect(String id) {
		situationEffectMapper.deleteById(id);
	}

	@Override
	public ListRsp<SituationEffectBo> pageEffect(SituationEffectPageReq req) {
		Page page = req.toPageParam();

		IPage<SituationEffectBo> result = situationEffectMapper.pageEffect(page,
				req.getCategoryId(), req.getPattern());

		return PageUtil.getListResult(result);
	}

	@Override
	public ListRsp<SituationEffectCategoryName> pageCategory(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();

		IPage<SituationEffectBo> result = situationEffectCategoryMapper.page(page,
				reqBase.getPattern());

		return PageUtil.getListResult(result);
	}

	private SituationEffect req2Entity(SituationEffectAddReq req) {
		SituationEffect effect = new SituationEffect();
		effect.setName(req.getName());
		effect.setLogo(req.getLogo());
		effect.setCategoryId(req.getCategoryId());
		effect.setFile(req.getFile());
		return effect;
	}

	private boolean ifCategoryNameDuplicate(String name, String resourceId) {
		return situationEffectCategoryMapper.ifNameDuplicate(name, resourceId);
	}

	private boolean ifEffectNameDuplicate(String name, String resourceId) {
		return situationEffectMapper.ifNameDuplicate(name, resourceId);
	}
}

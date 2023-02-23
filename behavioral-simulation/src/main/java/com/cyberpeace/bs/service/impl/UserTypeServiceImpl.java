package com.cyberpeace.bs.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserType;
import com.cyberpeace.bs.dao.mapper.UserTypeMapper;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.user.UserTypeAddBo;
import com.cyberpeace.bs.service.bo.user.UserTypeBo;
import com.cyberpeace.bs.service.bo.user.UserTypeModifyBo;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	UserTypeMapper userTypeMapper;

	@Override
	public ListRsp<UserTypeBo> pageTypes(Page page, String search) {
		IPage<UserTypeBo> userTypeBoIPage = userTypeMapper.page(page, search);
		return PageUtil.getListResult(userTypeBoIPage);
	}

	@Override
	public void create(UserTypeAddBo addBo) {
		String name = StringUtils.strip(addBo.getName());
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		if (userTypeMapper.existByName(name, "")) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		UserType userType = reqToEntity(addBo.getName());
		userTypeMapper.insert(userType);
	}

	@Override
	public void update(UserTypeModifyBo modifyBo) {
		String name = StringUtils.strip(modifyBo.getName());
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		if (userTypeMapper.existByName(name, modifyBo.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		UserType userType = reqToEntity(modifyBo.getName());
		userType.setResourceId(modifyBo.getResourceId());
		userTypeMapper.updateById(userType);
	}

	@Override
	public void batchDelete(List<String> ids) {
		userTypeMapper.deleteBatchIds(ids);
	}

	@Override
	public Map<String, String> getIdNameMap(Collection<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return Maps.newHashMap();
		}
		List<UserType> userTypeList = userTypeMapper.selectBatchIds(ids);
		return StreamApiUtils.toMap(userTypeList, UserType::getResourceId, UserType::getName);
	}

	@Override
	public String getNameById(String resourceId) {
		if (StringUtils.isEmpty(resourceId)) {
			return CommonConstant.EMPTY_STRING;
		}
		UserType userType = userTypeMapper.selectById(resourceId);
		if (userType == null) {
			return CommonConstant.EMPTY_STRING;
		}
		return userType.getName();
	}

	private UserType reqToEntity(String name) {
		UserType userType = new UserType();
		userType.setName(name);
		return userType;
	}
}

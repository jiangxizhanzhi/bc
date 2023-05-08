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

/**
 * 用户类型Service实现
 *
 * @author SN
 * @date 2023/05/08
 */
@Service
@Slf4j
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	UserTypeMapper userTypeMapper;

	@Override
	public ListRsp<UserTypeBo> pageTypes(Page page, String search) {
		// 分页查询用户类型
		IPage<UserTypeBo> userTypeBoIPage = userTypeMapper.page(page, search);
		return PageUtil.getListResult(userTypeBoIPage);
	}

	@Override
	public void create(UserTypeAddBo addBo) {
		// strip，避免传入的文本左右的空格
		String name = StringUtils.strip(addBo.getName());
		// 校验长度
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		// 校验是否已存在同名类型
		if (userTypeMapper.existByName(name, "")) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		// 生成用户类型实例
		UserType userType = reqToEntity(name);
		// 插入数据库
		userTypeMapper.insert(userType);
	}

	@Override
	public void update(UserTypeModifyBo modifyBo) {
		// strip，避免用户输入的左右空格
		String name = StringUtils.strip(modifyBo.getName());
		// 校验长度
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		// 校验是否重名
		if (userTypeMapper.existByName(name, modifyBo.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		// 生成实例
		UserType userType = reqToEntity(name);
		userType.setResourceId(modifyBo.getResourceId());
		// 更新数据
		userTypeMapper.updateById(userType);
	}

	@Override
	public void batchDelete(List<String> ids) {
		// 根据id批量删除
		userTypeMapper.deleteBatchIds(ids);
	}

	@Override
	public Map<String, String> getIdNameMap(Collection<String> ids) {
		// id列表为空，返回空字典
		if (ids == null || ids.isEmpty()) {
			return Maps.newHashMap();
		}
		// 查询用户类型
		List<UserType> userTypeList = userTypeMapper.selectBatchIds(ids);
		return StreamApiUtils.toMap(userTypeList, UserType::getResourceId, UserType::getName);
	}

	@Override
	public String getNameById(String resourceId) {
		// id不存在，返回空字符串
		if (StringUtils.isEmpty(resourceId)) {
			return CommonConstant.EMPTY_STRING;
		}
		// 查询用户类型
		UserType userType = userTypeMapper.selectById(resourceId);
		// 查询不到
		if (userType == null) {
			return CommonConstant.EMPTY_STRING;
		}
		// 返回类型名称
		return userType.getName();
	}

	/**
	 * 请求体转换为实体
	 *
	 * @param name 名称
	 * @return {@code UserType}
	 */
	private UserType reqToEntity(String name) {
		UserType userType = new UserType();
		userType.setName(name);
		return userType;
	}
}

package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PracticeSetServiceImpl extends ServiceImpl<PracticeSetMapper, PracticeSet> implements PracticeSetService {

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Override
	public AdminPracticeSetBo selectById(String resourceId, AuthContext authContext) {

		// 无权限则无数据返回
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (!authIds.contains(resourceId)) {
			return null;
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(resourceId);
		if (null == practiceSet) {
			log.error("Practice set not found! practice_set_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return entityToSetBo(practiceSet, authContext);
	}

	@Override
	public void enableAccess(String resourceId, AuthContext authContext) {

		// 数据权限校验
		List<String> accessSetIds = practiceSetMapper.selectByAuthContext(authContext);

		if (CollectionUtils.isEmpty(accessSetIds) || !accessSetIds.contains(resourceId)) {
			log.error("This set not permission! practice_set_id=[{}]", resourceId);
			throw new InvalidParamException("非法操作！");
		}
	}

	/**
	 * 实体类数据转换
	 * @param practiceSet
	 * @return
	 */
	private AdminPracticeSetBo entityToSetBo(PracticeSet practiceSet, AuthContext authContext) {

		AdminPracticeSetBo setBo = BeanUtils.copy(practiceSet, AdminPracticeSetBo.class);
		UserInfo userInfo = remoteUserService.getById(practiceSet.getCreateUserId());
		setBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");

		if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.OPERATION.getCode()
				.intValue()) {
			List<String> practiceOperations = practiceOperationMapper.selectBySetIds(
					Arrays.asList(practiceSet.getResourceId()), authContext.getIsWeb());
			setBo.setPracticeCount(practiceOperations.size());
		} else {
			List<String> theoryIds = practiceTheoryMapper.selectBySetIds(
					Arrays.asList(practiceSet.getResourceId()), authContext.getIsWeb());
			setBo.setPracticeCount(theoryIds.size());
		}

		List<String> operationIds = JsonUtil.toJsonObjectArray(practiceSet.getOperationAuthIds(),
				String.class);
		setBo.setOperationCount(operationIds.size());

		List<String> readIds = JsonUtil.toJsonObjectArray(practiceSet.getReadAuthIds(),
				String.class);
		setBo.setReadCount(readIds.size());

		return setBo;
	}

	@Override
	public IPage<AdminPracticeSetBo> selectByTypeAndName(Page page, Integer practiceType,
			String pattern, AuthContext authContext) {

		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authIds)) {
			return null;
		}

		SetListQuery listQuery = SetListQuery.builder().practiceType(practiceType).setIds(authIds)
				.pattern(pattern).build();
		IPage<PracticeSet> iPage = practiceSetMapper.pageByTypeAndName(page, listQuery,
				authContext.getIsWeb());

		IPage<AdminPracticeSetBo> boIPage = iPage
				.convert(practiceSet -> entityToSetBo(practiceSet, authContext));
		return boIPage;
	}

	@Override
	public Boolean updateByAuth(PracticeSet practiceSet, AuthContext authContext) {

		List<String> permissionIds = practiceSetMapper.selectByAuthContext(authContext);
		// 权限校验
		if (CollectionUtils.isEmpty(permissionIds)
				|| !permissionIds.contains(practiceSet.getResourceId())) {
			log.error("This practice not permission! practice_set_id=[{}]",
					practiceSet.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		PracticeSet existSet = practiceSetMapper.selectById(practiceSet.getResourceId());
		// 名称校验
		if (StringUtils.isNotBlank(existSet.getName())) {
			String categoryId = practiceSetMapper.selectByName(existSet.getName());
			if (StringUtils.isNotBlank(categoryId)) {
				throw new InvalidParamException("习题集名称已存在！");
			}
		}

		// 类型校验 防止习题集类型改变
		if (null != practiceSet.getPracticeType()) {
			if (existSet.getPracticeType().intValue() != practiceSet.getPracticeType().intValue()) {
				throw new InvalidParamException("非法操作,习题集下存在不兼容类型习题！");
			}
		}

		return practiceSetMapper.updateById(practiceSet) > CommonConstant.ZERO;
	}

	@Override
	public void enableDelete(List<String> resourceIds, AuthContext authContext) {

		for (String setId : resourceIds) {
			// 权限校验
			enableAccess(setId, authContext);

			// 数据校验
			PracticeSet practiceSet = practiceSetMapper.selectById(setId);
			List<String> practiceIds = hasPractice(practiceSet, authContext);
			if (CollectionUtils.isNotEmpty(practiceIds)) {
				throw new InvalidParamException("习题集[" + practiceSet.getName() + "]下存在习题！");
			}
		}
	}

	/**
	 * 查询习题集下是否存在数据
	 * @param practiceSet
	 * @param authContext
	 * @return
	 */
	private List<String> hasPractice(PracticeSet practiceSet, AuthContext authContext) {

		if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.THEORY.getCode()
				.intValue()) {
			return practiceTheoryMapper.selectBySetIds(Arrays.asList(practiceSet.getResourceId()),
					authContext.getIsWeb());
		} else {
			return practiceOperationMapper.selectBySetIds(
					Arrays.asList(practiceSet.getResourceId()), authContext.getIsWeb());
		}

	}
}

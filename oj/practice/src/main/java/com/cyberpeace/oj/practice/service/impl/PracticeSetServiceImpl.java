package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeSetServiceImpl extends ServiceImpl<PracticeSetMapper, PracticeSet> implements PracticeSetService {

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public AdminPracticeSetBo selectById(String resourceId, AuthContext authContext) {

		// 无权限则无数据返回
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (!authIds.contains(resourceId)) {
			return null;
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(resourceId);
		if (null == practiceSet) {
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_NOT_FIND);
		}

		return entityToSetBo(practiceSet);
	}

	/**
	 * 实体类数据转换
	 * @param practiceSet
	 * @return
	 */
	private AdminPracticeSetBo entityToSetBo(PracticeSet practiceSet) {

		AdminPracticeSetBo setBo = BeanUtils.copy(practiceSet, AdminPracticeSetBo.class);
		UserInfo userInfo = remoteUserService.getById(practiceSet.getCreateUserId());
		setBo.setCreateUserName(userInfo.getUserName());

		// todo 习题数量查询

		return setBo;
	}

	@Override
	public IPage<AdminPracticeSetBo> selectByTypeAndName(Page page, Integer practiceType,
			String pattern, AuthContext authContext) {

		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		SetListQuery listQuery = SetListQuery.builder().practiceType(practiceType).setIds(authIds)
				.pattern(pattern).build();
		IPage<PracticeSet> iPage = practiceSetMapper.pageByTypeAndName(page, listQuery,
				authContext.getIsWeb());

		IPage<AdminPracticeSetBo> boIPage = iPage
				.convert(practiceSet -> entityToSetBo(practiceSet));
		return boIPage;
	}
}

package com.cyberpeace.oj.practice.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PracticeValidateHandler {

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 字段数据长度校验
	 * @param fieldContent
	 * @param maxLength
	 */
	public void validateFieldLength(String fieldContent, Integer maxLength) {
		if (fieldContent.length() > maxLength) {
			log.error("field length too long!");
			throw new BusinessException(OjBusinessErrorCode.FILED_DATA_TOO_LONG);
		}
	}

	/**
	 * 校验操作题是否存在
	 * @param practiceOperationId
	 * @return
	 */
	public PracticeOperation validateOperationExist(String practiceOperationId) {
		PracticeOperation operation = practiceOperationMapper.selectById(practiceOperationId);

		if (null == operation) {
			log.error("Practice Operation not found! practice_operation_id=[{}]",
					practiceOperationId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return operation;
	}

	/**
	 * 校验习题分类类型
	 * @param categoryId
	 * @param practiceType
	 * @return
	 */
	public PracticeCategory validateCategoryType(String categoryId, Integer practiceType) {
		PracticeCategory practiceCategory = validateCategoryExist(categoryId);

		if (practiceCategory.getPracticeType().intValue() != practiceType.intValue()) {
			log.error(
					"Practice Category type error! practice_category_id=[{}], category_type=[{}], practice_type=[{}]",
					categoryId, practiceCategory.getPracticeType(), practiceType);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_CATEGORY_TYPE_ERROR);
		}

		return practiceCategory;
	}

	/**
	 * 校验分类是否存在
	 * @param categoryId
	 * @return
	 */
	public PracticeCategory validateCategoryExist(String categoryId) {
		PracticeCategory category = practiceCategoryMapper.selectById(categoryId);
		if (null == category) {
			log.error("Practice category not found! practice_category_id=[{}]", categoryId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return category;
	}

	/**
	 * 校验习题集类型
	 * @param practiceSetId
	 * @param practiceType
	 * @return
	 */
	public PracticeSet validateSetType(String practiceSetId, Integer practiceType) {

		PracticeSet practiceSet = validatePracticeSetExist(practiceSetId);

		if (practiceSet.getPracticeType().intValue() != practiceType.intValue()) {
			log.error(
					"Practice set type error! practice_set_id=[{}], set_practice_type=[{}], practice_type=[{}]",
					practiceSetId, practiceSet.getPracticeType(), practiceType);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_TYPE_ERROR);
		}

		return practiceSet;
	}

	/**
	 * 校验习题集是否存在
	 * @return
	 */
	public PracticeSet validatePracticeSetExist(String practiceSetId) {
		PracticeSet practiceSet = practiceSetMapper.selectById(practiceSetId);
		if (null == practiceSet) {
			log.error("PracticeSet not found! practice_id=[{}]", practiceSetId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return practiceSet;
	}

	/**
	 * 习题集权限校验
	 * @param practiceSetId
	 * @param authContext
	 */
	public void validatePracticeSetPermission(String practiceSetId, AuthContext authContext) {

		List<String> permissionSets = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(permissionSets) || !permissionSets.contains(practiceSetId)) {
			log.error("Practice set not permission! practice_set_id=[{}]", practiceSetId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}
	}

	/**
	 * 校验习题名称
	 * @param practiceSetId
	 * @param title
	 */
	public void validateOperationTitleExist(String practiceSetId, String title) {
		String practiceId = practiceOperationMapper.selectByTitle(practiceSetId, title);
		if (StringUtils.isNotBlank(practiceId)) {
			log.error(
					"Practice operation title exist! practice_set_id=[{}], practice_id=[{}], title=[{}]",
					practiceSetId, practiceId, title);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 校验习题内容是否存在
	 * @param practiceSetId
	 * @param content
	 */
	public void validateTheoryContentExist(String practiceSetId, String content) {
		String practiceId = practiceTheoryMapper.selectByContent(practiceSetId, content);
		if (StringUtils.isNotBlank(practiceId)) {
			log.error("Practice theory content exist! practice_set_id=[{}], practice_id=[{}]",
					practiceSetId, practiceId);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}
}

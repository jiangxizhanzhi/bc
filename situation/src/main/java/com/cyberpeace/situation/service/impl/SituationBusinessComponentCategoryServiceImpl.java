package com.cyberpeace.situation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.controller.req.BusinessComponentCategoryReq;
import com.cyberpeace.situation.dao.entity.SituationBusinessComponentCategory;
import com.cyberpeace.situation.dao.mapper.SituationBusinessComponentCategoryMapper;
import com.cyberpeace.situation.service.SituationBusinessComponentCategoryService;

/**
 * 业务组件分类服务
 *
 * @author dell
 * @date 2023/06/20
 */
@Service
public class SituationBusinessComponentCategoryServiceImpl extends ServiceImpl<SituationBusinessComponentCategoryMapper, SituationBusinessComponentCategory> implements SituationBusinessComponentCategoryService {

	@Autowired
	SituationBusinessComponentCategoryMapper situationBusinessComponentCategoryMapper;

	/**
	 * 业务组件类别列表
	 *
	 * @param name 名字
	 * @return {@link List}<{@link SituationBusinessComponentCategory}>
	 */
	@Override
	public List<SituationBusinessComponentCategory> getBusinessComponentCategoryList(String name) {
		List<SituationBusinessComponentCategory> situationBusinessComponentCategories = situationBusinessComponentCategoryMapper
				.selectAll();

		List<SituationBusinessComponentCategory> list = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(situationBusinessComponentCategories)) {
			if (StringUtils.isEmpty(name)) {
				return situationBusinessComponentCategories;
			}
			situationBusinessComponentCategories.forEach(situationBusinessComponentCategory -> {
				if (situationBusinessComponentCategory.getName().contains(name)) {
					list.add(situationBusinessComponentCategory);
				}
			});
		}
		return list;
	}

	/**
	 * 更新业务组件分类类别
	 *
	 * @param businessComponentCategoryReq 业务组件类别请求
	 * @return {@link String}
	 */
	@Override
	public String updateBusinessComponentCategoryCategory(
			BusinessComponentCategoryReq businessComponentCategoryReq) {
		String name = businessComponentCategoryReq.getName();
		String resourceId = businessComponentCategoryReq.getResourceId();

		validateName(name, resourceId);

		SituationBusinessComponentCategory situationBusinessComponentCategory = changeReqToSituationBusinessComponentCategory(
				businessComponentCategoryReq);
		situationBusinessComponentCategoryMapper.updateById(situationBusinessComponentCategory);

		return businessComponentCategoryReq.getResourceId();
	}

	/**
	 * 转化业务组件类别请求
	 *
	 * @param businessComponentCategoryReq 业务组件类别请求
	 * @return {@link SituationBusinessComponentCategory}
	 */
	private SituationBusinessComponentCategory changeReqToSituationBusinessComponentCategory(
			BusinessComponentCategoryReq businessComponentCategoryReq) {

		return BeanUtils.copy(businessComponentCategoryReq,
				SituationBusinessComponentCategory.class);
	}

	/**
	 * 验证名字
	 *
	 * @param name       名字
	 * @param resourceId 资源id
	 */
	private void validateName(String name, String resourceId) {

		String selectResourceId = situationBusinessComponentCategoryMapper.selectByName(name);
		if (StringUtils.isNotBlank(resourceId)) {
			if (StringUtils.isNotBlank(selectResourceId)
					&& !Objects.equals(resourceId, selectResourceId)) {
				throw new BusinessException(ErrorCode.NAME_DUPLICATE);
			}
		} else {
			if (StringUtils.isNotBlank(selectResourceId)) {
				throw new BusinessException(ErrorCode.NAME_DUPLICATE);
			}
		}
	}

	/**
	 * 创建业务组件类别类别
	 *
	 * @param businessComponentCategoryReq 业务组件类别请求
	 * @return {@link String}
	 */
	@Override
	public String createBusinessComponentCategoryCategory(
			BusinessComponentCategoryReq businessComponentCategoryReq) {
		String name = businessComponentCategoryReq.getName();

		validateName(name, null);

		SituationBusinessComponentCategory situationBusinessComponentCategory = changeReqToSituationBusinessComponentCategory(
				businessComponentCategoryReq);
		situationBusinessComponentCategory.setResourceId(IDGeneratorUtil.getID());
		situationBusinessComponentCategoryMapper.insert(situationBusinessComponentCategory);

		return situationBusinessComponentCategory.getResourceId();
	}

	/**
	 * 获得业务组件类别
	 *
	 * @param resourceId 资源id
	 * @return {@link SituationBusinessComponentCategory}
	 */
	@Override
	public SituationBusinessComponentCategory getBusinessComponentCategory(String resourceId) {
		return situationBusinessComponentCategoryMapper.selectById(resourceId);
	}

	/**
	 * 删除业务组件分类类别
	 *
	 * @param idList id列表
	 * @return {@link Integer}
	 */
	@Override
	public Integer deleteBusinessComponentCategoryCategory(List<String> idList) {
		Integer delete = situationBusinessComponentCategoryMapper.deleteBatchIds(idList);

		return delete;
	}
}

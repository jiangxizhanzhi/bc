package com.cyberpeace.assess.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.assess.constant.AssessCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.dao.mapper.AssessTagMapper;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;

/**
 *
 */
@Service
public class AssessTagServiceImpl extends ServiceImpl<AssessTagMapper, AssessTag> implements AssessTagService {

	@Autowired
	AssessTagMapper assessTagMapper;

	@Override
	public ListRsp<AssessTag> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AssessTag> result = assessTagMapper.listAssessTag(page, pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}

	@Override
	public void checkTagIds(List<String> tags) {
		LambdaQueryWrapper<AssessTag> queryWrapper = new LambdaQueryWrapper<>();
		if (!tags.isEmpty()) {
			queryWrapper.in(AssessTag::getResourceId, tags);
		}
		List<AssessTag> assessTags = assessTagMapper.selectList(queryWrapper);
		if (assessTags.isEmpty() || tags.size() > assessTags.size()) {
			throw new BusinessException(AssessErrorCode.TAG_ID_ERROR);
		}
	}

	@Override
	public String getTagContent(String tags) {


//		List<String> tagList = JSONObject.parseArray(tags, String.class);
//		LambdaQueryWrapper<AssessTag> assessTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//		List<AssessTag> assessTags = assessTagMapper.selectList(assessTagLambdaQueryWrapper);
//
//		String tagStr = "";
//		List<String> tagList = JSONObject.parseArray(tags, String.class);
//		if (tagList.isEmpty()) {
//			return tagStr;
//		}
//		Map<String, String> tagMap = assessTags.stream()
//				.collect(Collectors.toMap(AssessTag::getResourceId, AssessTag::getContent));
//		StringBuffer tagNames = new StringBuffer();
//		for (String tmpType : tagList) {
//			if (null == tagMap.get(tmpType)) {
//				continue;
//			}
//			tagNames.append(tagMap.get(tmpType));
//			tagNames.append(",");
//		}
//		String str = tagNames.toString();
//		tagStr = StringUtils.isBlank(str) ? "" : str.substring(0, str.length() - 1);
		return tags;
	}

	@Override
	public void checkNameRepeat(String name, String id) {
		LambdaQueryWrapper<AssessTag> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AssessTag::getContent, name);
		if (StringUtils.isNotBlank(id)) {
			queryWrapper.ne(AssessTag::getResourceId, id);
		}
		AssessTag assessTag = assessTagMapper.selectOne(queryWrapper);
		if (null != assessTag) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

	@Override
	public void checkTag(List<String> originalTags) {
		List<String> tags = originalTags.stream().distinct().collect(Collectors.toList());
		if(originalTags.size() != tags.size()){
			throw new BusinessException(AssessErrorCode.TAG_REPEAT_ERROR);
		}
		if(tags.size() > AssessCommonConstants.TAG_MAX_SIZE){
			throw new BusinessException(AssessErrorCode.TAG_SIZE_OVER_MAX_ERROR);
		}
		if(!tags.isEmpty()){
			for(String tag:tags){
				CrCommonValidator.validateLength(tag, AssessCommonConstants.TAG_MIN_LENGTH, AssessCommonConstants.TAG_MAX_LENGTH,
						CrBusinessErrorCode.INVALID_STAGE_NAME);
			}
		}
	}
}

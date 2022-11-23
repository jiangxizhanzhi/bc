package com.cyberpeace.assess.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.dao.mapper.AssessTagMapper;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author dell
* @description 针对表【assess_case】的数据库操作Service实现
* @createDate 2022-11-21 13:52:10
*/
@Service
public class AssessCaseServiceImpl extends ServiceImpl<AssessCaseMapper, AssessCase>
implements AssessCaseService{

	@Autowired
	private AssessCaseMapper assessCaseMapper;

    @Autowired
    private AssessTagMapper assessTagMapper;

    @Override
	public String getTagContent(String tags) {
        LambdaQueryWrapper<AssessTag> assessTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        assessTagLambdaQueryWrapper.in(AssessTag::getResourceId, tags);
        List<AssessTag> assessTags = assessTagMapper.selectList(assessTagLambdaQueryWrapper);
        Map<String, String> tagMap = assessTags.stream().collect(Collectors.
                toMap(AssessTag::getResourceId, AssessTag::getContent));
		List<String> tagList = JSONObject.parseArray(tags, String.class);
		List<String> tagContents = assessTags.stream()
				.filter(assessTag -> tagList.contains(assessTag.getResourceId()))
				.map(AssessTag::getContent).collect(Collectors.toList());
		StringBuffer tagNames = new StringBuffer();
		for (String tmpType : tagContents) {
			if (null == tagMap.get(tmpType)) {
				continue;
			}
			tagNames.append(tagMap.get(tmpType));
			tagNames.append(",");
		}
		String str = tagNames.toString();
		String tagStr = StringUtils.isBlank(str) ? "" : str.substring(0, str.length() - 1);
		return tagStr;
	}

    @Override
    public AssessCase checkCaseId(String resourceId) {
		AssessCase assessCase = assessCaseMapper.selectById(resourceId);
		if(null == assessCase){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
    	return assessCase;
    }

    @Override
    public void checkNameRepeat(String name, String id) {
		LambdaQueryWrapper<AssessCase> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AssessCase::getName, name);
		if (StringUtils.isNotBlank(id)){
			queryWrapper.ne(AssessCase::getResourceId, id);
		}
		AssessCase assessCase = assessCaseMapper.selectOne(queryWrapper);
		if(null != assessCase){
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}
}

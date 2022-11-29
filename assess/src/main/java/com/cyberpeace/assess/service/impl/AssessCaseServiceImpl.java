package com.cyberpeace.assess.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.entity.AssessCaseMd;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.assess.dao.mapper.AssessTagMapper;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.assess.service.context.AssessCaselDumpContext;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private AssessMaterialMapper assessMaterialMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private AssessTagService assessCaseService;

    @Override
	public String getTagContent(String tags, List<AssessTag> assessTags) {
    	String tagStr = "";
		List<String> tagList = JSONObject.parseArray(tags, String.class);
		if(tagList.isEmpty()){
			return tagStr;
		}
        Map<String, String> tagMap = assessTags.stream().collect(Collectors.
                toMap(AssessTag::getResourceId, AssessTag::getContent));
		StringBuffer tagNames = new StringBuffer();
		for (String tmpType : tagList) {
			if (null == tagMap.get(tmpType)) {
				continue;
			}
			tagNames.append(tagMap.get(tmpType));
			tagNames.append(",");
		}
		String str = tagNames.toString();
		tagStr = StringUtils.isBlank(str) ? "" : str.substring(0, str.length() - 1);
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

    @Override
    public String makeDumpFile(String resourceId, AssessCase assessCase) throws IOException {
		// 1. make temp dir
		AssessCaselDumpContext context = dumpMaterial(resourceId);
		String assessCaseName = assessCase.getName();

		File folder = new File(AttachmentPath.ASSESS_DUMP.getDesc());
		if(!folder.isDirectory() || !folder.exists()){
			FileUtil.mkdirs(AttachmentPath.ASSESS_DUMP.getDesc());
		}
		String casePath = folder.getPath() + File.separator + assessCase.getResourceId();
		File caseFile = new File(casePath);
		if(!caseFile.isDirectory() || !caseFile.exists()){
			FileUtil.mkdirs(casePath);
		}
		String readmeMDFileName = casePath + File.separator+ assessCaseName +".md";
		MdParser mdParser = new MdParser();
		AssessCaseMd assessCaseMd = BeanUtils.copy(assessCase, AssessCaseMd.class);
		UserInfo userInfo = remoteUserService.getById(assessCase.getCreateUserId());
		assessCaseMd.setCreateUser(null == userInfo.getName() ? "" : userInfo.getName());
		assessCaseMd.setMaterialCount(String.valueOf(context.getAssessMaterialCount()));
		String tag = assessCase.getTag();
		String tagStr = assessCaseService.getTagContent(tag);
		assessCaseMd.setTag(tagStr);
		mdParser.write(assessCaseMd, new File(readmeMDFileName));
		Map<String, String> fileMap = context.getFiles();

		// 2. copy attachments
		for (String fileName : fileMap.values()) {
			String originPath = fileName;
			String[] split = fileName.split("\\/");
			if(split.length < 1){
				throw new BusinessException("Incorrect attachement path format");
			}
			String realFileName = split[split.length-1];

			String destPath = casePath + File.separator + realFileName;
			try {
				FileUtil.copyFile(originPath, destPath, false);
			} catch (Exception e) {
				log.error("error occurred when copying picture");
				throw new IOException(e);
			}
		}
		// 3. generate zip file, return zip file path
		return ZipUtil.toZip(casePath);
    }

	private AssessCaselDumpContext dumpMaterial(String resourceId) {


		LambdaQueryWrapper<AssessMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(AssessMaterial::getCaseId, resourceId);
		List<AssessMaterial> assessMaterials = assessMaterialMapper.selectList(lambdaQueryWrapper);
		Map<String, String> materialMap = assessMaterials.stream().collect(Collectors.toMap(AssessMaterial::getName, AssessMaterial::getAttachment));
		AssessCaselDumpContext assessCaselDumpContext = new AssessCaselDumpContext();
		assessCaselDumpContext.setFiles(materialMap);
		assessCaselDumpContext.setAssessMaterialCount(assessMaterials.size());
		return assessCaselDumpContext;
	}
}

package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionSchemeTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSchemeTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalSchemeTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalSchemeTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.StageTemplateData;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EvalSchemeTemplateServiceImpl extends ServiceImpl<EvalSchemeTemplateMapper, EvalSchemeTemplate> implements EvalSchemeTemplateService {

	@Autowired
	private EvalSchemeTemplateMapper evalSchemeTemplateMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	public static final String RESOURCE_FILE_NAME = "data.json";

	@Override
	public void addEvalSchemeTemplate(EvalSchemeTemplate evalSchemeTemplate) {
		evalSchemeTemplateMapper.insert(evalSchemeTemplate);
	}

	@Override
	public EvalSchemeTemplate getEvalSchemeTemplate(String resourceId) {
		EvalSchemeTemplate evalSchemeTemplate = evalSchemeTemplateMapper.selectById(resourceId);
		if (evalSchemeTemplate == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return evalSchemeTemplate;
	}

	@Override
	public void deleteEvalSchemeTemplate(List<String> ids) {
		evalSchemeTemplateMapper.deleteBatchIds(ids);
	}

	@Override
	public void updateEvalSchemeTemplate(EvalSchemeTemplate evalSchemeTemplate) {
		evalSchemeTemplateMapper.updateById(evalSchemeTemplate);
	}

	@Override
	public List<EvalMissionSchemeTemplateListRsp> getEvalSchemeTemplateList(PageReqBase pageReqBase) {
		List<EvalSchemeTemplate> evalSchemeTemplateList = evalSchemeTemplateMapper.getTemplateList(pageReqBase.getPattern());
		List<String> createUserIds = evalSchemeTemplateList.stream().map(EvalSchemeTemplate::getCreateUserId).distinct().collect(Collectors.toList());
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(createUserIds);
		return evalSchemeTemplateList.stream()
									 .map(evalSchemeTemplate -> {
												EvalMissionSchemeTemplateListRsp evalMissionSchemeTemplateListRsp = BeanUtils.copy(evalSchemeTemplate, EvalMissionSchemeTemplateListRsp.class);
												List<StageTemplateData> stageTemplateDataList = JsonUtil.toJsonObjectArray(evalSchemeTemplate.getTemplateData(), StageTemplateData.class);
												evalMissionSchemeTemplateListRsp.setStageCount(stageTemplateDataList.size());
												int taskCount = 0;
												for (StageTemplateData stageTemplateData : stageTemplateDataList) {
													taskCount += stageTemplateData.getTaskList().size();
												}
												evalMissionSchemeTemplateListRsp.setTaskCount(taskCount);
												evalMissionSchemeTemplateListRsp.setUpdateTime(TimeUtil.format1(evalSchemeTemplate.getUpdateTime()));
												evalMissionSchemeTemplateListRsp.setCreateUserName(idNameMap.get(evalSchemeTemplate.getCreateUserId()));
												return evalMissionSchemeTemplateListRsp;
											})
									 .sorted(Comparator.comparing(EvalMissionSchemeTemplateListRsp::getUpdateTime).reversed())
									 .collect(Collectors.toList());
	}



	@Override
	public String exportSchemeTemplate(List<String> resourceIds, String tempDir) throws IOException {
		if (CollectionUtils.isEmpty(resourceIds)) {
			log.error("Resource id list is empty!");
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		FileUtil.mkdirs(tempDir);
		List<EvalSchemeTemplate> evalSchemeTemplateList = evalSchemeTemplateMapper.selectBatchIds(resourceIds);
		String data = JsonUtil.toJsonString(evalSchemeTemplateList);
		String jsonPath = tempDir + File.separator + RESOURCE_FILE_NAME;
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
				StandardCharsets.UTF_8);
		FileUtil.writeString(data, osw);
		// 5. generate zip file, return zip file path
		return ZipUtil.toZip(tempDir);
	}

	@Override
	public void importSchemeTemplate(String unZipPath) {
		// 1. read data.json
		String dataJson = FileUtil.getContent(
				new File(unZipPath + File.separator + RESOURCE_FILE_NAME));
		List<EvalSchemeTemplate> evalSchemeTemplateList = JsonUtil.toJsonObjectArray(dataJson, EvalSchemeTemplate.class);
		for (EvalSchemeTemplate evalSchemeTemplate : evalSchemeTemplateList) {
			evalSchemeTemplate.setId(null);
			evalSchemeTemplate.setResourceId(null);
			evalSchemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
			evalSchemeTemplate.setCreateTime(null);
			evalSchemeTemplate.setUpdateTime(null);
			evalSchemeTemplateMapper.insert(evalSchemeTemplate);
		}
	}
}
